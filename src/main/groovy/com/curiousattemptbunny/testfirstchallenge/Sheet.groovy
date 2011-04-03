package com.curiousattemptbunny.testfirstchallenge

class Sheet {
	Map cellToValue = [:].withDefault( { '' } )
	Map cellToLiteralValue = [:].withDefault( { '' } )
	
	String get(String cell) {
		cellToValue.getAt(cell).toString()
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
		cellToValue.putAt cell, storedValue	
		cellToLiteralValue.putAt cell, literalValue
	}
	
}