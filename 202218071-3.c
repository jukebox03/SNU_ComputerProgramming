#include <stdio.h>
#include <stdlib.h>

#define MAX_ARR_SIZE 10

void printArr(int* arr);

void printArr(int* arr)
{
    for(int i=0; i<MAX_ARR_SIZE; i++)
    {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main()
{
    int MyArr[MAX_ARR_SIZE];
    for(int i=0; i<10; i++)
    {
        MyArr[i]=i+100;
    }
    printArr(MyArr);
    return 0;
}
