package com.curiousattemptbunny.testfirstchallenge

import spock.lang.Specification

class SheetTest extends Specification {
	def "cells are empty by default"() {
		given:
		Sheet sheet = new Sheet();
		
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
		Sheet sheet = new Sheet();
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
		Sheet sheet = new Sheet();
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
		Sheet sheet = new Sheet();
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
		Sheet sheet = new Sheet();
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
}