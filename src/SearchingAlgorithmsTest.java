import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Comparator;

class SearchingAlgorithmsTest {

    private final Comparator<Integer> intComp = Integer::compare;
    private final Comparator<String> strComp = String::compareTo;

    @Test
    void testElementInMiddle() {
        Integer[] arr = {1, 3, 5, 7, 9};
        assertEquals(2, SearchingAlgorithms.binarySearchRecursive(arr, 5, intComp));
    }

    @Test
    void testElementAtStart() {
        Integer[] arr = {1, 3, 5, 7, 9};
        assertEquals(0, SearchingAlgorithms.binarySearchRecursive(arr, 1, intComp));
    }

    @Test
    void testElementAtEnd() {
        Integer[] arr = {1, 3, 5, 7, 9};
        assertEquals(4, SearchingAlgorithms.binarySearchRecursive(arr, 9, intComp));
    }

    @Test
    void testElementMissing() {
        Integer[] arr = {1, 3, 5, 7, 9};
        assertEquals(-1, SearchingAlgorithms.binarySearchRecursive(arr, 4, intComp));
        assertEquals(-1, SearchingAlgorithms.binarySearchRecursive(arr, 10, intComp));
        assertEquals(-1, SearchingAlgorithms.binarySearchRecursive(arr, 0, intComp));
    }

    @Test
    void testSingleElementArray() {
        Integer[] arr = {10};
        assertEquals(0, SearchingAlgorithms.binarySearchRecursive(arr, 10, intComp));
        assertEquals(-1, SearchingAlgorithms.binarySearchRecursive(arr, 5, intComp));
    }

    @Test
    void testEmptyArray() {
        Integer[] arr = {};
        assertEquals(-1, SearchingAlgorithms.binarySearchRecursive(arr, 5, intComp));
    }

    @Test
    void testStringArray() {
        String[] arr = {"apple", "banana", "cherry", "date"};
        assertEquals(2, SearchingAlgorithms.binarySearchRecursive(arr, "cherry", strComp));
        assertEquals(-1, SearchingAlgorithms.binarySearchRecursive(arr, "elderberry", strComp));
    }



//Iterative binary search tests:
    @Test
    void testFoundInMiddle() {
        Integer[] arr = {2, 4, 6, 8, 10, 12, 14};
        // Target 8 is exactly at index 3
        assertEquals(3, SearchingAlgorithms.binarySearchIterative(arr, 8, intComp));
    }

    @Test
    void testFoundAtStart() {
        Integer[] arr = {10, 20, 30, 40};
        assertEquals(0, SearchingAlgorithms.binarySearchIterative(arr, 10, intComp));
    }

    @Test
    void testFoundAtEnd() {
        Integer[] arr = {10, 20, 30, 40};
        assertEquals(3, SearchingAlgorithms.binarySearchIterative(arr, 40, intComp));
    }

    @Test
    void testNotFoundLowerBound() {
        Integer[] arr = {10, 20, 30, 40};
        // Searching for something smaller than the smallest element
        assertEquals(-1, SearchingAlgorithms.binarySearchIterative(arr, 5, intComp));
    }

    @Test
    void testNotFoundUpperBound() {
        Integer[] arr = {10, 20, 30, 40};
        // Searching for something larger than the largest element
        assertEquals(-1, SearchingAlgorithms.binarySearchIterative(arr, 50, intComp));
    }

    @Test
    void testNotFoundInBetween() {
        Integer[] arr = {10, 20, 30, 40};
        // Searching for a gap in the sorted data
        assertEquals(-1, SearchingAlgorithms.binarySearchIterative(arr, 25, intComp));
    }

    @Test
    void testEmptyArrayIterative() {
        Integer[] arr = {};
        assertEquals(-1, SearchingAlgorithms.binarySearchIterative(arr, 10, intComp));
    }

    @Test
    void testSingleElementArrayIterative() {
        Integer[] arr = {100};
        assertEquals(0, SearchingAlgorithms.binarySearchIterative(arr, 100, intComp));
        assertEquals(-1, SearchingAlgorithms.binarySearchIterative(arr, 50, intComp));
    }

    @Test
    void testStringArrayIterative() {
        String[] arr = {"alpha", "beta", "gamma", "zeta"};
        assertEquals(1, SearchingAlgorithms.binarySearchIterative(arr, "beta", strComp));
        assertEquals(-1, SearchingAlgorithms.binarySearchIterative(arr, "delta", strComp));
    }
}

