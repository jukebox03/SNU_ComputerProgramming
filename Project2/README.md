## CP Final Project
- ### 구현 내용
<br>
 12개 기능 모두 구현하였습니다.
<br>

- ### 가장 어려웠던 기능 및 접근 방법
<br>
 과제를 하면서 가장 시간을 오래 사용했던 기능은 Google에서 받아온 JSON 형식의 검색 결과를 parsing하는 일이었습니다. 
Google에서 제공하는 JSON 파일이 복잡하였기 때문에 한번에 쉽게 파싱하기 어려웠습니다. 

 본 과제를 수행하면서 GSON 이라는 외부 라이브러리를 사용하였습니다. 구조가 복잡하기 때문에 JsonObject를 받고 다시 여기서 원하는 성분을 뽑는 방법으로 JSON을 parsing 하였습니다. 

 본 과제에서 필요한 데이터는 다음과 같습니다. 

 queries.request[0].searchTerms

 queries.request[0].totalResults

 items[0].tile, items[0].link (item[1], item[2] 또한 동일한 정보를 포함한다.)

 이때, 한 층으로 원하는 정보를 얻을 수 없음을 알 수 있다. 특히, queries.request[0].searchTerms 와 같은 항의 경우, 여러번 JSON 을 따라 안으로 들어가야 나오게 된다.

 아래는 parsing을 한 코드입니다. 

```
    List<SearchResult> searchResults = new ArrayList<>();
    Gson gson = new Gson();
    JsonParser parser = new JsonParser();
    JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();
    JsonArray itemsArray = jsonObject.getAsJsonArray("items");
    JsonObject queries = jsonObject.getAsJsonObject("queries");
    JsonArray queriesArray = queries.getAsJsonArray("request");

    for(JsonElement element: itemsArray){
        JsonObject itemObject = element.getAsJsonObject();
        String title = itemObject.get("title").getAsString();
        String link = itemObject.get("link").getAsString();
        SearchResult searchResult = new SearchResult(title, link);
        searchResults.add(searchResult);
    }
    
    List<QueriesResult> queriesInfo = new ArrayList<>();

    for(JsonElement element: queriesArray){
        JsonObject itemObject = element.getAsJsonObject();
        String title = itemObject.get("searchTerms").getAsString();
        String link = itemObject.get("totalResults").getAsString();
        QueriesResult queriesResult = new QueriesResult(title, link);
        queriesInfo.add(queriesResult);
    }
```
 또한, txt 파일로 data를 저장하였습니다. 이 경우, 원하는 내용을 삭제하거나 수정하는 것이 어려웠습니다. 해당 과제는 수정이나 삭제가 필요한 경우, 모든 string을 읽어온 후, 삭제 및 수정을 완료한 상태에서 다시 txt 파일을 만들었습니다. 

 이러한 구현방식의 경우, 효율적이지 않다고 생각합니다. 더 복잡한 backend 프로그램을 짠다면 sql 등을 활용하는 것이 좋을 것이라고 생각합니다. 

 아래는 파일을 수정하는 코드의 예시입니다. 

```
 BufferedReader reader = new BufferedReader(new FileReader("data/member.txt"));
            String line = null;
            ArrayList<String> lines = new ArrayList<String>();
            String delete = null;
            boolean isExist = false;

            while((line = reader.readLine()) != null){
                if((id.equals(line.split(" ")[0]) && passwd.equals(line.split(" ")[1])) == false) {
                    lines.add(line);
                }else{
                    isExist = true;
                    delete = line;
                }
            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter("data/member.txt"), true);
            for(String str : lines){
                System.out.println(str);
                writer.println(str);
            }
            writer.close();
            if(delete != null) {
                writer = new PrintWriter(new FileWriter("data/delete.txt", true), true);
                writer.println(delete);
                writer.close();
            }
 ```
- ### 느낀점
 본 과제를 수행하면서 Computer Programming 수업시간에 배운 File I/O, 외부 라이브러리 사용, exception throw 등을 실습할 수 있었습니다. 
 과제 전반적으로 수업시간에 다룬 내용을 모두 사용하는 좋은 과제였다고 생각합니다. 이러한 점이 유익하였습니다. 

 아쉬운 점은 html을 수정할 수 있었으면 더 많은 기능을 만들 수 있었다고 생각합니다. 예를 들어서, 에러가 난 것에 대해서 메세지를 출력하지 않기 때문에 사용자로 하여금 어떤 것이 문제인 것인지 알 수 있는 방법이 없습니다. 
 html을 수정함으로써 error가 발생할 경우에도 적절한 메세지를 띄울 수 있었으면 좋겠습니다.

 Server를 만들면서 더 다양한 예외를 처리할 수 있었으면 좋을 것 같습니다. 

<br>