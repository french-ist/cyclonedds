#include "hello.h"
#include <stdlib.h>
#include <stdio.h>


void person_say_hello(char ch[], person_t* p) {
    printf("hello.c > person_say_hello > '%s' from '%s', his money: '%d'€\n", ch, p->name, p->money);
}

void say_hello(char* ch) {
    printf("hello.c > say_hello > '%s'\n", ch);
}

void say_goodbye(char* ch) {
    printf("hello.c > say_goodbye > '%s'\n", ch);
}
