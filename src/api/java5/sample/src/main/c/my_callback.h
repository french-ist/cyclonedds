typedef void (*callback)(char *, char *);

int myfunc(char *);

void registerCallback(callback myc);

struct _functions
{
    int (*open)(const char *, int);
    int (*close)(int);
};