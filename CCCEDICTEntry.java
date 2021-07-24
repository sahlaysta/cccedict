package com.github.sahlaysta.cccedict;

import java.util.List;

public class CCCEDICTEntry {
	public String traditional;
	public String simplified;
	public String pronunciation;
	public List<String> definitions;
	
	@Override
	public String toString() {
		StringBuilder defs = new StringBuilder();
		for (String definition: definitions)
			defs.append(definition + " :: ");
		defs.setLength(defs.length() - 4);
		return traditional + "\u3000\uFF08" + simplified
				+ "\uFF09\u3000\u2014\u3000" + pronunciation
				+ "\u3000\u2014\u3000" + defs;
	}
}
