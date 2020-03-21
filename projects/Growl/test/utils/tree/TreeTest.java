
package utils.tree;

import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import utils.tree.TreeBenchmark.IntWrapper;
import java.util.Random;
import java.util.TreeSet;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author mihir
 */
public class TreeTest {
    
    private FastTree<IntWrapper> tree;
    private TreeSet<IntWrapper> check;
    
    private IntWrapper[] insert;
    private Random rand;
    
    private static final int INSERT_SIZE = 9_900_000;
    private static final int QUERY_SIZE = 5_000_000;
 
    @Before
    public void setUp() {
        tree = new FastTree(new IntWrapper(0));
        check = new TreeSet<>();
        insert = new IntWrapper[INSERT_SIZE];
        rand = new Random();
        
        for(int i = 0; i<insert.length; ++i) {
            insert[i] = new IntWrapper(rand.nextInt());
        }
    }
    
    @After
    public void tearDown() {
        tree = null;
        assertNull(tree);
    }

    @Test
    public void largeRandomized() {
        for(IntWrapper iw: insert) {
            tree.insert(iw);
            check.add(iw);
        }
        
        IntWrapper test;
        for(int i = 0; i<QUERY_SIZE; ++i) {
            if(rand.nextBoolean()) {
                test = new IntWrapper(rand.nextInt());
            } else {
                test = insert[rand.nextInt(insert.length)];
            }
            assertEquals(tree.contains(test), check.add(test));
        }
    }
}
