typedef void (*callback)(char *, char *);

int myfunc(char *);

int myfunc2(callback myc, char *);

void registerCallback(callback myc);

typedef int (*openCallback)(const char *, int);
typedef int (*closeCallback)(int);

struct _functions
{
    int (*open)(const char *, int);
    int (*close)(int);
} functions;

void init(struct _functions funcs);
int myopen(char *name);
int myclose(int fd);