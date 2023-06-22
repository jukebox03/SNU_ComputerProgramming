package com.SNUSearch.Data;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StartData { // login 전의 user가 접근 가능한 method들을 구현합니다.
    public boolean join(String id_s, String passwd){ //회원가입을 하는 method입니다.
        File directory = new File("data"); // 파일이 없다면 만들어 줍니다.
        if (!directory.exists()){
            directory.mkdir();
        }
        String id = id_s.toLowerCase(); //대소문자를 무시합니다.
        File txt = new File("data/member.txt");
        if(txt.exists()){
            try {
                BufferedReader reader = new BufferedReader(new FileReader("data/member.txt")); //기존의 memberlist를 보면서 같은 아이디가 있는지 확인합니다.
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (id.equals(line.split(" ")[0])) {
                        reader.close();
                        return false; //만일 있다면 회원가입을 실패합니다.
                    }
                }
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(passwd.length()<4) //길이가 적절한지
            return false;
        if(Character.isLetter(passwd.charAt(0)) == false) //첫글자가 알파벳인지
            return false;
        String regex = "[^a-zA-Z0-9@%]"; // 위 3가지를 통해 비밀번호로써 기준이 적합한지 확인합니다.
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passwd);
        if(matcher.find())
            return false; // 위 3가지를 통해 비밀번호로써 기준이 적합한지 확인합니다.
        String data = id+" "+passwd; //id passwd 형식으로 저장을 할 것입니다.
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("data/member.txt", true), true);
            writer.println(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public boolean login(String id, String passwd){ //login을 하는 method입니다.
        try {
            File txt = new File("data/member.txt");
            if(txt.exists() == false){
                return false;
            } //만일 계정이 하나라도 없다면 실패합니다.
            BufferedReader reader = new BufferedReader(new FileReader("data/member.txt"));
            String line = null;
            while((line = reader.readLine()) != null){
                if(id.equals(line.split(" ")[0]) && passwd.equals(line.split(" ")[1])){ //memberlist를 돌면서 같은 것이 있는지 확인합니다.
                    reader.close();
                    return true; //있다면 로그인합니다.
                }
            }
            reader.close();
            return false;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean leave(String id, String passwd){ //탈퇴하는 method입니다.
        try {
            File txt = new File("data/member.txt");
            if(txt.exists() == false){
                return false;
            } //회원이 없다면 탈퇴할 수 없습니다.
            BufferedReader reader = new BufferedReader(new FileReader("data/member.txt"));
            String line = null;
            ArrayList<String> lines = new ArrayList<String>();
            String delete = null;
            boolean isExist = false;

            while((line = reader.readLine()) != null){
                if((id.equals(line.split(" ")[0]) && passwd.equals(line.split(" ")[1])) == false) {
                    lines.add(line);
                }else{
                    isExist = true;//삭제하고자 하는 member가 있는지 확인합니다.
                    delete = line;
                }
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter("data/member.txt"), true); //그리고 member들을 다시 재작성합니다.
            for(String str : lines){
                System.out.println(str);
                writer.println(str);
            }
            writer.close();
            if(delete != null) {
                writer = new PrintWriter(new FileWriter("data/delete.txt", true), true); //또한, 삭제된 member 정보를 delete.txt에 저장합니다.
                writer.println(delete);
                writer.close();
            }
            if(isExist) return true;
            else return false;//마지막에 성공적으로 삭제를 했는지 안했는지 판단합니다.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean recover(String id, String passwd){ //삭제했던 member를 다시 복구하는 method입니다.
        try {
            File txt = new File("data/delete.txt");
            if(txt.exists() == false){
                return false;
            } //만일 삭제했던 member가 없다면 복구에도 실패합니다.
            BufferedReader reader = new BufferedReader(new FileReader("data/delete.txt"));
            String line = null;
            ArrayList<String> lines = new ArrayList<String>();
            String delete = null;
            boolean isExist = false;

            while((line = reader.readLine()) != null){
                if((id.equals(line.split(" ")[0]) && passwd.equals(line.split(" ")[1])) == false) { //삭제했던 애들을 읽으면서 일치하는 것이 있는지 확인합니다.
                    lines.add(line);
                }else{
                    isExist = true;
                    delete = line;
                }
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter("data/delete.txt"), true); //삭제 목록을 재작성합니다.
            for(String str : lines){
                System.out.println(str);
                writer.println(str);
            }
            writer.close();
            if(delete != null) {
                writer = new PrintWriter(new FileWriter("data/member.txt", true), true);//그리고 살아남은 계정의 목록도 재작성합니다.
                writer.println(delete);
                writer.close();
            }
            if(isExist) return true;
            else return false; //마지막에 성공 여부를 return합니다.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveLog(String line){
        try {
            File directory = new File("data");
            if (!directory.exists()){
                directory.mkdir();
            }

            PrintWriter writer= new PrintWriter(new FileWriter("data/log.txt", true), true);
            String new_line = "[NotUser]"+"request URL"+" "+line;
            writer.println(new_line);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void saveData(String id, String line){

    }
    public String loadData(String line){
        return null;
    }
    public String loadHot(){
        return null;
    }
    public String loadAcc(){
        return null;
    }
    public String loadLog(){
        return null;
    }
}
