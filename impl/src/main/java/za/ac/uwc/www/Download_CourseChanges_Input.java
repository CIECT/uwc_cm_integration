/**
 * Download_CourseChanges_Input.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_CourseChanges_Input  implements java.io.Serializable {
    private java.math.BigInteger year;

    private java.lang.String course_Changes;

    public Download_CourseChanges_Input() {
    }

    public Download_CourseChanges_Input(
           java.math.BigInteger year,
           java.lang.String course_Changes) {
           this.year = year;
           this.course_Changes = course_Changes;
    }


    /**
     * Gets the year value for this Download_CourseChanges_Input.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_CourseChanges_Input.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }


    /**
     * Gets the course_Changes value for this Download_CourseChanges_Input.
     * 
     * @return course_Changes
     */
    public java.lang.String getCourse_Changes() {
        return course_Changes;
    }


    /**
     * Sets the course_Changes value for this Download_CourseChanges_Input.
     * 
     * @param course_Changes
     */
    public void setCourse_Changes(java.lang.String course_Changes) {
        this.course_Changes = course_Changes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_CourseChanges_Input)) return false;
        Download_CourseChanges_Input other = (Download_CourseChanges_Input) obj;
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
            ((this.course_Changes==null && other.getCourse_Changes()==null) || 
             (this.course_Changes!=null &&
              this.course_Changes.equals(other.getCourse_Changes())));
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
        if (getCourse_Changes() != null) {
            _hashCode += getCourse_Changes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_CourseChanges_Input.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_CourseChanges_Input"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("course_Changes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Course_Changes"));
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
