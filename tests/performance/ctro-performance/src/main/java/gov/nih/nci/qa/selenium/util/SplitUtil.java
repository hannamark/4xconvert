package gov.nih.nci.qa.selenium.util;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;

public class SplitUtil {

	private static final String ROOT_NAME = "root";
	public static final String PAGE_LOAD_CATEGORY = "navigation";

	public static String getRootName() {
		return ROOT_NAME;
	}

	public static Split getPageElementSplit(String pageObjectName,
			String pageElementName) {
		return SimonManager.getStopwatch(
				ROOT_NAME + Manager.HIERARCHY_DELIMITER + pageObjectName
						+ Manager.HIERARCHY_DELIMITER + pageElementName)
				.start();
	}

	public static Split getTestSplit(String name) {
		return SimonManager.getStopwatch(
				ROOT_NAME + Manager.HIERARCHY_DELIMITER + "test"
						+ Manager.HIERARCHY_DELIMITER + name).start();
	}

	public static Split getTestSplit(String root, String name) {
		return SimonManager.getStopwatch(
				root + Manager.HIERARCHY_DELIMITER + "test"
						+ Manager.HIERARCHY_DELIMITER + name).start();
	}

	public static Split getBrowserStartSplit() {
		Split split = SimonManager.getStopwatch(
				ROOT_NAME + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "startup").start();
		return split;
	}

	public static Split getBrowserShutdownSplit() {
		Split split = SimonManager.getStopwatch(
				ROOT_NAME + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "shutdown").start();
		return split;
	}

}
