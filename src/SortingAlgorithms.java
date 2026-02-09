import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class SortingAlgorithms {

//-------------------------------------Merge Sort-------------------------------------//
//Implementation with focus of maximising efficiency:
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



    //Simple Implementation
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

    private static <E extends Comparable<E>> boolean isSorted(E[] array){
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
}





