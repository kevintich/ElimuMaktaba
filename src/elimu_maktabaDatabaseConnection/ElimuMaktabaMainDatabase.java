package elimu_maktabaDatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.mysql.jdbc.SQLError;

public class ElimuMaktabaMainDatabase {
	public Connection conn;

	ReadProperties props;
	PreparedStatement getReturnDetails = null;
	PreparedStatement getReturnStatus = null;
	PreparedStatement getBookBCNumbers = null;
	PreparedStatement psUpdateStaffDetailsTable = null;
	PreparedStatement getAllMembers = null, commitTheChanges = null;
	PreparedStatement changeOffence = null;
	PreparedStatement changeOffence2 = null;
	PreparedStatement getOffences = null;
	PreparedStatement addAUser = null;
	PreparedStatement getAUser = null;
	PreparedStatement updateUserAcc = null;
	PreparedStatement deleteUserAcc = null;
	PreparedStatement specificRegAccounts = null;
	PreparedStatement allRegAccounts = null;
	PreparedStatement specificBLAccounts = null;
	PreparedStatement allBlackListAccounts = null;
	PreparedStatement addMember = null;
	PreparedStatement saveAccountRecords = null;
	PreparedStatement getSpecificMemberDetails = null;
	PreparedStatement updateMemberDetails = null;
	PreparedStatement deleteMemberDetails = null;
	PreparedStatement saveLoginDetails = null;
	PreparedStatement getAllMemberRegDetails = null;
	PreparedStatement getAllUserDetails = null;
	PreparedStatement deleteBookAcc = null;
	PreparedStatement getABook = null;
	PreparedStatement updateBookAcc = null;
	PreparedStatement addABook = null;
	PreparedStatement getAllBookRecords = null;
	PreparedStatement addMemmberBornDate = null;
	PreparedStatement getBooksBorrowedToday = null;
	PreparedStatement getBooksBorrowedThisWeek = null;
	PreparedStatement getBooksBorrowedThisMonth = null;
	PreparedStatement getPeopleInBlacklist = null;
	PreparedStatement getBooksInBadCondition = null;
	PreparedStatement getBooksLost = null;
	PreparedStatement getMembersRegisteredToday = null;
	PreparedStatement getMembersRegisteredThisWeek = null;
	PreparedStatement getAccountsForPastHour = null;
	PreparedStatement getAccountsForToday = null;
	PreparedStatement getAccountsForThisWeek = null;
	PreparedStatement getABookToBorrower = null;
	PreparedStatement updateBooKBorrowedDatabase = null;
	PreparedStatement saveBorroweNBook = null;
	PreparedStatement obtainLastAdminStaffRegID = null;
	PreparedStatement obtainLastLibStaffRegID = null;
	PreparedStatement populateBlacklist = null;
	PreparedStatement getMemberName = null;
	PreparedStatement getBlacklistMember = null;
	PreparedStatement removeSpecificOffence = null;
	PreparedStatement getAllOffences = null;
	PreparedStatement getMemberIDS = null;
	PreparedStatement getLastMemID = null;
	PreparedStatement removeFromBlacklist = null;
	PreparedStatement addUserBornDate = null;
	PreparedStatement psUpdateBookDetailsTable = null;
	PreparedStatement getWithHeld = null;
	PreparedStatement updateReturn = null;
	public ElimuMaktabaMainDatabase() {
		props = new ReadProperties();
		getConnection();
	}

