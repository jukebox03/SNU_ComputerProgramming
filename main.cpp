#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>

using namespace std;

int main()
{
    int n;
    string command;
    ifstream* commandReader = new ifstream("p1input.txt");
    ofstream writer;
    writer.open("p1output.txt");
    vector<int>* stack_p=new vector<int>();

    getline(*commandReader, command);
    n = stoi(command);
    for(int i=0; i<n; i++){
        getline(*commandReader, command);
        if(command.length()>=6){
            string num_s=command.substr(5);
            stack_p->push_back(stoi(num_s));
        }else{
            if(command.compare("top")==0){
                if(stack_p->size()==0)
                    writer << -1 << endl;
                else writer << stack_p->back() <<endl;
            }else if(command.compare("pop")==0){
                if(stack_p->size()==0)
                    writer << -1 << endl;
                else{
                    writer << stack_p->back() <<endl;
                    stack_p->pop_back();
                }
            }else if(command.compare("size")==0){
                writer << stack_p->size() << endl;
            }else if(command.compare("empty")==0){
                if(stack_p->empty()) writer << 1 << endl;
                else writer << 0 << endl;
            }
        }
    }
    commandReader->close();
    writer.close();
    return 0;
}
