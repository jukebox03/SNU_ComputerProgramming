#ifndef SAVINGS_H
#define SAVINGS_H

#include "BankAccount.h"

class Savings : public BankAccount {
    public:
        /*Savings(int num=0,float bal=0,float rate=3.5);
        void interest(); // add monthly interest to current balance
        int withdraw(float);
        virtual void print();*/

        Savings(int num=0,float bal=0,float rate=3.5) : intrate(rate), BankAccount(num, bal){}

        void interest(){
            bal = bal + bal*intrate/100./12.;
            setCredit();
        }

        int withdraw(float amount){
            if(bal<=amount){
                cout << "Cannot withdraw $" << amount <<" on account #" << acctnum << " because the balance is low." << endl;
                return 0;
            }else{
                bal = bal - amount;
                setCredit();
                return 1;
            }
        }

        virtual void print(){
            cout << "Savings Account: " << acctnum;
            cout << '\t' << "Bankname: " << bank_name;
            cout << '\t' << "Credit: " << credit << endl;
            cout << '\t' << "Balance: " << bal << endl;
            cout << '\t' << "Interest: " << intrate << "%" << endl;
            cout << "\n" << endl;
        }
    protected:
        float intrate; //interest rate(이자율)
};

#endif
