import java.util.Scanner;
public class User{
    private String id = null;
    private String pw = null;
    private String accountNumber = null;
    private int balance = 0;
    private boolean hasCard = false;
    private int cash = 0;

    public String getId() {
        return id;
    }
    public String getPw() {
        return pw;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public int getBalance() {
        return balance;
    }
    public boolean isHasCard() {
        return hasCard;
    }
    public int getCash() {
        return cash;
    }

    public void addBalance(int amount) {
        balance += amount;
    }
    public void rmvBalance(int amount) {
        balance -= amount;
    }

    public User(String id, String pw, String accountNumber, int balance, boolean hasCard, int cash) {
        this.id = id;
        this.pw = pw;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.hasCard = hasCard;
        this.cash = cash;
    }

    public void withdraw() throws NegativeBalanceException {
        Scanner scan = new Scanner(System.in);
        int amount = 0;

        System.out.print("인출할 금액을 입력하세요: ");
        amount = scan.nextInt();
        if(amount<0)
            throw new NegativeBalanceException("잘못된 금액");
        else if(balance-amount<0)
            throw new NegativeBalanceException("잔고 부족");
        else {
            balance -= amount;
            cash += amount;
            System.out.println(id+"님 "+amount+"원이 인출되었습니다.");
            System.out.println("잔고: "+balance);
            System.out.println("현금: "+cash);
        }
    }
    public void deposit() throws Exception {
        Scanner scan = new Scanner(System.in);
        int amount = 0;

        System.out.print("입금할 금액을 입력하세요: ");
        amount = scan.nextInt();
        if(amount<0)
            throw new NegativeBalanceException("음수 입금액");
        else if(cash-amount<0)
            throw new NegativeCashException("현금 부족");
        else {
            balance += amount;
            cash -= amount;
            System.out.println(id+"님 "+amount+"원이 입금되었습니다.");
            System.out.println("잔고: "+balance);
        }
    }
    public void transfer() throws Exception {
        Scanner scan = new Scanner(System.in);
        String toAccountNum = null;
        int amount = 0;

        System.out.print("송금할 상대방 계좌를 입력하세요: ");
        toAccountNum = scan.nextLine();
        User to = Bank.getUserByAccountNumber(toAccountNum);
        System.out.print("송금할 금액을 입력하세요: ");
        amount = scan.nextInt();

        if(amount<0)
            throw new NegativeBalanceException("잘못된 금액");
        else{
            if(amount<0)
                throw new NegativeBalanceException("잘못된 금액");
            else if(balance-amount<0)
                throw new NegativeBalanceException("잔고 부족");
            else {
                rmvBalance(amount);
                to.addBalance(amount);
                System.out.println(to.id+"님 "+amount+"원이 입금되었습니다.");
                System.out.println("잔고: "+to.getBalance());
                System.out.println(id+"님 "+amount+"원이 송금되었습니다.");
                System.out.println("잔고: "+balance);
            }

        }
    }
    public void purchase() throws Exception {
        Scanner scan = new Scanner(System.in);
        int price = 0;

        System.out.print("물건의 가격을 입력하세요: ");
        price = scan.nextInt();
        if(price<0)
            throw new NegativeBalanceException("잘못된 금액");
        if(hasCard == true){
            if(balance>=price) {
                balance -= price;
                System.out.println("물건이 구매되었습니다. (카드 결제)");
                System.out.println("잔고: "+balance);
                System.out.println("현금: "+cash);
            }
            else if(balance<price){
                System.out.println("잔고 부족 (카드 단독 결제 실패)");
                if(cash+balance>=price){
                    cash -= (price - balance);
                    balance = 0;
                    System.out.println("물건이 구매되었습니다. (카드 + 현금 결제)");
                    System.out.println("잔고: "+balance);
                    System.out.println("현금: "+cash);
                }else throw new NegativeCashException("현금 부족 (카드 + 현금 결제 실패)");
            }
        }else{
            if(cash>=price){
                cash -= price;
                System.out.println("물건이 구매되었습니다. (현금 결제)");
                System.out.println("잔고: "+balance);
                System.out.println("현금: "+cash);
            }else throw new NegativeCashException("현금 부족 (현금 결제 실패)");
        }
    }
}
