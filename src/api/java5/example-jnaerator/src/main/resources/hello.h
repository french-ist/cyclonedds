typedef struct person_struct {
    char name[50];	
    int money;
} person_t;

void person_say_hello(char ch[], person_t* p);
void say_hello(char* ch);
void say_goodbye(char* ch);
