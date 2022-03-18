package coza.opencollab.sakai.cm;

import java.util.HashSet;
import java.util.Set;

public class SISEnrollmentSet {
	private String sisCode;
	private String id;
	private String title;
	private String description;
	private String category;
	private String defaultEnrollmentCredits;
	private String courseOfferingEid;
	private Set<String> officialInstructors;
	
	public SISEnrollmentSet(String sisCode){
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDefaultEnrollmentCredits() {
		return defaultEnrollmentCredits;
	}

	public void setDefaultEnrollmentCredits(String defaultEnrollmentCredits) {
		this.defaultEnrollmentCredits = defaultEnrollmentCredits;
	}

	public String getCourseOfferingEid() {
		return courseOfferingEid;
	}

	public void setCourseOfferingEid(String courseOfferingEid) {
		this.courseOfferingEid = courseOfferingEid;
	}

	public Set<String> getOfficialInstructors() {
		return officialInstructors;
	}

	public void setOfficialInstructors(Set<String> officialInstructors) {
		this.officialInstructors = officialInstructors;
	}
	
	public void addOfficialInstructor(String officialInstructor) {
		if(officialInstructors == null){
			officialInstructors = new HashSet<String>();
		}
		officialInstructors.add(officialInstructor);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISEnrollmentSet){
    		SISEnrollmentSet other = (SISEnrollmentSet)obj;
    		return id.equals(other.id);
    	}else{
    		return false;
    	}
	}
}
