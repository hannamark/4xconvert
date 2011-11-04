package gov.nih.nci.qa.selenium.util;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TableUtil {

	WebElement tableElement;
	WebElement tableHead;
	WebElement tableBody;

	public TableUtil(WebElement tableElement) {
		this.tableElement = tableElement;
	}

	/**
	 * Assuming it's the first row of the table.
	 * 
	 * @return
	 */
	public List<String> getHeaderValues() {
		List<String> rowValues = getRowValues(0);
		return rowValues;
	}

	public List<WebElement> getRowWebElements(int row) {
		if (tableBody == null) {
			tableBody = getTableBody(tableElement);
		}
		List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
		WebElement currentRow = tableRows.get(row);
		List<WebElement> rowElements = currentRow
				.findElements(By.tagName("td"));

		return rowElements;
	}

	public List<String> getRowValues(int row) {
		if (tableBody == null) {
			tableBody = getTableBody(tableElement);
		}
		List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
		WebElement currentRow = tableRows.get(row);
		List<WebElement> rowElements = currentRow
				.findElements(By.tagName("td"));

		ArrayList<String> rowList = new ArrayList<String>();

		for (WebElement we : rowElements) {
			rowList.add(we.getText());
		}

		return rowList;
	}

	public List<String> getColumnValues(String header) {
		int column = getColumnIndex(header);
		int rowCount = getRowCount();

		List<String> columnValues = new ArrayList<String>();
		for (int i = 0; i < rowCount; i++) {
			String columnValue = getCellValue(column, i);
			columnValues.add(columnValue);
		}
		return columnValues;
	}

	/**
	 * If you don't know the column value but know the row.
	 * 
	 * @param header
	 * @param row
	 * @return
	 */
	public String getCellValue(String header, int row) {
		if (tableBody == null) {
			tableBody = getTableBody(tableElement);
		}
		int column = getColumnIndex(header);

		List<String> rowValues = getRowValues(row);
		String cellValue = rowValues.get(column);
		return cellValue;
	}

	/**
	 * Get the value of a cell using column/row coordinates.
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public String getCellValue(int column, int row) {
		if (tableBody == null) {
			tableBody = getTableBody(tableElement);
		}

		List<String> rowValues = getRowValues(row);
		String cellValue = rowValues.get(column);
		return cellValue;
	}

	public WebElement getCellWebElement(int column, int row) {
		if (tableBody == null) {
			tableBody = getTableBody(tableElement);
		}

		List<WebElement> rowWebElements = getRowWebElements(row);
		WebElement cellElement = rowWebElements.get(column);
		WebElement findElement = cellElement.findElement(By.className("btn"));
		System.out.println("findElement :" + cellElement.getText());
		// return rowWebElements.get(column);
		return findElement;
	}

	// Private methods...
	/**
	 * Get the index of the column in the header.
	 * 
	 * @param headerValue
	 * @return
	 */
	private int getColumnIndex(String headerValue) {
		List<String> headers = getHeaderValues();

		int counter = 0;
		for (String currentValue : headers) {
			if (headerValue.equals(currentValue)) {
				return counter;
			}
			counter++;
		}
		return -1;
	}

	public int getRowCount() {
		if (tableBody == null) {
			tableBody = getTableBody(tableElement);
		}

		List<WebElement> tableRows = tableBody.findElements(By.tagName("tr"));
		return tableRows.size();
	}

	public int getColumnCount() {
		if (tableHead == null) {
			getTableHead(tableElement);
		}
		List<WebElement> tableHeaders = tableHead
				.findElements(By.tagName("th"));
		return tableHeaders.size();
	}

	private WebElement getTableBody(WebElement webElement) {
		return tableElement.findElement(By.tagName("tbody"));
	}

	private WebElement getTableHead(WebElement webElement) {
		return tableElement.findElement(By.tagName("thead"));
	}

}
