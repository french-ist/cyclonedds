typedef int dds_domainid_t;

typedef struct person_struct {
    char * name;	
    int money;
    //dds_domainid_t domainid;
} person_t;

void person_say_hello(char ch[], person_t* p);
void say_hello(char* ch);
void say_goodbye(char* ch);