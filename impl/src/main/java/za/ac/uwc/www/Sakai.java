/**
 * Sakai.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public interface Sakai extends javax.xml.rpc.Service {

/**
 * applctns system sakai web services.
 */
    public java.lang.String getsakaiSoapAddress();

    public za.ac.uwc.www.SakaiSoap getsakaiSoap() throws javax.xml.rpc.ServiceException;

    public za.ac.uwc.www.SakaiSoap getsakaiSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
