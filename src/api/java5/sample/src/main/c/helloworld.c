#include "helloworld.h"
#include <stdlib.h>
#include <stdio.h>

void display(char* ch) {
    printf("%s\n", ch);
}

void hello(char* name, int id) {
    printf("Hello {%s} {%d}\n", name, id);
}

void create_domain (uint32_t id){
    printf("create_domain %d\n", id);
}