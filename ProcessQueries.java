import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.*;

import org.jsoup.UnsupportedMimeTypeException;

/**
 * TODO - add some comments here.
 *
 * @author Benjamin Kuperman (Fall 2011)
 * @author Alex Peterson & Michael Kemp
 */



public class ProcessQueries {

    private static ArrayList<WebPageIndex> createIndex(String file) throws IOException {
	    
	Scanner URLListFile = new Scanner(new File(file));
	ArrayList<String> URLList = new ArrayList<String>();
	while(URLListFile.hasNextLine()) {
	    String url = URLListFile.nextLine();

	    URLList.add(url);
	}

	
	ArrayList<WebPageIndex> WebPageIndexList = new ArrayList<WebPageIndex>();
	
	for(int i = 0; i < URLList.size(); i++) {
	    try {
		WebPageIndexList.add(new WebPageIndex(URLList.get(i)));
	    }catch(org.jsoup.HttpStatusException e) {
		
	    }catch(java.nio.charset.IllegalCharsetNameException e) {
		
	    }catch(UnsupportedMimeTypeException e) {
		
	    }catch(java.lang.NoClassDefFoundError e) {
		
	    }
	    
	}
	return WebPageIndexList;
	    
    }

    public static void main(String[] args) throws InterruptedException, IOException {
    
	int cutOff = Integer.parseInt(args[1]);
	String file = args[0];
	//String file = "urls-cs";
	//int cutOff = 10;
	
	Scanner URLListFile = new Scanner(new File(file));
	ArrayList<String> URLList = new ArrayList<String>();
	while(URLListFile.hasNextLine()) {
	    String url = URLListFile.nextLine();

	    URLList.add(url);
	}
	
	WebPageLoader loader = new WebPageLoader(URLList, 10);
	
	List<WebPageIndex> IndexList = loader.getResults();
	
	//ArrayList<WebPageIndex> IndexList = createIndex(args[0]);

	
	String userInput;
	
	Console console = System.console();
	boolean go = true;
	
	userInput = console.readLine("Search: ");
	
	MyPriorityQueue<WebPageIndex> indexQueue = new MyPriorityQueue<WebPageIndex>(new URLComparator(userInput));
	for(int i = 0; i < IndexList.size(); i++) {
	    indexQueue.offer(IndexList.get(i));
	}
	for(int i = 0; i < cutOff; i++) {
	    WebPageIndex page = indexQueue.poll();
	    if(page.getCount(userInput) > 0) {
		System.out.println("(Priority = " + page.getCount(userInput) +") "+ page.getUrl());
	    }
	}
	
	
	while(go == true) {
	    userInput = console.readLine("Search: ");
	    
	    if(userInput == "-1") {
		go = false;
		break;
	    }if(go == true) {
		//indexQueue.setComparator(new URLComparator(userInput));
		/*
		 * I was using the set comparator function, but since i was using poll to get the webpageindexes the queue was losing values
		 * so instead I make a new queue after every loop.
		 */
		
		indexQueue = new MyPriorityQueue<WebPageIndex>(new URLComparator(userInput));
		for(int i = 0; i < IndexList.size(); i++) {
		    indexQueue.offer(IndexList.get(i));
		}
		for(int i = 0; i < cutOff; i++) {
		    WebPageIndex page = indexQueue.poll();
		    
		    List<String> phrase = Arrays.asList(userInput.split("\\s+"));
		    int sum = 0;
		    for(int j = 0; j < phrase.size(); j++) {
			sum += page.getCount(phrase.get(j));
		    }
		    
		    if(sum > 0) {
			System.out.println("(Priority = " + sum +") "+ page.getUrl());
		    }
		    
		}
	    }
	    
	    
	}
	
	
	
	
    }

}
