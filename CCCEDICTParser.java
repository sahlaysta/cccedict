package com.github.sahlaysta.cccedict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** Parser of CC-CEDICT files */
public class CCCEDICTParser {

	/** Parses a List of CC-CEDICT Entries from a Reader */
	public static List<CCCEDICTEntry> parse(Reader reader, boolean closeReader) throws IOException {
		List<CCCEDICTEntry> output = new CCCEDICTReader(reader).output;
		if (closeReader)
			reader.close();
		return output;
	}
	
	/** Parses a List of CC-CEDICT Entries from an InputStream */
	public static List<CCCEDICTEntry> parse(InputStream is, boolean closeInputStream) throws IOException {
		List<CCCEDICTEntry> output = new CCCEDICTReader(
				new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))).output;
		if (closeInputStream)
			is.close();
		return output;
	}
	
	/** Parses a List of CC-CEDICT Entries from an InputStream with the passed buffer size */
	public static List<CCCEDICTEntry> parse(InputStream is, int bufferSize, boolean closeInputStream) throws IOException {
		List<CCCEDICTEntry> output = new CCCEDICTReader(
				new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8), bufferSize)).output;
		if (closeInputStream)
			is.close();
		return output;
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
