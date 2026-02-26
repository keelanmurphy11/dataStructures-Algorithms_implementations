import java.util.Comparator;

public class SearchingAlgorithms {
    public static <T extends Comparable<T>> int binarySearchRecursive(final T[] array, final T elem, final Comparator<T> comparator){
        return binarySearchHelper(array, elem, 0, array.length - 1, comparator);
    }

    private static <T extends Comparable<T>> int binarySearchHelper(final T[] array, final T elem, int low, int high, final Comparator<T> comparator){
        //Base case: list is exhausted
        if(low > high) {
            return -1;
        }

        int mid = low + (high-low)/2; //Better to calc this way, avoid chance of int overflow
        int cmp = comparator.compare(array[mid], elem;

        //base case
        if (cmp == 0){
            return mid;
        }
        else if (cmp > 0){
            return binarySearchHelper(array, elem, low, mid - 1, comparator);
        }
        else{
            return binarySearchHelper(array, elem, mid + 1, high, comparator);
        }
    }
}
