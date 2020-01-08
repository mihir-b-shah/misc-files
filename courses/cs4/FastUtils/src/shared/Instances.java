
package shared;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

public class Instances {
    
    private static Unsafe unsafe;
    
    static {
        Field f;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException |
                IllegalArgumentException | IllegalAccessException e) {
        }
        
    }
    
    static Unsafe getUnsafe() {
        return unsafe;
    }
}