	public void getConnection() {
		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			String serverName = props.getServerName();
			String databaseName = props.getDatabaseName();
			String databaseLoginUserName = props.getDatabaseLoginUserName();
			String databasePassword = props.getDatabasePassword();
			int p = Integer.parseInt(props.getPortNumber());
			int portNumber = p;

			ds.setServerName(serverName);
			ds.setPortNumber(portNumber);
			ds.setDatabaseName(databaseName);
			ds.setUser(databaseLoginUserName);
			ds.setPassword(databasePassword);
			conn = ds.getConnection();

			getReturnDetails = conn.prepareStatement("select BookBarcodeNumber, MembershipID, DateRecorded,Returned from em_BookToBorrower where BookBarcodeNumber = ?");
			getBookBCNumbers = conn
					.prepareStatement("select BookBarcodeNumber from em_BookDetails");
			removeFromBlacklist = conn
					.prepareStatement("delete from em_BlackListMembers where MembershipID = ?");

			commitTheChanges = conn
					.prepareStatement("update em_MemberDetails set Firstname = ?, surname = ?, Middlename = ?, "
							+ "Gender = ? , MobileNumber = ?, email_address = ?, NationalID = ?, DateOfBirth = ? where MembershipID = ?");

			getAllMembers = conn
					.prepareStatement("select FirstName, Surname, MiddleName, Gender, MobileNumber, email_address, NationalID, DateOfBirth from em_MemberDetails");

			getMemberIDS = conn
					.prepareStatement("select MembershipID from em_MemberDetails");

			getMemberName = conn
					.prepareStatement("select FirstName, Surname, MiddleName from em_MemberDetails where MembershipID = ?");

			changeOffence = conn
					.prepareStatement("update em_BlackListMembers set OffenceDone = ? where MembershipID = ? and OffenceDone = ? and Penalties = ?");
			changeOffence2 = conn
					.prepareStatement("update em_BlackListMembers set Penalties = ? where MembershipID = ? and OffenceDone = ? and Penalties = ?");

			populateBlacklist = conn
					.prepareStatement("insert into em_BlackListMembers(MembershipID, OffenceDone, Penalties) values (?,?,?)");
			getAllOffences = conn
					.prepareStatement("select OffenceDone from em_BlackListMembers where MembershipID = ?");

			getAllBookRecords = conn
					.prepareStatement("select * from em_BookDetails");
			addABook = conn
					.prepareStatement("insert into em_BookDetails"
							+ "(BookBarcodeNumber, BookTitle, BookVersion, BookAuthor, BookPublisher, NumberOfPages, BookGenre)"
							+ "values(?,?,?,?,?,?,?)");
			removeSpecificOffence = conn
					.prepareStatement("delete from em_BlackListMembers where MembershipID = ? and OffenceDone = ?");

			getABook = conn
					.prepareStatement("select * from em_BookDetails where BookBarcodeNumber = ?");

			getAllMemberRegDetails = conn
					.prepareStatement("select * from em_MemberDetails");
			addAUser = conn
					.prepareStatement("insert into em_StaffDetails"
							+ "(StaffID, Firstname, surname, Middlename,email_address, Address, NationalID)"
							+ "values(?,?,?,?,?,?,?)");
			addUserBornDate = conn
					.prepareStatement("update em_StaffDetails set DateOfBirth = ? where StaffID = ?");
			addMemmberBornDate = conn
					.prepareStatement("update em_MemberDetails set DateOfBirth = ? where MembershipID = ?");
			getAUser = conn
					.prepareStatement("select * from em_StaffDetails where StaffID = ?");

			updateUserAcc = conn
					.prepareStatement("update em_StaffDetails set Firstname = ?, surname = ?, Middlename = ?, "
							+ "DateOfBirth = ?, Address = ?, NationalID = ?, email_address = ? where StaffID = ?");

			deleteUserAcc = conn
					.prepareStatement("delete from em_StaffDetails where StaffID = ?");

			specificRegAccounts = conn
					.prepareStatement("select * from em_AccountRegistration where MembershipID = ?");

			allRegAccounts = conn
					.prepareStatement("select * from em_AccountRegistration");

			specificBLAccounts = conn
					.prepareStatement("select * from em_AccountsBlackList where MembershipID = ?");

			allBlackListAccounts = conn
					.prepareStatement("select * from em_AccountsBlackList");

			getBlacklistMember = conn
					.prepareStatement("select OffenceDone from em_BlackListMembers where MembershipID = ?");

			addMember = conn
					.prepareStatement("insert into em_MemberDetails"
							+ "(MembershipID,FirstName,Surname,MiddleName,Gender,MobileNumber,email_address,NationalID)"
							+ "values(?,?,?,?,?,?,?,?)");

			saveAccountRecords = conn
					.prepareStatement("insert into em_AccountRegistration (MembershipID,AmountPaid,MembershipDuration,TemporaryMemeber) values (?,?,?,?)");

			getSpecificMemberDetails = conn
					.prepareStatement("select * from em_MemberDetails where MembershipID = ?");

			updateReturn = conn.prepareStatement("update em_BookToBorrower set Returned = ? where  BookBarcodeNumber = ? and DateRecorded = ?");
			updateMemberDetails = conn
					.prepareStatement("update em_MemberDetails set FirstName = ?,Surname = ?,MiddleName = ?,Gender = ?,MobileNumber = ?,email_address = ?,NationalID = ? ,DateOfBirth = ?  where MembershipID = ?");
			deleteMemberDetails = conn
					.prepareStatement("delete from em_MemberDetails where MembershipID = ?");
			saveLoginDetails = conn
					.prepareStatement("insert into em_StaffLoginDetails (Staff_ID,Password) values(?,?)");
			getAllUserDetails = conn
					.prepareStatement("select * from em_StaffDetails order by StaffID");
			deleteBookAcc = conn
					.prepareStatement("delete from em_BookDetails where BookBarcodeNumber = ?");
			updateBookAcc = conn
					.prepareStatement("update em_BookDetails set BookTitle = ?, BookVersion = ?,"
							+ " BookAuthor = ?, BookPublisher = ?, NumberOfPages = ?, BookGenre = ? where BookBarcodeNumber = ?");

			getBooksBorrowedToday = conn
					.prepareStatement("SELECT bd.BookTitle as `Book Title`, concat( FirstName, ' ',Surname) as `Borrower Name`, concat(hour(DateRegistered),':',minute(DateRegistered)) as `Time Borrowed`  from em_MemberDetails  e, em_BookDetails bd , em_BookToBorrower btb where e.MembershipID = btb.MembershipID and bd.BookBarCodeNumber = btb.BookBarCodeNumber and day(now()) = day(DateRegistered) and month(now()) = month(DateRegistered) and year(now()) = year(DateRegistered);");

			getBooksBorrowedThisWeek = conn
					.prepareStatement("SELECT bd.BookTitle as `Book Title`, concat( FirstName, ' ',Surname) as `Borrower Name`, concat(hour(DateRegistered),':',minute(DateRegistered)) as `Time Borrowed`  from em_MemberDetails  e, em_BookDetails bd , em_BookToBorrower btb where e.MembershipID = btb.MembershipID and bd.BookBarCodeNumber = btb.BookBarCodeNumber and week(now()) = week(DateRegistered) and year(now()) = year(DateRegistered);");

			getBooksBorrowedThisMonth = conn
					.prepareStatement("SELECT bd.BookTitle as `Book Title`, concat( FirstName, ' ',Surname) as `Borrower Name`, concat(hour(DateRegistered),':',minute(DateRegistered)) as `Time Borrowed`  from em_MemberDetails  e, em_BookDetails bd , em_BookToBorrower btb where e.MembershipID = btb.MembershipID and bd.BookBarCodeNumber = btb.BookBarCodeNumber and month(now()) = month(DateRegistered) and year(now()) = year(DateRegistered);");

			getPeopleInBlacklist = conn
					.prepareStatement("SELECT concat(FirstName, ' ' ,Surname) as `Member Name`, Date(DateRecorded) as DateRecorded, OffenceDone FROM  em_MemberDetails md, em_BlackList bl where md.MembershipID = bl.MembershipID;");

			getBooksInBadCondition = conn
					.prepareStatement("SELECT bd.BookBarcodeNumber, BookTitle, BookStatus FROM em_BookCondition bc , em_BookDetails bd where bc.BookBarcodeNumber = bd.BookBarcodeNumber and BookStatus != 'Lost';");

			getBooksLost = conn
					.prepareStatement("SELECT bd.BookBarcodeNumber, BookTitle, BookStatus FROM em_BookCondition bc , em_BookDetails bd where bc.BookBarcodeNumber = bd.BookBarcodeNumber and BookStatus = 'Lost';");

			getMembersRegisteredToday = conn
					.prepareStatement("SELECT MembershipID, concat(FirstName,' ',Surname) as MemberName, Gender, Date(DateRegistered) as DateRegistered FROM em_MemberDetails e where Day(DateRegistered) = Day(now()) and Month(DateRegistered) = Month(now()) and Year(DateRegistered) = Year(now());");

			getMembersRegisteredThisWeek = conn
					.prepareStatement("SELECT MembershipID, concat(FirstName,' ',Surname) as MemberName, Gender, Date(DateRegistered) as DateRegistered FROM em_MemberDetails e where Week(DateRegistered) = Week(now()) and Year(DateRegistered) = Year(now());");

			getAccountsForPastHour = conn
					.prepareStatement("SELECT ar.MembershipID, concat(FirstName, ' ',Surname) as MemberName, AmountPaid, time(DateRecorded) as DateRecorded FROM em_AccountRegistration ar, em_MemberDetails md  where ar.MembershipID = md.MembershipID and hour(DateRecorded) = hour(now()) and day(DateRecorded) = day(now()) and month(DateRecorded) = month(now()) and year(DateRecorded) =  year(now());");

			getAccountsForToday = conn
					.prepareStatement("SELECT ar.MembershipID, concat(FirstName, ' ',Surname) as MemberName, AmountPaid, time(DateRecorded) as DateRecorded FROM em_AccountRegistration ar, em_MemberDetails md  where ar.MembershipID = md.MembershipID and day(DateRecorded) = day(now()) and month(DateRecorded) = month(now()) and year(DateRecorded) =  year(now());");

			getAccountsForThisWeek = conn
					.prepareStatement("SELECT ar.MembershipID, concat(FirstName, ' ',Surname) as MemberName, AmountPaid, date(DateRecorded) as DateRecorded FROM em_AccountRegistration ar, em_MemberDetails md  where ar.MembershipID = md.MembershipID and week(DateRecorded) = week(now()) and year(DateRecorded) =  year(now());");

			getABookToBorrower = conn
					.prepareStatement("select * from em_BookToBorrower where BookBarcodeNumber = ?");

			updateBooKBorrowedDatabase = conn
					.prepareStatement("update em_BookToBorrower set Returned = ? where  BookBarcodeNumber = ?");

			saveBorroweNBook = conn
					.prepareStatement("insert into  em_BookToBorrower (BookBarcodeNumber,MembershipID) values(?,?) ");

			obtainLastAdminStaffRegID = conn
					.prepareStatement("SELECT Staff_ID FROM em_StaffLoginDetails where substring(Staff_ID,1,1) = '1'");
			obtainLastLibStaffRegID = conn
					.prepareStatement("SELECT Staff_ID FROM em_StaffLoginDetails where substring(Staff_ID,1,1) = '2'");
			getOffences = conn
					.prepareStatement("select OffenceDone, Penalties from em_BlackListMembers where MembershipID = ?");
			getLastMemID = conn
					.prepareStatement("select MembershipID from em_MemberDetails order by MembershipID DESC LIMIT 0,1");
			psUpdateStaffDetailsTable = conn
					.prepareStatement("UPDATE em_StaffDetails e SET Firstname= ?, surname=?, middlename=?, DateOfBirth=?, Address=? where StaffID=?;");
			psUpdateBookDetailsTable = conn
					.prepareStatement("UPDATE em_BookDetails e SET BookTitle=?,BookVersion=?,BookAuthor=?,BookPublisher=?,NumberOfPages=?,BookGenre=? where BookBarcodeNumber=?;");

			getReturnStatus = conn
					.prepareStatement("select Returned from em_BookToBorrower where MembershipID = ?");
			getWithHeld = conn.prepareStatement("select BookBarcodeNumber from em_BookToBorrower where MembershipID = ? ");
			System.out.println("database connected successfully");
		} catch (Exception e) {

			JOptionPane
					.showMessageDialog(
							null,
							"Cannot connect to the MYSQL server please check <maktaba.propeties> file or your network connection");
			System.exit(0);

		}
	}

	public String getWitheldBook(String memID){
		String result = "";
		ResultSet resultSet = null;

		try {
			getWithHeld.setString(1, memID);

			resultSet = getWithHeld.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString("BookBarcodeNumber");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}
	public String returnBorroweredStatus(String memID) {
		String result = "";
		ResultSet resultSet = null;

		try {
			getReturnStatus.setString(1, memID);

			resultSet = getReturnStatus.executeQuery();
			while (resultSet.next()) {
				result = resultSet.getString("Returned");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Vector<String> getBookBCNumbers() {
		String bookID = null;
		ResultSet resultSet = null;
		Vector<String> ids = new Vector<String>();
		try {

			resultSet = getBookBCNumbers.executeQuery();

			while (resultSet.next()) {
				bookID = resultSet.getString("BookBarcodeNumber");
				ids.add(bookID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}

	public Vector<String> getMemberIDs() {
		String ID = null;
		ResultSet resultSet = null;
		Vector<String> ids = new Vector<String>();
		try {

			resultSet = getMemberIDS.executeQuery();

			while (resultSet.next()) {
				ID = resultSet.getString("MembershipID");
				ids.add(ID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ids;
	}

	public int updateMemberBirthDay(String birthDay, String memberID) {
		int result = 0;

		try {
			addMemmberBornDate.setString(1, birthDay);
			addMemmberBornDate.setString(2, memberID);

			result = addMemmberBornDate.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int updateUserBirthDay(String birthDay, String staffID) {
		int result = 0;

		try {
			addUserBornDate.setString(1, birthDay);
			addUserBornDate.setString(2, staffID);

			result = addUserBornDate.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int removeBlacklistMember(String ID) {
		int result = 0;

		try {
			removeFromBlacklist.setString(1, ID);

			result = removeFromBlacklist.executeUpdate();

			System.out.println("" + result);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getLastMemberID() {
		String name = null;
		ResultSet resultSet = null;

		try {

			resultSet = getLastMemID.executeQuery();

			while (resultSet.next()) {
				name = resultSet.getString("MembershipID");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return name;

	}

	public ResultSet allMembers() {
		ResultSet resultSet = null;

		try {

			resultSet = getAllMembers.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public ArrayList<Integer> commitChanges(
			HashMap<Integer, HashMap<Integer, String>> map) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int count = 0;
		ResultSet resultSet = null;
		String id = "";

		try {
			resultSet = getMemberIDS.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getString(1);

				for (int i = 0; i < map.get(count).size(); i++) {
					commitTheChanges
							.setString(i + 1, map.get(count).get(i + 1));
				}

				commitTheChanges.setString(map.get(count).size() + 1, id);
				result.add(commitTheChanges.executeUpdate());
				count++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int removeOffenceSpecific(String ID, String offence) {
		int result = 0;

		try {
			removeSpecificOffence.setString(1, ID);
			removeSpecificOffence.setString(2, offence);

			result = removeSpecificOffence.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Vector<String> getMemberOffences(String ID) {
		Vector<String> offences = new Vector<String>();
		ResultSet resultSet = null;

		try {
			getAllOffences.setString(1, ID);

			resultSet = getAllOffences.executeQuery();

			while (resultSet.next()) {
				offences.add(resultSet.getString("OffenceDone"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return offences;
	}

	public boolean inBlackList(String ID) {
		boolean result = false;
		ResultSet resultSet = null;

		try {
			getBlacklistMember.setString(1, ID);

			resultSet = getBlacklistMember.executeQuery();

			resultSet.absolute(1);
			String off = resultSet.getString("OffenceDone");

			if (off != null)
				result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getColName(String ID, int index) {
		String result = "";
		ResultSet resultSet = null;
		ResultSetMetaData metadata = null;

		try {
			getOffences.setString(1, ID);

			resultSet = getOffences.executeQuery();
			metadata = resultSet.getMetaData();
			result = metadata.getColumnName(index + 1);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int updateTableCell(String ID, int row, int col, Object value,
			String offence, String Penalties) {
		// System.out.println("Called!" + row + col);
		int result = 0;

		try {
			if (col == 0) {
				changeOffence.setString(1, value.toString());
				changeOffence.setString(2, ID);
				changeOffence.setString(3, offence);
				changeOffence.setString(4, Penalties);

				result = changeOffence.executeUpdate();
			} else if (col == 1) {
				changeOffence2.setString(1, value.toString());
				changeOffence2.setString(2, ID);
				changeOffence2.setString(3, offence);
				changeOffence2.setString(4, Penalties);

				result = changeOffence2.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getCellValue(String ID, int row, int col) {
		String result = "";
		ResultSet resultSet = null;

		try {
			getOffences.setString(1, ID);

			resultSet = getOffences.executeQuery();

			resultSet.absolute(row + 1);

			if ((col + 1) == 1) {
				result = resultSet.getString("OffenceDone");
			} else if ((col + 1) == 2) {
				result = resultSet.getString("Penalties");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getNoRows(String ID) {
		int result = 0;
		ResultSet resultSet = null;

		try {
			getOffences.setString(1, ID);

			resultSet = getOffences.executeQuery();

			while (resultSet.next()) {
				result++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getNoCols(String ID) {
		int result = 0;
		ResultSet resultSet = null;
		ResultSetMetaData metadata = null;

		try {
			getOffences.setString(1, ID);

			resultSet = getOffences.executeQuery();
			metadata = resultSet.getMetaData();
			result = metadata.getColumnCount();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String getFullName(String ID) {
		String name = null;
		String test = null;
		ResultSet resultSet = null;

		try {
			getMemberName.setString(1, ID);

			resultSet = getMemberName.executeQuery();

			while (resultSet.next()) {
				test = resultSet.getString("FirstName");
				if (test != null) {
					name = resultSet.getString("Surname") + ", "
							+ resultSet.getString("FirstName") + " "
							+ resultSet.getString("MiddleName");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return name;
	}

	public int addToBlacklist(String ID, String offence, String Penalties) {
		int result = 0;

		try {
			populateBlacklist.setString(1, ID);
			populateBlacklist.setString(2, offence);
			populateBlacklist.setString(3, Penalties);

			result = populateBlacklist.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Vector<String> staffIDVector = new Vector<String>();
	public HashMap<String, String> hashFirstName = new HashMap<String, String>();
	public HashMap<String, String> hashSurname = new HashMap<String, String>();
	public HashMap<String, String> hashMiddleName = new HashMap<String, String>();
	public HashMap<String, String> hashDateOfBirth = new HashMap<String, String>();
	public HashMap<String, String> hashAddresses = new HashMap<String, String>();
	public HashMap<String, String> hashNationalID = new HashMap<String, String>();
	public HashMap<String, String> hashEmailAddress = new HashMap<String, String>();

	/*
	 * new method returns vector to populate modify user admin screen table
	 */
	public Vector<HashMap<String, String>> getStaffUserDetailsRecords() {

		Vector<HashMap<String, String>> data = new Vector<HashMap<String, String>>();

		ResultSet resultSet = null;
		try {
			resultSet = getAllUserDetails.executeQuery();
			while (resultSet.next()) {
				String staffID = resultSet.getString("StaffID");
				String firstN = resultSet.getString("Firstname");
				String surN = resultSet.getString("surname");
				String middN = resultSet.getString("MiddleName");
				String dob = resultSet.getString("DateOfBirth");
				String address = resultSet.getString("Address");
				String nationID = resultSet.getString("NationalID");
				String email = resultSet.getString("email_address");
				staffIDVector.add(staffID);
				hashFirstName.put(staffID, firstN);
				hashSurname.put(staffID, surN);
				hashMiddleName.put(staffID, middN);
				hashDateOfBirth.put(staffID, dob);
				hashAddresses.put(staffID, address);
				hashNationalID.put(staffID, nationID);
				hashEmailAddress.put(staffID, email);
			}
			data.add(hashFirstName);
			data.add(hashSurname);
			data.add(hashMiddleName);
			data.add(hashDateOfBirth);
			data.add(hashAddresses);
			data.add(hashNationalID);
			data.add(hashEmailAddress);

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public Vector<String> bookIDVector = new Vector<String>();
	public HashMap<String, String> hashBookTitle = new HashMap<String, String>();
	public HashMap<String, String> hashBVersion = new HashMap<String, String>();
	public HashMap<String, String> hashBAuthor = new HashMap<String, String>();
	public HashMap<String, String> hashBPublisher = new HashMap<String, String>();
	public HashMap<String, String> hashBPages = new HashMap<String, String>();
	public HashMap<String, String> hashBGenre = new HashMap<String, String>();

	/*
	 * method to populate modify book table in BooksTabbedPaneClass
	 */
	public Vector<HashMap<String, String>> getBookDetailsRecords() {

		Vector<HashMap<String, String>> data = new Vector<HashMap<String, String>>();

		ResultSet resultSet = null;
		try {
			resultSet = getAllBookRecords.executeQuery();
			while (resultSet.next()) {
				String bookID = resultSet.getString("BookBarcodeNumber");
				String title = resultSet.getString("BookTitle");
				String version = resultSet.getString("BookVersion");
				String author = resultSet.getString("BookAuthor");
				String publisher = resultSet.getString("BookPublisher");
				String pages = resultSet.getString("NumberOfPages");
				String genre = resultSet.getString("BookGenre");
				bookIDVector.add(bookID);
				hashBookTitle.put(bookID, title);
				hashBVersion.put(bookID, version);
				hashBAuthor.put(bookID, author);
				hashBPublisher.put(bookID, publisher);
				hashBPages.put(bookID, pages);
				hashBGenre.put(bookID, genre);
			}
			data.add(hashBookTitle);
			data.add(hashBVersion);
			data.add(hashBAuthor);
			data.add(hashBPublisher);
			data.add(hashBPages);
			data.add(hashBGenre);

		} catch (SQLException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public String getLastLibsStaffRegID() {
		String previousStaffID = null;
		ResultSet resultSet = null;
		try {
			resultSet = obtainLastLibStaffRegID.executeQuery();
			while (resultSet.next()) {
				previousStaffID = resultSet.getString("Staff_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return previousStaffID;
	}

	public String getLastAdminStaffRegID() {
		String previousStaffID = null;
		ResultSet resultSet = null;
		try {
			resultSet = obtainLastAdminStaffRegID.executeQuery();
			while (resultSet.next()) {
				previousStaffID = resultSet.getString("Staff_ID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return previousStaffID;
	}

	public String[] findBookToBorrower(String bookNum) {
		String[] result = new String[4];
		ResultSet resultSet = null;

		try {
			getABookToBorrower.setString(1, bookNum);

			resultSet = getABookToBorrower.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("BookBarcodeNumber");
				result[1] = resultSet.getString("MembershipID");
				result[2] = resultSet.getString("DateRecorded");
				result[3] = resultSet.getString("Returned");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int addUser(String[] userDetails) {
		int result = 0;

		try {

			for (int i = 0; i < userDetails.length; i++) {
				addAUser.setString(i + 1, userDetails[i]);
			}

			result = addAUser.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String[] findUser(String ID) {
		String[] result = new String[8];
		ResultSet resultSet = null;

		try {
			getAUser.setString(1, ID);

			resultSet = getAUser.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("StaffID");
				result[1] = resultSet.getString("Firstname");
				result[2] = resultSet.getString("surname");
				result[3] = resultSet.getString("Middlename");
				result[4] = resultSet.getString("DateOfBirth");
				result[5] = resultSet.getString("Address");
				result[6] = resultSet.getString("NationalID");
				result[7] = resultSet.getString("email_address");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int updateUser(String[] updateDetails) {
		int result = 0;

		try {
			updateUserAcc.setString(1, updateDetails[1]);
			updateUserAcc.setString(2, updateDetails[2]);
			updateUserAcc.setString(3, updateDetails[3]);
			updateUserAcc.setString(4, updateDetails[4]);
			updateUserAcc.setString(5, updateDetails[5]);
			updateUserAcc.setString(6, updateDetails[6]);
			updateUserAcc.setString(7, updateDetails[7]);
			updateUserAcc.setString(8, updateDetails[0]);

			result = updateUserAcc.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int removeUser(String ID) {
		int result = 0;

		try {
			deleteUserAcc.setString(1, ID);

			result = deleteUserAcc.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public String[] getUserRegistrationDetails(String RegID) {
		String[] result = new String[5];
		ResultSet resultSet = null;

		try {
			specificRegAccounts.setString(1, RegID);

			resultSet = specificRegAccounts.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("MembershipID");
				result[1] = String.valueOf(resultSet.getInt("AmountPaid"));
				result[2] = resultSet.getString("MembershipDuration");
				result[3] = resultSet.getString("DateRecorded");
				result[4] = resultSet.getString("TemporaryMemeber");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	public ListIterator<RecordsMemberRegisrationAccounts> getAllDetailsFromRegAccount() {
		String[] result = new String[5];
		ResultSet resultSet = null;
		Vector<RecordsMemberRegisrationAccounts> returnvalue = new Vector<RecordsMemberRegisrationAccounts>();
		ListIterator<RecordsMemberRegisrationAccounts> list = null;
		try {
			resultSet = allRegAccounts.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("MembershipID");
				result[1] = String.valueOf(resultSet.getInt("AmountPaid"));
				result[2] = resultSet.getString("MembershipDuration");
				result[3] = resultSet.getString("DateRecorded");
				result[4] = resultSet.getString("TemporaryMemeber");
				RecordsMemberRegisrationAccounts recordsMemberRegisrationAccounts = new RecordsMemberRegisrationAccounts(
						result[0], result[1], result[2], result[3], result[4]);
				returnvalue.add(recordsMemberRegisrationAccounts);
			}
			list = returnvalue.listIterator();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public String[] getBlackListDetails(String BlID) {
		String[] result = new String[2];
		ResultSet resultSet = null;

		try {
			specificBLAccounts.setString(1, BlID);

			resultSet = specificBLAccounts.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("MembershipID");
				result[1] = resultSet.getString("OffencePaid");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}

	public ListIterator<RecordsMemberBlackList> getAllDetailsFromAccountsBL() {
		String[] result = new String[2];
		ResultSet resultSet = null;
		Vector<RecordsMemberBlackList> vector = new Vector<RecordsMemberBlackList>();
		ListIterator<RecordsMemberBlackList> list = null;

		try {

			resultSet = allBlackListAccounts.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("MembershipID");
				result[1] = resultSet.getString("OffencePaid");
				RecordsMemberBlackList recordsBL = new RecordsMemberBlackList(
						result[0], result[1]);
				vector.add(recordsBL);
			}
			list = vector.listIterator();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public int addNewMember(String[] memberDetails) {
		int result = 0;

		try {

			for (int i = 0; i < memberDetails.length; i++) {
				addMember.setString(i + 1, memberDetails[i]);
			}

			result = addMember.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int saveAccountDetails(String meID, String amount, String duration,
			String temp) {
		System.out.println("String amount " + amount);
		int result = 0;
		int t = Integer.parseInt(amount);

		System.out.println("Amount From db " + t);
		try {
			saveAccountRecords.setString(1, meID);
			saveAccountRecords.setInt(2, t);
			saveAccountRecords.setString(3, duration);
			saveAccountRecords.setString(4, temp);

			result = saveAccountRecords.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String[] findReturnBook(String bookID){
		String[] bookReturnDetails = new String[4];
		ResultSet resultSet = null;
		try{
			getReturnDetails.setString(1,bookID);
			resultSet = getReturnDetails.executeQuery();
			while(resultSet.next()){
				bookReturnDetails[0] = resultSet.getString("BookBarcodeNumber");
				bookReturnDetails[1] = resultSet.getString("MembershipID");
				bookReturnDetails[2] = resultSet.getString("DateRecorded");
				bookReturnDetails[3] = resultSet.getString("Returned");
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bookReturnDetails;
	}
	public String[] findSpecificMembers(String memID) {
		String[] memDetails = new String[9];
		ResultSet resultSet = null;
		try {
			getSpecificMemberDetails.setString(1, memID);
			resultSet = getSpecificMemberDetails.executeQuery();
			// resultSet = specificRegAccounts.executeQuery();

			while (resultSet.next()) {
				memDetails[0] = resultSet.getString("MembershipID");
				memDetails[1] = resultSet.getString("FirstName");
				memDetails[2] = resultSet.getString("Surname");
				memDetails[3] = resultSet.getString("MiddleName");
				memDetails[4] = resultSet.getString("Gender");
				memDetails[5] = resultSet.getString("MobileNumber");
				memDetails[6] = resultSet.getString("email_address");
				memDetails[7] = resultSet.getString("NationalID");
				memDetails[8] = resultSet.getString("DateOfBirth");

			}
		} catch (SQLException s) {
			s.printStackTrace();
		}
		return memDetails;

	}

	public int updateReturnDetails(String[] updateDetails){
		int result = 0;
		try{
			updateReturn.setString(1,updateDetails[3]);
			updateReturn.setString(2,updateDetails[0]);
			updateReturn.setString(3,updateDetails[2]);
			
			result = updateReturn.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public int updateMember(String[] updateDetails) {
		int result = 0;

		try {
			updateMemberDetails.setString(1, updateDetails[1]);
			updateMemberDetails.setString(2, updateDetails[2]);
			updateMemberDetails.setString(3, updateDetails[3]);
			updateMemberDetails.setString(4, updateDetails[4]);
			updateMemberDetails.setString(5, updateDetails[5]);
			updateMemberDetails.setString(6, updateDetails[6]);
			updateMemberDetails.setString(7, updateDetails[7]);
			updateMemberDetails.setString(8, updateDetails[8]);
			updateMemberDetails.setString(9, updateDetails[0]);

			result = updateMemberDetails.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int deleteMember(String details) {
		int result = 0;

		try {
			deleteMemberDetails.setString(1, details);

			result = deleteMemberDetails.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int saveUserLoginDetails(String staffID, String password) {
		int result = 0;

		try {
			saveLoginDetails.setString(1, staffID);
			saveLoginDetails.setString(2, password);
			result = saveLoginDetails.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ListIterator<RecordsMemberRegDetails> getAllMemberRegistrationDetails() {
		String[] result = new String[10];
		ResultSet resultSet = null;
		Vector<RecordsMemberRegDetails> vector = new Vector<RecordsMemberRegDetails>();
		ListIterator<RecordsMemberRegDetails> list = null;

		try {

			resultSet = getAllMemberRegDetails.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("MembershipID");
				result[1] = resultSet.getString("FirstName");
				result[2] = resultSet.getString("Surname");
				result[3] = resultSet.getString("MiddleName");
				result[4] = resultSet.getString("Gender");
				result[5] = resultSet.getString("MobileNumber");
				result[6] = resultSet.getString("email_address");
				result[7] = resultSet.getString("NationalID");
				result[8] = resultSet.getString("DateOfBirth");
				result[9] = resultSet.getString("DateRegistered");
				RecordsMemberRegDetails records = new RecordsMemberRegDetails(
						result[0], result[1], result[2], result[3], result[4],
						result[5], result[6], result[7], result[8], result[9]);
				vector.add(records);
			}
			list = vector.listIterator();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public int removeBook(String ID) {
		int result = 0;

		try {
			deleteBookAcc.setString(1, ID);

			result = deleteBookAcc.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ListIterator<RecordsUserDetails> getAllUserDetails() {
		String[] result = new String[9];
		ResultSet resultSet = null;
		Vector<RecordsUserDetails> vector = new Vector<RecordsUserDetails>();
		ListIterator<RecordsUserDetails> list = null;

		try {

			resultSet = getAllUserDetails.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("StaffID");
				result[1] = resultSet.getString("Firstname");
				result[2] = resultSet.getString("surname");
				result[3] = resultSet.getString("MiddleName");
				result[4] = resultSet.getString("DateOfBirth");
				result[5] = resultSet.getString("Address");
				result[6] = resultSet.getString("NationalID");
				result[7] = resultSet.getString("email_address");
				result[8] = resultSet.getString("DateRegistered");
				RecordsUserDetails records = new RecordsUserDetails(result[0],
						result[1], result[2], result[3], result[4], result[5],
						result[6], result[7], result[8]);
				vector.add(records);
			}
			list = vector.listIterator();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public String[] findBook(String ID) {
		String[] result = new String[7];
		ResultSet resultSet = null;

		try {
			getABook.setString(1, ID);

			resultSet = getABook.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("BookBarcodeNumber");
				result[1] = resultSet.getString("BookTitle");
				result[2] = resultSet.getString("BookVersion");
				result[3] = resultSet.getString("BookAuthor");
				result[4] = resultSet.getString("BookPublisher");
				result[5] = resultSet.getString("NumberOfPages");
				result[6] = resultSet.getString("BookGenre");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int updateBook(String[] updateDetails) {
		int result = 0;

		try {
			updateBookAcc.setString(1, updateDetails[1]);
			updateBookAcc.setString(2, updateDetails[2]);
			updateBookAcc.setString(3, updateDetails[3]);
			updateBookAcc.setString(4, updateDetails[4]);
			updateBookAcc.setString(5, updateDetails[5]);
			updateBookAcc.setString(6, updateDetails[6]);
			updateBookAcc.setString(7, updateDetails[0]);

			result = updateBookAcc.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int addABook(String[] bookDetails) {
		int result = 0;

		try {

			for (int i = 0; i < bookDetails.length; i++) {
				addABook.setString(i + 1, bookDetails[i]);
			}

			result = addABook.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ListIterator<RecordsAllBooks> getAllBookRecords() {
		String[] result = new String[7];
		ResultSet resultSet = null;
		Vector<RecordsAllBooks> vector = new Vector<RecordsAllBooks>();
		ListIterator<RecordsAllBooks> list = null;
		try {
			// getABook.setString(1, ID);

			resultSet = getAllBookRecords.executeQuery();

			while (resultSet.next()) {
				result[0] = resultSet.getString("BookBarcodeNumber");
				result[1] = resultSet.getString("BookTitle");
				result[2] = resultSet.getString("BookVersion");
				result[3] = resultSet.getString("BookAuthor");
				result[4] = resultSet.getString("BookPublisher");
				result[5] = resultSet.getString("NumberOfPages");
				result[6] = resultSet.getString("BookGenre");
				RecordsAllBooks ra = new RecordsAllBooks(result[0], result[1],
						result[2], result[3], result[4], result[5], result[6]);
				vector.add(ra);
			}
			list = vector.listIterator();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Vector<Vector<String>> librarianViewBooksBorrowedToday() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getBooksBorrowedToday.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("Book Title"));
				tempVector.add(resultSet.getString("Borrower Name"));
				tempVector.add(resultSet.getString("Time Borrowed"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewBooksBorrowedThisWeek() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getBooksBorrowedThisWeek.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("Book Title"));
				tempVector.add(resultSet.getString("Borrower Name"));
				tempVector.add(resultSet.getString("Time Borrowed"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewBooksBorrowedThisMonth() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getBooksBorrowedThisMonth.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("Book Title"));
				tempVector.add(resultSet.getString("Borrower Name"));
				tempVector.add(resultSet.getString("Time Borrowed"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewPeopleInBlacklist() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getPeopleInBlacklist.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("Member Name"));
				tempVector.add(resultSet.getString("DateRecorded"));
				tempVector.add(resultSet.getString("OffenceDone"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewBooksInBadCondition() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getBooksInBadCondition.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("BookBarcodeNumber"));
				tempVector.add(resultSet.getString("BookTitle"));
				tempVector.add(resultSet.getString("BookStatus"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewBooksLost() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getBooksLost.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("BookBarcodeNumber"));
				tempVector.add(resultSet.getString("BookTitle"));
				tempVector.add(resultSet.getString("BookStatus"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewMembersRegisteredToday() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getMembersRegisteredToday.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("MembershipID"));
				tempVector.add(resultSet.getString("MemberName"));
				tempVector.add(resultSet.getString("Gender"));
				tempVector.add(resultSet.getString("DateRegistered"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewMembersRegisteredThisWeek() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getMembersRegisteredThisWeek.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("MembershipID"));
				tempVector.add(resultSet.getString("MemberName"));
				tempVector.add(resultSet.getString("Gender"));
				tempVector.add(resultSet.getString("DateRegistered"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewAccoutsForPastHour() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getAccountsForPastHour.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("MembershipID"));
				tempVector.add(resultSet.getString("MemberName"));
				tempVector.add(resultSet.getString("AmountPaid"));
				tempVector.add(resultSet.getString("DateRecorded"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewAccountsForToday() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getAccountsForToday.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("MembershipID"));
				tempVector.add(resultSet.getString("MemberName"));
				tempVector.add(resultSet.getString("AmountPaid"));
				tempVector.add(resultSet.getString("DateRecorded"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;

	}

	public Vector<Vector<String>> librarianViewAccountsForThisWeek() {
		Vector<Vector<String>> mainVector = new Vector<Vector<String>>();
		try {
			ResultSet resultSet = getAccountsForThisWeek.executeQuery();
			Vector<String> tempVector = new Vector<String>();
			while (resultSet.next()) {
				tempVector = new Vector<String>();
				tempVector.add(resultSet.getString("MembershipID"));
				tempVector.add(resultSet.getString("MemberName"));
				tempVector.add(resultSet.getString("AmountPaid"));
				tempVector.add(resultSet.getString("DateRecorded"));
				mainVector.add(tempVector);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Return the vector of vectors of Strings to the caller
		return mainVector;
	}

	public int updateBookToBorrower(String barcodeNumber, String returned) {
		int result = 0;

		try {
			updateBooKBorrowedDatabase.setString(1, returned);
			updateBooKBorrowedDatabase.setString(2, barcodeNumber);
			result = updateBooKBorrowedDatabase.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int addBookToBorrowerDetails(String memberID, String barcodeNumber) {
		int result = 0;

		try {
			saveBorroweNBook.setString(1, barcodeNumber);
			saveBorroweNBook.setString(2, memberID);

			result = saveBorroweNBook.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateStudentDetailsTable(String staffID, String firstName,
			String surname, String middlename, String dateOfBirth,
			String address) {
		int result = 0;
		try {
			psUpdateStaffDetailsTable.setString(6, staffID);
			psUpdateStaffDetailsTable.setString(1, firstName);
			psUpdateStaffDetailsTable.setString(2, surname);
			psUpdateStaffDetailsTable.setString(3, middlename);
			psUpdateStaffDetailsTable.setString(4, dateOfBirth);
			psUpdateStaffDetailsTable.setString(5, address);

			result = psUpdateStaffDetailsTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateBookDetailsTable(String bookBarcode, String title,
			String version, String author, String publisher, String noOfPages,
			String genre) {
		int result = 0;
		try {
			psUpdateBookDetailsTable.setString(7, bookBarcode);
			psUpdateBookDetailsTable.setString(1, title);
			psUpdateBookDetailsTable.setString(2, version);
			psUpdateBookDetailsTable.setString(3, author);
			psUpdateBookDetailsTable.setString(4, publisher);
			psUpdateBookDetailsTable.setString(5, noOfPages);
			psUpdateBookDetailsTable.setString(6, genre);

			result = psUpdateBookDetailsTable.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
