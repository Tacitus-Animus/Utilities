package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.lang.StringBuilder;

public class TextParser {
	
	int[][] a = {
			{1,2,3},
			{4,5,6},
			{7,8,9},
	};
	
	private TextParser() {
	}
	
	public static File parseTextFile(String filename, String delimiter) {
		List<String> text = extractTextFile(filename);
		int maxLength = text.stream().map(string -> string.split(delimiter).length).max(Integer::compare).get();
		int maxHeight = text.size();
		
		StringBuilder array = new StringBuilder("{\n");
		
		for(String line : text) {
			array.append("{");
			String[] subArray = line.split(delimiter);
			for(String number : subArray) {
				array.append(Float.parseFloat(number));
				array.append(",");
			}
			array.append("}");			
		}
		
		array.append("}");
		
		return null;
	}

	private static List<String> extractTextFile(String filename) {
		try {
			return Files.readAllLines(Paths.get(filename));
		} catch (IOException e) {
			System.out.println("Failed to parse text File: " + filename);
		}
		return null;
	}
}
