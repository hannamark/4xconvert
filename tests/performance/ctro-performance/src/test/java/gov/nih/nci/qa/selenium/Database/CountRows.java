package gov.nih.nci.qa.selenium.Database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class CountRows {
	private static final String FILENAME = "config.properties";

	Connection connection = null;
	Properties properties;

	@Before
	public void readProperties() {
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

		// Load the Driver class.
		try {
			Class.forName("org.postgresql.Driver");
			Statement stmt = connection.createStatement();
			String sqlStatement = getFile();
			createRelation(stmt, sqlStatement);
			ResultSet resultSet = stmt.executeQuery("select count_em_all();");
			BufferedWriter report = new BufferedWriter(new FileWriter(
					"RowCounts.txt"));
			while (resultSet.next()) {
				report.write(resultSet.getString("count_em_all") + '\n');
			}
			report.close();
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private String getFile() {
		String statement = "";

		File file = new File("./src/test/resources/create-replace-function.sql");
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

}
