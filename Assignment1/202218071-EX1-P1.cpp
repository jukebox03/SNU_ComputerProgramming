#include <iostream>
#include<sstream>

using namespace std;

string to_string(int a)
{
    stringstream ss;
    ss<<a;
    string result;
    ss>>result;
    return result;
}

class Equation{
private:
    int a=0;
    int b=0;
    int c=0;
public:
    Equation(int c)
    {
        this->c=c;
    }

    Equation(int b, int c)
    {
        this->b=b;
        this->c=c;
    }
    Equation(int a, int b, int c)
    {
        this->a=a;
        this->b=b;
        this->c=c;
    }
    int getA()
    {
        return this->a;
    }
    int getB()
    {
        return this->b;
    }
    int getC()
    {
        return this->c;
    }
};

class EquationUtility{
public:
    Equation add(Equation a, Equation b)
    {
        int a_t=a.getA()+b.getA();
        int b_t=a.getB()+b.getB();
        int c_t=a.getC()+b.getC();
        Equation result=Equation(a_t, b_t, c_t);
        return result;
    }
    string output(Equation a)
    {
        int check=0;
        string result="";
        if(a.getA()!=0)
        {
            result=result+to_string(a.getA())+"x^2";
            check=1;
        }
        if(a.getB()!=0)
        {
            if(check==0)
            {
                result=result+to_string(a.getB())+"x";
                check=1;
            }
            else
            {
                if(a.getB()>0)
                    result=result+"+"+to_string(a.getB())+"x";
                else
                    result=result+to_string(a.getB())+"x";
            }
        }
        if(a.getC()!=0)
        {
            if(check==0)
            {
                result=result+to_string(a.getC());
                check=1;
            }
            else
            {
                if(a.getC()>0)
                    result=result+"+"+to_string(a.getC());
                else
                    result=result+to_string(a.getC());
            }
        }
        if(check==0) return "0";
        else return result;
    }

};

int main()
{
    Equation e1(2);
    Equation e2(4, -5);
    EquationUtility a;
    Equation result=a.add(e1, e2);
    cout<<a.output(result)<<endl;
    Equation e3(3, 0, 5);
    result=a.add(e1, e3);
    cout<<a.output(result)<<endl;
    return 0;
}
