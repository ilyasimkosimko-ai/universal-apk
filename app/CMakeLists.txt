cmake_minimum_required(VERSION 3.18.1)
project("ghostlock")

add_library(ghostlock SHARED src/main/cpp/ghostlock.c)
target_link_libraries(ghostlock log)
