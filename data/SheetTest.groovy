package com.curiousattemptbunny.testfirstchallenge

import spock.lang.Specification


class SheetTest extends Specification {
	def sheet = new Sheet()

	def "cells are empty by default"() {
		expect:
		result == sheet.get(cell)

		where:
		result | cell
		""     | "A1"
		""     | "ZX347"
	}

	/*1*/// Implement each test before going to the next one.

	def "text cells are stored"() {
		given:
		String theCell = "A21"
		sheet.put(theCell, value)

		expect:
		value == sheet.get(theCell)

		where:
		value << [
			"A string",
			"A different string",
			""
		]
	}

	/*2*/// Implement each test before going to the next one then refactor.

	def "many cells exist"() {
		given:
		sheet.put("A1", "First")
		sheet.put("X27", "Second")
		sheet.put("ZX901", "Third")
		sheet.put("A1", "Fourth")

		expect:
		value == sheet.get(cell)

		where:
		value    | cell
		"Fourth" | "A1"
		"Second" | "X27"
		"Third"  | "ZX901"
	}

	/*3*/// Implement each test before going to the next one.
	// You can split this test case if it helps.

	def "numeric cells are identified and stored"() {
		given:
		String theCell = "A21"

		when:
		sheet.put(theCell, value)

		then:
		result == sheet.get(theCell)

		where:
		result    | value
		"X99"     | "X99"
		"14"      | "14"
		" 99 X"   | " 99 X"
		"1234"    | " 1234 "
		" "       | " "
	}

	/*4*/// Refactor before going to each succeeding test.

	def "we have access to cell literal values for editing"() {
		given:
		String theCell = "A21"

		when:
		sheet.put(theCell, value)

		then:
		value == sheet.getLiteral(theCell)

		where:
		value << [
			"Some string",
			" 1234 ",
			"=7"
		]
	}

	// We"ll talk about "get" and formulas next time.

	/*5*/// Implement code for previous test before moving to next one.

	def "formula spec"() {
		when:
		sheet.put("B1", " =7") // note leading space

		then:
		" =7" == sheet.get("B1")
		" =7" == sheet.getLiteral("B1")
	}

	/*6*/// Next - start on parsing expressions

	def "constant formula"() {
		when:
		sheet.put("A1", "=7")

		then:
		"=7" == sheet.getLiteral("A1")
		"7" == sheet.get("A1")
	}

	/*7*/// More formula tests. You may feel the need to make up
	// additional intermediate test cases to drive your code
	// better. (For example, you might want to test "2*3"

	// before "2*3*4".) That"s fine, go ahead and create them.
	// Just keep moving one test at a time.

	// We"re doing expressions you may need to do a spike
	// (investigation) if you"re not familiar with parsing.
	// For background, look up "recursive descent" or
	// "operator precedence". (Other techniques can work as well.)

	// Order of tests - I"m familiar enough with parsing to think
	// it"s probably easiest to do them in this order (highest
	// precedence to lowest). For extra credit, you might redo
	// this part of the exercise with the tests in a different order
	// to see what difference it makes.

	def "parentheses"() {
		when:
		sheet.put("A1", "=(7)")

		then:
		"7" == sheet.get("A1")
	}

	/*8*/def "deep parentheses"() {
		when:
		sheet.put("A1", "=((((10))))")

		then:
		"10" == sheet.get("A1")
	}

	/*9*/def "multiply"() {
		when:
		sheet.put("A1", "=2*3*4")

		then:
		"24" == sheet.get("A1")
	}

	/*10*/def "add"() {
		when:
		sheet.put("A1", "=71+2+3")

		then:
		"76" == sheet.get("A1")
	}

	/*11*/def "precedence"() {
		when:
		sheet.put("A1", "=7+2*3")

		then:
		"13" == sheet.get("A1")
	}

	/*12*/def "full expression"() {
		when:
		sheet.put("A1", "=7*(2+3)*((((2+1))))")

		then:
		"105" == sheet.get("A1")
	}

	/*13*/// Add any test cases you feel are missing based on
	// where your code is now.

	// Then try your hand at a few test cases: Add "-" and "/"

	// with normal precedence.

	// Next, error handling.

	def "simple formula error"() {
		when:
		sheet.put("A1", "=7*")

		then:
		"#Error" == sheet.get("A1")
	}

	/*14*/def "parenthesis error"() {
		when:
		sheet.put("A1", "=(((((7))")

		then:
		"#Error" == sheet.get("A1")
	}

	/*15*/// Add any more error cases you need. Numeric errors (e.g.,
	// divide by 0) can return #Error too.

	// Take a deep breath and refactor. This was a big jump.
	// Next time we"ll tackle formulas involving cells.

	def "cell reference works"() {
		when:
		sheet.put("A1", "8")
		sheet.put("A2", "=A1")

		then:
		"8" == sheet.get("A2")
	}

	/*16*/def "cell changes propagate"() {
		when:
		sheet.put("A1", "8")
		sheet.put("A2", "=A1")

		then:
		"8" == sheet.get("A2")

		when:
		sheet.put("A1", "9")

		then:
		"9" == sheet.get("A2")
	}

	/*17*/def "formulas know cells and recalculate"() {
		when:
		sheet.put("A1", "8")
		sheet.put("A2", "3")
		sheet.put("B1", "=A1*(A1-A2)+A2/3")
		
		then:
		"41" == sheet.get("B1")

		when:
		sheet.put("A2", "6")
		
		then:
		"18" == sheet.get("B1")
	}

	/*18*/def "deep propagation works"() {
		when:
		sheet.put("A1", "8")
		sheet.put("A2", "=A1")
		sheet.put("A3", "=A2")
		sheet.put("A4", "=A3")
		
		then:
		"8" == sheet.get("A4")

		when:
		sheet.put("A2", "6")
		
		then:
		"6" == sheet.get("A4")
	}

	/*19*/// The following test is likely to pass already.
	def "formula works with many cells"() {
		when:
		sheet.put("A1", "10")
		sheet.put("A2", "=A1+B1")
		sheet.put("A3", "=A2+B2")
		sheet.put("A4", "=A3")
		sheet.put("B1", "7")
		sheet.put("B2", "=A2")
		sheet.put("B3", "=A3-A2")
		sheet.put("B4", "=A4+B3")

		then:
		"34" == sheet.get("A4")
		"51" == sheet.get("B4")
	}

	// Refactor and get everything nice and clean.

	/*20*/// Next: (I almost made this a separate part, and when I
	// originally did it, I did it in a different design session).
	// So take a break if you need one.

	// There"s one big open issue for formulas: what about
	// circular references?

	// I"ll sketch some hints, but you should define your own tests
	// that drive toward a solution compatible with your own
	// implementation.

	def "circular reference doesnt crash"() {
		given:
		sheet.put("A1", "=A1")
	}

	/*21*/// Just like errors return a special value, it might be nice
	// if circular references did too. (See notes below).

	def "circular references admit it"() {
		when:
		sheet.put("A1", "=A1")
		
		then:
		"#Circular" == sheet.get("A1")
	}

	// You might come up with some other approach that suits your
	// taste. We won"t be exploring this corner of the solution
	// any further you just want a scheme that blocks silly mistakes.
	// Make sure you test deep circularities involving partially
	// evaluated expressions.

	// A hint: if you blindly evaluate an expression you have no
	// control over how deep the expression can be, since
	// circular references appear to be infinitely deep.

	// Where are we? I intend to spend the next two parts hooking
	// up a GUI. Then there will be an optional part that pushes
	// things in an unexpected direction just to get a sense
	// of our software"s robustness.
}