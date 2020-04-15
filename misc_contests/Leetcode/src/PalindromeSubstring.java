
public class PalindromeSubstring {

    public static String longestPalindrome(String str) {
        if(str.length() == 0) {
            return str;
        }
        String bestStr = str.substring(0,1);
        int bestStride = 1;
        boolean[][] matrix = new boolean[str.length()][str.length()+1];
        for(int stride = 1; stride<=str.length(); ++stride) {
            for(int i = 0; i<=str.length()-stride; ++i) {
                switch(stride){
                    case 1:
                        matrix[i][i+stride] = true;
                        break;
                    case 2:
                        matrix[i][i+stride] = str.charAt(i) == str.charAt(i+1);
                        if(matrix[i][i+stride] && bestStride < stride) {
                            bestStr = str.substring(i,i+stride);
                            bestStride = stride;
                        }
                        break;
                    default:
                        matrix[i][i+stride] = str.charAt(i) == str.charAt(i+stride-1)
                                && matrix[i+1][i+stride-1];
                        if(matrix[i][i+stride] && bestStride < stride) {
                            bestStr = str.substring(i,i+stride);
                            bestStride = stride;
                        }
                        break;
                }
            }
        }
        return bestStr;
    }
    
    public static void main(String[] args) {
        System.out.println(longestPalindrome("ba"));
    }
}
