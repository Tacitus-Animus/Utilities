package utils.io;

import java.util.List;
import java.util.Scanner;

/**
* <h1>Input Class</h1>
* The Input program implements a Scanner Object to
* retrieve, filter, and parse user input. 
* This makes it easy for the Client to get desired/proper
* input from the user.
* <p>
* <code>private static Scanner input = new Scanner(System.in);</code>
* <p> 
* Note: Most methods here contain a loop in which user won't
* be able to break out of until user satisfies method conditions.
* <p>
* <code>float number = Input.getFloat("Please Enter a a floating point value: ");</code>
* <p>
* Note: All methods need a prompt specified by Client that will be displayed to the user.
* @see Scanner
* 
* @author  Alexander J Paul
* @version 2.1
* @since   2017-SEP-11 
*/
public class Input 
{
	/**
	 * Scanner Object used to get basic input from user.
	 */
	private static Scanner input = new Scanner(System.in);
	/**
	 * <h2>getStringln</h2>
	 * This method is used to get a line of String that isn't empty else prints out
	 * input error. 
	 * @param prompt This is what will be displayed to the user prior to input
	 * <p> Note: prompt is printed out by System.out.print().
	 * @return String that isn't empty and doesn't contain whitespace on the front and back ends of it.
	 */
	public static String getStringln(String prompt) 
	{		
		while(true) 
		{
			System.out.print(prompt);
			String storedString = input.nextLine().trim();
			if(storedString.isEmpty()) System.out.println("Invalid Input: Nothing entered.");
			else return storedString;
		}
	}
	/**
	 * <h2>getString</h2>
	 * This method makes use of method getStringln() to return a line of String from the user.
	 * Prints input error if it contains whitespace between non-whitespace characters.
	 * This makes it possible to only get a word/phrase from the user.
	 * @return String that doesn't contain whitespace between characters;
	 */
	public static String getString(String prompt) 
	{		
		while(true) 
		{	
			String storedString = getStringln(prompt);
			if(storedString.contains(" ")) System.out.println("Invalid Input: Too many variables entered.");
			else return storedString;
		}
	}
	/**
	 * <h2>getChar</h2>
	 * This method makes use of Method getString() to return a String from the user.
	 * Prints input error if the String length is greater than 1.
	 * This makes it possible to only get one char from the user.
	 * @return char stored at index 0 if String.
	 * @see Character
	 */
	public static char getChar(String prompt) 
	{		
		while(true) 
		{	
			String storedString = getString(prompt);
			if(storedString.length() > 1) System.out.println("Invalid Input: Too Long.");
			else return storedString.charAt(0);
		}
	}
	/**
	 * <h2>getLetter</h2>
	 * This method makes use of the method getChar() to return a char from user.
	 * Prints input error if char isn't a letter.
	 * @return char that is a letter only. (a-z,A-Z)
	 * @see Character
	 */
	public static char getLetter(String prompt) 
	{		
		while(true) 
		{
			char storedChar = getChar(prompt);
			if(!Character.isLetter(storedChar)) System.out.println("Invalid Input: Not a Letter.");
			else return storedChar;
		}
	}
	/**
	 * <h2>getDigit</h2>
	 * This method makes use of method getChar() to return char from user.
	 * Prints input error if char isn't a digit.
	 * @return char that is a digit only. (0-9)
	 * @see Character
	 */
	public static char getDigit(String prompt) 
	{		
		while(true) 
		{
			char storedChar = getChar(prompt);
			if(!Character.isDigit(storedChar)) System.out.println("Invalid Input: Not a Digit.");
			else return storedChar;;
		}
	}
	/**
	 * <h2>getLetterRange</h2>
	 * This method makes use of method getChar() to return char from user.
	 * Prints input error if char isn't a letter in range of specified values.	 
	 * @param min value user can input.
	 * @param max value user can input.
	 * @return char value which is a letter within min and max values.
	 */
	public static char getLetterRange(String prompt, char min, char max) 
	{		
		while(true) 
		{
			char storedChar = getLetter(prompt);
			if(storedChar < min || storedChar > max) System.out.println("Invalid Input: Out of Range.");
			else return storedChar;
		}
	}
	/**
	 * <h2>getDigitRange</h2>
	 * This method makes use of method getDigit() to return char from user.
	 * Prints input error if char isn't a digit in range of specified values.	 
	 * @param min value user can input.
	 * @param max value user can input.
	 * @return char value which is a digit within min and max values.
	 */
	public static char getDigitRange(String prompt, char min, char max) 
	{
		while(true) 
		{
			char storedChar = getDigit(prompt);
			if(storedChar < min || storedChar > max) System.out.println("Invalid Input: Out of Range.");
			else return storedChar;;
		}
	}
	/** 
	 * <h2>getDouble</h2>
	 * This Method prints out input error if it can't parse double from getString().
	 * @return double value.
	 */
	public static double getDouble(String prompt) 
	{
		while(true) 
		{
			try{ 
				return Double.parseDouble(getString(prompt)); 
			}catch(NumberFormatException E){ 
				System.out.println("Invalid Input: Not a Number."); 
			}
		}
	}
	/** 
	 * <h2>getFloat</h2>
	 * This Method prints out input error if it can't parse float from getString().
	 * @return float value.
	 */
	public static float getFloat(String prompt) 
	{
		while(true) 
		{
			try{ 
				return Float.parseFloat(getString(prompt)); 
			}catch(NumberFormatException E){ 
				System.out.println("Invalid Input: Not a Number."); 
			}
		}
	}
	/** 
	 * <h2>getInt</h2>
	 * This Method prints out input error if getFloat() method returns a non Whole Number/Integer.
	 * <p> It checks this by checking it's floating point value by it's casted int value. Quick and dirty >;)
	 * <p> Thank you StackOverflow... but their are limitations. 
	 * @return int value.
	 */
	public static int getInt(String prompt) 
	{
		while(true) 
		{
			float storedFloat = getFloat(prompt);
			if(storedFloat % 1 != 0) System.out.println("Invalid Input: Not a Whole Number."); 
			else return (int)storedFloat;
		}
	}
	/**
	 * <h2>getIntRange</h2>
	 * This method makes use of method getInt() to return char from user.
	 * Prints input error if int isn't in range of specified values.	 
	 * @param min value user can input.
	 * @param max value user can input.
	 * @return int value which is within min and max values.
	 */
	public static int getIntRange(String prompt, int min, int max)
	{				
		while(true)
		{
			int storedInt = getInt(prompt);
			if(storedInt < min || storedInt > max) System.out.println("Invalid Input: Out of Range.");
			else return storedInt;
		}
	}
	/**
	 * <h2>getDoubleRange</h2>
	 * This method makes use of method getDouble() to return double from user.
	 * Prints input error if double isn't in range of specified values.	 
	 * @param min value user can input.
	 * @param max value user can input.
	 * @return double value which is within min and max values.
	 */
	public static double getDoubleRange(String prompt, double min, double max) 
	{				
		while(true)
		{
			double storedDouble = getDouble(prompt);
			if(storedDouble < min || storedDouble > max) System.out.println("Invalid Input: Out of Range.");
			else return storedDouble;
		}
	}
	/**
	 * <h2>getFloatRange</h2>
	 * This method makes use of method getFloat() to return float from user.
	 * Prints input error if float isn't in range of specified values.	 
	 * @param min value user can input.
	 * @param max value user can input.
	 * @return float value which is within min and max values.
	 */
	public static float getFloatRange(String prompt, float min, float max) 
	{				
		while(true)
		{
			float storedFloat = getFloat(prompt);
			if(storedFloat < min || storedFloat > max) System.out.println("Invalid Input: Out of Range.");
			else return storedFloat;
		}
	}
	public static <T> T getListOption(List<T> pool_of_things) {
		
		for(int i = 1;  i <= pool_of_things.size(); i++) {
			System.out.println(i + ". " + pool_of_things.get(i - 1));
		}
		
		int option = Input.getIntRange("Choose Probability Object 1: ", 1, pool_of_things.size()) - 1;
		
		return pool_of_things.remove(option);
	}
	
}
