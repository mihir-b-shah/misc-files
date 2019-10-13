
public class TreeTester {

    public static void main(String[] args) {
        BinarySearchTree<Character> bst = new BinarySearchTree<>('M');
        randomizeBSTChar(10, bst);
        System.out.println(bst.printTree(110));
    }
    
    public static void randomizeBSTChar(int num, BinarySearchTree<Character> bst) {
        int already = 1 << 12;
        for(int i = 0; i<num; i++) {
            
            int x = 65 + (int) (Math.random()*26);
            if((already & (1 << (x-65))) != 0) {
                i--;
                continue;
            }
                
            already += (1 << (x-65));
            bst.add((char) x);
        }
    }
    
    public static void randomizeBSTInt(int num, BinarySearchTree<Integer> bst) {
        boolean[] bitmask = new boolean[1000];
        
        for(int i = 0; i<num; i++) {
            
            int x = (int) (Math.random()*1000);
            if(bitmask[x]) {
                i--;
                continue;
            }
                
            bitmask[x] = true;
            bst.add(x);
        }
    }
}
