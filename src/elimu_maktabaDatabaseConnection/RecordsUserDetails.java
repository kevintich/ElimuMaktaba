package elimu_maktabaDatabaseConnection;

public class RecordsUserDetails {
	String staffID,fistName,surname,middlename,dateOB,address,NatID,email,dateReg;

	public RecordsUserDetails(String staffID,String fistName,String surname,String middlename,String dateOB,String address,String NatID,String email,String dateReg){
		this.staffID= staffID;
		this.fistName= fistName;
		this.surname = surname;
		this.middlename = middlename;
		this.dateOB = dateOB;
		this.address = address;
		this.NatID  = NatID;
		this.email = email;
		this.dateReg = dateReg;
	}
	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getDateOB() {
		return dateOB;
	}

	public void setDateOB(String dateOB) {
		this.dateOB = dateOB;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNatID() {
		return NatID;
	}

	public void setNatID(String natID) {
		NatID = natID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateReg() {
		return dateReg;
	}

	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
}
