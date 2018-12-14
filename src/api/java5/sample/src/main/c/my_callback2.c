#include <string.h>
#include <stdio.h>
#include "my_callback2.h"

functions myfuncs;

int open(const char *name, int option)
{
    printf("[C] open(%s, %d)\n", name, option);
    return 50000;
}

int (*open_ptr)(const char *, int) = &open;

int close(int fd)
{
    printf("[C]close(%d)\n", fd);
    return 60000;
}

int (*close_ptr)(int) = &close;

void init2(functions *funcs)
{
    printf("[C] init2 struct : open %p - close(%p)\n", (void *)funcs->open, (void *)funcs->close);
    *funcs = (functions){&open, &close};
    printf("[C] init2 struct : open %p - close(%p)\n", (void *)funcs->open, (void *)funcs->close);
}
