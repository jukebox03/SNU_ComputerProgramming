#include <iostream>
#include <fstream> // - ���� File IO�� ���� library�� include
#include <vector> // - vector�� ���� library�� include
#include <string>

using namespace std;

void openGradeFiles(ifstream* filenameInput, vector<ofstream>* nameVector);
void readScores(ifstream* inputFile, vector<int>* scoreVector);
void writeScores(vector<int>* scoreVector, vector<ofstream>* nameVector);

void closeInputFile(ifstream* inputFile);
void closeGradeFiles(vector<ofstream>* nameVector);

void openGradeFiles(ifstream* filenameInput, vector<ofstream>* nameVector) {                // �������� �������� �ʰ� ����
    vector<ofstream>::iterator nameItr;                                                        // - nameVector�� ���� iterator ����
    string tmpString;                                                      // - line read�� ���� string ����

    for(nameItr = nameVector->begin() ; nameItr != nameVector->end(); nameItr++) {                          // - iterator�� �̿��� nameVector�� ó������ ������ ����
        if(!getline(*filenameInput, tmpString)) {
            printf("Error: getline error\n");
            exit(1);
        }

        nameItr->open(tmpString);                                                             // - iterator�� tmpString�� �̿��� file create �� open
    }
}

void readScores(ifstream* inputFile, vector<int>* scoreVector) {                       // �������� �������� �ʰ� ����
    string tmpString;                                                      // - line read�� ���� string ����

    while(getline(*inputFile, tmpString)) {                                   // - inputFile�� �� �� �� �о��
        scoreVector->push_back(stoi(tmpString));           // - ���� line�� stoi()�� �̿��� scoreVector �� �ڿ� ����
    }
}

void writeScores(vector<int>* scoreVector, vector<ofstream>* nameVector) {                     // �������� �������� �ʰ� ����
    vector<int>::iterator scoreItr;                                                       // - scoreVector�� ���� iterator

    for(scoreItr = scoreVector->begin(); scoreItr != scoreVector->end(); scoreItr++) {                         // - iterator�� �̿��� scoreVector�� ó������ ������ ����
        switch (*scoreItr)                                                    // - scoreVector�� ���� ���� Ȯ��
        {                                                     /* !!** case ���� ...�� ������ ǥ���ϴ� ���̹Ƿ� �������� �ʽ��ϴ� **!! */
        case 90 ... 100:
            nameVector->at(0) << *scoreItr << "\n";                                         // - ù ��° output file stream ("A_score.txt")�� write
            break;
        case 80 ... 89:
            nameVector->at(1) << *scoreItr << "\n";                                         // - �� ��° output file stream ("B_score.txt")�� write
            break;
        case 70 ... 79:
            nameVector->at(2) << *scoreItr << "\n";                                         // - �� ��° output file stream ("C_score.txt")�� write
            break;
        case 60 ... 69:
            nameVector->at(3) << *scoreItr << "\n";                                         // - �� ��° output file stream ("D_score.txt")�� write
            break;
        default:
            nameVector->at(4) << *scoreItr << "\n";                                         // - �ټ� ��° output file stream ("Fail_score.txt")�� write
            break;
        }
    }
}

void closeInputFile(ifstream* inputFile) {                           // �������� �������� �ʰ� ����
    inputFile->close();                                             // - inputFile�� close
}

void closeGradeFiles(vector<ofstream>* nameVector) {                                  // �������� �������� �ʰ� ����
    vector<ofstream>::iterator nameItr;                                                        // - nameVector�� ���� iterator

    for(nameItr = nameVector->begin(); nameItr != nameVector->end(); nameItr++) {                           // - iterator�� �̿��� nameVetor�� ó������ ������ ����
        nameItr->close();                                                             // - iterator�� �̿��� output file stream�� close
    }
}

int main() {
    ifstream* tmpfilenameInput = new ifstream("filename.txt");           // for "filename.txt"
    ifstream* tmpscoreInput = new ifstream("score.txt");              // for "score.txt"

    vector<ofstream> *tmpnameVector = new vector<ofstream>(5);                     // for output file stream vector
    vector<int> *tmpscoreVector = new vector<int>();                    // for integer vector

    readScores(tmpscoreInput, tmpscoreVector);
    openGradeFiles(tmpfilenameInput, tmpnameVector);                                         // Fuction Calls
    writeScores(tmpscoreVector, tmpnameVector);

    closeInputFile(tmpscoreInput);
    closeInputFile(tmpfilenameInput);
    closeGradeFiles(tmpnameVector);

    return 0;
}
