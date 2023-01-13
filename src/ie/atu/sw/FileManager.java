package ie.atu.sw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/*
 * This Class is used to generate the files for the Runner class, it uses the user input to create
 * the dictionary, the output file, and the input file.
 */
public class FileManager {
	private static Scanner input = new Scanner(System.in);
	
	/*
	 * This method gets the text file the user wants to generate the index for
	 */
	public static File GetTextFile() {
		String filePath;
		File file;
		System.out.println("Enter the file path of the desired file (e.g. C:\\books\\text.txt) > ");
		filePath = input.nextLine();
		
		try {
			file = new File(filePath);
			if (!file.exists() || file.isDirectory()) {
				System.out.println("Incorrect Directory, try again");
				return null;
			}
			System.out.println("\nFile has been read!\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
		} catch (Exception e) {
			System.out.println("\nFile not found, try again\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
			return null;
		}
		return file;
	}
	/*
	 * This method generates a FileWriter which is used as the output file for the index later
	 */
	public static FileWriter GetOutputFile() {
		// variables
		FileWriter output;
		String filePath, fileName;
		Scanner keyboard = new Scanner(System.in);

		System.out.print("Enter the DIRECTORY you wish to output the file to (e.g. C:\\Folder) > ");
		filePath = keyboard.next();
		System.out.print("Enter the name of the file you wish to output (without the .txt part) > ");
		fileName = keyboard.next();
		try {
			output = new FileWriter(filePath + "\\" + fileName + ".txt");
			
			System.out.println("\nOutput file created!\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
		} catch (Exception e) {
			System.out.println("\ninvalid output directory... try again\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
			output = null;
		}
		return output;
	}
	/*
	 * This method reads the google-1000.txt file and stores all the words in a String Set
	 */
	public static Set<String> ConfigureCommonWords() {
		Set<String> tempCommonWords = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader("../google-1000.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				tempCommonWords.add(line);
			}
			System.out.println("\nCorrectly Configured Common Words\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
		} catch (FileNotFoundException e) {
			System.out.println("\nIssue finding file, make sure the google-1000.txt"
					+ "file is inside the Project Folder and you are launching this from the bin folder\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tempCommonWords;
	}
	/*
	 * This method uses the string set from the ConfigureCommanWords() Method to read the dictionary into a TreeMap while ignoring
	 * the common 1000 words
	 */
	public static Map<String, WordDetail> ConfigureDictionary(Set<String> commonWords) {
		Map<String, WordDetail> tempDictionary = new TreeMap<>();

		if (commonWords == null) {
			System.out.println("\nYou must first configure the Common Words (Function 2)\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();

			return null;
		}

		// Read the dictionary.csv file line by line and parse the words and definitions
		try (BufferedReader br = new BufferedReader(new FileReader("../dictionary.csv"))) {
			String line;
			while ((line = br.readLine()) != null) { //O(1)
				// Split the line on the comma character to separate the word and definition
				String[] parts = line.split(",");
				String word = parts[0];
				word = word.toLowerCase();
				String definition = parts[1];
				
				WordDetail wordDetail = new WordDetail(definition);
				
				if (!commonWords.contains(word)) {
					tempDictionary.put(word, wordDetail);
				}
			}
			System.out.println("\nDictionary has been configured!\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
		} catch (FileNotFoundException e) {
			System.out.println("\nIssue finding file, make sure the dictionary.txt"
					+ "file is inside the Project Folder and you are launching this from the bin folder\n\nCLICK ENTER TO CONTINUE");
			input.nextLine();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tempDictionary;
	}
}
