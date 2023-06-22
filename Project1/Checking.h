#ifndef CHECKING_H
#define CHECKING_H

#include "BankAccount.h"

class Checking : public BankAccount{
    public:
        /*Checking(int num=0,float bal=0,float min=1000,float chg=2);
        int withdraw(float amount);
        virtual void print();*/

        Checking(int num=0,float bal=0,float min=1000,float chg=2) : BankAccount(num, bal), minimum(min), charge(chg){}

        int withdraw(float amount){
            if(bal<=amount){
                cout << "Cannot withdraw $" << amount <<" on account #" << acctnum << " because the balance is low." << endl;
                return 0;
            }else{
                if(bal<minimum){
                    bal = bal - (amount + charge);
                }else{
                    bal = bal - amount;
                }
                setCredit();
                return 1;
            }
        }

        virtual void print(){
            cout << "Checking Account: " << acctnum;
            cout << '\t' << "Bankname: " << bank_name;
            cout << '\t' << "Credit: " << credit << endl;
            cout << '\t' << "Balance: " << bal << endl;
            cout << '\t' << "Minimum to Avoid Charges: " << minimum << endl;
            cout << '\t' << "Charge per Check: " << charge << endl;
            cout << "\n" << endl;
    }
    protected:
        //minimum account balance to keep(유지되야 하는 최소한의 잔고)
        float minimum;
        // amount of money charged when balance is below minimum(minimum이 유지안되었을 때 발생하는 벌금)
        float charge;
};

#endif
