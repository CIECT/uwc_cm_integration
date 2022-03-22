/**
 * Download_Students_Input.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public class Download_Students_Input  implements java.io.Serializable {
    private java.math.BigInteger year;

    private java.lang.String intakeCode;

    private java.lang.String moduleCode;

    private java.lang.String periodCode;

    public Download_Students_Input() {
    }

    public Download_Students_Input(
           java.math.BigInteger year,
           java.lang.String intakeCode,
           java.lang.String moduleCode,
           java.lang.String periodCode) {
           this.year = year;
           this.intakeCode = intakeCode;
           this.moduleCode = moduleCode;
           this.periodCode = periodCode;
    }


    /**
     * Gets the year value for this Download_Students_Input.
     * 
     * @return year
     */
    public java.math.BigInteger getYear() {
        return year;
    }


    /**
     * Sets the year value for this Download_Students_Input.
     * 
     * @param year
     */
    public void setYear(java.math.BigInteger year) {
        this.year = year;
    }


    /**
     * Gets the intakeCode value for this Download_Students_Input.
     * 
     * @return intakeCode
     */
    public java.lang.String getIntakeCode() {
        return intakeCode;
    }


    /**
     * Sets the intakeCode value for this Download_Students_Input.
     * 
     * @param intakeCode
     */
    public void setIntakeCode(java.lang.String intakeCode) {
        this.intakeCode = intakeCode;
    }


    /**
     * Gets the moduleCode value for this Download_Students_Input.
     * 
     * @return moduleCode
     */
    public java.lang.String getModuleCode() {
        return moduleCode;
    }


    /**
     * Sets the moduleCode value for this Download_Students_Input.
     * 
     * @param moduleCode
     */
    public void setModuleCode(java.lang.String moduleCode) {
        this.moduleCode = moduleCode;
    }


    /**
     * Gets the periodCode value for this Download_Students_Input.
     * 
     * @return periodCode
     */
    public java.lang.String getPeriodCode() {
        return periodCode;
    }


    /**
     * Sets the periodCode value for this Download_Students_Input.
     * 
     * @param periodCode
     */
    public void setPeriodCode(java.lang.String periodCode) {
        this.periodCode = periodCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Students_Input)) return false;
        Download_Students_Input other = (Download_Students_Input) obj;
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
            ((this.intakeCode==null && other.getIntakeCode()==null) || 
             (this.intakeCode!=null &&
              this.intakeCode.equals(other.getIntakeCode()))) &&
            ((this.moduleCode==null && other.getModuleCode()==null) || 
             (this.moduleCode!=null &&
              this.moduleCode.equals(other.getModuleCode()))) &&
            ((this.periodCode==null && other.getPeriodCode()==null) || 
             (this.periodCode!=null &&
              this.periodCode.equals(other.getPeriodCode())));
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
        if (getIntakeCode() != null) {
            _hashCode += getIntakeCode().hashCode();
        }
        if (getModuleCode() != null) {
            _hashCode += getModuleCode().hashCode();
        }
        if (getPeriodCode() != null) {
            _hashCode += getPeriodCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_Students_Input.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Download_Students_Input"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("year");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Year"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intakeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "IntakeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("moduleCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "ModuleCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "PeriodCode"));
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
