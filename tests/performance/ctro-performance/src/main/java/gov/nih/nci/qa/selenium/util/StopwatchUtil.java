package gov.nih.nci.qa.selenium.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.javasimon.Simon;
import org.javasimon.SimonManager;
import org.javasimon.Stopwatch;

import au.com.bytecode.opencsv.CSVWriter;

public class StopwatchUtil {

	private static final String FILENAME = "config.properties";
	private static final String DELIMETER = ".";

	public static void printReport(String stopwatchParent) {
		Simon parent = SimonManager.getSimon(stopwatchParent);
		List<Simon> children = parent.getChildren();
		String filename = getFilename();
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(filename), ',');
			String[] header = getHeader();
			writer.writeNext(header);
			for (Simon firstLevel : children) {
				List<Simon> secondLevel = firstLevel.getChildren();
				for (Simon child : secondLevel) {
					String[] entries = getChildStatistics(child);
					writer.writeNext(entries);
				}
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printConsoleReport(String stopwatchParent) {
		Simon parent = SimonManager.getSimon(stopwatchParent);
		List<Simon> children = parent.getChildren();

		PrintWriter printWriter = new PrintWriter(System.out, true);
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(printWriter, ',');
			String[] header = getHeader();
			writer.writeNext(header);
			for (Simon firstLevel : children) {
				List<Simon> secondLevel = firstLevel.getChildren();
				for (Simon child : secondLevel) {
					String[] entries = getChildStatistics(child);
					writer.writeNext(entries);
				}
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String[] getHeader() {
		List<String> header = new ArrayList<String>();
		header.add("name");
		header.add("total");
		header.add("counter");
		header.add("min");
		header.add("max");
		header.add("minTimestamp");
		header.add("maxTimestamp");
		header.add("maxActive");
		header.add("maxActiveTimestamp");
		header.add("last");
		header.add("mean");
		header.add("standardDeviation");
		header.add("note");
		header.add("firstUsage");
		header.add("lastUsage");
		String[] statsArray = header.toArray(new String[header.size()]);
		return statsArray;
	}

	public static Map<String, Double> getMaxValues(String stopwatchParent) {
		Simon parent = SimonManager.getSimon(stopwatchParent);
		List<Simon> children = parent.getChildren();

		Map<String, Double> entries = new HashMap<String, Double>();
		for (Simon firstLevel : children) {
			List<Simon> secondLevel = firstLevel.getChildren();
			for (Simon child : secondLevel) {
				entries.put(child.getName(), getMaxInSeconds((Stopwatch) child));
			}
		}
		return entries;
	}

	public static String[] getChildStatistics(Simon child) {
		List<String> stats = new ArrayList<String>();
		stats.add(child.getName());
		stats.add(getTotal((Stopwatch) child));
		stats.add(getCount((Stopwatch) child));
		stats.add(getMin((Stopwatch) child));
		stats.add(getMax((Stopwatch) child));
		stats.add(getMinTimestamp((Stopwatch) child));
		stats.add(getMaxTimestamp((Stopwatch) child));
		stats.add(getMaxActive((Stopwatch) child));
		stats.add(getmaxActiveTimestamp((Stopwatch) child));
		stats.add(getLast((Stopwatch) child));
		stats.add(getMean((Stopwatch) child));
		stats.add(getStandardDeviation((Stopwatch) child));
		stats.add(getNote((Stopwatch) child));
		stats.add(getFirstUsage((Stopwatch) child));
		stats.add(getLastUsage((Stopwatch) child));
		String[] statsArray = stats.toArray(new String[stats.size()]);
		return statsArray;
	}

	private static String getLastUsage(Stopwatch child) {
		return convertMillisToDate(((Stopwatch) child).getLastUsage());
	}

	private static String getFirstUsage(Stopwatch child) {
		return convertMillisToDate(((Stopwatch) child).getFirstUsage());
	}

	private static String getNote(Stopwatch child) {
		return ((Stopwatch) child).getNote();
	}

	private static String getStandardDeviation(Stopwatch child) {
		long round = Math.round(((Stopwatch) child).getStandardDeviation());
		return convertFromNanoToSeconds(round);
	}

	private static String getMean(Stopwatch child) {
		long round = Math.round(((Stopwatch) child).getMean());
		return convertFromNanoToSeconds(round);
	}

	private static String getLast(Stopwatch child) {
		return convertFromNanoToSeconds(((Stopwatch) child).getLast());
	}

	private static String getmaxActiveTimestamp(Stopwatch child) {
		return convertMillisToDate(((Stopwatch) child).getMaxActiveTimestamp());
	}

	private static String getMaxActive(Stopwatch child) {
		return getValueOfLong(((Stopwatch) child).getMaxActive());
	}

	private static String getMaxTimestamp(Stopwatch child) {
		return convertMillisToDate(((Stopwatch) child).getMaxTimestamp());
	}

	private static String getMinTimestamp(Stopwatch child) {
		return convertMillisToDate(((Stopwatch) child).getMinTimestamp());
	}

	private static Double getMaxInSeconds(Stopwatch child) {
		long number = ((Stopwatch) child).getMax();
		double seconds = (double) number / 1000000000.0;
		return Double.valueOf(seconds);
	}

	private static String getMax(Stopwatch child) {
		return convertFromNanoToSeconds(((Stopwatch) child).getMax());
	}

	private static String getMin(Stopwatch child) {
		return convertFromNanoToSeconds(((Stopwatch) child).getMin());
	}

	private static String getTotal(Stopwatch child) {
		return convertFromNanoToSeconds(((Stopwatch) child).getTotal());
	}

	private static String getCount(Stopwatch child) {
		return getValueOfLong(((Stopwatch) child).getCounter());
	}

	private static String convertFromNanoToSeconds(long number) {
		double seconds = (double) number / 1000000000.0;
		return String.valueOf(seconds);
	}

	private static String getValueOfLong(long number) {
		return String.valueOf(number);
	}

	private static String convertMillisToDate(long number) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
		Date date = new Date(number);
		return sdf.format(date);
	}

	public static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	private static String getFilename() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(FILENAME));
		} catch (IOException e) {
			System.out.println("Couldn't read the file [" + FILENAME + "].");
			e.printStackTrace();
		}
		String filename = properties.getProperty("sample.output.filename");
		String extension = properties.getProperty("sample.output.extension");

		boolean timestamps = properties.getProperty("filename.timestamps")
				.equalsIgnoreCase("true");
		if (timestamps) {
			return filename + "-" + now() + DELIMETER + extension;
		} else {
			return filename + DELIMETER + extension;
		}
	}
}
