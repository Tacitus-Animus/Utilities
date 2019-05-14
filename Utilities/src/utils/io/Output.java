package utils.io;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
* @author  Alexander J Paul
* @version 1.1
* @since   2017-SEP-11 
 */
public class Output 
{
	private static int milliSeconds = 250;
	/**
	 * This method prints out String in typewriter like fashion.
	 * @param output - String to be printed out.
	 */
	public static void type(String output)
	{
		for(char index : output.toCharArray())
		{
			System.out.print(index);
			try {
				Thread.sleep(milliSeconds);
			} catch (InterruptedException e) {
				//Interrupted
			}
		}
		System.out.println();
	}
	/**
	 * This method prints out Strings in typewriter like fashion.
	 * @param output - Strings to be printed out.
	 */
	public static void type(String... output)
	{
		for(String index : output)
		{
			type(index);
		}
	}
	/**
	 * 
	 * @param milliSeconds - The delay between printing out Strings when type() method is invoked.
	 */
	public static void setDelay(int milliSeconds)
	{
		if(milliSeconds < 0) Output.milliSeconds = 0;
		else if(milliSeconds > 1000) Output.milliSeconds = 1000;
		else Output.milliSeconds = milliSeconds;
	}
	
	public static void testSpeed(String taskName, Runnable task) {
		System.out.println("Start " + taskName);
		long start = System.currentTimeMillis();
		
		task.run();
		
		long duration = System.currentTimeMillis() - start;
		System.out.printf("End %s\n"
						+ "Time: %d.%d seconds\n", taskName,TimeUnit.MILLISECONDS.toSeconds(duration), 
															  TimeUnit.MILLISECONDS.toMillis(duration));
	}
	
	/**
	 * Create a consumer of a {@code ThreadLocalRandom}. Randomly choose variables to change. Able to change variables randomly within specified bounds. 
	 * @param iterations - number of times to randomly choose a variable to change.
	 * @param timeDelay - the delay between iterations.
	 * @param randomOptions - 
	 */
	public static void simulateChange(int iterations, long timeDelay, List<Consumer<ThreadLocalRandom>> randomOptions) {
		
		final ThreadLocalRandom random = ThreadLocalRandom.current();
				
		for(;iterations > 0; iterations--) {
			randomOptions.get(random.nextInt(randomOptions.size())).accept(random);
			try {
				Thread.sleep(timeDelay);
			} catch (InterruptedException e) {
				//Do nothing...
			}
		}
	}
	
}
