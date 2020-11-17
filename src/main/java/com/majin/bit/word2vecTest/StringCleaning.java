package com.majin.bit.word2vecTest;

import java.util.regex.Pattern;

public class StringCleaning {
	 private static final Pattern punctPattern = Pattern.compile("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]*$"); // [\\.:,\"\'\\(\\)\\[\\]|/?!;]+

	    private StringCleaning() {}

	    /**
	     * Removes ASCII punctuation marks, which are: .:,"'()[]|/?!;
	     * @param base the base string
	     * @return the cleaned string
	     */
	    public static String stripPunct(String base) {

	        return punctPattern.matcher(base).replaceAll("");
	    }

}
