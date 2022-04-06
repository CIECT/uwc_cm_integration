package coza.opencollab.sakai.cm.uwc;

import java.util.Date;

import coza.opencollab.sakai.cm.SISAcademicSession;
import coza.opencollab.sakai.cm.Utils;

public class AcademiaModule {
		
	private String moduleCode;
	
	private String modulePeriodCode;

	private String modulePeriodCodeDescr;

	private Date startDate;

	private Date endDate;

	private String description;

	private String academicSessionCode;

	private SISAcademicSession academicSession;

	public AcademiaModule() {
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAcademicSessionCode() {
		return academicSessionCode;
	}

	public void setAcademicSessionCode(String academicSessionCode) {
		this.academicSessionCode = academicSessionCode;
	}

	public SISAcademicSession getAcademicSession() {
		return academicSession;
	}

	public void setAcademicSession(SISAcademicSession academicSession) {
		this.academicSession = academicSession;
	}

	/**
	 * Course Management Helper method - CanonicalCourse eid example: ESS 112
	 */
	public String getCanonicalCourseReference() {
		return moduleCode;
	}

	/**
	 * Course Management Helper method - CourseOffering eid example: ESS Y 112 2012
	 */
	public String getCourseOfferingReference() {
		return getCanonicalCourseReference() + " " + academicSession.getTitle() + " " + modulePeriodCodeDescr + " " + academicSession.getYear();
	}

	/**
	 * Course Management Helper method - EnrollmentSet eid example: ESS112 2012 ES
	 */
	public String getEnrollmentSetReference() {
		return getCourseOfferingReference() + " ES";
	}

	public String getYear() {
		return academicSession.getYear();
	}

	@Override
	public int hashCode() {
		return moduleCode.hashCode() + modulePeriodCode.hashCode() + academicSession.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (obj instanceof AcademiaModule) {
			AcademiaModule other = (AcademiaModule) obj;
			return Utils.isEquals(moduleCode, other.moduleCode)
					&& Utils.isEquals(modulePeriodCode, other.modulePeriodCode)
					&& Utils.isEquals(academicSession, other.academicSession);
		} else {
			return false;
		}
	}
}
