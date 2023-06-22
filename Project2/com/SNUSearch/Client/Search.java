package com.SNUSearch.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search { //검색을 하는 역할을 합니다. HttpClient 객체를 가지고 검색을 한 이후에 이 결과를 return 합니다.
    public String Search(String search){
        String regex = "[^a-zA-Z0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(search.split("%20")[0]);
        if(matcher.find())
            return null;
        String newSearch = search.replace("%20?", "&"); //적절한 편집을 통해 검색할 수 있는 형태로 input을 바꿉니다.
        HttpClient searchEngine = new HttpClient();
        return searchEngine.search(newSearch); //결과를 return 합니다.
    }
}
