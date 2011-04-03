package com.curiousattemptbunny.testfirstchallenge

import spock.lang.Specification

class SheetTest extends Specification {
	def sheet = new Sheet()
	
	def "cells are empty by default"() {
		expect:
		result == sheet.get(cell)

		where:
		result | cell
		''     | 'A1'
		''     | 'ZX347'
	}

	// Implement each test before going to the next one.

	def "text cells are stored"() {
		given:
		String theCell = "A21";
		sheet.put(theCell, value);

		expect:
		value == sheet.get(theCell);

		where:
		value << [
			'A string',
			'A different string',
			''
		]
	}

	// Implement each test before going to the next one; then refactor.

	def "many cells exist"() {
		given:
		sheet.put('A1', 'First');
		sheet.put('X27', 'Second');
		sheet.put('ZX901', 'Third');
		sheet.put('A1', 'Fourth');

		expect:
		value == sheet.get(cell)

		where:
		value    | cell
		'Fourth' | 'A1'
		'Second' | 'X27'
		'Third'  | 'ZX901'
	}

	// Implement each test before going to the next one.
	// You can split this test case if it helps.

	def "numeric cells are identified and stored"() {
		given:
		String theCell = "A21";

		when:
		sheet.put(theCell, value);

		then:
		result == sheet.get(theCell);

		where:
		result    | value
		'X99'     | 'X99'
		'14'      | '14'
		' 99 X'   | ' 99 X'
		'1234'    | ' 1234 '
		' '       | ' '
	}

	// Refactor before going to each succeeding test.

	def "we have access to cell literal values for editing"() {
		given:
		String theCell = 'A21';

		when:
		sheet.put(theCell, value);

		then:
		value == sheet.getLiteral(theCell)

		where:
		value << [
			'Some string',
			' 1234 ',
			'=7'
		]
	}

	// We'll talk about "get" and formulas next time.

	// Implement code for previous test before moving to next one.

	def "formula spec"() {
		when:
		sheet.put("B1", " =7"); // note leading space
		
		then:
		' =7' == sheet.get('B1')
		' =7' == sheet.getLiteral('B1')
	}

	// Next - start on parsing expressions

	def "constant formula"() {
		when:
		sheet.put("A1", "=7");
		
		then:
		"=7" == sheet.getLiteral("A1");
		"7" == sheet.get("A1");
	}

	// More formula tests. You may feel the need to make up
	// additional intermediate test cases to drive your code
	// better. (For example, you might want to test "2*3"

	// before "2*3*4".) That's fine, go ahead and create them.
	// Just keep moving one test at a time.

	// We're doing expressions; you may need to do a spike
	// (investigation) if you're not familiar with parsing.
	// For background, look up "recursive descent" or
	// "operator precedence". (Other techniques can work as well.)

	// Order of tests - I'm familiar enough with parsing to think
	// it's probably easiest to do them in this order (highest
	// precedence to lowest). For extra credit, you might redo
	// this part of the exercise with the tests in a different order
	// to see what difference it makes.

	def "parentheses"() {
		when:
		sheet.put("A1", "=(7)");
		
		then:
		"7" == sheet.get("A1");
	}

	def "deep parentheses"() {
		when:
		sheet.put("A1", "=((((10))))");
		
		then:
		"10" == sheet.get("A1");
	}

	def "multiply"() {
		when:
		sheet.put("A1", "=2*3*4");
		
		then:
		"24" == sheet.get("A1");
	}

	def "add"() {
		when:
		sheet.put("A1", "=71+2+3");
		
		then:
		"76" == sheet.get("A1");
	}

	def "precedence"() {
		when:
		sheet.put("A1", "=7+2*3");
		
		then:
		"13" == sheet.get("A1");
	}

	def "full expression"() {
		when:
		sheet.put("A1", "=7*(2+3)*((((2+1))))");
		
		then:
		"105" == sheet.get("A1");
	}

	// Add any test cases you feel are missing based on
	// where your code is now.

	// Then try your hand at a few test cases: Add "-" and "/"

	// with normal precedence.

	// Next, error handling.

	def "simple formula error"() {
		when:
		sheet.put("A1", "=7*");
		
		then:
		"#Error" == sheet.get("A1");
	}

	def "parenthesis error"() {
		when:
		sheet.put("A1", "=(((((7))");
		
		then:
		"#Error" == sheet.get("A1");
	}

	// Add any more error cases you need. Numeric errors (e.g.,
	// divide by 0) can return #Error too.

	// Take a deep breath and refactor. This was a big jump.
	// Next time we'll tackle formulas involving cells.
}