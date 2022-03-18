package coza.opencollab.sakai.cm;

public class SISEnrollment {
	private String sisCode;
	private String studentId;
	private String enrollmentSetId;
	private String status;
	private String credits;
	private String gradingScheme;
	
	public SISEnrollment(String sisCode){
		this.sisCode = sisCode;
	}
	
	public String getSISCode() {
		return sisCode;
	}

	public void setSISCode(String sisCode) {
		this.sisCode = sisCode;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getEnrollmentSetId() {
		return enrollmentSetId;
	}

	public void setEnrollmentSetId(String enrollmentSetId) {
		this.enrollmentSetId = enrollmentSetId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getGradingScheme() {
		return gradingScheme;
	}

	public void setGradingScheme(String gradingScheme) {
		this.gradingScheme = gradingScheme;
	}
	
	@Override
	public int hashCode() {
		return sisCode.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISEnrollment){
    		SISEnrollment other = (SISEnrollment)obj;
    		return sisCode.equals(other.sisCode);
    	}else{
    		return false;
    	}
	}
}
