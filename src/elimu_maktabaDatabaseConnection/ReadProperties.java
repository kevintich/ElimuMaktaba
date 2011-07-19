package elimu_maktabaDatabaseConnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {

	String serverName, databaseName, databaseLoginUserName, databasePassword;
	Properties props;
	String portNumber;

	public ReadProperties() {
		props = new Properties();
		try {
			props.load(new BufferedReader(new FileReader("maktaba.properties")));
			serverName = props.getProperty("serverName");
			databaseName = props.getProperty("" +
					"database");
			databaseLoginUserName = props.getProperty("username");
			portNumber = props.getProperty("port");
			databasePassword = props.getProperty("password");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getServerName() {

		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
		props.getProperty("databaseName");
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDatabaseLoginUserName() {
		return databaseLoginUserName;
	}

	public void setDatabaseLoginUserName(String databaseLoginUserName) {
		this.databaseLoginUserName = databaseLoginUserName;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}


}
