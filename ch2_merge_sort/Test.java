import java.util.*;

public class Test{

    public static void main (String[] args){

        Integer[] a = {6,7,8,9,10, 1,2,3,4,5};
        Integer[] aux = new Integer[10];

        //sort(a, aux, 0, 9);
        merge(a,aux,0,4,9);


        System.out.println(Arrays.toString(a));
    }

    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {

        if (hi <= lo) return ;
        int mid = lo + (hi - lo) / 2;
        sort(a,aux,lo,mid);
        sort(a,aux,mid+1,hi);
        merge(a,aux,lo,mid,hi);
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {


        // copy to aux[]
        for (int k = lo; k <= mid; k++) {
            aux[k] = a[k]; 
        }

        for (int m = mid+1, k = hi; k > mid; k--, m++){
            aux[m] = a[k];
        }

        //for (int j = mid+1; j <= hi; j++)
        //    aux[j] = a[hi-j+mid+1]; 

       System.out.println(Arrays.toString(aux));

        // merge back to a[]
        int i = lo, j = hi;
        for (int k = lo; k <= hi; k++) {
            /*if      (i > mid)              a[k] = aux[j++];  
            else if (j > hi)               a[k] = aux[i++];
            else*/ if ((aux[j].compareTo(
                            aux[i])) < 0)  a[k] = aux[j--];
            else                           a[k] = aux[i++];
        }

    }


} // end of class
