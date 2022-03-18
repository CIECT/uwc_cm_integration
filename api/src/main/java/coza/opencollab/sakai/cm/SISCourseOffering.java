package coza.opencollab.sakai.cm;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SISCourseOffering {
	private String sisCode;
	private String id;
	private String title;
	private String description;
	private String status;
	private String academicSessionId;
	private String canonicalCourseId;
	private String modulePeriodCode;
	private String modulePeriodCodeDescr;
	private Date startDate;
	private Date endDate;
	private Set<String> courseSetIds = new HashSet<String>();
	
	public SISCourseOffering(String sisCode){
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAcademicSessionId() {
		return academicSessionId;
	}

	public void setAcademicSessionId(String academicSessionId) {
		this.academicSessionId = academicSessionId;
	}

	public String getCanonicalCourseId() {
		return canonicalCourseId;
	}

	public void setCanonicalCourseId(String canonicalCourseId) {
		this.canonicalCourseId = canonicalCourseId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Set<String> getCourseSetIds() {
		return courseSetIds;
	}

	public void addCourseSetIds(String courseSetId) {
		this.courseSetIds.add(courseSetId);
	}
	
	public String getModulePeriodCode() {
		return modulePeriodCode;
	}

	public void setModulePeriodCode(String modulePeriodCode) {
		this.modulePeriodCode = modulePeriodCode;
	}

	public String getModulePeriodCodeDescr() {
		return modulePeriodCodeDescr;
	}

	public void setModulePeriodCodeDescr(String modulePeriodCodeDescr) {
		this.modulePeriodCodeDescr = modulePeriodCodeDescr;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISCourseOffering){
    		SISCourseOffering other = (SISCourseOffering)obj;
    		return id.equals(other.id);
    	}else{
    		return false;
    	}
	}
    
    @Override
    public String toString() {    	
    	return "sisCode: " + sisCode + ", " + "id: " + id + ", " + "title: " + title + ", " + 
    			"status: " + status + ", " + "academicSessionId: " + academicSessionId + ", " + "canonicalCourseId: " + canonicalCourseId + ", " + 
    			"startDate: " + startDate + ", " + "endDate: " + endDate + ", " + "modulePeriodCode: " + modulePeriodCode + ", " + "modulePeriodCodeDescr: " + modulePeriodCodeDescr;
    }
}
