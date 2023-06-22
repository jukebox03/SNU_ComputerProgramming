import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        Bank bank = new Bank();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/data.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.split("\\|")[8].equals("y"))
                    bank.addUser(line.split("\\|")[0], line.split("\\|")[2], line.split("\\|")[4], Integer.parseInt(line.split("\\|")[6]), true, Integer.parseInt(line.split("\\|")[10]));
                else
                    bank.addUser(line.split("\\|")[0], line.split("\\|")[2], line.split("\\|")[4], Integer.parseInt(line.split("\\|")[6]), false, Integer.parseInt(line.split("\\|")[10]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bank.login();
    }
}

class UserNotFoundException extends Exception{
    public UserNotFoundException(){super("존재하지 않는 회원입니다.");}
}
class InvalidPasswordException extends Exception{
    public InvalidPasswordException(){super("잘못된 비밀번호입니다.");}
}
class NegativeBalanceException extends Exception{
    public NegativeBalanceException(String message){super(message);}
}
class NegativeCashException extends Exception{
    public NegativeCashException(String message){super(message);}
}
class AccountNotFoundException extends Exception{
    public AccountNotFoundException(){super("해당 계좌는 없습니다.");}
}
