package com.github.sahlaysta.cccedict;

/** Parse exception thrown in padding or format error when parsing a CC-CEDICT file */
public class CCCEDICTParseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	CCCEDICTParseException(String message) {
		super(message);
	}
}
