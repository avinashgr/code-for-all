package com.covisint.iot.services.utils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
public class RandomizeUtils {

		
		private static final char[] CHARS_TO_RANDOMIZE = new char[]{'a','b','c','d','e'};

		public static String randomString(int size){
			return RandomStringUtils.random(size, CHARS_TO_RANDOMIZE);
		}
		
		public static int randomInt(int size){
			return RandomUtils.nextInt(size);
		}


}
