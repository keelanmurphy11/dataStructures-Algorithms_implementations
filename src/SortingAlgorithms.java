import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SortingAlgorithms {



//-------------------------------------Quick Sort-------------------------------------//
    public static <E extends Comparable <E>> void quickSort(E[] array){ //Don't have to put in indexs in initial call
        quickSort(array, 0, array.length - 1);
    }

    public static <E extends Comparable <E>> void quickSort(E[] array, int low_index, int high_index){
        //Optimization: Use Insertion Sort for small arrays
        if (low_index + 15 > high_index) {
            insertionSort(array);
            return;
        }

        //Pivot selection: median of three
        int pivotIndex = low_index + (high_index - low_index) / 2;
        if (array[low_index].compareTo(array[pivotIndex]) > 0) swap(array, low_index, pivotIndex);
        if (array[low_index].compareTo(array[high_index]) > 0) swap(array, low_index, high_index);
        if (array[pivotIndex].compareTo(array[high_index]) > 0) swap(array, pivotIndex, high_index);

        E pivotValue = array[pivotIndex];

        int lp = low_index; //left and right pointers
        int rp = high_index - 1;

        swap(array, pivotIndex, high_index); //pivot is now the rightmost element

        while (lp < rp) {
            while (lp < rp && array[lp].compareTo(pivotValue) <= 0) {
                lp++;
            }
            while (rp > lp && array[rp].compareTo(pivotValue) >= 0) {
                rp--;
            }
            if (lp == rp) {
                swap(array, lp, high_index); //Swap pivot from rightmost position to its sorted position
            } else {
                swap(array, lp, rp);
            }
        }

        quickSort(array, low_index, lp-1);
        quickSort(array, lp+1, high_index);
    }


//-------------------------------------Merge Sort-------------------------------------//
//**--------Implementation with focus of maximising efficiency--------**//
    public static <E extends Comparable<E>> void mergeSort(E[] array) {
        if  (array.length <= 1) return;

//Create an auxiliary array just once, this will save costly operations of constantly creating new arrays
        //Reflection used here as generics don't support new E[] directly.
        @SuppressWarnings("unchecked")
        E[] aux = (E[]) Array.newInstance(array.getClass().getComponentType(), array.length);

        mergeSortHelper(array, aux, 0, array.length - 1);
    }

    private static <E extends Comparable<E>> void mergeSortHelper(E[] array, E[] aux, int low, int high){
        if (high <= low) return; //if array length = 1

        int mid = low +(high - low)/2; //calculate midpoint of given range

        mergeSortHelper(array, aux, low, mid);
        mergeSortHelper(array, aux, mid+1, high);

        if (array[mid].compareTo(array[mid+1]) <= 0){ //If the largest item in left is <= smallest in right skip merge
            return;
        }else{
            System.arraycopy (array, low, aux, low, high-low+1); //Copy range we're working on

            int i = low;      // pointer for left side
            int j = mid + 1;  // pointer for right side
            int k = low;      // pointer for merged array

            // 1. Compare while both sides have elements
            while (i <= mid && j <= high) {
                if (aux[i].compareTo(aux[j]) <= 0) {
                    array[k++] = aux[i++];
                } else {
                    array[k++] = aux[j++];
                }
            }

            // 2. Copy remaining elements from the left side
            // Note: We don't need to copy the right side because if any are left,
            // they are already in the correct position in the main array.
            while (i <= mid) {
                array[k++] = aux[i++];
            }
        }
    }



//**-----Implementation with focus of maximising efficiency-----**//
    public static <E extends Comparable<E>> void mergeSortSimple(E[] array) {
        int inLength = array.length;
        if  (inLength <= 1) {
            return;
        }

        int mid =  inLength / 2;
        E[] left = Arrays.copyOfRange(array, 0, mid);
        E[] right = Arrays.copyOfRange(array, mid, inLength);
        mergeSort(left);
        mergeSort(right);

        //Merging arrays:
        int l = 0, r = 0, i = 0;
        int rightLength = right.length;
        int leftLength = left.length;

        while(i < array.length){
            if(l != leftLength && (r == rightLength || left[l].compareTo(right[r]) <= 0)){ //use <= merge sort is supposed to be stable
                array[i++] = left[l++];
            }else{
                array[i++] = right[r++];
            }
        }
    }

//**--------Bottom up merge sort--------**//
    //Less overhead from recursion
    //No chance of a stack overflow
    //Doesn't change the fundamental time or space complexity, only reduces overhead

    public static <E extends Comparable<E>> void bottomUpMergeSort(E[] array){
        if (array == null || array.length < 2) return;

        int n =  array.length;

        Object[] aux = new Object[n];

        //size of subarrays to be merged: 1, 2, 4, 8, 16, ...
        for (int size = 1; size < n; size *= 2){
            for (int leftStart = 0; leftStart < n - size; leftStart += 2*size) {
                int mid = leftStart + size -1;
                int rightEnd = Math.min(leftStart + 2*size - 1, n-1); //Math.min incase it overflows the end of the array

                bottomUpMerge(array, aux, leftStart, mid, rightEnd);
            }
        }
    }

    private static <E extends Comparable<E>> void bottomUpMerge(E[] array, Object[] aux, int low, int mid, int high) {
        //copy range to aux array
        for (int k = low; k < high; k++){
            aux[k] = array[k];
        }

        E[] auxE = Arrays.copyOfRange(array, 0, aux.length);

        //merge back into original array
        int i = low; //pointer to the left half
        int j = mid + 1; //pointer to right half

        for(int k = low; k <= high; k++){
            if (i > mid){
                array[k] = auxE[j++]; //left half is exhausted, fill remainder
            } else if (j > high){
                array[k] = auxE[i++]; //right half is exhausted
            }  else if (auxE[i].compareTo(auxE[j]) <= 0){ //compare elements and appropriately merge
                array[k] = auxE[i++];
            } else  {
                array[k] = auxE[j++];
            }

        }
    }


    //-------------------------------------Shell Sort-------------------------------------//
    public static <E extends Comparable<E>> void shellSort(E[] array){
        int n =  array.length;
        for (int gap = n / 2; gap > 0; gap /= 2){
            for (int i = gap; i < n; i++){
                E temp = array[i];
                int j;

                // Shift earlier gap-sorted elements up until the correct
                // location for arr[i] is found
                for (j = i; j >= gap && array[j - gap].compareTo(temp) > 0; j -= gap) {
                    array[j] = array[j - gap];
                }

                // Put temp (the original arr[i]) in its correct location
                array[j] = temp;
            }
        }
    }



//-------------------------------Bucket Sort--------------------------------//
    public static void bucketSortDec(int[] array, int exp){
        int n = array.length;

        List<Integer>[] buckets = new ArrayList[10];
        for (int i = 0; i < n; i++){
            buckets[i] = new ArrayList<>();
        }

        //scatter into buckets
        for (int i = 0; i < n; i++){
            int bucketIndex = (array[i] /exp) % 10;
            buckets[bucketIndex].add(array[i]);
        }

        //Gather
        int j = 0;
        for (int i = 0; i < 10; i++){
            for (Integer value : buckets[i]){
                array[j++] = value;
            }
        }
    }



//-------------------------------Counting Sort--------------------------------//
    public static void countingSort(int[] array){
        if  (array == null || array.length < 2) return;
        int n =  array.length;

        //Find max in array, determine range
        int max = array[0];
        for(int i = 1; i < n; i++){
            if (array[i] > max) max = array[i];
        }

        //make count array
        int[] count = new int[max+1];

        //Count occurrences of each
        for (int i = 0; i < n; i++){
            count[array[i]]++;
        }

        //update count list to store index pos
        for (int i = 1; i <= max; i++){
            count[i] = count[i]+count[i-1];
        }

        //Build output array
        int[] output = new int[n];
        //start backwards to maintain stability (order of equal elements)
        for (int i = n-1; i >= 0; i--){
            output[count[array[i]]-1] = array[i]; //say i = 2, take arrays 3rd element, say 5, count[5] hold the index position+1 of where the 5 should go
            count[array[i]]--;
        }

        //copy back to array
        for (int i = 0; i < n; i++){
            array[i] = output[i];
        }
    }






//-------------------------------------Bubble Sort-------------------------------------//
    public static <E extends Comparable<E>> void bubbleSort(E[] array){

        boolean swapped;
        int length = array.length;

        for (int i = 0; i < length - 1; i++) {
            swapped = false;
            for (int j = 0; j < length - i - 1; j++){
                if (array[j].compareTo(array[j+1]) > 0){
                    E temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;

                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
//-------------------------------------Insertion Sort-------------------------------------//

    public static <E extends Comparable<E>> void insertionSort(E[] array){
        //start at index 1 as the first element has already sorted itself
        for(int i = 1; i < array.length; i++){
            E temp = array[i]; //The element we are currently positioning
            int j = i-1;
            while(j >= 0 && temp.compareTo(array[j]) < 0 ){
                array[j+1] = array[j]; //shift to the right
                j--;
            }
            array[j+1] = temp;
        }
    }

//Insertion sort for part of a list, used in quick sort.
    public static <E extends Comparable<E>> void insertionSort(E[] array, int low_index, int high_index){
        for(int i = low_index+1; i < high_index+1; i++){
            E temp = array[i]; //The element we are currently positioning
            int j = i-1;
            while(j >= low_index && temp.compareTo(array[j]) < 0 ){
                array[j+1] = array[j]; //shift to the right
                j--;
            }
            array[j+1] = temp;
        }
    }

    public static void insertionSort(int[] array){
        //start at index 1 as the first element has already sorted itself
        for(int i = 1; i < array.length; i++){
            int temp = array[i]; //The element we are currently positioning
            int j = i-1;
            while(j >= 0 && temp < array[j]){
                array[j+1] = array[j]; //shift to the right
                j--;
            }
            array[j+1] = temp;
        }
    }

//-------------------------------------Selection Sort-------------------------------------//
    public static <E extends Comparable<E>> void selectionSort(E[] array){
        if (array == null || array.length < 2) {
            return;
        }
        for (int i = 0; i < array.length-1; i++){
            int min = i;
            for (int j = i + 1; j < array.length; j++){
                if (array[j].compareTo(array[min]) < 0){     // if array[j] < min
                    min = j;
                }
            }
            if (min != i) {
                E temp = array[min];
                array[min] = array[i];
                array[i] = temp;
            }
        }
    }



//-------------------------------------Bogo Sort-------------------------------------//
    public static <E extends Comparable<E>> void bogoSort(E[] array){
        while(!isSorted(array)){
            shuffle(array);
        }
    }

    static <E extends Comparable<E>> boolean isSorted(E[] array){
        for (int i = 0; i < array.length - 1; i++){
            if (array[i].compareTo(array[i+1]) > 0){
                return false;
            }
        }
        return true;
    }

    private static <E extends Comparable<E>> void shuffle(E[] array){
        Random rand = new Random();
        for (int i = 0; i < array.length; i++){
            int randomIndex = rand.nextInt(array.length); //returns an integer up to array.length
            // Swap element at i with the element at randomIndex
            E temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }
    }





    //-------------------------------------General Helper Functions-------------------------------------//
    public static <E extends Comparable <E>>void swap (E[] array, int index1, int index2){
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
}





