package com.curiousattemptbunny.testfirstchallenge

import groovy.util.Eval;

class Sheet {
	Map cellToFormula = [:].withDefault( { return { '' } } )
	Map cellToLiteralValue = [:].withDefault( { '' } )
	
	String get(String cell) {
		cellToFormula.getAt(cell).call().toString()
	}

	String getLiteral(String cell) {
		cellToLiteralValue.getAt(cell)
	}

	String put(String cell, String literalValue) {
		def storedValue = literalValue
		try {
			storedValue = Integer.parseInt(literalValue.trim())
		} catch (NumberFormatException) {
			
		}
		def formula = { storedValue }

		if (literalValue.startsWith('=')) {
			formula = {
				try {
					return Eval.me(literalValue[1..-1])
				} catch (Error) {
					return "#Error"
				}
			}	
		}
		cellToFormula.putAt cell, formula
		cellToLiteralValue.putAt cell, literalValue
	}
	
}