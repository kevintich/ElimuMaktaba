package elimu_maktabaLogin;

import java.sql.*;
import elimu_maktabaDatabaseConnection.*;

public class LoginDatabaseClass {
	 //Class from main database. Holds the main connection object
	ElimuMaktabaMainDatabase mainDatabase;
	
	Statement st;
	int portNumber = 3306;
	String databaseTable = "em_StaffLoginDetails";

	ResultSet resultSet;
	boolean allow;

	public LoginDatabaseClass() {
		mainDatabase = new ElimuMaktabaMainDatabase();

	}

	public boolean verifyLoginDetails(String staffID, String password) {
		String queryString = "select Staff_ID, Password from " + databaseTable
				+ " where Staff_ID = '" + staffID + "' and Password = '"
				+ password + "'";
		try {
			st = mainDatabase.conn.createStatement();
			resultSet = st.executeQuery(queryString);
			allow = resultSet.next();
		} catch (SQLException exp) {
			System.err.println("SQL Exception, connecting to database");
			exp.printStackTrace();
			return false;
		}
		return allow;
	}
	
	public ElimuMaktabaMainDatabase getInstance() {
		return mainDatabase;
	}
}
