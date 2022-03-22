/**
 * Download_CalendarGroup_Input.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_CalendarGroup_Input  implements java.io.Serializable {
    private java.math.BigInteger year;

    private java.lang.String calendar_Group;

    public Download_CalendarGroup_Input() {
    }

    public Download_CalendarGroup_Input(
           java.math.BigInteger year,
           java.lang.String calendar_Group) {
           this.year = year;
           this.calendar_Group = calendar_Group;
    }


    /**
     * Gets the year value for this Download_CalendarGroup_Input.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_CalendarGroup_Input.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }


    /**
     * Gets the calendar_Group value for this Download_CalendarGroup_Input.
     * 
     * @return calendar_Group
     */
    public java.lang.String getCalendar_Group() {
        return calendar_Group;
    }


    /**
     * Sets the calendar_Group value for this Download_CalendarGroup_Input.
     * 
     * @param calendar_Group
     */
    public void setCalendar_Group(java.lang.String calendar_Group) {
        this.calendar_Group = calendar_Group;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_CalendarGroup_Input)) return false;
        Download_CalendarGroup_Input other = (Download_CalendarGroup_Input) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.year==null && other.getYear()==null) || 
             (this.year!=null &&
              this.year.equals(other.getYear()))) &&
            ((this.calendar_Group==null && other.getCalendar_Group()==null) || 
             (this.calendar_Group!=null &&
              this.calendar_Group.equals(other.getCalendar_Group())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getYear() != null) {
            _hashCode += getYear().hashCode();
        }
        if (getCalendar_Group() != null) {
            _hashCode += getCalendar_Group().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_CalendarGroup_Input.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_CalendarGroup_Input"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("calendar_Group");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Calendar_Group"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
