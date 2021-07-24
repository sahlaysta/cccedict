package com.github.sahlaysta.cccedict;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

final class CCCEDICTReader {
	public final List<CCCEDICTEntry> output;
	private InputStreamReader isr;
	
	//Constructors
	public CCCEDICTReader(InputStreamReader isr) throws IOException {
		this.isr = isr;
		output = parse();
	}
	public CCCEDICTReader(String filePath) throws IOException {
		isr = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
		output = parse();
	}
	public CCCEDICTReader(File file) throws IOException {
		isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
		output = parse();
	}
	
	//Reader methods
	private int read, line;
	private void read() throws IOException {
		read = isr.read();
		if (read == '\n' || read == -1)
			line++;
	}
	private void readCheck() {
		if (read == '\n' || read == -1)
			throw new CCCEDICTParseException("Unexpected end of line: line " + line);
	}
	private List<CCCEDICTEntry> parse() throws IOException {
		List<CCCEDICTEntry> output = new ArrayList<>();
		while (read != -1) {
			read();
			if (read=='#')
				skipLine();
			else
				output.add(readItem());
		}
		return output;
	}
	private void skipLine() throws IOException {
		while (read != '\n' && read != -1)
			read();
	}
	private CCCEDICTEntry readItem() throws IOException {
		CCCEDICTEntry output = new CCCEDICTEntry();
		output.traditional = readWord();
		output.simplified = readWord();
		output.pronunciation = readPronunciation();
		output.definitions = readDefinitions();
		return output;
	}
	private String readWord() throws IOException {
		StringBuilder sb = new StringBuilder();
		while (read != ' ') {
			sb.append((char)read);
			read();
			readCheck();
		}
		read();
		return sb.toString();
	}
	private String readPronunciation() throws IOException {
		StringBuilder sb = new StringBuilder();
		read();
		while (read != ']') {
			sb.append((char)read);
			read();
			readCheck();
		}
		read();
		return sb.toString();
	}
	private List<String> readDefinitions() throws IOException {
		read(); read(); //(two spaces after reading pronunciation)
		List<String> output = new ArrayList<>();
		String addition;
		while ((addition = readDefinition()) != null)
			output.add(addition);

		if (output.size() == 0)
			throw new CCCEDICTParseException("Error reading definition(s): line " + line);
		return output;
	}
	private String readDefinition() throws IOException {
		StringBuilder sb = new StringBuilder();
		while (read != '/') {
			sb.append((char)read);
			read();
			if (read == '\n' || read == -1)
				return null;
			readCheck();
		}
		read();
		return sb.toString();
	}
}
