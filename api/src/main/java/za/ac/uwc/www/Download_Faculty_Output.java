/**
 * Download_Faculty_Output.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class Download_Faculty_Output  implements java.io.Serializable {
    private java.math.BigInteger status;

    private java.lang.String faculty;

    private java.lang.String[][] faculty_List;

    private java.lang.String message;

    private java.lang.String[] statusMessages;

    public Download_Faculty_Output() {
    }

    public Download_Faculty_Output(
           java.math.BigInteger status,
           java.lang.String faculty,
           java.lang.String[][] faculty_List,
           java.lang.String message,
           java.lang.String[] statusMessages) {
           this.status = status;
           this.faculty = faculty;
           this.faculty_List = faculty_List;
           this.message = message;
           this.statusMessages = statusMessages;
    }


    /**
     * Gets the status value for this Download_Faculty_Output.
     * 
     * @return status
     */
    public java.math.BigInteger getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Download_Faculty_Output.
     * 
     * @param status
     */
    public void setStatus(java.math.BigInteger status) {
        this.status = status;
    }


    /**
     * Gets the faculty value for this Download_Faculty_Output.
     * 
     * @return faculty
     */
    public java.lang.String getFaculty() {
        return faculty;
    }


    /**
     * Sets the faculty value for this Download_Faculty_Output.
     * 
     * @param faculty
     */
    public void setFaculty(java.lang.String faculty) {
        this.faculty = faculty;
    }


    /**
     * Gets the faculty_List value for this Download_Faculty_Output.
     * 
     * @return faculty_List
     */
    public java.lang.String[][] getFaculty_List() {
        return faculty_List;
    }


    /**
     * Sets the faculty_List value for this Download_Faculty_Output.
     * 
     * @param faculty_List
     */
    public void setFaculty_List(java.lang.String[][] faculty_List) {
        this.faculty_List = faculty_List;
    }


    /**
     * Gets the message value for this Download_Faculty_Output.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Download_Faculty_Output.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the statusMessages value for this Download_Faculty_Output.
     * 
     * @return statusMessages
     */
    public java.lang.String[] getStatusMessages() {
        return statusMessages;
    }


    /**
     * Sets the statusMessages value for this Download_Faculty_Output.
     * 
     * @param statusMessages
     */
    public void setStatusMessages(java.lang.String[] statusMessages) {
        this.statusMessages = statusMessages;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Download_Faculty_Output)) return false;
        Download_Faculty_Output other = (Download_Faculty_Output) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.faculty==null && other.getFaculty()==null) || 
             (this.faculty!=null &&
              this.faculty.equals(other.getFaculty()))) &&
            ((this.faculty_List==null && other.getFaculty_List()==null) || 
             (this.faculty_List!=null &&
              java.util.Arrays.equals(this.faculty_List, other.getFaculty_List()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.statusMessages==null && other.getStatusMessages()==null) || 
             (this.statusMessages!=null &&
              java.util.Arrays.equals(this.statusMessages, other.getStatusMessages())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getFaculty() != null) {
            _hashCode += getFaculty().hashCode();
        }
        if (getFaculty_List() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFaculty_List());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFaculty_List(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getStatusMessages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getStatusMessages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getStatusMessages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Download_Faculty_Output.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Download_Faculty_Output"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faculty");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Faculty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("faculty_List");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Faculty_List"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "General_ListColumns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "row"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "Message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "StatusMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "StatusMessage"));
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
