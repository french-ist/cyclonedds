#include <stdint.h>
void display(char* ch);
typedef uint32_t dds_domainid_t;

// Test profile with an typedef uint32_t - jna interface using int (HelloworldDirectMapping.java)
void create_domain (dds_domainid_t id);
