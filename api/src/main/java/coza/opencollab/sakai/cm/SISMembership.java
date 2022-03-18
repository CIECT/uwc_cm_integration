package coza.opencollab.sakai.cm;

public class SISMembership {
	private String sisCode;
	private String userId;
	private String role;
	private String status;
	
	public SISMembership(String sisCode){
		this.sisCode = sisCode;
	}
	
	public String getSISCode() {
		return sisCode;
	}

	public void setSISCode(String sisCode) {
		this.sisCode = sisCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		return sisCode.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISMembership){
    		SISMembership other = (SISMembership)obj;
    		return sisCode.equals(other.sisCode);
    	}else{
    		return false;
    	}
	}
}
