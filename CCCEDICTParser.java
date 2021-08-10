package com.github.sahlaysta.cccedict;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/** Parser of CC-CEDICT files */
public class CCCEDICTParser {

	/** Parses a List of CC-CEDICT Entries from a CC-CEDICT file */
	public static List<CCCEDICTEntry> parse(InputStream is) throws IOException {
		return new CCCEDICTReader(is).output;
	}
	
	/** Parses a List of CC-CEDICT Entries from a CC-CEDICT file */
	public static List<CCCEDICTEntry> parse(String filePath) throws IOException {
		return new CCCEDICTReader(filePath).output;
	}
	
	/** Parses a List of CC-CEDICT Entries from a CC-CEDICT file */
	public static List<CCCEDICTEntry> parse(File file) throws IOException {
		return new CCCEDICTReader(file).output;
	}
	
	//Invisible constructor
	CCCEDICTParser() {}
}
