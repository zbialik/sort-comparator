package sorting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lists.ListLinked;
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
		inputFiles.clear();
		inputFiles.push("src/test/resources/inputs/order-categories/reverse/rev10K.dat");
		
		
		// function variables
		String file;
		int[] data;
		
		while (!inputFiles.isEmpty()) {
			file = (String) inputFiles.pop();
			data = readFile(file); // grab data
			for (int sortCase : sortCases ) {
				processData(file, data, data.length, sortCase);
			}
		}
	}
	
	/**
	 * Method sorts the provided data using an algorithm identified with sortCase
	 * 
	 * The method will print the number of comparisons and exchanges that occur for each thread, 
	 * as well as provide this data in an output file.
	 * 
	 * For data size 50 or less, the method will print the unsorted data and the sorted data
	 * out to the console. For data larger than this, the method will truncate the output.
	 * 
	 * @param file
	 * @param data
	 * @param n
	 * @param sortCase
	 * @throws Exception
	 */
	private static void processData(String file, int[] data, int n, int sortCase) throws Exception {
		
		Sort sort;
		int maxDataLength = 50;
		
		System.out.println();
		ListLinked output = new ListLinked();
		output.append("--------------------------------------");
		output.append(String.format("FILENAME: %s",file));
		output.append(String.format("SORT METHOD: %s", getSortMethodName(sortCase)));
		
//		TODO: DELETE after testing completed
//		output.append(String.format("n=%d, n^2=%d, n*log2(n)=%d",n, (n*n), (n*(int) (Math.log(n) / Math.log(2)))));
		
		if (n <= maxDataLength) {
			
			output.append("\tunsorted data: " + Arrays.toString(data));
			sort = executeSort(sortCase, data, n);
			output.append("\tsorted data: " + Arrays.toString(sort.data));
			
		} else {
			output.append("\tunsorted data: " + Arrays.toString(data).substring(0, Math.min(n, 100)) + " ...");
			
			sort = executeSort(sortCase, data, n);

			output.append("\tsorted data: " + Arrays.toString(sort.data).substring(0, Math.min(n, 100)) + " ...");
		}
		
		// print number of comparisons and exchanges
		output.append("\tnumber of comparisons: " + sort.comparisons);
		output.append("\tnumber of exchanges: " + sort.exchanges);
		output(file, output); // print and write output
	}
	
	/**
	 * Returns string representing description of sorting algorithm
	 * @param sortCase
	 * @return
	 * @throws Exception
	 */
	private static String getSortMethodName(int sortCase) throws Exception {
		if (sortCase == 1) {
			
			return "QuickSort - Select the first item of the partition as the pivot. "
					+ "Treat partitions of size one and two as stopping cases.";
			
		} else if (sortCase == 2) {
			
			return "QuickSort - Select the first item of the partition as the pivot. "
					+ "For a partition of size 100 or less, use an insertion sort to finish.";
			
		} else if (sortCase == 3) {

			return "QuickSort - Select the first item of the partition as the pivot. "
					+ "For a partition of size 50 or less, use an insertion sort to finish.";
			
		} else if (sortCase == 4) {
			
			return "QuickSort - Select the first item of the partition as the pivot. "
					+ "Treat partitions of size one and two as stopping cases.";
			
		} else if (sortCase == 5) {
			
			return "Select the median-of-three as the pivot. "
					+ "Treat partitions of size one and two as stopping cases";
			
		} else {
			String error = "unknown sort case: " + Integer.toString(sortCase);
			LOGGER.error(error);
			throw new Exception(error);
		}
	}

	/**
	 * Method writes 
	 * @param outputFile
	 * @param data
	 * @throws Exception 
	 */
	private static void output(String outputFile, ListLinked content) throws Exception {

		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile)); // output file
		
		String line;
		while (!content.isEmpty()) {
			line = (String) content.remove(0);
			LOGGER.info(line);
			outputWriter.write(line);
		}

		outputWriter.write("");
		
		
		
		outputWriter.close();
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
