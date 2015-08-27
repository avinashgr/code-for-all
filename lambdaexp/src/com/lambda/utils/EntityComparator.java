package com.lambda.utils;
/**
 * Interface to perform compares
 * @author aranjalkar
 *
 */
public interface EntityComparator<T>{
	T isGreaterThan(T a, T b);
	T isLesserThan(T a, T b);
}
