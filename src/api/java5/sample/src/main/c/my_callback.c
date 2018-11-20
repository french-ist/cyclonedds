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

void registerCallback(callback myc)
{
    c = myc;
}

/*
static struct _functions funcs;
void init(_functions myfuncs)
{
    funcs = myfuncs;
}
*/