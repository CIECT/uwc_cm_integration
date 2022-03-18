package coza.opencollab.sakai.cm;

public class SISSection {
	private String sisCode;
	private String id;
	private String title;
	private String description;
	private String categoryCode;
	private String categoryDescription;
	private String parentSectionId;
	private String courseOfferingId;
	private String enrollmentSetId;
	
	public SISSection(String sisCode){
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

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getParentSectionId() {
		return parentSectionId;
	}

	public void setParentSectionId(String parentSectionId) {
		this.parentSectionId = parentSectionId;
	}

	public String getCourseOfferingId() {
		return courseOfferingId;
	}

	public void setCourseOfferingId(String courseOfferingId) {
		this.courseOfferingId = courseOfferingId;
	}

	public String getEnrollmentSetId() {
		return enrollmentSetId;
	}

	public void setEnrollmentSetId(String enrollmentSetId) {
		this.enrollmentSetId = enrollmentSetId;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISSection){
    		SISSection other = (SISSection)obj;
    		return id.equals(other.id);
    	}else{
    		return false;
    	}
	}
}
