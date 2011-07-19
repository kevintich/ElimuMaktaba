package elimu_maktabaDatabaseConnection;

public class RecordsMemberRegisrationAccounts {
	String memberID, amountPaid, duration, dateID, tempMember;

	public RecordsMemberRegisrationAccounts(String memberID, String amountPaid, String duration,
			String dateID, String tempMember) {
		this.memberID = memberID;
		this.amountPaid = amountPaid;
		this.duration = duration;
		
		this.dateID = dateID;
		this.tempMember = tempMember;
		
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDateID() {
		return dateID;
	}

	public void setDateID(String dateID) {
		this.dateID = dateID;
	}

	public String getTempMember() {
		return tempMember;
	}

	public void setTempMember(String tempMember) {
		this.tempMember = tempMember;
	}

}
