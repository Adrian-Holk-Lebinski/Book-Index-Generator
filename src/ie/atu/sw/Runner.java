package ie.atu.sw;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * The Runner Method is the main class for this project,
 * This is where the main method is executed and the user is asked for input to generate
 * all the files necessary for the project to work
 */

public class Runner {
	public static Scanner input = new Scanner(System.in);
	public static int option = 0; // used to get user's option for the menu
	public static File textFile;
	public static Set<String> commonWords;
	public static Map<String, WordDetail> dictionary;
	public static FileWriter outputFile;

	public static void main(String[] args) throws Exception {
		// keep looping through menu until user enters 6
		while (option != 6) {
			option = Menu.getMenuOption();

			if (option == 1) {
				textFile = FileManager.GetTextFile();
			} else if (option == 2) {
				commonWords = FileManager.ConfigureCommonWords();
			} else if (option == 3) {
				dictionary = FileManager.ConfigureDictionary(commonWords);
			} else if (option == 4) {
				outputFile = FileManager.GetOutputFile();
			} else if (option == 5) {
				Execute();
			}

		}
	}
	/*
	 * This Method uses the generated dictionary, input file, and output file to generate an index with the help
	 * of the FileReaderThread class
	 */
	public static void Execute() throws Exception {

		BufferedWriter bw = new BufferedWriter(outputFile);
		
		//Uses threads to parse each line from the text file and output the final index
		new FileReaderThread(dictionary).go(textFile.getAbsolutePath()); 
		
		for (Map.Entry<String, WordDetail> word : dictionary.entrySet()) {
			if(word.getValue().pageNumbers.isEmpty() == false) {
				bw.write(word.getKey() + ": " + word.getValue().pageNumbers + ": " + word.getValue().getDefinition() + "\n");
			}
				
		}
		
		System.out.println("\nIndex file was created!\n\nCLICK ENTER TO CONTINUE");
		Scanner input = new Scanner(System.in);
		input.nextLine();
		bw.close();
	}
}