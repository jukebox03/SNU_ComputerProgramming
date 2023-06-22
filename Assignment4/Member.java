import java.io.*;
import java.util.*;

public class Member {
    private String id;
    private String password;

    public Member(String id, String password){
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String toString(){
        return "id : "+id+" pw : "+password;
    }

    public void printWork(){
        System.out.println("원하시는 업무를 선택해 주세요.");
        System.out.println("1. 탈퇴하기 2. 로그아웃");
        System.out.print("선택하기 : ");
    }

    public void printInfo() {
    }
}
