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

}