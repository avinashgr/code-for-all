package com.lambda.utils;

import com.lambda.vo.Person;

/**
 * Class to implement comparator functions in lambda 
 * @author aranjalkar
 *
 */
public class LambdaComparator implements EntityComparator<Person> {

	@Override
	public Person isGreaterThan(Person a, Person b) {
		return b;
	}

	@Override
	public Person isLesserThan(Person a, Person b) {
		// TODO Auto-generated method stub
		return a;
	}
	
}
