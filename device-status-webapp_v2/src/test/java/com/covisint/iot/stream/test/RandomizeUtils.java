package com.covisint.iot.stream.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class RandomizeUtils {

		
		private static final char[] CHARS_TO_RANDOMIZE = new char[]{'a','b','c','d','e'};

		public static String randomString(int size){
			return RandomStringUtils.random(size, CHARS_TO_RANDOMIZE);
		}
		
		public static int randomInt(int size){
			return RandomUtils.nextInt(size, size);
		}
		public static int randomInt(int start, int end){
			return RandomUtils.nextInt(start, end);
		}

}