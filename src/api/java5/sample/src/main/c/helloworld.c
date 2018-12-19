#include "helloworld.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <stddef.h>

void display(char* ch) {
    printf("%s\n\tsizeof(HelloWorld)=%ld\n\tsizeof(char*)=%ld\n", ch, sizeof(HelloWorld), sizeof(char*));
    printf("\tsizeof(int)=%ld\n", sizeof(int));
    printf("\toffsetof(HelloWorld,userID)=%ld\n",offsetof(HelloWorld, userID));
    printf("\toffsetof(HelloWorld,message)=%ld\n",offsetof(HelloWorld, message));
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

void helloPointerFromJava(void *sample){
    printf("helloPointerFromJava pointer {%p},  ", sample);
    HelloWorld * hello = (HelloWorld*) sample;
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

void helloArrayFromJava(void *samples, int nb){
    HelloWorld* hellos = (HelloWorld*)samples;
    for(int i=0;i<nb;i++){
        printf("helloArrayFromJava String:{%s} Int:{%d}\n", hellos[i].message, hellos[i].userID);
    }
}