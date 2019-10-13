/* 
 * File:   hashmap.h
 * Author: mihir
 *
 * Created on April 19, 2019, 8:46 PM
 */

#ifndef HASHMAP_H
#define	HASHMAP_H

#ifdef	__cplusplus
extern "C" {
#endif

#include <stdint.h>
    
#define SIZE 97
#define JUMP 4
#define ERROR 66;
    
struct node {
    uint8_t val;
    struct node* ptr;
    char* arr;
    uint8_t len;
};    

#ifdef	__cplusplus
}
#endif

#endif	/* HASHMAP_H */

