package com.SNUSearch.Data;

import java.io.*;
import java.util.*;

public class UserData extends StartData implements SearchData { //일반적인 사용자가 접근가능한 method입니다.
    public String id = null;
    public String passwd = null;

    public UserData(String id, String passwd){
        this.id = id;
        this.passwd = passwd;
    }

    public void saveData(String id, String line){ //원하는 검색어를 저장합니다.
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(String.format("data/%s.txt", id), true), true); //각자의 저장공간에도 저장해야 합니다.
            PrintWriter writer_t = new PrintWriter(new FileWriter("data/total.txt", true), true); //또한, 전체 검색어 저장공간에도 저장을 해야합니다.
            writer.println(line);
            writer_t.println(line);
            writer.close();
            writer_t.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String loadData(String id){ //원하는 id의 저장내용을 출력합니다.
        ArrayList<String> search = new ArrayList<String>();
        try {
            File file = new File(String.format("data/%s.txt", id));
            if (!file.exists()){
                file.createNewFile();
            }

            String lines = null;
            BufferedReader reader = new BufferedReader(new FileReader(String.format("data/%s.txt", id))); //해당되는 id의 저장공간에 접근한 이후에 내용을 읽어옵니다.
            String line = null;
            while((line = reader.readLine()) != null){
                if(lines == null)
                    lines =line + "\n";
                else
                    lines = lines + line + "\n";
            }
            reader.close();
            return lines; //내용을 string에 저장한 이후에 return합니다.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String loadHot() { //전체 검색어 중에서 가장 많이 검색된 검색어를 return합니다.
        ArrayList<String> search = new ArrayList<String>();
        try {
            File file = new File("data/total.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader("data/total.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                search.add(line);
            }
            reader.close();
            Map<String, Integer> map = new HashMap<String, Integer>(); //map을 이용해서 빈도를 확인하고 정렬할 것입니다.
            for (String str : search) {
                Integer count = map.get(str);
                if (count == null) {
                    map.put(str, 1);
                } else {
                    map.put(str, count + 1);
                }
            }
            List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());
            Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                    return entry2.getValue().compareTo(entry1.getValue());
                }
            });
            Map<String, Integer> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<String, Integer> entry : entryList) {
                sortedMap.put(entry.getKey(), entry.getValue());
            } //정렬을 모두 완료한 후에 key값을 얻을 것입니다.
            search.clear();
            line = null;
            for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
                if(search.size() < 10) { //최대 10개만 출력합니다.
                    search.add(entry.getKey());
                    if(line == null)
                        line = entry.getKey() + "\n";
                    else
                        line = line + entry.getKey() + "\n";
                }
            }
            return line; //결과를 string 형태로 return 합니다.
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveLog(String line){ //각 명령어를 실행할 때마다 명령어를 저장합니다.
        try {
            PrintWriter writer= new PrintWriter(new FileWriter("data/log.txt", true), true);
            String new_line = "["+id+"]"+"request URL"+" "+line; //id와 함께 저장합니다.
            writer.println(new_line);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}