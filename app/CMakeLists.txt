
cmake_minimum_required(VERSION 3.4.1)

include_directories(${CMAKE_SOURCE_DIR}/src/main/cpp/include)#h文件目录
add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/native-lib.cpp
        src/main/cpp/nativeactivity.cpp



        )

add_library(
        JniNative

        SHARED

        src/main/cpp/JniNative.cpp

)

add_library(
        nativesign

        SHARED

        src/main/cpp/sign/nativesign.cpp
        src/main/cpp/sign/nativesign.h

)

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)


target_link_libraries( # Specifies the target library.
        native-lib

        jnigraphics
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})

target_link_libraries(
        JniNative


        ${log-lib}
)

target_link_libraries(
        nativesign
        ${log-lib}
)