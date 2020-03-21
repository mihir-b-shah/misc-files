/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.tree;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author mihir
 */
public class TreeTestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TreeTest.class);
        for (Failure failure : result.getFailures()) {
          System.out.println(failure.toString());
        }
    }
}
