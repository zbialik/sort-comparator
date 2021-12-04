package sorting;

import java.io.FileNotFoundException;
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
		if (args.length != 2) {
			String msg = "you must provide exactly 2 input args: sortCase (1, 2, 3, 4, or 5) and inputFile.";
			throw new Exception(msg);
		}

		int sortCase = Integer.valueOf(args[0]);
		String inputFile = args[1];
		
		LOGGER.info("reading user inputs");
		LOGGER.debug("sortCase: " + sortCase);
		LOGGER.debug("input file: " + inputFile);
		
		try {
			
			int[] data = readFile(inputFile); // grab data
			
			testAllResults(); // TODO: DELETE
//			printResults(sortCase, data); // TODO: UNCOMMENT

		} catch (FileNotFoundException e) {
			LOGGER.error("file not found:" + inputFile);
			e.printStackTrace();
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
	 * test method for returning results of multiple sorting methods on multiple
	 * file inputs
	 * @throws Exception
	 */
	private static void testAllResults() throws Exception {
		
		// TEST INPUTS
		String[] files = {
				"src/test/resources/asc1K.dat", 
				"src/test/resources/rev1K.dat", 
				"src/test/resources/ran1K.dat"
		};
		int[] sortCases = {1, 4};
		
		// function variables
		Sort sort;
		int[] data;
		int n;
		
		int maxDataLength = 500;
		
		for (String file : files) {
			data = readFile(file); // grab data
			n = data.length;
			System.out.println();
			LOGGER.info("--------------------------------------");
			LOGGER.info("FILENAME: {}", file);
			LOGGER.info("n={}, n^2={}, n*log2(n)={}",n, (n*n), (n*(int) (Math.log(n) / Math.log(2))));
			for (int sortCase : sortCases ) {
				LOGGER.info("\tSORT CASE: {}",sortCase);
				if (data.length >= maxDataLength) {
					LOGGER.trace("\tdata too large to print (length >= "+maxDataLength+") - truncating output.");
					LOGGER.trace("\tunsorted data: " + Arrays.toString(data).substring(0, Math.min(data.length, 100)) + " ...");
					
					sort = executeSort(sortCase, data.clone());
					
					LOGGER.trace("\tsorted data: " + Arrays.toString(sort.data).substring(0, Math.min(data.length, 100)) + " ...");
				} else {
					LOGGER.trace("\tunsorted data: " + Arrays.toString(data));
					
					sort = executeSort(sortCase, data.clone());
					
					LOGGER.trace("\tsorted data: " + Arrays.toString(sort.data));
				}
				
				// print number of comparisons and exchanges
				LOGGER.info("\tnumber of comparisons: " + sort.comparisons);
				LOGGER.info("\tnumber of exchanges: " + sort.exchanges);
				
				// clear data
				sort.comparisons = 0;
				sort.exchanges = 0;
			}
		}
		
	}
	
	/**
	 * prints the results desired for the lab
	 * 
	 * @param data
	 */
	private static void printResults(int sortCase, int[] data) {
		
		Sort sort;
		
		int maxDataLength = 500;
		if (data.length >= maxDataLength) {
			LOGGER.info("data too large to print (length >= "+maxDataLength+") - truncating output.");
			LOGGER.info("unsorted data: " + Arrays.toString(data).substring(0, Math.min(data.length, 100)) + " ...");
			
			sort = executeSort(sortCase, data);
			
			LOGGER.info("sorted data: " + Arrays.toString(sort.data).substring(0, Math.min(data.length, 100)) + " ...");
		} else {
			LOGGER.info("unsorted data: " + Arrays.toString(data));
			
			sort = executeSort(sortCase, data);
			
			LOGGER.info("sorted data: " + Arrays.toString(sort.data));
		}
		
		// print number of comparisons and exchanges
		LOGGER.info("------------------------------");
		LOGGER.info("number of data elements: " + data.length);
		LOGGER.info("number of comparisons: " + sort.comparisons);
		LOGGER.info("number of exchanges: " + sort.exchanges);
		
	}

	/**
	 * Main method for executing sort
	 * @param sortCase
	 * @param data
	 */
	private static Sort executeSort(int sortCase, int[] data) {
		
		Sort sort = null;
		
		if (sortCase == 1) { // quick sort with first item as pivot and normal stop
			sort = new QuickSort(data);
		} else if (sortCase == 2) { // quick sort with first item as pivot and insertion stop when partition has 100 elements
			sort = new QuickSortToInsertionSort(data, 100);
		} else if (sortCase == 3) { // quick sort with first item as pivot and insertion stop when partition has 50 elements
			sort = new QuickSortToInsertionSort(data, 50);
		} else if (sortCase == 4) { // quick sort with medium-of-three as pivot and normal stop
			sort = new QuickSortMedianPivot(data);
		} else if (sortCase == 5) { // TODO: heap sort
			
		}
		
		if (sort instanceof QuickSort) {
			((QuickSort) sort).quickSort(0, data.length - 1);
		} else if (sort instanceof HeapSort) {
			// TODO: do heap sort
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

}
