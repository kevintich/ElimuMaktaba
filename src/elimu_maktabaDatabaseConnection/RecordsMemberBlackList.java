package elimu_maktabaDatabaseConnection;

public class RecordsMemberBlackList {
	String memID,offencePaid;

	public RecordsMemberBlackList(String memID,String offencePaid ){
		this.memID = memID;
		this.offencePaid = offencePaid;
	}
	public String getMemID() {
		return memID;
	}

	public void setMemID(String memID) {
		this.memID = memID;
	}

	public String getOffencePaid() {
		return offencePaid;
	}

	public void setOffencePaid(String offencePaid) {
		this.offencePaid = offencePaid;
	}

}
