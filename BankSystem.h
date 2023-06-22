#ifndef BANKSYSTEM_H
#define BANKSYSTEM_H

#include "BankAccount.h"

class BankSystem {
    public:
        /*void transaction(BankAccount* from, BankAccount* to, float amount); //electronic money transaction; there are sender and receiver
        void deposit(BankAccount* b, float amount); //person puts cash into his or her account
        void withdraw(BankAccount* b, float amount); //person takes cash out of his or her account
        void loan(BankAccount* b, float amount);//person takes out a loan*/

        void transaction(BankAccount* from, BankAccount* to, float amount){
            float fee=5;
            if(from->getBankname().compare(to->getBankname())==0){
                if(from->withdraw(amount)==1) to->deposit(amount);
            }else{
                if(from->withdraw(amount+fee)) to->deposit(amount);
            }
        }

        void deposit(BankAccount* b, float amount){
            b->deposit(amount);
        }

        void withdraw(BankAccount* b, float amount){
            b->withdraw(amount);
        }

        void loan(BankAccount* b, float amount){
            b->loan(amount);
        }
};


#endif
