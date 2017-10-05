/**
 * Compare two string objects with a preference for words that are
 * lexicographically first (i.e., alphabetical order).  Note that this uses
 * the ASCII values for the strings so case makes a difference and upper
 * case words come before lower case ones.
 *
 * @author Benjamin Kuperman (Fall 2011)
 */

import java.util.Comparator;

class StringComparator implements Comparator<String> {

    /**
     * Performs a case-sensistive comparison of two string objects.
     *
     * @param s1 first string to compare
     * @param s2 second string to compare
     * @return negative if s1 comes before s2, positive if s2 comes before
     *          s1, and 0 if they are equivalent
     */
    public int compare(String s1, String s2){
        return s1.compareTo(s2);
    }

}
