#include <string.h>
#include <stdio.h>
#include "example22.h"
void example22_triggerCallback(const Example22Callback pfn)
{
    printf("[C] example22_triggerCallback\n");
	(*pfn)(230);
}