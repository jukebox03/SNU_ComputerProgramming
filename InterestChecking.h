#ifndef INTERESTCHECKING_H
#define INTERESTCHECKING_H

#include "BankAccount.h"
#include "Checking.h"

class InterestChecking : public Checking {
    public:
        /*InterestChecking(int num=0,float bal=0,float cmin=1000,float imin=2500,float chg=2,float rate=2.5,float monchg=10);
        void interest(); // add monthly interest to current balance
        virtual void print();*/

        InterestChecking(int num=0,float bal=0,float cmin=1000,float imin=2500,float chg=2,float rate=2.5,float monchg=10) : Checking(num, bal, cmin, chg), minint(imin) , intrate(rate), moncharge(monchg){}

        void interest(){
            if(bal<minint){
                bal = bal - moncharge;
            }else{
                bal = bal + bal*intrate/100./12.;
            }
            setCredit();
        }

        virtual void print(){
            cout << "Interest Checking Account: " << acctnum;
            cout << '\t' << "Bankname: " << bank_name;
            cout << '\t' << "Credit: " << credit << endl;
            cout << '\t' << "Balance: " << bal << endl;
            cout << '\t' << "Minimum to Avoid Charges: " << minimum << endl;
            cout << '\t' << "Charge per Check: " << charge << endl;
            cout << '\t' << "Minimum balance for getting interest and No Monthly Fee: " << minint << endl;
            cout << '\t' << "Interest: " << intrate << "%" << endl;
            cout << '\t' << "Monthly Fee: " << moncharge << endl;
            cout << "\n" << endl;
        }
    protected:
        float intrate; // interest rate(이자율)
        float minint; // minimum checking balance to get interest(이자를 받는데 있어서 요구되는 최소한의 잔고)
        float moncharge; //monthly charge when balance is below minint(매월 이자 발생 시 최소한의 잔고가 없을 때 부과되는 벌금)
};

#endif
