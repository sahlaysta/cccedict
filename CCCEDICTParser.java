package com.github.sahlaysta.cccedict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** Parser of CC-CEDICT files */
public class CCCEDICTParser {

	/** Parses a List of CC-CEDICT Entries from a Reader. This method does not close the reader */
	public static List<CCCEDICTEntry> parse(Reader reader) throws IOException {
		return new CCCEDICTReader(reader).output;
	}
	
	/** Parses a List of CC-CEDICT Entries from a CC-CEDICT file */
	public static List<CCCEDICTEntry> parse(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
			    new FileInputStream(file), StandardCharsets.UTF_8));
		List<CCCEDICTEntry> output = new CCCEDICTReader(br).output;
		br.close();
		return output;
	}
	
	/** Parses a List of CC-CEDICT Entries from a CC-CEDICT file with the passed buffer size */
	public static List<CCCEDICTEntry> parse(File file, int bufferSize) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
			    new FileInputStream(file), StandardCharsets.UTF_8), bufferSize);
		List<CCCEDICTEntry> output = new CCCEDICTReader(br).output;
		br.close();
		return output;
	}
	
	//Invisible constructor
	CCCEDICTParser() {}
}
