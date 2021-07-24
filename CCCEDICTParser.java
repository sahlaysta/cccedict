package com.github.sahlaysta.cccedict;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CCCEDICTParser {
	//Static parse method overloads
	public static List<CCCEDICTEntry> parse(InputStreamReader isr) throws IOException {
		return new CCCEDICTReader(isr).output;
	}
	public static List<CCCEDICTEntry> parse(String filePath) throws IOException {
		return new CCCEDICTReader(filePath).output;
	}
	public static List<CCCEDICTEntry> parse(File file) throws IOException {
		return new CCCEDICTReader(file).output;
	}
	
	//Invisible constructor
	CCCEDICTParser() {}
}
