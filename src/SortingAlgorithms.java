import java.util.Random;

public class SortingAlgorithms {

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



    //Bogo Sort:
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





