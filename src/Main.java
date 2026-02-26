import java.util.Arrays;

public class Main{
    public static void main(String[] args){
        Integer[] numbers = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] simpleNumbers = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        SortingAlgorithms.bottomUpMergeSort(numbers);
        System.out.println(Arrays.toString(numbers));

        SortingAlgorithms.bucketSortDec(simpleNumbers, 1);
        System.out.println(Arrays.toString(simpleNumbers));
    }
}
