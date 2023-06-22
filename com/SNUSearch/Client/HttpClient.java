package com.SNUSearch.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.*;


public class HttpClient {
    public String search(String search) {
        String url = String.format("https://www.googleapis.com/customsearch/v1?key=AIzaSyDVLulsHjuFZRKy8DJh2hj79BQE89ivkMA&cx=6271b25dd4bda44eb&q=%s", search); // 검색할 내용을 검색엔진 정보 등과 함께 string으로 저장한다.
        String result = null;
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //내용을 읽는 buffer이다. 이를 통해 검색 결과를 읽을 수 있다.
            String line; //검색 결과를 받아오는데 사용하는 임시 string 변수이다.
            StringBuilder response = new StringBuilder(); // 이 곳에 순서대로 결과를 저장한다.
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();
            JsonArray itemsArray = jsonObject.getAsJsonArray("items"); //item에 해당하는 내용을 array로 받습니다.
            JsonObject queries = jsonObject.getAsJsonObject("queries"); //queries에 해당하는 내용을 Jsonobject로 받습니다.
            JsonArray queriesArray = queries.getAsJsonArray("request"); //request에 해당하는 내용을 array로 받습니다.

            List<SearchResult> searchResults = new ArrayList<>(); //검색결과를 저장합니다.

            for(JsonElement element: itemsArray){
                JsonObject itemObject = element.getAsJsonObject();
                String title = itemObject.get("title").getAsString();
                String link = itemObject.get("link").getAsString();
                SearchResult searchResult = new SearchResult(title, link);
                searchResults.add(searchResult);
            }

            List<QueriesResult> queriesInfo = new ArrayList<>(); //검색결과를 저장합니다.

            for(JsonElement element: queriesArray){
                JsonObject itemObject = element.getAsJsonObject();
                String title = itemObject.get("searchTerms").getAsString();
                String link = itemObject.get("totalResults").getAsString();
                QueriesResult queriesResult = new QueriesResult(title, link);
                queriesInfo.add(queriesResult);
            }

            System.out.println("Response Code: " + responseCode); //검색결과를 출력합니다.
            System.out.println("Response Body:");
            System.out.println(response.toString());

            connection.disconnect();
            result = "SearchTerm: "+queriesInfo.get(0).getSearchTerms()+"\n"+"TotalResults: "+queriesInfo.get(0).getTotalResults()+"\n\n"+"1."+searchResults.get(0).getTitle()+" "+searchResults.get(0).getLink()+"\n\n"+"2."+searchResults.get(1).getTitle()+" "+searchResults.get(1).getLink()+"\n\n"+"3."+searchResults.get(2).getTitle()+" "+searchResults.get(2).getLink(); //결과물을 보기 좋게 정리하여 string으로 만들어서 전달합니다.
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

class SearchResult { // 검색 내용을 저장할수 있는 새로운 class입니다. 이곳에 저장한 이후에 string으로 return합니다.
    private String title;
    private String link;

    public SearchResult(String title, String link) {
        this.title = title;
        this.link = link;
    }
    public String getTitle() {
        return title;
    }
    public String getLink() {
        return link;
    }
}

class QueriesResult { // 검색 내용을 저장할수 있는 새로운 class입니다. 이곳에 저장한 이후에 string으로 return합니다.
    private String searchTerms;
    private String totalResults;

    public QueriesResult(String searchTerms, String totalResults) {
        this.searchTerms = searchTerms;
        this.totalResults = totalResults;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public String getTotalResults() {
        return totalResults;
    }
}