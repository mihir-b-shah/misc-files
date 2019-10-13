
/*

 ID: mihirsh1
 PROB: maze1
 LANG: JAVA

 */

import java.io.*;
import java.util.*;

public class maze1 {

    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
    public static int H;
    public static int W;

    public static class IntPair {

        public int X;
        public int Y;
        public int val = 0;

        public IntPair(int X, int Y) {
            this.X = X;
            this.Y = Y;
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner f = new Scanner(new File("maze1.in"));
        PrintWriter out = new PrintWriter("maze1.out");

        W = f.nextInt();
        H = f.nextInt();

        int e1x = -1;
        int e2x = -1;
        int e1y = -1;
        int e2y = -1;

        f.nextLine();
        int[][] maze = new int[H][W];

        for (int i = 0; i < 2 * H + 1; i++) {

            String p = f.nextLine();

            for (int j = 0; j < 2 * W + 1; j++) {

                try {

                    char k = p.charAt(j);

                    if ((i + j) % 2 == 1) {

                        if (k == '-' || k == '|') {
                            if (i % 2 == 0) {
                                if (i == 0) {
                                    maze[i / 2][j / 2] += (1 << NORTH);
                                } else if (i == 2 * H) {
                                    maze[i / 2 - 1][j / 2] += (1 << SOUTH);
                                } else {
                                    maze[i / 2][j / 2] += (1 << NORTH);
                                    maze[i / 2 - 1][j / 2] += (1 << SOUTH);
                                }
                            } else {
                                if (j == 0) {
                                    maze[i / 2][j / 2] += (1 << WEST);
                                } else if (j == 2 * W) {
                                    maze[i / 2][j / 2 - 1] += (1 << EAST);
                                } else {
                                    maze[i / 2][j / 2] += (1 << WEST);
                                    maze[i / 2][j / 2 - 1] += (1 << EAST);
                                }
                            }
                        } else {
                            if (i == 0) {
                                if (e1x == -1) {
                                    e1x = i / 2;
                                    e1y = j / 2;
                                } else {
                                    e2x = i / 2;
                                    e2y = j / 2;
                                }
                            } else if (i == 2 * H) {
                                if (e1x == -1) {
                                    e1x = i / 2 - 1;
                                    e1y = j / 2;
                                } else {
                                    e2x = i / 2 - 1;
                                    e2y = j / 2;
                                }
                            } else if (j == 0) {
                                if (e1x == -1) {
                                    e1x = i / 2;
                                    e1y = j / 2;
                                } else {
                                    e2x = i / 2;
                                    e2y = j / 2;
                                }
                            } else if (j == 2 * W) {
                                if (e1x == -1) {
                                    e1x = i / 2;
                                    e1y = j / 2 - 1;
                                } else {
                                    e2x = i / 2;
                                    e2y = j / 2 - 1;
                                }
                            }
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(i + " " + j);
                }
            }
        }

        int[][] d1 = ff(e1x, e1y, maze);
        int[][] d2 = ff(e2x, e2y, maze);
        int max = 0;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                max = Math.max(max, Math.min(d1[i][j], d2[i][j]));
            }
        }

        //print2DArray(distances_1);
        //print2DArray(distances_2);
        
        if(H == 1 && W == 1)
            out.println(max);
        else
            out.println(max+1);
        
        out.flush();
        out.close();
        f.close();
        System.exit(0);
    }

    public static int[][] ff(int x, int y, int[][] maze) {
        int[][] visited = new int[maze.length][maze[0].length];
        
        Queue<IntPair> queue = new LinkedList<>();
        IntPair init = new IntPair(x, y);
        init.val = 1;
        queue.add(init);
        visited[x][y] = 1;

        while (!queue.isEmpty()) {
            IntPair e = queue.poll();
            //System.out.printf("X: %d, Y: %d\n", e.X, e.Y);
            if (((maze[e.X][e.Y] & (1 << EAST)) == 0) && (e.Y < W - 1) && visited[e.X][e.Y+1] == 0) {
                IntPair a = new IntPair(e.X, e.Y+1);
                visited[e.X][e.Y+1] = e.val;
                a.val = e.val+1;
                queue.offer(a);
            } 
            if (((maze[e.X][e.Y] & (1 << WEST)) == 0) && (e.Y > 0) && visited[e.X][e.Y-1] == 0) {
                IntPair a = new IntPair(e.X, e.Y-1);
                visited[e.X][e.Y-1] = e.val;
                a.val = e.val+1;
                queue.offer(a);
            } 
            if (((maze[e.X][e.Y] & (1 << NORTH)) == 0) && (e.X > 0) && visited[e.X-1][e.Y] == 0) {
                IntPair a = new IntPair(e.X-1, e.Y);
                visited[e.X-1][e.Y] = e.val;
                a.val = e.val+1;
                queue.offer(a);
            } 
            if (((maze[e.X][e.Y] & (1 << SOUTH)) == 0) && (e.X < H - 1) && visited[e.X+1][e.Y] == 0) {
                IntPair a = new IntPair(e.X+1, e.Y);
                visited[e.X+1][e.Y] = e.val;
                a.val = e.val+1;
                queue.offer(a);
            }
        }

        return visited;
    }

    public static void print2DArray(int[][] arr) {
        for (int[] a : arr) {
            for (int b : a) {
                System.out.printf("%4d", b);
            }
            System.out.println();
        }

        System.out.println("\n");
    }

    public static boolean[][] clone2D(boolean[][] arr) {
        boolean[][] clone = new boolean[arr.length][arr[0].length];

        for (int i = 0; i < arr.length; i++) {
            clone[i] = arr[i].clone();
        }

        return clone;
    }

    public static void floodFill(int X, int Y, int[][] maze, int[][] visited, int sum, boolean[][] visit) {

        visit[X][Y] = true;

        if (visited[X][Y] != 0) {

            if (visited[X][Y] <= sum) {
                return;
            }

            visited[X][Y] = Math.min(sum, visited[X][Y]);
        } else {
            visited[X][Y] = sum;
        }

        boolean[][] clone = clone2D(visit);

        if (X > 0 && !visit[X - 1][Y] && ((maze[X][Y] & (1 << NORTH)) == 0)) {
            floodFill(X - 1, Y, maze, visited, sum + 1, clone);
        }

        if (Y < maze[0].length - 1 && !visit[X][Y + 1] && ((maze[X][Y] & (1 << EAST)) == 0)) {
            floodFill(X, Y + 1, maze, visited, sum + 1, clone);
        }

        if (X < maze.length - 1 && !visit[X + 1][Y] && ((maze[X][Y] & (1 << SOUTH)) == 0)) {
            floodFill(X + 1, Y, maze, visited, sum + 1, clone);
        }

        if (Y > 0 && !visit[X][Y - 1] && ((maze[X][Y] & (1 << WEST)) == 0)) {
            floodFill(X, Y - 1, maze, visited, sum + 1, clone);
        }
    }
}
