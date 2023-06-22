#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>

using namespace std;

int main()
{
    int n, m;
    string command;
    string word;
    ifstream* commandReader = new ifstream("p2input.txt");
    ofstream writer;
    writer.open("p2output.txt");
    vector<int>* stack_p=new vector<int>();

    getline(*commandReader, command);
    stringstream ss(command);
    getline(ss, command, ' ');
    n = stoi(command);
    getline(ss, command, ' ');
    m = stoi(command);

    getline(*commandReader, command);
    stringstream ss1(command);
    while(ss1>>command){
        stack_p->push_back(stoi(command));
    }

    for(int i=0; i<m; i++){
        getline(*commandReader, command);

        stringstream ss2(command);
        getline(ss2, command, ' ');
        if(command.compare("BP")==0){
            int I, J;
            getline(ss2, command, ' ');
            I = stoi(command);
            getline(ss2, command, ' ');
            J = stoi(command);
            for (int i=0; i<stack_p->size(); i++){
                if(stack_p->at(i) == I){
                    if(i==0){
                        writer << stack_p->back() << endl;
                        stack_p->push_back(J);
                    }else{
                        writer << stack_p->at(i-1) << endl;
                        stack_p->insert(stack_p->begin()+i, J);
                    }
                    break;
                }
            }
        }else if(command.compare("BN")==0){
            int I, J;
            getline(ss2, command, ' ');
            I = stoi(command);
            getline(ss2, command, ' ');
            J = stoi(command);
            for (int i=0; i<stack_p->size(); i++){
                if(stack_p->at(i) == I){
                    if(i==stack_p->size()-1){
                        writer << stack_p->front() << endl;
                        stack_p->push_back(J);
                    }else{
                        writer << stack_p->at(i+1) << endl;
                        stack_p->insert(stack_p->begin()+i+1, J);
                    }
                    break;
                }
            }
        }else if(command.compare("CP")==0){
            int I;
            getline(ss2, command, ' ');
            I = stoi(command);
            for (int i=0; i<stack_p->size(); i++){
                if(stack_p->at(i) == I){
                    if(i==0){
                        writer << stack_p->back() << endl;
                        stack_p->pop_back();
                    }else{
                        writer << stack_p->at(i-1) << endl;
                        stack_p->erase(stack_p->begin()+i-1);
                    }
                    break;
                }
            }
        }else if(command.compare("CN")==0){
            int I;
            getline(ss2, command, ' ');
            I = stoi(command);
            for (int i=0; i<stack_p->size(); i++){
                if(stack_p->at(i) == I){
                    if(i==stack_p->size()-1){
                        writer << stack_p->front() << endl;
                        stack_p->erase(stack_p->begin());
                    }else{
                        writer << stack_p->at(i+1) << endl;
                        stack_p->erase(stack_p->begin()+i+1);
                    }
                    break;
                }
            }
        }
    }

    commandReader->close();
    writer.close();
    return 0;
}
