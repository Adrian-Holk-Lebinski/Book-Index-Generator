package ie.atu.sw;

import java.util.Scanner;

/*
 * This class prints out the menu into the console whenever its only method "getMenuOption" is called
 * and it returns and integer value for the option
 */
public class Menu {
	static Scanner input = new Scanner(System.in);
		
	static public int getMenuOption() {
		System.out.println("************************************************************");
		System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*              Virtual Threaded Text Indexer               *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Text File");
		System.out.println("(2) Configure Common Words");
		System.out.println("(3) Configure Dictionary");
		System.out.println("(4) Specify Output File");
		System.out.println("(5) Execute");
		System.out.println("(6) Quit");

		// Output a menu of options and solicit text from the user
		System.out.print("Select Option [1-6]>");
		int option = input.nextInt();

		return option;
	}
}