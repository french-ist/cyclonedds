// If your native code initializes function pointers within a struct.
// JNA will automatically generate a Callback instance matching the declared type.
// This enables you to easily call the function supplied by native code using proper Java syntax.
typedef struct _functions
{
    int (*open)(const char *, int);
    int (*close)(int);
} functions;
void init2(functions *funcs);