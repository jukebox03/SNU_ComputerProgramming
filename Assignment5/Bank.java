import java.util.ArrayList;
import java.util.Scanner;
public class Bank{
    public static ArrayList<User> userList = new ArrayList<User>();

    public void addUser(String id, String pw, String accountNumber, int balance, boolean hasCard, int cash){
        User newUser = new User(id, pw, accountNumber, balance, hasCard, cash);
        userList.add(newUser);
    }
    public static User getUser(String id) throws UserNotFoundException {
        for(User i: userList){
            if(i.getId().equals(id))
                return i;
        }
        throw new UserNotFoundException();
    }
    public static User getUserByAccountNumber(String accountNumber) throws AccountNotFoundException {
        for(User i: userList){
            if(i.getAccountNumber().equals(accountNumber))
                return i;
        }
        throw new AccountNotFoundException();
    }

    public void login(){
        Scanner scan = new Scanner(System.in);
        String id = null;
        String pw = null;
        User temp = null;
        System.out.print("아이디를 입력하세요: ");
        id = scan.nextLine();
        try{
            temp = getUser(id);
            System.out.print("비밀번호를 입력하세요: ");
            pw = scan.nextLine();
            if(temp.getPw().equals(pw)==false)
                throw new InvalidPasswordException();

        }catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("로그인되었습니다.");
        int num = 0;
        System.out.println("1. 인출");
        System.out.println("2. 입금");
        System.out.println("3. 송금");
        System.out.println("4. 물건 구매");
        System.out.print("원하는 기능의 번호를 입력하세요: ");
        num = scan.nextInt();
        switch (num){
            case 1:
                try {
                    temp.withdraw();
                } catch (NegativeBalanceException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                try {
                    temp.deposit();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 3:
                try {
                    temp.transfer();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                try {
                    temp.purchase();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
        }
    }
}
