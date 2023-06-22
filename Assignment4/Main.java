import java.io.*;
import java.util.*;

public class Main {
    public static void main(String args[])
    {
        Scanner in = new Scanner(System.in);
        Login login = new Login();
        boolean isLogin = false;
        boolean isAdmin = false;
        System.out.println("어서오세요. 간단한 로그인 프로그램입니다.");
        while(true){
            if(isLogin==false) {
                System.out.println("원하시는 업무를 선택해 주세요.");
                System.out.println("1. 로그인 2. 회원가입 3. 종료");
                System.out.print("선택하기  : ");

                String oper = in.nextLine();

                switch (oper) {
                    case "1":
                        System.out.println("**********************" + "\n");
                        if(login.Login()) {
                            isLogin = true;
                            if(login.member.getId().equals("admin"))
                                isAdmin = true;
                        }
                        break;
                    case "2":
                        System.out.println("**********************" + "\n");
                        login.Signup();
                        break;
                    case "3":
                        System.out.println("**********************" + "\n");
                        System.out.println("**********************"+"\n");
                        System.out.println("로그인 프로그램을 종료합니다.");
                        System.out.println("**********************"+"\n");
                        return;
                    default:
                        System.out.println("잘못 입력하셨습니다. 1 ~ 3 숫자를 입력해 주세요.");
                        System.out.println("**********************" + "\n");
                        break;
                }
            }else{
                System.out.println(login.member.getId()+" 계정으로 로그인 되었습니다.");
                if(login.member!=null)
                    login.member.printWork();
                String oper = in.nextLine();
                System.out.println("**********************" + "\n");
                switch (oper){
                    case "1":
                        if(isAdmin) login.member.printInfo();
                        else {
                            login.Delete();
                            isLogin = false;
                            isAdmin = false;
                        }
                        break;
                    case "2":
                        login.Logout();
                        isLogin = false;
                        isAdmin = false;
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다. 1 ~ 2 숫자를 입력해 주세요.");
                        System.out.println("**********************" + "\n");
                        break;
                }
            }
        }
    }
}
