/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mihir
 */
public class Atoi {
    
    public static int myAtoi(String str) {
        String s = str.trim();
        long val = 0;
        int ptr = 0;
        boolean neg = false;
        if(s.charAt(0) == '+' || s.charAt(0) == '-') {
            if(s.charAt(0) == '-') {
                neg = true;
            }
            ptr = 1;
        }

        while(ptr < s.length()) {
            if(s.charAt(ptr) < '0' || s.charAt(ptr) > '9') {
                break;
            }
            val *= 10;
            val += s.charAt(ptr)-'0';
            ++ptr;
        }
        long ret = neg ? -val : val;
        System.out.println(ret);
        if(ret < Integer.MIN_VALUE) {
            ret = Integer.MIN_VALUE;
        } else if(ret > Integer.MAX_VALUE) {
            ret = Integer.MAX_VALUE;
        }
        return (int) ret;
    }
    
    public static void main(String[] args) {
        System.out.println(myAtoi("9223372036854775808"));
    }
}
