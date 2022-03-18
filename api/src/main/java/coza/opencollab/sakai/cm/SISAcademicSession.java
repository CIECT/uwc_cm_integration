package coza.opencollab.sakai.cm;

import java.util.Date;

public class SISAcademicSession {
	private String sisCode;

	private String id;
	
	private String title;

	private String year;

    private Date startDate;

    private Date endDate;

    private String description;
    
    public SISAcademicSession(final String sisCode){
    	this.sisCode = sisCode;
    }
    
    public SISAcademicSession(final String sisCode, final String id, final String title, final String year, final Date startDate, final Date endDate, final String description) {
        this.sisCode = sisCode;
    	this.id = id;
        this.title = title;
    	this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
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

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    
    @Override
    public int hashCode() {
    	return startDate.hashCode() + endDate.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(obj == null) return false;
    	if(this == obj) return true;
    	if(obj instanceof SISAcademicSession){
    		SISAcademicSession other = (SISAcademicSession)obj;
    		return Utils.isEquals(startDate, other.startDate) &&
    				Utils.isEquals(endDate, other.endDate);
    	}else{
    		return false;
    	}
    }
    
    @Override
    public String toString() {    	
    	return "sisCode: " + sisCode + ", " + "id: " + id + ", " + "title: " + title + ", " + 
    			"year: " + year + ", " + "startDate: " + startDate + ", " + "endDate: " + endDate;
    }
}
