package elimu_maktabaAdminScreen;

import elimu_maktabaDatabaseConnection.*;

import java.sql.*;

public class AdminDatabase {
	ElimuMaktabaMainDatabase mainDatabase;
	Statement st;
	String databaseTable = "em_StaffDetails";
	String databaseName = "elimu_maktaba";
	ResultSet resultSet;
	boolean allow;
	
	public AdminDatabase() {
		mainDatabase = new ElimuMaktabaMainDatabase();
	}
	public void selectMemberIDfromAccountsRegTable(String memberID){
		String queryString = "select MembershipID,AmountPaid,MembershipDuration,DateRecorded,TemporaryMember from em_AccountRegistration where MembershipID = '" + memberID+ "'";
		String duration,dateRecorded,temp;
		int amount;
		try {
			System.err.println("String to send for query: " + queryString);
			resultSet = st.executeQuery(queryString);
			while (resultSet.next()) {
				memberID = resultSet.getString("MembershipID");
				amount = resultSet.getInt("AmountPaid");
				duration = resultSet.getString("MembershipDuration");
				dateRecorded= resultSet.getString("DateRecorded");
				temp = resultSet.getString("TemporaryMember");
				//databaseInteface.memberRegistrationDetails(memberID,amount,duration,dateRecorded,temp);
				}

		} catch (SQLException exp) {
			System.err.println("SQL Exception, connecting to database");

			exp.printStackTrace();

		}
	}
	public void inputStaffDetailsToTable(String staffID,String firstName,String surname,String middleName,String dataOfBirth,String address,String nationalID,String email){
		
		String query = "insert into "+ databaseTable+"(StaffID,Firstname,surname,Middlename,DateofBirth,Address,NationalID,email_address) values('"+staffID+"','"+firstName+"','"+surname+"','"+middleName+"','"+dataOfBirth+"','"+address+"','"+nationalID+"','"+email+"')";
		boolean view = true;
		try {
			st = mainDatabase.conn.createStatement();

			view = st.execute(query);

		} catch (SQLException exp) {
			System.err.println("SQL Exception, connecting to database");
			exp.printStackTrace();
		}
	}
}
