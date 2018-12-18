#include "helloworld.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

void display(char* ch) {
    printf("%s\n", ch);
}

void hello(char* name, int id) {
    printf("Hello {%s} {%d}\n", name, id);
}

void helloFromJava(HelloWorld* hello){
    printf("helloFromJava {%s} {%d}\n", hello->message, hello->userID);
}

void helloFromC(HelloWorld* hello){    
    hello->message = "helloFromC";
    hello->userID = 21;
    printf("helloFromC {%s} {%d}\n", hello->message, hello->userID);
}

void helloPointerFromJava(void *data){
    printf("helloPointerFromJava pointer {%p},  ", data);
    HelloWorld * hello = (HelloWorld*) data;
    printf("String:{%s} int:{%d}\n", hello->message, hello->userID);
}

void helloPointerArrayFromC(void **samples, int *size){    
    *size = 2;
    HelloWorld** hwSamples = (HelloWorld**)samples; 
    *hwSamples = (HelloWorld*) malloc(sizeof(HelloWorld) * (*size));
    memset(*hwSamples, 0, sizeof(HelloWorld)* (*size));
    for(int i=0;i<*size;i++){        
        (*hwSamples)[i].message = "Hi in array!";        
        (*hwSamples)[i].userID = (i+10)*5;
    }
}
