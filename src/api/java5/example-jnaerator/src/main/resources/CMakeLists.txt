cmake_minimum_required(VERSION 3.0)

project(hello)

set(SRCS
    hello.c)

set(HEADERS
    hello.h)

add_library(hello SHARED ${SRCS} ${HEADERS})