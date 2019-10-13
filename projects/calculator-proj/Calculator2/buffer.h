/* 
 * File:   buffer.h
 * Author: mihir
 *
 * Created on April 18, 2019, 10:03 PM
 */

#ifndef BUFFER_H
#define	BUFFER_H

#ifdef	__cplusplus
extern "C" {
#endif

#include <stdbool.h>
#define MAX_LEN 10

struct buf {
    char buffer[MAX_LEN];
    int8_t pos;
    bool lock;
};

// BUFFER OPERATIONS (represented as a stack, but really only the push and print

#ifdef	__cplusplus
}
#endif

#endif	/* BUFFER_H */

