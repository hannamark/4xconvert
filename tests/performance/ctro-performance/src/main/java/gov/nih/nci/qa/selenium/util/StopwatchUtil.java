package gov.nih.nci.qa.selenium.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.javasimon.Sample;
import org.javasimon.Simon;
import org.javasimon.SimonManager;
import org.javasimon.Stopwatch;
import org.javasimon.utils.SimonUtils;

public class StopwatchUtil {

	public static void printReport(String stopwatchParent) {
		Simon parent = SimonManager.getSimon(stopwatchParent);
		List<Simon> children = parent.getChildren();
		System.out.println("Printing Report.");
		System.out.println("parent children count = " + children.size());
		for (Simon child : children) {
			// printChildrenToConsole(child);
			printChildrenToFile(child);
		}

	}

	public static void printChildrenToFile(Simon children) {
		BufferedWriter sampleReport = null;
		BufferedWriter report = null;
		try {
			sampleReport = new BufferedWriter(new FileWriter(
					"StopwatchSampleReport.txt"));
			report = new BufferedWriter(new FileWriter("StopwatchReport.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Printing Report.");
		List<Simon> nextLevel = children.getChildren();
		long totalSum = 0;
		for (Simon child : nextLevel) {
			Sample sample = child.sample();
			try {
				report.write(child.toString() + '\n');
				sampleReport.write(sample.toString() + '\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
			// do whatever you need to do here...
			totalSum += ((Stopwatch) child).getTotal();
		}
		try {
			sampleReport.write("totalSum = "
					+ SimonUtils.presentNanoTime(totalSum));
			report.write("totalSum = " + SimonUtils.presentNanoTime(totalSum));
			sampleReport.close();
			report.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void printChildrenToConsole(Simon children) {
		List<Simon> nextLevel = children.getChildren();
		long totalSum = 0;
		for (Simon child : nextLevel) {
			System.out.println(child);
			// do whatever you need to do here...
			totalSum += ((Stopwatch) child).getTotal();
		}
		System.out
				.println("totalSum = " + SimonUtils.presentNanoTime(totalSum));
	}

}
