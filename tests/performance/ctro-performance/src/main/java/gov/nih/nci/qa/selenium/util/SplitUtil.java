package gov.nih.nci.qa.selenium.util;

import org.javasimon.Manager;
import org.javasimon.SimonManager;
import org.javasimon.Split;

public class SplitUtil {

	public static Split getTestSplit(String name) {
		return SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "test"
						+ Manager.HIERARCHY_DELIMITER + name).start();
	}

	public static Split getBrowserStartSplit() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "startup").start();
		return split;
	}

	public static Split getBrowserShutdownSplit() {
		Split split = SimonManager.getStopwatch(
				"parent" + Manager.HIERARCHY_DELIMITER + "Browser"
						+ Manager.HIERARCHY_DELIMITER + "shutdown").start();
		return split;
	}

}
