
import java.util.Arrays;

public class TraceShortestPath {
    
    private static final int INF = 1_000_000_000;
    
    public static void main(String[] args) {
        int[][] adjMatrix = {{0,2,INF,INF,INF},{2,0,5,4,INF},{INF,5,0,1,INF},{INF,1,1,0,5},{INF,INF,INF,3,0}};
        int[][] parent = new int[adjMatrix.length][adjMatrix.length];
        for(int i = 0; i<adjMatrix.length; ++i) {
            for(int j = 0; j<adjMatrix.length; ++j) {
                parent[i][j] = i;
            }
        }
        
        for(int k = 0; k<adjMatrix.length; ++k) {
            for(int i = 0; i<adjMatrix.length; ++i) {
                for(int j = 0; j<adjMatrix.length; ++j) {
                    if(adjMatrix[i][k]+adjMatrix[k][j] < adjMatrix[i][j]) {
                        adjMatrix[i][j] = adjMatrix[i][k]+adjMatrix[k][j];
                        parent[i][j] = parent[k][j];
                    }
                }
            }
        }
        
        for(int[] list: adjMatrix) {
            System.out.println(Arrays.toString(list));
        }
        
        printPath(parent, 0,2);
        
    }
    
    public static void printPath(int[][] parent, int i, int j) {
        if(i != j) {
            printPath(parent, i, parent[i][j]);
        } System.out.printf(" %d", j);
    }
}
