package com.SNUSearch.Server;

import com.SNUSearch.Client.Search;
import com.SNUSearch.Data.AdminData;
import com.SNUSearch.Data.StartData;
import com.SNUSearch.Data.UserData;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {
    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        public static StartData user = new StartData();
        public static Search search = new Search();
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                System.out.println("Received request: " + request);

                String[] requestParts = request.split(" ");
                String path = requestParts[1];
                user.saveLog(request);

                if (path.equals("/")) { //처음 실행시 해당되는 내용입니다.
                    String filePath = "src/index.html";
                    File file = new File(filePath);
                    if (file.exists() && file.isFile()) {
                        System.out.println(file.getAbsolutePath());
                        String contentType = Files.probeContentType(Paths.get(filePath));
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: " + contentType);
                        out.println("Content-Length: " + content.length());
                        out.println();
                        out.println(content);
                    } else {
                        out.println("HTTP/1.1 404 Not Found");
                        out.println();
                    }
                }else {
                    String content;
                    if(path.equals("/favicon.ico") == false){ //시작한 이후 user와 본격적으로 상호작용을 하는 부분입니다.
                        content = null;

                        path = path.replace("?", "/").replace("&", "/"); //편의를 위해 문양을 모두 바꾸었습니다.

                        String function = path.split("/")[1];
                        String instruction = path.split("/")[2]; //위 두변수를 통해 명령어의 종류를 구분합니다.
                        if(function.equals("user")){ //먼저 user에 해당하는 명령어의 경우입니다.
                            String id = path.split("/")[3].substring(3); // 이경우 id와
                            String passwd = path.split("/")[4].substring(7); //passwd 정보가 필요합니다.
                            switch (instruction){ //그 안에서 명령어를 구분합니다.
                                case "join": //회원가입을 하는 명령어입니다.
                                    if(user.join(id, passwd)){
                                        content = "Join Success";
                                        out.println("HTTP/1.1 200 OK");
                                    }else{
                                        content = "Error";
                                        out.println("HTTP/1.1 400");
                                    }
                                    out.println("Content-Type: text/plain");
                                    out.println("Content-Length: " + content.length());
                                    out.println();
                                    out.println(content);
                                    break;
                                case "login": //로그인을 하는 명령어입니다.
                                    if(user.login(id, passwd)){
                                        if(id.equals("admin"))
                                            user = new AdminData(id, passwd); //만일 관리자라면 관리자 계정을 주고
                                        else
                                            user = new UserData(id, passwd); //만일 일반 회원이라면 일반 회원 계정을 줍니다.
                                        content = "Login Success";
                                        out.println("HTTP/1.1 200 OK");
                                    }else{
                                        content = "Error";
                                        out.println("HTTP/1.1 400");
                                    }
                                    out.println("Content-Type: text/plain");
                                    out.println("Content-Length: " + content.length());
                                    out.println();
                                    out.println(content);
                                    break;
                                case "logout": //logout을 하는 명령어입니다.
                                    if(user instanceof UserData){
                                        user = new StartData(); //다시 회원가입을 하는 계정으로 돌아갑니다.
                                        content = "Logout Success";
                                        out.println("HTTP/1.1 200 OK");
                                    }else{
                                        content = "Wrong";
                                        out.println("HTTP/1.1 400");
                                    }
                                    out.println("Content-Type: text/plain");
                                    out.println("Content-Length: " + content.length());
                                    out.println();
                                    out.println(content);
                                    break;
                                case "leave": //탈퇴를 하는 명령어입니다.
                                    if(user.leave(id, passwd)){
                                        user = new StartData();
                                        content = "Leave Success";
                                        out.println("HTTP/1.1 200 OK");
                                    }else{
                                        content = "Wrong";
                                        out.println("HTTP/1.1 400");
                                    }
                                    out.println("Content-Type: text/plain");
                                    out.println("Content-Length: " + content.length());
                                    out.println();
                                    out.println(content);
                                    break;
                                case "recover": //회원 계정 복구를 하는 명령어입니다.
                                    if(user.recover(id, passwd)){
                                        content = "Recover Success";
                                        out.println("HTTP/1.1 200 OK");
                                    }else{
                                        content = "Wrong";
                                        out.println("HTTP/1.1 400");
                                    }
                                    out.println("Content-Type: text/plain");
                                    out.println("Content-Length: " + content.length());
                                    out.println();
                                    out.println(content);
                                    break;
                            }
                        }
                        else if(function.equals("data")){ //data에 해당하는 명령어입니다.
                            if(user instanceof UserData == false){ //만일 로그인을 안한 경우라면 접근할 수 없습니다.
                                content = "Login Please";
                                out.println("HTTP/1.1 400");
                                out.println("Content-Type: text/plain");
                                out.println("Content-Length: " + content.length());
                                out.println();
                                out.println(content);
                            }else{
                                String line = null;
                                String id = null;
                                switch (instruction){
                                    case "load_acc": //계정 정보를 불러오는 명령어입니다.
                                        if(user instanceof AdminData){ //관리자여야만 접근가능한 정보입니다.
                                            content = user.loadAcc();
                                            if(content == null)
                                                content = "NoData";
                                            out.println("HTTP/1.1 200 OK");
                                        }else{
                                            content = "Login Please";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                    case "load_log": //log를 출력하는 명령어입니다.
                                        if(user instanceof AdminData){ //마찬가지로 관리자여야만 접근가능한 정보입니다.
                                            content = user.loadLog();
                                            if(content == null)
                                                content = "NoData";
                                            out.println("HTTP/1.1 200 OK");
                                        }else{
                                            content = "Login Please";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                    case "search": //검색기능입니다.
                                        line = requestParts[1].split("/")[2].substring(9);
                                        line = line.replace(line.split("&")[line.split("&").length-1], "");
                                        line = line.substring(0, line.length()-1); //여기에서 검색어를 얻어내기 위해서는 다시 string을 잘 잘라야 합니다.
                                        System.out.println(line);
                                        if(user instanceof UserData){ //회원이어야 합니다.
                                            content = search.Search(line);
                                            if(content == null) { //만일 결과가 없다면 잘못된 검색 형식을 준 것일 것입니다.
                                                content = "Wrong";
                                                out.println("HTTP/1.1 400");
                                            }else {
                                                out.println("HTTP/1.1 200 OK");
                                            }
                                        }else{
                                            content = "Login Please";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                    case "save_data": //정보를 저장합니다.
                                        line = path.split("/")[3].substring(2); //저장할 정보와
                                        id = path.split("/")[4].substring(5); //위치를 알아내고
                                        if(user instanceof UserData){
                                            user.saveData(id, line); //저장합니다.
                                            content = "Save";
                                            out.println("HTTP/1.1 200 OK");
                                        }else{
                                            content = "Wrong";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                    case "load_data": //자신의 저장된 검색어를 출력합니다.
                                        id = path.split("/")[4].substring(5);
                                        if(user instanceof UserData){
                                            content = user.loadData(id);
                                            if(content == null) //만일 없다면 data가 없음을 출력합니다.
                                                content = "NoData";
                                            out.println("HTTP/1.1 200 OK");
                                        }else{
                                            content = "Wrong";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                    case "load_fri": //친구의 저장된 검색어를 출력합니다.
                                        id = path.split("/")[3].substring(2);
                                        if(user instanceof UserData){
                                            content = user.loadData(id);
                                            if(content == null)
                                                content = "NoData"; //만일 없다면 data가 없음을 출력합니다.
                                            out.println("HTTP/1.1 200 OK");
                                        }else{
                                            content = "Wrong";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                    case "load_hot": //가장 핫한 검색어를 출력합니다.
                                        id = path.split("/")[4].substring(5);
                                        if(user instanceof UserData){
                                            content = user.loadHot();
                                            if(content == null) //만일 없다면 data가 없음을 출력합니다.
                                                content = "NoData";
                                            out.println("HTTP/1.1 200 OK");
                                        }else{
                                            content = "Wrong";
                                            out.println("HTTP/1.1 400");
                                        }
                                        break;
                                }
                            }
                        }
                    }else {
                        content = "Hello";
                        out.println("HTTP/1.1 200");
                    }
                    out.println("Content-Type: text/plain");
                    out.println("Content-Length: " + content.length());
                    out.println();
                    out.println(content);
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
