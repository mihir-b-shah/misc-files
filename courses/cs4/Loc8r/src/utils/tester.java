/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

public class tester {
    public static void main(String[] args) {
        String s1 = "The biggest most amazing piece of chocolate cake you have ever seen";
        String s2 = "I do not like chocolate cake even if it is the biggest";
        StringSimilarity.genScore(s1, s2);
    }
}
