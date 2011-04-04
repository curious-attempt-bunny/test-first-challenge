package com.curiousattemptbunny.testfirstchallenge

import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener
import javax.swing.table.TableModel

import spock.lang.Specification

class SheetTableModelTest extends Specification {
	Sheet sheet = new Sheet()
	TableModel table = new SheetTableModel(sheet)

	// As usual, do one test at a time and refactor after each.

	// For now, we"re willing to hard-code a maximum spreadsheet size.
	// A future story can deal with this.

	static final int LAST_COLUMN_INDEX = 49
	static final int LAST_ROW_INDEX = 99

	def "table model required overrides"() {
		expect:
		table.getColumnCount() > LAST_COLUMN_INDEX
		table.getRowCount() > LAST_ROW_INDEX
		"" == table.getValueAt(10,10)
	}

	/*22*/// Take a look at AbstractTableModel"s documentation before doing this test.

	public void testColumnNames() {
		expect:
		 "" == table.getColumnName(0)
		 "A" == table.getColumnName(1)
		 "Z" == table.getColumnName(26)
		 "AW" == table.getColumnName(LAST_COLUMN_INDEX)
	}

	/*23*/def "column 0 contains index"() {
		expect:
		 "1" == table.getValueAt(0,0)
		 "50"== table.getValueAt(49, 0)
		 "100" == table.getValueAt(LAST_ROW_INDEX,0)
	}

	/*24*/// Remember, one test at a time, followed by refactoring.

	def "main columns have contents"() {
		when:
		sheet.put cell, value
		
		then:
		value == table.getValueAt(x, y)
		
		where:
		value         | x              | y                 | cell
		"upper left"  | 0              | 1                 | "A1"
		"lower left"  | LAST_ROW_INDEX | 1                 | "A100"
		"upper right" | 0              | LAST_COLUMN_INDEX | "AW1"
		"lower right" | LAST_ROW_INDEX | LAST_COLUMN_INDEX | "AW100"
	}

	/*25*/def "stores work through table model"() {
		when:
		table.setValueAt("21", 0, 1)
		table.setValueAt("=A1", 1, 1)

		then:
		"21" == table.getValueAt(0,1)
		"21" == table.getValueAt(1,1)

		when:
		table.setValueAt("22", 0, 1)
		then:
		
		"22" == table.getValueAt(0,1)
		"22" == table.getValueAt(1,1)
	}

	/*26*/// We"ve established that the table model can get and set values.
	// But JTable uses an event notification mechanism to find out
	// about the changes.

	// To test this, we"ll introduce a test helper class. It"s a very
	// simple listener, and will assure us that notifications are
	// sent when changes are made.

	// There"s a couple of design decisions implicit here. One is that
	// we won"t attempt to be specific about which cells change we"ll
	// just say that the table data has changed and let JTable refresh
	// its view of whichever cells it wants. (Because of cell dependencies,
	// changes in one cell could potentially no others, all others,
	// or anything in between.) We might revisit this decision during
	// performance tuning, and try to issue finer-grained notifications.

	// The other decision is that we have no mechanism for our Sheet
	// to tell the table model about changes. So changes will either need
	// to come in through the table model, or we"ll have to add some
	// notification mechanism to Sheet. For now, just make changes through the table model.

	public class TestTableModelListener implements TableModelListener {
		public boolean wasNotified = false

		public void tableChanged(TableModelEvent e) {wasNotified = true}
	}

	def "table model notifies"() {
		given:
		TestTableModelListener listener = new TestTableModelListener()
		
		when:
		table.addTableModelListener(listener)
		
		then:
		!listener.wasNotified

		when:
		table.setValueAt("22", 0, 1)

		then:
		listener.wasNotified
	}

	/*27*/// Note the cast in our test here. Previous tests have been straight
	// implementations of TableModel functions now we"re saying that
	// our model has some extra functions. We"ll face a small tradeoff later
	// when we want access to the feature: if we get the model back from JTable,
	// we"ll have to cast it if we don"t want to cast it we"ll have to
	// track it somewhere.

	def "SheetTableModel can get literal"() {
		when:
		sheet.put("A1", "=7")
		
		then:
		"=7" == table.getLiteralValueAt(0, 1)
	}

	// We"ve left isCellEditable() false, on the assumption that the way to edit
	// the cell is to go to a textbox provided for that purpose (rather than
	// in place).
	/*28*/
}
