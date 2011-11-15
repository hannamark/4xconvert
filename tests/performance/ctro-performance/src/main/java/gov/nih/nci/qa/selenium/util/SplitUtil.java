package gov.nih.nci.qa.selenium.util;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;
import org.openqa.selenium.WebDriver;

public class SplitUtil {

	public static final String ROOT_NAME = "root";

	/**
	 * Used to store timings such as loading and navigation.
	 */
	public static final String NAVIGATION_CATEGORY = ROOT_NAME
			+ Manager.HIERARCHY_DELIMITER + "navigation";

	/**
	 * Used to store timings related to page operations such as clicking.
	 */
	public static final String PAGE_CATEGORY = ROOT_NAME
			+ Manager.HIERARCHY_DELIMITER + "page";

	/**
	 * Used to store timings related to page operations such as clicking.
	 */
	public static final String REVISION_CATEGORY = ROOT_NAME
			+ Manager.HIERARCHY_DELIMITER + "revision";

	/**
	 * Used to store timings related to overall test methods.
	 */
	public static final String TEST_CATEGORY = ROOT_NAME
			+ Manager.HIERARCHY_DELIMITER + "test";

	/**
	 * Used to store timings related to browser startup and shutdown.
	 */
	public static final String BROWSER_CATEGORY = ROOT_NAME
			+ Manager.HIERARCHY_DELIMITER + "browser";

	/**
	 * Use this to for reporting elements on pages.
	 * 
	 * @param pageObjectName
	 * @param pageElementName
	 * @return
	 */
	public static Split getPageElementSplit(WebDriver webDriver,
			String pageObjectName, String pageElementName) {
		Stopwatch stopwatch = SimonManager.getStopwatch(PAGE_CATEGORY
				+ Manager.HIERARCHY_DELIMITER + pageObjectName
				+ Manager.HIERARCHY_DELIMITER + pageElementName);
		stopwatch.setNote(webDriver.getCurrentUrl());
		return stopwatch.start();
	}

	/**
	 * Use this for reporting tests.
	 * 
	 * @param name
	 * @return
	 */
	public static Split getTestSplit(String name) {
		return SimonManager.getStopwatch(
				TEST_CATEGORY + Manager.HIERARCHY_DELIMITER + name).start();
	}

	/**
	 * Use this for navigation.
	 * 
	 * @param name
	 * @return
	 */
	public static Split getNavigationSplit(String name) {
		return SimonManager.getStopwatch(
				NAVIGATION_CATEGORY + Manager.HIERARCHY_DELIMITER + name)
				.start();
	}

	public static Split getBrowserStartSplit() {
		Split split = SimonManager.getStopwatch(
				BROWSER_CATEGORY + Manager.HIERARCHY_DELIMITER + "startup")
				.start();
		return split;
	}

	public static Split getBrowserShutdownSplit() {
		Split split = SimonManager.getStopwatch(
				BROWSER_CATEGORY + Manager.HIERARCHY_DELIMITER + "shutdown")
				.start();
		return split;
	}

	public static Split getRevisionSplit(String number) {
		Stopwatch stopwatch = SimonManager.getStopwatch(REVISION_CATEGORY
				+ Manager.HIERARCHY_DELIMITER + "viewer");
		stopwatch.setNote(number);
		return stopwatch.start();
	}

}
