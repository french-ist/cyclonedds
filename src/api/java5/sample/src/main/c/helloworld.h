typedef struct HelloWorld_struct{
    int userID;
    char* message;    
} HelloWorld;

void display(char* ch);
void hello(char* message, int id);
void helloFromJava(HelloWorld* hello);
void helloFromC(HelloWorld* hello);
void helloPointerFromJava(void* data);
void helloPointerArrayFromC(void **samples, int *size);
