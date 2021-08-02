package com.github.sahlaysta.cccedict;

import java.util.List;

/** CC-CEDICT dictionary entry with traditional, simplified, pronunciation and definitions */
public class CCCEDICTEntry {
	/** The traditional-character Chinese form of this entry */
	public String traditional;
	/** The simplified-character Chinese form of this entry */
	public String simplified;
	/** The Pinyin pronunciation of this entry */
	public String pronunciation;
	/** The definitions of this entry */
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
