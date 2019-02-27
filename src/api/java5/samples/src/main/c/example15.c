// Send a Struct Containing an Array of Structs to C
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Example15StructA
{
    int val;
} Example15StructA;

typedef struct Example15StructB
{
    int numAs;
    Example15StructA *as;
} Example15StructB;

// Send a Struct Containing an Array of Structs to C
void example15_send(const Example15StructB *pVal)
{
    int loop = 0;
    printf("(C) Example 15: %d values\n", pVal->numAs);
    for (loop = 0; loop < pVal->numAs; loop++)
    {
        printf("\t(C): %d\n", pVal->as[loop].val);
    }
}

// Receive a Struct Containing an Array of Structs from C
Example15StructB example15_get()
{
	int loop = 0;
	Example15StructB sVal;
	sVal.numAs = 3;
	sVal.as = (Example15StructA*)malloc(sizeof(Example15StructA) * sVal.numAs);
	memset(sVal.as, 0, sizeof(Example15StructA) * sVal.numAs);
	for (loop=0; loop<sVal.numAs; loop++)
	{
		// fill in dummy data for the purpose of this example
		sVal.as[loop].val = loop;
	}
	return sVal;
}

void example15_cleanup(Example15StructB sVal)
{
	free(sVal.as);
}