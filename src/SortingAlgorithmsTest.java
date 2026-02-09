import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SortingAlgorithmsTest {

    // Helper to check if any generic array is sorted
    private <E extends Comparable<E>> boolean isSorted(E[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].compareTo(array[i + 1]) > 0) return false;
        }
        return true;
    }

    @Test
    void testBubbleSort() {
        Integer[] data = {5, 1, 4, 2, 8};
        SortingAlgorithms.bubbleSort(data);
        assertArrayEquals(new Integer[]{1, 2, 4, 5, 8}, data);
    }

    @Test
    void testInsertionSortGeneric() {
        String[] data = {"banana", "apple", "cherry"};
        SortingAlgorithms.insertionSort(data);
        assertArrayEquals(new String[]{"apple", "banana", "cherry"}, data);
    }

    @Test
    void testInsertionSortPrimitive() {
        int[] data = {10, -1, 3, 0};
        SortingAlgorithms.insertionSort(data);
        assertArrayEquals(new int[]{-1, 0, 3, 10}, data);
    }

    @Test
    void testSelectionSort() {
        Integer[] data = {9, 7, 5, 3, 1}; // Reverse sorted
        SortingAlgorithms.selectionSort(data);
        assertTrue(isSorted(data));
    }

    @Test
    void testBogoSortSmall() {
        // Warning: Do not use large arrays for Bogo Sort! 
        // 4 or 5 elements is the safe "waiting" limit.
        Integer[] data = {3, 1, 2};
        SortingAlgorithms.bogoSort(data);
        assertArrayEquals(new Integer[]{1, 2, 3}, data);
    }

    @Test
    void testEdgeCases() {
        // Empty array
        Integer[] empty = {};
        SortingAlgorithms.selectionSort(empty);
        assertEquals(0, empty.length);

        // Single element
        Integer[] single = {42};
        SortingAlgorithms.bubbleSort(single);
        assertEquals(42, single[0]);
    }
}