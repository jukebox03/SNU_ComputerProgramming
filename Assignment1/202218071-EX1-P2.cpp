#include <iostream>

using namespace std;

class Salad{
public:
    string meat;
    int price=0;
    Salad(string meat)
    {
        this->meat=meat;
    }
    void addSomething(string add)
    {
        if(add.compare("cheese")==0)
            this->price+=1000;
        else if(add.compare("avocado")==0)
            this->price+=2000;
    }
    void showPrice()
    {
        if(meat.compare("chicken")==0)
            this->price+=8500;
        else if(meat.compare("turkey")==0)
            this->price+=9000;
        cout<<"price : "<<price<<"원"<<endl;
    }
};

class Sandwich:public Salad{
public:
    int sizeOfSandwich=0;
    Sandwich(int size, string meat):Salad(meat)
    {
        this->sizeOfSandwich=(int)(size/30);
    }
    void showPrice()
    {
        if(meat.compare("chicken")==0)
            this->price+=7500*sizeOfSandwich;
        else if(meat.compare("turkey")==0)
            this->price+=8000*sizeOfSandwich;
        cout<<"price : "<<price<<"원"<<endl;
    }
};

int main()
{
    int num;
    cout<<"샐러드 주문은 1, 샌드위키 주문은 2"<<endl;
    cin>>num;
    if(num==1){
        Salad salad1("chicken");
        salad1.addSomething("cheese");
        salad1.showPrice();
    }
    else if(num==2)
    {
        Sandwich sandwich1(30, "turkey");
        sandwich1.addSomething("avocado");
        sandwich1.showPrice();
    }
}
