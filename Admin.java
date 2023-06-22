import java.io.*;
import java.util.*;

public class Admin extends Member{
    ArrayList<Member> members =  new ArrayList<Member>();

    public Admin(String id, String password, ArrayList<Member> members) {
        super(id, password);
        this.members = members;
    }

    public void printInfo(){
        System.out.println("현재 등록된 모든 회원들의 정보입니다.");
        for(Member m: members){
            if(m.getId().equals("admin")==false)
                System.out.println(m);
        }
        System.out.println("전체 회원 수 : "+(members.size()-1));
        System.out.println("**********************" + "\n");
    }

    public void printWork(){
        System.out.println("원하시는 업무를 선택해 주세요.");
        System.out.println("1. 전체 회원 조회 2. 로그아웃");
        System.out.print("선택하기 : ");
    }
}
