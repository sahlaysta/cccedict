package com.github.sahlaysta.cccedict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/** Parser and reader of CC-CEDICT files */
final class CCCEDICTReader {
	public final List<CCCEDICTEntry> output;
	private BufferedReader br;
	
	//Constructors
	public CCCEDICTReader(InputStream is) throws IOException {
		this.br = new BufferedReader(new InputStreamReader(is));
		output = parse();
		br.close();
	}
	public CCCEDICTReader(String filePath) throws IOException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
		output = parse();
		br.close();
	}
	public CCCEDICTReader(File file) throws IOException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		output = parse();
		br.close();
	}
	
	//Reader methods
	private int read, line;
	private boolean endLine;
	private void read() throws IOException {
		read = br.read();
		if ((endLine = (read == '\n' || read == -1)))
			line++;
	}
	private void readCheck() {
		if (endLine)
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
		while (!endLine)
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
		read(); read(); //(skip two spaces after reading pronunciation)
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
			if (endLine)
				return null;
			readCheck();
		}
		read();
		return sb.toString();
	}
}
