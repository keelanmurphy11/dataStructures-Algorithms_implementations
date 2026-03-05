import java.util.Arrays;

public class StringSearching {
    public static void main(String[] args) {
        String string = "ababcdabacdabababababba";
        String target = "abab";
        double bfStart = System.nanoTime();
        System.out.println("Brute Force Algorithm found matches at: " + Arrays.toString(bruteForceSearch(string, target)));
        double bfDifference = System.nanoTime() - bfStart;
        double kmpStart = System.nanoTime();
        System.out.println("kmp Algorithm found matches at: " + Arrays.toString(kmpSearch(string, target)));
        double kmpDifference = System.nanoTime() - kmpStart;
        int percentDif = (int)((int)bfDifference/kmpDifference * 100);
        System.out.println("The brute force algorithm took: " + bfDifference + " ns, while the kmp algorithm took: " + kmpDifference + " ns. \nThe kmp algorithm was roughly " +
                percentDif + "% faster." );
    }

    public static int[] bruteForceSearch(String str, String target) {
        final int n = str.length();
        final int m = target.length();

        int foundCount = 0;
        int[] index = new int[n-m];

        for (int i = 0; i < n-m; i++) {
            if (str.substring(i, i+m).equals(target)) {
                index[foundCount++] = i;
            }
        }
        return Arrays.copyOf(index, foundCount);
    }

    public static int[] kmpSearch(String str, String target) {
        int n = str.length();
        int m = target.length();
        int[] lps =  new int[m];

        computeLPS(target, m, lps);

        int j = 0; //index for taget
        int i = 0; //index for str
        int foundCount = 0;
        int[] resultIndex = new int[n-m];

        while (i < n) {
            if (str.charAt(i) == target.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                resultIndex[foundCount++] = i - j;
                j = lps[j-1]; //Use lps to find next potential matcg
            }
            //Don't match:
            else if(i < n && target.charAt(j) != str.charAt(i)) {
                if (j != 0){ //we have found some character matches
                    j = lps[j-1];
                } else{ //j is equal to 0, increment i and continue looking
                    i++;
                }
            }
        }
        return Arrays.copyOf(resultIndex, foundCount);
    };

    public static void computeLPS(final String target, final int M, final int lps[]){
        int len = 0; //length of previous longest suffix
        int i = 1; //current character being evaluated
        lps[0] = 0; //always 0 to start

        while (i < M){
            if (target.charAt(i) == target.charAt(len)){ //If the characters match, it means we have successfully extended the prefix by one
                len++;
                lps[i] = len;
                i++;
            } else{ //target.charAt(i) != target.charAt(len)
                //If they don't match we try find a smaller prefix that might still match
                if (len != 0){
                    len = lps[len - 1];
                } else{
                    lps[i++] = len;
                }
            }
        }
    }



}
