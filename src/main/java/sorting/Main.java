package sorting;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import stacks.StackLinked;

public class Main {
	
	private static Logger LOGGER = LogManager.getLogger(Main.class);

	private static FileReader inputReader = null;

	public static void main(String[] args) throws Exception {

		// validate args
		if (args.length != 1) {
			String msg = "you must provide exactly 1 input args: inputsDir.";
			throw new Exception(msg);
		}
		
		String inputsDir = args[0].replaceAll("/+$", ""); // remove trailing '/' too
		
		LOGGER.info("reading user inputs");
		LOGGER.debug("folder containing input files: " + inputsDir);
		
		try {
			
			processInputs(inputsDir);

		} finally {
			try {
				if (inputReader != null) {
					inputReader.close();
				}
			} catch (IOException e) {
				LOGGER.error("error closing stream.");
			}
		}
	}
	
	/**
	 * Method for returning results of multiple sorting methods on multiple file inputs
	 * 
	 * @throws Exception
	 */
	private static void processInputs(String inputsDir) throws Exception {
		
		StackLinked inputFiles = filesInFolder(new File(inputsDir));
		int[] sortCases = {1, 2, 3, 4, 5}; // iterate for each sorting case defined in lab assignment
		
		// TEST
//		inputFiles.clear();
//		inputFiles.push("src/test/resources/inputs/order-categories/reverse/rev10K.dat");
		
		
		// function variables
		String file;
		int[] data;
		
		while (!inputFiles.isEmpty()) {
			file = (String) inputFiles.pop();
			data = readFile(file); // grab data
			for (int sortCase : sortCases ) {
				processFile(file, data, data.length, sortCase);
			}
		}
	}
	
	private static void processFile(String file, int[] data, int n, int sortCase) throws Exception {
		
		System.out.println();
		LOGGER.info("--------------------------------------");
		LOGGER.info("FILENAME: {}", file);

		Sort sort;
		int maxDataLength = 500;
		
		LOGGER.debug("n={}, n^2={}, n*log2(n)={}",n, (n*n), (n*(int) (Math.log(n) / Math.log(2))));
		LOGGER.info("\tSORT CASE: {}", sortCase);
		if (n <= maxDataLength) {
			LOGGER.debug("\tunsorted data: " + Arrays.toString(data));
			
			sort = executeSort(sortCase, data, n);
			
			LOGGER.debug("\tsorted data: " + Arrays.toString(sort.data));
		} else {
			LOGGER.trace("\tdata too large to print (length >= "+maxDataLength+") - truncating output.");
			LOGGER.debug("\tunsorted data: " + Arrays.toString(data).substring(0, Math.min(n, 100)) + " ...");
			
			sort = executeSort(sortCase, data, n);
			
			LOGGER.debug("\tsorted data: " + Arrays.toString(sort.data).substring(0, Math.min(n, 100)) + " ...");
		}
		
		// print number of comparisons and exchanges
		LOGGER.info("\tnumber of comparisons: " + sort.comparisons);
		LOGGER.info("\tnumber of exchanges: " + sort.exchanges);
			
	}

	/**
	 * Main method for executing sort
	 * @param sortCase
	 * @param data
	 */
	private static Sort executeSort(int sortCase, int[] data, int dataLength) {
		
		Sort sort;
		
		if (sortCase == 1) { // quick sort with first item as pivot and normal stop
			sort = new QuickSort(data);
		} else if (sortCase == 2) { // quick sort with first item as pivot and insertion stop when partition has 100 elements
			sort = new QuickSortToInsertionSort(data, 100);
		} else if (sortCase == 3) { // quick sort with first item as pivot and insertion stop when partition has 50 elements
			sort = new QuickSortToInsertionSort(data, 50);
		} else if (sortCase == 4) { // quick sort with medium-of-three as pivot and normal stop
			sort = new QuickSortMedianPivot(data);
		} else if (sortCase == 5) { // heap sort
			sort = new HeapSort(data);
		} else {
			sort = new Sort(data); // default to super class
		}
		
		if (sort instanceof QuickSort) {
			((QuickSort) sort).quickSort(0, dataLength - 1);
		} else if (sort instanceof HeapSort) {
			((HeapSort) sort).heapSort();
		}
		
		return sort;
		
	}

	/**
	 * Reads the input file and returns the integer array for the input data
	 * @param inputFile
	 * @return data
	 * @throws Exception 
	 */
	private static int[] readFile(String inputFile) throws Exception {
		int cInt;
		String dataEntryString = "";
		int dataEntry;
		StackLinked auxStack = new StackLinked();
		inputReader = new FileReader(inputFile); // input file
		
		// read file and append each number to auxilary stack
		while ((cInt = inputReader.read()) != -1) {
			
			if (!isWhiteSpace(cInt)) { // if character is not white space: append to string
				
				char c = (char) cInt;
				dataEntryString += c;
				
			} else if (dataEntryString != "") {
				dataEntry = Integer.valueOf(dataEntryString);
				auxStack.push(dataEntry);
				dataEntryString = "";
			}
		}
		
		if (dataEntryString != "") { // add last data entry assuming
			LOGGER.trace("Adding last data entry: " + dataEntryString);
			dataEntry = Integer.valueOf(dataEntryString);
			auxStack.push(dataEntry);
			dataEntryString = "";
		}
		
		// initialize int[] of size of auxilary stack
		int[] data = new int[auxStack.size()];
		
		// loop and pop all elements of aux stack to int[]
		int i = auxStack.size() - 1;
		while (!auxStack.isEmpty()) {
			data[i] = (int) auxStack.pop();
			i--;
		}
		
		return data;
	}

	/**
	 * Checks if the following character is white space
	 * @param cInt
	 * @return
	 * @throws Exception
	 */
	private static boolean isWhiteSpace(int cInt) throws Exception {

		char c = (char) cInt;
		if (!String.valueOf(cInt).matches(".") && !String.valueOf(c).equals(" ") && !String.valueOf((char) cInt).equals("\n")
				&& !String.valueOf((char) cInt).equals("\r")) {
			return false;
		} else {
			return true;
		}
	}
	
	private static StackLinked filesInFolder(final File folder) throws Exception {
		StackLinked files = new StackLinked();
		StackLinked subfiles;
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	subfiles = filesInFolder(fileEntry);
	        	while (!subfiles.isEmpty()) {
	        		files.push(subfiles.pop());
	        	}
	        } else {
	            files.push(fileEntry.getPath());
	        }
	    }
		return files;
	}

}
