typedef void (*callback)(char *, char *);

int myfunc(char *);

void registerCallback(callback myc);

typedef int (*openCallback)(const char *, int);
typedef int (*closeCallback)(int);


