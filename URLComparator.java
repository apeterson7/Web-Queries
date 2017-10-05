import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class URLComparator implements Comparator<WebPageIndex>{

    
    private String query;
    
    public URLComparator(String q) {
	query = q;
    }
    
    /**
     * 
     * @param page1 is the first page we are looking at
     * @param page2 is the second page we are looking at
     * 
     * If the query is one word we compare the word counts on the respective pages of it
     * 
     * If the query is multiple words we find the sum of the word counts 
     * of each word in the query in the respective pages and compare those
     * 
     * @return -1,0, or 1 if the first result is less than, equal to, or greater than the second.
     */
    @Override
    public int compare(WebPageIndex page1, WebPageIndex page2) {
	if(query.split("\\s+").length == 1) {
	    if(page1.getCount(query) > page2.getCount(query)) {
		return -1;
	    }else if(page1.getCount(query) < page2.getCount(query)) {
		return 1;    
	    }else {
		return 0;
	    }
	}else {
	    List<String> phrase = Arrays.asList(query.split("\\s+"));
	    int sum1 = 0;
	    int sum2 = 0;
	    
	    for(int i = 0; i < phrase.size(); i++) {
		sum1 += page1.getCount(phrase.get(i));
		sum2 += page2.getCount(phrase.get(i));
	    }
	    
	    if(sum1 > sum2) {
		return -1;
		    
	    }else if(sum1 < sum2) {
		return 1;
	    }else {
		return 0;
	    }
	}
	

    }



}
