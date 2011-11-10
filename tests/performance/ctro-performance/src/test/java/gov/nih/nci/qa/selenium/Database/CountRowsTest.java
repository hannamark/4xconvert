package gov.nih.nci.qa.selenium.Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVWriter;

public class CountRowsTest {
	private static final String FILENAME = "config.properties";
	private static final String SQL_FILENAME = "./src/test/resources/create-replace-function.sql";
	private static final String DELIMETER = ".";

	Connection connection = null;
	Properties properties;

	@Before
	public void loadProperties() {
		this.properties = new Properties();
		try {
			properties.load(new FileInputStream(FILENAME));

		} catch (IOException e) {
			System.out.println("Couldn't read the file [" + FILENAME + "].");
			e.printStackTrace();
		}
	}

	@Test
	public void getAllDatabaseRowCounts() {
		connectToDatabase();

		try {
			Class.forName("org.postgresql.Driver");
			Statement stmt = connection.createStatement();
			String sqlStatement = getFileContents(SQL_FILENAME);
			createRelation(stmt, sqlStatement);
			ResultSet resultSet = stmt.executeQuery("select count_em_all();");
			String filename = getFilename();
			CSVWriter writer = new CSVWriter(new FileWriter(filename), ',');
			String[] databaseUrl = getDatabaseUrl();
			writer.writeNext(databaseUrl);
			String[] header = getHeader();
			writer.writeNext(header);
			while (resultSet.next()) {
				String results = resultSet.getString("count_em_all");
				String removed = removeParatheses(results);
				String[] split = removed.split(",");
				writer.writeNext(split);
			}
			writer.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] getHeader() {
		List<String> headerList = new ArrayList<String>();
		headerList.add("table");
		headerList.add("count(*)");
		String[] headerArray = headerList
				.toArray(new String[headerList.size()]);
		return headerArray;
	}

	/**
	 * The pattern is "(activity_relationship,0)". Remove the paratheses from
	 * the ends.
	 * 
	 * @param remove
	 * @return
	 */
	private String removeParatheses(String remove) {
		int openParantheses = remove.indexOf("(");
		int closeParantheses = remove.indexOf(")");
		String removed = remove
				.substring(openParantheses + 1, closeParantheses);
		return removed;
	}

	private String[] getDatabaseUrl() {
		List<String> urlLine = new ArrayList<String>();
		String host = properties.getProperty("database.host");
		String port = properties.getProperty("database.port");
		String name = properties.getProperty("database.name");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + name;
		urlLine.add(url);
		String[] urlArray = urlLine.toArray(new String[urlLine.size()]);
		return urlArray;
	}

	private void connectToDatabase() {
		String host = properties.getProperty("database.host");
		String port = properties.getProperty("database.port");
		String name = properties.getProperty("database.name");
		String url = "jdbc:postgresql://" + host + ":" + port + "/" + name;
		String username = properties.getProperty("database.username");
		String password = properties.getProperty("database.password");

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void createRelation(Statement statement, String sqlStatement) {
		// Execute the SQL statement and get the results in a Resultset
		try {
			statement.executeQuery(sqlStatement);
		} catch (SQLException e) {
			// Gobble gobble...
		}
	}

	private String getFileContents(String filename) {
		String statement = "";

		File file = new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			String line;
			while ((line = br.readLine()) != null) {
				statement = statement + line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return statement;
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
		String filename = properties.getProperty("table.rows.filename");
		String extension = properties.getProperty("table.rows.extension");

		boolean timestamps = properties.getProperty("filename.timestamps")
				.equalsIgnoreCase("true");
		if (timestamps) {
			return filename + "-" + now() + DELIMETER + extension;
		} else {
			return filename + DELIMETER + extension;
		}
	}

}
