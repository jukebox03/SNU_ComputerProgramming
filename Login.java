import java.io.*;
import java.util.*;

public class Login {
    Member member;
    ArrayList<Member> members;

    public Login(){
        members = new ArrayList<Member>();
        Admin admin = new Admin("admin", "admin", members);
        members.add(admin);
        member = null;
    }

    public void Signup(){
        Scanner in = new Scanner(System.in);
        String tempId = null;
        String tempPassword = null;
        System.out.println("회원가입을 진행합니다.");
        System.out.println("** 주의 ** 아이디와 비밀번호는 알파벳 대소문자와 숫자로만 이뤄져야 합니다.");
        System.out.println("** 주의 ** 아이디는 대소문자를 구분하지 않습니다.");
        System.out.println("** 주의 ** 비밀번호는 10자 이상이여야 합니다.");
        while(tempId==null||optId(tempId)==false){
            System.out.print("아이디를 입력하세요 : ");
            tempId = in.nextLine();
            tempId = tempId.toLowerCase();
        }
        while(tempPassword==null||optPassword(tempPassword)==false){
            System.out.print("비밀번호를 입력하세요 : ");
            tempPassword = in.nextLine();
            tempPassword = tempPassword.toLowerCase();
        }

        System.out.println(tempId+"님 회원 가입을 축하드립니다.");
        System.out.println("**********************"+"\n");
        Member newMember = new Member(tempId, tempPassword);
        members.add(newMember);
    }

    public boolean existId(String tempId){
        for(Member m: members){
            if(m.getId().equals(tempId))
                return true;
        }
        return false;
    }

    public boolean wrongChar(String tempId){
        if (!tempId.matches("[0-9|a-z|A-Z]*")) {
            return true;
        }
        return false;
    }

    public boolean optId(String tempId){
        if(existId(tempId)){
            System.out.println("이미 존재하는 회원 아이디입니다.");
            System.out.println("다시 입력해주세요.");
            return false;
        }
        if(wrongChar(tempId)){
            System.out.println("특수 문자는 포함할 수 없습니다.");
            System.out.println("알파벳 대소문자와 숫자로만 입력해주세요.");
            System.out.println("다시 입력해주세요.");
            return false;
        }
        return true;
    }

    public boolean optPassword(String tempPassword){
        if(tempPassword.length()<10){
            System.out.println("길이가 너무 짧습니다.");
        }
        if(wrongChar(tempPassword)){
            System.out.println("특수 문자는 포함할 수 없습니다.");
            System.out.println("알파벳 대소문자와 숫자로만 입력해주세요.");
        }

        if(wrongChar(tempPassword)||tempPassword.length()<10){
            System.out.println();
            return false;
        }
        return true;
    }

    public boolean Login(){
        Scanner in = new Scanner(System.in);
        String tempId = null;
        String tempPassword = null;
        System.out.print("아이디를 입력하세요 : ");
        tempId = in.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        tempPassword = in.nextLine();

        if(existId(tempId)==false){
            System.out.println("존재하지 않는 회원 아이디입니다.");
            System.out.println("회원가입을 진행해 주시길 바랍니다.");
            System.out.println("**********************"+"\n");
            return false;
        }

        for(Member m: members){
            if(m.getId().equals(tempId)){
                if(m.getPassword().equals(tempPassword)==false){
                    System.out.println("비밀 번호가 일치하지 않습니다.");
                    System.out.println("**********************"+"\n");
                    return false;
                }else{
                    member = m;
                    System.out.println("**********************"+"\n");
                    return true;
                }
            }
        }
        System.out.println("존재하지 않는 회원 아이디입니다.");
        System.out.println("회원가입을 진행해 주시길 바랍니다.");
        System.out.println("**********************"+"\n");
        return false;
    }

    public void Logout(){
        member = null;
        System.out.println("로그아웃 되었습니다.");
        System.out.println("**********************"+"\n");
        System.out.println("**********************"+"\n");
    }

    public void Delete(){
        members.remove(member);
        member = null;
        System.out.println("탈퇴가 완료되었습니다.");
        System.out.println("이용해 주셔서 감사합니다.");
        System.out.println("**********************"+"\n");
        System.out.println("**********************"+"\n");
    }
}
