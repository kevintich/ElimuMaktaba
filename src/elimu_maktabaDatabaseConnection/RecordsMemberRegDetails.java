package elimu_maktabaDatabaseConnection;

public class RecordsMemberRegDetails {
	String memberID,firstName,surName,middleName,gender,mobileNo,email,NatID,dateOfBirth,dateReg;

	public RecordsMemberRegDetails(String memberID, String firstName, String surName, String middleName,String gender,String mobileNo,String email,String NatID,String dateOfBirth,String dateReg){
		this.memberID = memberID;
		this.firstName = firstName;
		this.surName = surName;
		this.middleName = middleName;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.email= email;
		this.NatID  =  NatID;
		this.dateOfBirth = dateOfBirth;
		this.dateReg = dateReg;
		
	}
	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNatID() {
		return NatID;
	}

	public void setNatID(String natID) {
		NatID = natID;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateReg() {
		return dateReg;
	}

	public void setDateReg(String dateReg) {
		this.dateReg = dateReg;
	}
}
