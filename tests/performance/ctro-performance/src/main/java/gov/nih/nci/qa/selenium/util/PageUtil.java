package gov.nih.nci.qa.selenium.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PageUtil {

	public static void setDropDown(WebElement webElement, String keysToSend) {
		Select select = new Select(webElement);

		// Get a list of the options
		List<WebElement> options = select.getOptions();

		// For each option in the list, verify if it's the one you want and then
		// click it
		for (WebElement we : options) {
			if (we.getText().equals(keysToSend)) {
				we.click();
				break;
			}
		}
	}

	public static void setMultiSelect(WebElement webElement,
			List<String> selectList) {
		Select select = new Select(webElement);

		select.deselectAll();

		for (String option : selectList) {
			setDropDown(webElement, option);
		}
	}

	public static void setDateInterval(WebElement startElement,
			String startDate, WebElement endElement, String endDate) {
		setDate(startElement, startDate);
		setDate(endElement, endDate);
	}

	public static void setDate(WebElement dateElement, String date) {
		dateElement.clear();
		dateElement.sendKeys(date);
	}

	public static void setCheckbox(WebElement webElement, boolean select) {
		if (select) {
			webElement.click();
		} else {
			if (webElement.isSelected()) {
				webElement.click();
			}
		}
	}

	public static List<String> getOptions(WebElement webElement) {
		Select select = new Select(webElement);

		// Get a list of the options
		List<WebElement> elementsList = select.getOptions();

		List<String> optionsText = new ArrayList<String>();

		for (WebElement we : elementsList) {
			optionsText.add(we.getText());
		}
		return optionsText;
	}

	public static String getViewerRevision(String pageSource) {
		Pattern pattern = Pattern
				.compile("\\<![ \\r\\n\\t]*(--([^\\-]|[\\r\\n]|-[^\\-])*--[ \\r\\n\\t]*)\\>");
		Matcher matcher = pattern.matcher(pageSource);
		String revision = null;
		while (matcher.find()) {
			String s = matcher.group();
			if (s.contains("Version: ") && s.contains("revision")) {
				revision = s;
			}
		}
		int start = revision.indexOf("Version");
		int end = revision.indexOf("-->");

		return revision.substring(start, end);
	}
}
