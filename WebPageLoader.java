/**
 * A class that is designed to simplify the parallel fetching
 * of web pages and other URLs to help speed up the building
 * of the priority queue for the search engine project.
 * 
 *  Much of the code/design comes from examples posted on:
 *  
 *      http://www.vogella.de/articles/JavaConcurrency/article.html
 * 
 * @author Benjamin Kuperman (Spring 2012)
 *
 */

import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;


public class WebPageLoader {
    
    /** List of WebPageIndex classes that are processed from web pages */
    private List<WebPageIndex> results;
    /** List of error messages from URLs that weren't loaded into WebPageIndex results */
    private List<String> errors;
    
    /**
     * Process a list of URLs into WebPageIndex objects.  This constructor will
     * not complete until it has attempted to fetch all of the URLs.  Note that 
     * it does display information about the activity to System.out.  Errors are
     * stored in a list instead of being immediately displayed.
     * @param todoList List of URLs to process
     * @param threadCount Number of parallel fetches of URLs to allow
     */
    public WebPageLoader(List<String> todoList, int threadCount) {
	
	if (threadCount < 1) {
	    throw new IllegalArgumentException("Number of requested threads must be positive: " + threadCount);
	}
	
	results = Collections.synchronizedList(new LinkedList<WebPageIndex>());
	errors = Collections.synchronizedList(new LinkedList<String>());

	// Based off of examples from http://www.vogella.de/articles/JavaConcurrency/article.html
	
	// build a pool of workers
	ExecutorService executor = Executors.newFixedThreadPool(threadCount);
	
	// a place to store the results
	List<Future<WebPageIndex>> futureResults = new ArrayList<Future<WebPageIndex>>(todoList.size());

	// populate the list of expected results
	for (String s : todoList ) {
	    Callable<WebPageIndex> worker = new WebPageLoaderThread(s);
	    Future<WebPageIndex> submit = executor.submit(worker);
	    futureResults.add(submit);
	}
	
	// process the results
	Future<WebPageIndex> wpi = null;
	Iterator<Future<WebPageIndex>> wpiitr = futureResults.iterator();
	int total = futureResults.size();
	while (wpiitr.hasNext()) {
	    wpi = wpiitr.next();
	    try {
		// get the results from the worker thread
		results.add(wpi.get());
	    } catch (InterruptedException e) {
		errors.add( "Interrupted exception: " + e.getCause().getMessage());
	    } catch (ExecutionException e) {
		Throwable cause = e.getCause();
		if (cause instanceof FileNotFoundException) {
		    errors.add("Could not open file: " + e.getCause().getMessage());
		} else if (cause instanceof MalformedURLException) {
		    errors.add("Could not process URL: " + e.getCause().getMessage());
		} else if (cause instanceof IOException) {
		    errors.add("Could not open URL: " + e.getCause().getMessage());
		} else {
		    errors.add("Unexpected execution exception: " + e.getCause().getMessage());
		}
	    } catch (Exception e) {
		errors.add("Unexpected exception: " + e.getCause().getMessage());
	    }
	    // Status message for updating user
	    System.out.print("\rFetched: " + results.size() + "   Errors: " + errors.size() + "   out of " + total);
	}
	
	System.out.println("");		// finishes off the status display
	
	// wait here until all of the threads are done
	executor.shutdown();
    }
    
    /**
     * Helper class to allow parallel web page fetches.
     */
    private class WebPageLoaderThread implements Callable<WebPageIndex> {

	private String url;
	
	public WebPageLoaderThread(String url) {
	    this.url = url;
	}
	
	@Override
	public WebPageIndex call() throws Exception {
	    return new WebPageIndex(url);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
	    return this.url;
	}
    }

    
    /**
     * Accessor method to get the list of successful page indexes.
     * @return list of WebPageIndex objects from fetched pages
     */
    public List<WebPageIndex> getResults() {
	return this.results;
    }
    
    /**
     * Accessor method to get the list of errors.
     * @return list of error messages for un-indexed pages
     */
    public List<String> getErrors() {
	return this.errors;
    }

    public static void main(String[] args) {

	LinkedList<String> testing = new LinkedList<String>();
	
	testing.add("http://www.cs.oberlin.edu/");
	testing.add("http://www.google.com/");
	testing.add("http://www.invalid/");
	testing.add("/etc/passwd");
	testing.add("/etc/passwdQ");
	
	WebPageLoader wpl = new WebPageLoader(testing, 5);

	System.out.println("Errors");
	System.out.println("======");
	int x=1;
	for (String err: wpl.getErrors()) {
	    System.out.println(x+". "+ err);
	    x++;
	}
    }
}
