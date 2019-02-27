#include <string.h>
#include <stdio.h>
#include "my_callback.h"

static callback c;

int myfunc(char *name)
{
    printf("[C] myfunc(%s)\n", name);
    printf("[C] calling callback (received, %s)\n", name);
    (*c)("received", name);
    return strlen(name);
}
int myfunc2(callback myc, char *name)
{
    printf("[C] myfunc2 callback %p - myfunc(%s)\n", (void*) myc, name);
    printf("[C] myfunc2 calling callback (received, %s)\n", name);
    (*myc)("received", name);
    return strlen(name);
}

void registerCallback(callback myc)
{
    c = myc;
}

void open_triggerCallback(const openCallback pfn)
{
    printf("[C] open_triggerCallback\n");
	int res = (*pfn)("(from C) myfile", 0);
    printf("[C] res of open_triggerCallback (from Java) = %d\n", res);
}
void close_triggerCallback(const closeCallback pfn)
{
    printf("[C] close_triggerCallback\n");
	int res = (*pfn)(10);  
    printf("[C] res of close_triggerCallback (from Java) = %d\n", res);
}

struct _functions myfuncs;
void init (struct _functions funcs) {
    myfuncs = funcs;
    printf("[C] myfuncs: open %p - close(%p)\n", (void*) myfuncs.open, (void*) myfuncs.close);
}

// functions calling back java
int myopen(char *name)
{
    printf("[C] myopen(%s)\n", name);
    int option = 10000;
    printf("[C] calling callback open (%s, %d)\n",name, option);
    return (*myfuncs.open)(name, 10000);
}
int myclose(int fd)
{
    printf("[C] myclose(%d)\n", fd);
    printf("[C] calling callback close (%d)\n", fd);
    return (*myfuncs.close)(fd);
}