package com.lambda.utils;

import com.lambda.vo.Person;

/**
 * Class to implement functions in non lambda
 * @author aranjalkar
 *
 */
public class NonLambdaComparator implements EntityComparator<Person> {

	@Override
	public Person isGreaterThan(Person a, Person b) {
		Person greaterPerson = a;
		greaterPerson = (a.getAge()>b.getAge())?   a:  b;
		return greaterPerson;
	}

	@Override
	public Person isLesserThan(Person a, Person b) {
		Person greaterPerson = a;
		greaterPerson = (a.getAge()>b.getAge())?   b:  a;
		return greaterPerson;
	}

}
