package utils.Collections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Table of rows and columns mapped to values.
 * So far, Table is a multi-purpose Data-Structure. 
 * Used to retrieve data from a 2-D Array
 * via user-specified row and column keys.
 * Row and column intervals can have separate characteristics,  
 * such as min, max, and intervals.
 * This only supports fixed intervals.
 * Data can be interpolated between row and column intervals.
 * <p>
 * Main issues discovered from reading Effective Java Edition 2 
 * is the code smell of driving methods using try catching exceptions.
 * Handling Exceptions can be expensive.
 * 
 * @author Alexander Paul
 *
 */
public class Table {
	
	/**
	 * 2-D array representation of numeric values constrained to rows and columns.
	 */
	private final float[][] table;
	
	/**
	 * Only used to parse the file to get the table, but now isn't used.
	 * Might be useful to solve an edge case involving proper number of row/col IRT intervals.
	 */
	private final int tableWidth, tableHeight;
	
	/**
	 * A map of numeric intervals mapped to corresponding index of the rows and columns of the table.
	 */
	private final Map<Float, Integer> rowMap, columnMap;

	/**
	 * Set of numeric values representing the intervals that are mapped to corresponding index of the rows and columns of the table.
	 */
	private final NavigableSet<Float> rowIntervals, columnIntervals;	
	
	private final float rowMin, rowMax, columnMin, columnMax;
	
	/**
	 * Creates a Table using the given file path, and the given Row and Column Bounds.
	 * Updated version allows the {@code float[][]} table to be directly inserted to avoid file look up and parsing.
	 * @param path - to parse the file from.
	 * @param rowBounds - containing the min, max, and interval properties of this table's row.
	 * @param columnBounds - containing the min, max, and interval properties of this table's column.
	 */
	private Table(final Data path, final Boundary rowBounds, final Boundary columnBounds) {
		this.table = path.table;
		rowMap = rowBounds.map;
		columnMap = columnBounds.map;
		rowIntervals = new TreeSet<>(rowMap.keySet());
		columnIntervals = new TreeSet<>(columnMap.keySet());
		rowMin = rowBounds.min;
		rowMax = rowBounds.max;
		columnMin = columnBounds.min;
		columnMax = columnBounds.max;
		tableWidth = path.tableWidth;
		tableHeight = path.tableHeight;
	}
	
	/**
	 * @return a builder for Table construction.
	 */
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * Used to implement the Builder Pattern in Table Creation.
	 * TODO: Didn't implement a fail-safe for null values for mins and maxs.
	 * @author Alexander Paul
	 */
	public static class Builder {
		
		String path;
		float[][] table;
		float rowMin, rowMax, rowInterval, columnMin, columnMax, columnInterval;

		public Builder withData(final String path) {
			this.path = path;
			return this;
		}
		
		public Builder withData(final float[][] table) {
			this.table = table;
			return this;
		}
		
		public Builder rowMin(final float rowMin) {
			this.rowMin = rowMin;
			return this;
		}

		public Builder rowMax(final float rowMax) {
			this.rowMax = rowMax;
			return this;
		}

		public Builder rowInterval(final float rowInterval) {
			this.rowInterval = rowInterval;
			return this;
		}

		public Builder columnMin(final float columnMin) {
			this.columnMin = columnMin;
			return this;
		}

		public Builder columnMax(final float columnMax) {
			this.columnMax = columnMax;
			return this;
		}

		public Builder columnInterval(final float columnInterval) {
			this.columnInterval = columnInterval;
			return this;
		}
		
		public Table build() {
			return new Table(Data.check(path == null ? table : path), new Boundary(rowMin, rowMax, rowInterval),
					new Boundary(columnMin, columnMax, columnInterval));
		}

	}

	/**
	 * Data Object used to store and parse file to 2-D array representation.
	 * <strong>
	 * Text File to be parsed must have numeric values in table-like format.
	 * Numbers must me split with a tab else parsing could have unusual results.
	 * Each Row of the text file with reflect the row of this table.
	 * If an index can't be parsed, it will be be considered NaN.
	 * </strong>
	 * <p>
	 * Updated to insert the {@code float[][]} of the table directly.
	 * @author Alexander Paul
	 */
	private static class Data {

