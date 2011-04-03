package com.curiousattemptbunny.testfirstchallenge

import groovy.lang.GroovyShell;
import groovy.util.Eval;

class Sheet {
	Map cellToFormula = [:].withDefault( { return { '' } } )
	Map cellToLiteralValue = [:].withDefault( { '' } )
	
	Object getValue(String cell) {
		cellToFormula.getAt(cell).call()
	}
	
	String get(String cell) {
		getValue(cell).toString()
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
					Binding binding = new Binding(sheet:this)
					GroovyShell shell = new GroovyShell(binding)
					def expression = literalValue[1..-1]
					return shell.evaluate("this.metaClass.propertyMissing = { name -> sheet.getValue(name) } ; "+expression)
				} catch (StackOverflowError e) {
					return "#Circular"
				} catch (e) {
					e.printStackTrace();
					return "#Error"
				}
			}	
		}
		cellToFormula.putAt cell, formula
		cellToLiteralValue.putAt cell, literalValue
	}
	
}