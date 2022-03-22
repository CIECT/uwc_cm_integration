/**
 * DownloadModulePeriodsOutput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc_academia.www;

public class DownloadModulePeriodsOutput  implements java.io.Serializable {
    private java.math.BigInteger status;

    private java.lang.String[][] module_Period_List;

    private java.lang.String[] statusMessages;

    public DownloadModulePeriodsOutput() {
    }

    public DownloadModulePeriodsOutput(
           java.math.BigInteger status,
           java.lang.String[][] module_Period_List,
           java.lang.String[] statusMessages) {
           this.status = status;
           this.module_Period_List = module_Period_List;
           this.statusMessages = statusMessages;
    }


    /**
     * Gets the status value for this DownloadModulePeriodsOutput.
     * 
     * @return status
     */
    public java.math.BigInteger getStatus() {
        return status;
    }


    /**
     * Sets the status value for this DownloadModulePeriodsOutput.
     * 
     * @param status
     */
    public void setStatus(java.math.BigInteger status) {
        this.status = status;
    }


    /**
     * Gets the module_Period_List value for this DownloadModulePeriodsOutput.
     * 
     * @return module_Period_List
     */
    public java.lang.String[][] getModule_Period_List() {
        return module_Period_List;
    }


    /**
     * Sets the module_Period_List value for this DownloadModulePeriodsOutput.
     * 
     * @param module_Period_List
     */
    public void setModule_Period_List(java.lang.String[][] module_Period_List) {
        this.module_Period_List = module_Period_List;
    }


    /**
     * Gets the statusMessages value for this DownloadModulePeriodsOutput.
     * 
     * @return statusMessages
     */
    public java.lang.String[] getStatusMessages() {
        return statusMessages;
    }


    /**
     * Sets the statusMessages value for this DownloadModulePeriodsOutput.
     * 
     * @param statusMessages
     */
    public void setStatusMessages(java.lang.String[] statusMessages) {
        this.statusMessages = statusMessages;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DownloadModulePeriodsOutput)) return false;
        DownloadModulePeriodsOutput other = (DownloadModulePeriodsOutput) obj;
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
            ((this.module_Period_List==null && other.getModule_Period_List()==null) || 
             (this.module_Period_List!=null &&
              java.util.Arrays.equals(this.module_Period_List, other.getModule_Period_List()))) &&
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
        if (getModule_Period_List() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getModule_Period_List());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getModule_Period_List(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
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
        new org.apache.axis.description.TypeDesc(DownloadModulePeriodsOutput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "DownloadModulePeriodsOutput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("module_Period_List");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "Module_Period_List"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "General_ListColumns"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "row"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusMessages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "StatusMessages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.uwc-academia.ac.za/", "StatusMessage"));
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
