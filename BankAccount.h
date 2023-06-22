#ifndef BANKACCOUNT_H
#define BANKACCOUNT_H
#include <string>
#include <cstdlib>
using namespace std;

class BankAccount{
    public:
        /*BankAccount(int num,float bal);
        void deposit(float amount); //money getting into account
        void loan(float amount);
        virtual int withdraw(float amount); // money getting out of account
        int getAcctnum(); // getter function
        float getBalance();// getter function
        int getcredit(); // getter function
        string getBankname(); // getter function*/
        virtual void print() = 0;

        BankAccount(int num, float bal) : acctnum(num), bal(bal){
            setCredit();
            int randomNumber = rand()%3;
            if(randomNumber==0){
                bank_name = "HANA";
            }else if(randomNumber==1){
                bank_name = "URI";
            }else{
                bank_name = "SHINHAN";
            }
        }

        void setCredit(){
            if(bal<1000) credit = 1;
            else if(bal<2000 && bal>=1000) credit = 2;
            else credit = 3;
        }

        void deposit(float amount){
            bal = bal + amount;
            setCredit();

        }

        virtual int withdraw(float amount){
            bal = bal - amount;
            setCredit();
            return 0;
        }

        int getAcctnum(){
            return acctnum;
        }

        float getBalance(){
            return bal;
        }

        int getcredit(){
            return credit;
        }

        string getBankname(){
                return bank_name;
        }

        void loan(float amount){
            if(bal<1000){
                credit = 1;
                cout << "The amount cannot be loaned" << endl;
            }else if(bal<2000 && bal>=1000){
                credit = 2;
                if(amount<100 || amount>500){
                    cout << "The amount cannot be loaned" << endl;
                }else{
                    bal = bal + amount*0.9;
                }
            }else{
                credit = 3;
                if(amount<100 || amount>1000){
                    cout << "The amount cannot be loaned" << endl;
                }else{
                    bal = bal + amount*0.95;
                }
            }
            setCredit();
        }

    protected:
        int acctnum; // account number
        float bal; //current balance of account
        string bank_name;
        int credit;
};

#endif
