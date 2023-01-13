package ie.atu.sw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/*
 * This Class uses Virtual Threads to read in each line from the File provided and store the page numbers from each word to the dictionary
 * provided in the constructor
 */
public class FileReaderThread {
    private int lineNumber = 0;
    private int pageNumber = 0;
    private Map<String, WordDetail> dictionary;
    
    public FileReaderThread(Map<String, WordDetail> dictionary) {
    	this.dictionary = dictionary;
    }
    /*
     * This method starts the process of virtual threads
     */
    public void go(String book) throws Exception{
    	long startTime = System.currentTimeMillis();
    	try (var es = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<Void>> tasks = new ArrayList<>();
            Files.lines(Paths.get(book)).forEach(text -> tasks.add(() -> {
                process(text, ++lineNumber);
                return null;
            }));
            List<Future<Void>> futures = es.invokeAll(tasks);
            for (Future<Void> future : futures) {
                future.get();
            }
        }
    	long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("\nElapsed time: " + elapsedTime + " milliseconds");
    }
    /*
     * Each thread goes into this method and processes the lines of code
     */
    public synchronized void process(String text, int lineNumber) {
    	
    	if (lineNumber % 40 == 0)
    		pageNumber++; // every 40 lines is a page
    	
        Arrays.stream(text.split("\\s+")).forEach(word -> {
        	if (this.dictionary.containsKey(word)) {// if the dictionary contains the word
				if (!this.dictionary.get(word).pageNumbers.contains(pageNumber)) {// if the pageNumber is not already											// entered into the words index
					this.dictionary.get(word).pageNumbers.add(pageNumber);// add page number
				}
			}
        });
    }
}




