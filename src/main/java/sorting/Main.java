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
		if (args.length != 2) {
			String msg = "you must provide exactly 2 input args: inputsDir and outputsDir.";
			throw new Exception(msg);
		}
		
		String inputsDir = args[0].replaceAll("/+$", ""); // remove trailing '/' too
		String outputsDir = args[1].replaceAll("/+$", ""); // remove trailing '/' too
		
		LOGGER.info("reading user inputs");
		LOGGER.debug("folder containing input files: " + inputsDir);
		LOGGER.debug("folder to write output files: " + outputsDir);
		
		try {
			
			validateInputs(inputsDir, outputsDir);
			
			processInputs(inputsDir, outputsDir);

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
	
	private static void validateInputs(String inputsDir, String outputsDir) throws Exception {
		File inputsDirFile = new File(inputsDir.toString());
	    File outputsDirFile = new File(outputsDir.toString());
	    
	    if (inputsDirFile.isDirectory() && outputsDirFile.isDirectory()) {
	    	return;
	    } else {
	    	String error = "one of the inputs directories are not a real folder";
	    	throw new Exception(error);
	    			
	    }
	}

	/**
	 * Method for returning results of multiple sorting methods on multiple file inputs
	 * 
	 * @throws Exception
	 */
	private static void processInputs(String inputsDir, String outputsDir) throws Exception {
		
		StackLinked inputFiles = filesInFolder(new File(inputsDir));
		int[] sortCases = {1, 2, 3, 4, 5}; // iterate for each sorting case defined in lab assignment
		Sort sort;
		ListLinked analysis = new ListLinked();
		
		// function variables
		String file;
		int[] data;
		String dataOrder; // retrieved from folder hierarchy of inputs (e.g. ascending, random, reverse)
		
		while (!inputFiles.isEmpty()) {
			file = (String) inputFiles.pop();
			data = readFile(file); // grab data
			dataOrder = getDataOrder(file); // returns the order category for the input (uses parent folder to determine)
			
			for (int sortCase : sortCases ) {
				sort = processData(file, outputsDir, data, data.length, sortCase);
				analysis.append(new OutputData(getSortMethodName(sortCase), dataOrder, sort));
			}
		}
		
		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputsDir + "/report.dat")); // output file
		writeAnalysisData(outputWriter, analysis);
	}
	
	private static String getDataOrder(String file) {
		int index=file.lastIndexOf('/');
		String output = file.substring(0,index);
		index=output.lastIndexOf('/');
		output = output.substring(index+1, output.length());
		return output;
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
	private static Sort processData(String file, String outputsDir, int[] data, int n, int sortCase) throws Exception {
		
		Sort sort;
		int maxDataLength = 50;
		
		System.out.println();
		ListLinked output = new ListLinked();
		output.append("-----------------------------------------------------");
		output.append(String.format("File Input: %s",file));
		output.append(String.format("Sort Name: %s", getSortMethodName(sortCase)));
		output.append(String.format("Description: %s", getSortMethodDescription(sortCase)));
		output.append("");
		output.append("Data Size (N): " + n);
		output.append("N^2: " + n*n);
		output.append("(N+1)*(N/2): " + ((n+1)*(n/2)));
		output.append("3*N*log2(N): " + (3 * n* ((Math.log(n) / Math.log(2)))));
		output.append("");
		
		if (n <= maxDataLength) {
			
			output.append("Original Data: " + Arrays.toString(data));
			sort = executeSort(sortCase, data, n);
			output.append("Sorted Data: " + Arrays.toString(sort.data));
			
		} else {
			
			output.append("Original Data: " + Arrays.toString(data).substring(0, Math.min(n, 100)) + " ...");
			sort = executeSort(sortCase, data, n);
			output.append("Sorted Data: " + Arrays.toString(sort.data).substring(0, Math.min(n, 100)) + " ...");
		}
		
		// print number of comparisons and exchanges
		output.append("# of Comparisons: " + sort.comparisons);
		output.append("# of Exchanges: " + sort.exchanges);
		
		// determine output file path
	    int index = file.lastIndexOf('/');
	    outputsDir += "/" + getSortMethodName(sortCase);
	    
	    File outputsDirFile = new File(outputsDir);
	    if (!outputsDirFile.isDirectory()) { // make housing directory if doesn't exist yet
	    	outputsDirFile.mkdirs();
	    			
	    }
	    
	    String outputFile = outputsDir + "/" + file.substring(index + 1,file.length());
	    writeOutput(outputFile, output); // print and write output
	    
	    return sort;
	}
	
	/**
	 * Returns string representing description of sorting algorithm
	 * @param sortCase
	 * @return
	 * @throws Exception
	 */
	private static String getSortMethodName(int sortCase) throws Exception {
		if (sortCase == 1) {
			
			return "quicksort-first-index";
			
		} else if (sortCase == 2) {

			return "quicksort-to-insertion-100";
			
		} else if (sortCase == 3) {

			return "quicksort-to-insertion-50";
			
		} else if (sortCase == 4) {

			return "quicksort-median-of-3";
			
		} else if (sortCase == 5) {
			
			return "heapsort";
			
		} else {
			String error = "unknown sort case: " + Integer.toString(sortCase);
			LOGGER.error(error);
			throw new Exception(error);
		}
	}
	
	/**
	 * Returns string representing description of sorting algorithm
	 * @param sortCase
	 * @return
	 * @throws Exception
	 */
	private static String getSortMethodDescription(int sortCase) throws Exception {
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
			
			return "QuickSort - Select the median-of-three as the pivot. "
					+ "Treat partitions of size one and two as stopping cases";
			
		} else if (sortCase == 5) {
			
			return "HeapSort - Heapify array then percolate down heap sort";
			
		} else {
			String error = "unknown sort case: " + Integer.toString(sortCase);
			LOGGER.error(error);
			throw new Exception(error);
		}
	}

	/**
	 * Method writes output files and prints output to console
	 * @param outputFile
	 * @param content
	 * @throws Exception 
	 */
	private static void writeOutput(String outputFile, ListLinked content) throws Exception {

		BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile)); // output file
		outputWriter.write("");
		
		String line;
		while (!content.isEmpty()) {
			line = (String) content.remove(0);
			LOGGER.info(line);
			outputWriter.write(line + "\n");
		}
		
		outputWriter.close();
	}
	
	/**
	 * Method writes report of data for later analysis
	 * @param outputFile
	 * @param outputData
	 * @throws Exception 
	 */
	private static void writeAnalysisData(BufferedWriter outputWriter, ListLinked outputData) throws Exception {
		outputWriter.write("");
		outputWriter.write(String.format("%30s %20s %20s %20s %20s \r\n", "SORT NAME", "SORT ORDER", "DATA SIZE", "COMPARISONS", "EXCHANGES"));
		OutputData data;
		while (!outputData.isEmpty()) {
			data = (OutputData) outputData.remove(0);
			outputWriter.write(data.toString());
		}
		
		outputWriter.close();
	}

	/**
	 * Main method for executing sort
	 * @param sortCase
	 * @param data
	 * @throws Exception 
	 */
	private static Sort executeSort(int sortCase, int[] data, int dataLength) throws Exception {
		
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
