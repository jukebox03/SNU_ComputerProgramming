package com.SNUSearch.Data;

import java.io.*;

public class AdminData extends UserData { //admin에 해당하는 user가 접근할 수 있는 method들을 구현합니다.
    public AdminData(String id, String passwd){
        super(id, passwd);
    }
    public String loadAcc(){ //member들의 정보를 출력합니다.
        try {
            String lines = null;
            BufferedReader reader = new BufferedReader(new FileReader("data/member.txt")); //member들의 정보는 member.txt에 저장이 되어있습니다.
            String line = null;
            while((line = reader.readLine()) != null){
                line = line.split(" ")[0];
                if(lines == null)
                    lines =line + "\n";
                else
                    lines = lines + line + "\n";
            }// 순서대로 읽어오면서 id 부분만 lines에 저장합니다.
            reader.close();
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String loadLog(){ //사용자들의 활동내역을 출력합니다.
        try {
            String lines = null;
            BufferedReader reader = new BufferedReader(new FileReader("data/log.txt")); //사용자들의 활동 내역은 log.txt에 저장되어 있습니다.
            String line = null;
            while((line = reader.readLine()) != null){
                if(lines == null)
                    lines = line + "\n";
                else lines = lines + line + "\n";
            }// 순서대로 읽어오면서 lines에 저장합니다.
            reader.close();
            return lines;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
