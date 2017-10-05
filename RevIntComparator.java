import java.util.Comparator;

/**
 * 
 * @author apeterso
 *simple comparator for testing purposes
 *
 */

class RevIntComparator implements Comparator<Integer> {


    public int compare(Integer i1, Integer i2){
        if(i1 > i2) {
            return -1;
        }else if (i1 < i2) {
            return 1;
        }else {
            return 0;
        }
    }

}