		private final int tableWidth;
		private final int tableHeight;
		final float[][] table;

		/**
		 * Skips the file look up and parsing which avoids File/Path/io issues.
		 * The parsing is driven by exceptions which could probably be avoided, but hard coding seems to be greater in the long run.
		 * For Example: Moving to Android means I don't have to worry about parsing/file/nio mechanisms... or do I...?
		 * @param table array to be used.
		 */
		private Data(final float[][] table) {
			tableHeight = tableWidth = 0;
			this.table = table;
		}
		
		/**
		 * Funny enough, I used this to get the hard coded float array to put into {@code Data(final float[][] table)}.
		 * Like the chicken and the egg. This came first, but now this won't be used in future uses.
		 * @param path to a file to be read from.
		 */
		private Data(final String path) {
			final List<String> data = readfromTextFile(path);
															//TAB.
			this.tableWidth = data.stream().map(s -> s.split("\t").length)
											.max(Integer::compare).get();

			this.tableHeight = data.size();

			final float[][] table = new float[tableHeight][];

			final Iterator<String> rowIterator = data.iterator();

			//need tablewidth to add the null-value intervals not tested by the engineers/pilots.
			//please look at toDoublArray for more info.
			for (int i = 0; i < data.size(); i++) table[i] = toDoubleArray(rowIterator.next(), tableWidth);

			this.table = table;
		}

		private static Data check(Object object) {
			if(object instanceof String) return new Data((String)object);
			return new Data((float[][])object);
		}

		/**
		 * There is a code smell I use which involves code driven by exceptions. 
		 * This is bad according to Josh Bloch's Book: Effective Java 2nd Ed.
		 * This is slow and should be changed.
		 * @param maxLength of the all the rows.
		 * @param file data row to be parsed into {@code double[]}.
		 * @return {@code double[]} representation of tabDataRow.
		 */
		private float[] toDoubleArray(final String fileDataRow, final int maxLength) {
			//As you can see, this API uses a Tabulated .txt file.
			//One by one, each individual row is treated like an array.
			final String[] split = fileDataRow.split("\t");
			final float[] floats = new float[maxLength];
			for (int i = 0; i < maxLength; i++) {

				try {
					floats[i] = i < split.length ? Float.parseFloat(split[i]) : Float.NaN;
				} catch (NumberFormatException e) {
					floats[i] = Float.NaN;
				}
				
			}
			return floats;
		}

