package coza.opencollab.sakai.cm;

public class SISCanonicalCourse {
	private String sisCode;
	private String id;
	private String title;
	private String description;
	
	public SISCanonicalCourse(String sisCode){
		this.sisCode = sisCode;
	}
	
	public String getSISCode() {
		return sisCode;
	}

	public void setSISCode(String sisCode) {
		this.sisCode = sisCode;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISCanonicalCourse){
    		SISCanonicalCourse other = (SISCanonicalCourse)obj;
    		return id.equals(other.id);
    	}else{
    		return false;
    	}
	}
}