		/**
		 * Uses Try-with-resources block to utilize the auto-closable mechanism of resources
		 * and uses class "getResource as Stream" method to automatically find the right files associated with this class. 
		 * That's why the chart data txt file are within the same package; 
		 * So the class will easily find them, even if they are in a jar.
		 * <strong> this. was. a. !%#$.</strong>
		 * @param path to be read and parsed.
		 * @return List of Strings representing each row of the file.
		 */
		private List<String> readfromTextFile(final String chartData) {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(chartData)))){
				return br.lines().collect(Collectors.toList());
			} catch (IOException e) {
				System.out.println("Read from Text File Error for: " + chartData);
			}
			return null;
		}

	}
	
	/**
	 * Data Object used to store table properties such as min, max, and interval values that constrain the table's rows and columns.
	 * <blockquote>
	 * Example: Rows for elevation from 0ft to 16,000ft with 500ft intervals.
	 * 			Columns for temperature from -45 degree to 55 degrees with 5 degree intervals.
	 * </blockquote>
	 */
	private static class Boundary { 
		private final float min;
		private final float max;
		
		private final HashMap<Float, Integer> map;
		
		private Boundary(float min, float max, float interval) {
			this.min = min;
			this.max = max;
			
			map = new HashMap<Float, Integer>();
			
			int tableIndex = 0;
			
			for(float mapIndex = min; mapIndex <= max; mapIndex += interval) {
				map.put(mapIndex, tableIndex);
				tableIndex++;
			}
		
		}
		
	}
	
	/**
	 * Returns value in respect to precise intervals of the table, else an empty optional.
	 * @param row to be searched by row interval.
	 * @param column to be searched by column interval.
	 * @return corresponding index in the table.
	 */
	public float getPrecise(final float row, final float column) {
		checkBounds(row, column);
		return getUnsafe(rowMap.get(row), columnMap.get(column));
	}

	/**
	 * Assumes row and column parameters are within bounds and within precise intervals,
	 * else throws {@code NullPointerException or NoSuchElementException}.
	 * @param row to be searched by row interval.
	 * @param column to be searched by column interval.
	 * @return corresponding index in the table.
	 * @throws NullPointerException
	 * @throws NoSuchElementException
	 */
	private float getUnsafe(final int row, final int column) {
		return table[row][column];
	}

	/**
	 * Utility function.
	 * @param row to check.
	 * @param column to check.
	 */
	private void checkBounds(final float row, final float column) {
		if(row < rowMin || row > rowMax || column < columnMin || column > columnMax) 
			throw new RuntimeException("Row and/or Column arguments are out of bounds.");
	}
	
	/**
	 * Gets optional value that has been interpolated corresponding to row and column inputs.
	 * @param row to look up.
	 * @param column to look up.
	 * @return interpolated value corresponding to row and column.
	 */
	float interpolate(final float row, final float column) {
		float rowLowerInterval = rowIntervals.floor(row);
		float rowHigherInterval = rowIntervals.ceiling(row);
		
		float rowRatio = getRatio(row, rowLowerInterval, rowHigherInterval);

		int rowLowerIndex = rowMap.get(rowLowerInterval);
		int rowHigherIndex = rowMap.get(rowHigherInterval);

		float columnLowerInterval = columnIntervals.floor(column);
		float columnHigherInterval = columnIntervals.ceiling(column);

		float columnRatio = getRatio(column, columnLowerInterval, columnHigherInterval);

		int columnLowerIndex = columnMap.get(columnLowerInterval);
		int columnHigherIndex = columnMap.get(columnHigherInterval);

		float topLeft = getUnsafe(rowHigherIndex, columnLowerIndex);
		float topRight = getUnsafe(rowHigherIndex, columnHigherIndex);
		float bottomLeft = getUnsafe(rowLowerIndex, columnLowerIndex);
		float bottomRight = getUnsafe(rowLowerIndex, columnHigherIndex);

		//Return a non-number here of any point is a NaN.
		//Can't interpolate using non-numbers.
		if (topLeft == Float.NaN || topRight == Float.NaN || bottomLeft == Float.NaN || bottomRight == Float.NaN) return Float.NaN;

		float topMean = getMean(columnRatio, topLeft, topRight);

		float bottomMean = getMean(columnRatio, bottomLeft, bottomRight);
		
		float resultMean = getMean(rowRatio, bottomMean, topMean);

		return resultMean;
	}

	private float getMean(float ratio, float base, float rangeReference) {
		return ((rangeReference - base) * ratio) + base;
	}

	private float getRatio(float column, float lowerInterval, float higherInterval) {
		return lowerInterval == higherInterval ? 1
				: (column - lowerInterval) / (higherInterval - lowerInterval);
	}
	
	@Override
	public String toString() {
		StringBuilder table = new StringBuilder();
		
		Iterator<Float> rowIt = rowIntervals.iterator();
		
		table.append("      ");
		
		for(double d : columnIntervals) {
			table.append(String.format("%3.0f ", d));
		}
		
		table.append("\n");
		
		for(int row = 0; row < tableHeight; row++) {
			for(int col = 0; col < tableWidth; col++) {
				if(col == 0) table.append(String.format("%5.0f ", rowIt.next()));
				table.append(String.format("%3.0f ", this.table[row][col]));
			}
			table.append("\n");
		}
		return table.toString();
	}
	
	public String toArrayFormat() {
		StringJoiner arrayString = new StringJoiner(",\n", "{\n", "\n}\n");
		
		for(float[] floats : table) {
			StringJoiner subArrayString = new StringJoiner(", ", "   {", "}");
			
			for(float number : floats) {
				String check = String.valueOf(number);
				subArrayString.add(check.equals("NaN") ? "Float.NaN" : check + 'f');
			}
			arrayString.add(subArrayString.toString());
		}
		return arrayString.toString();
	}
}
