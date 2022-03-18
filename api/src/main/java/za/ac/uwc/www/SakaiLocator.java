/**
 * SakaiLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package za.ac.uwc.www;

public class SakaiLocator extends org.apache.axis.client.Service implements za.ac.uwc.www.Sakai {

/**
 * applctns system sakai web services.
 */

    public SakaiLocator() {
    }


    public SakaiLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SakaiLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for sakaiSoap
    private java.lang.String sakaiSoap_address = "http://196.11.235.60/applctns_sakai_WS/sakai.asmx";

    public java.lang.String getsakaiSoapAddress() {
        return sakaiSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String sakaiSoapWSDDServiceName = "sakaiSoap";

    public java.lang.String getsakaiSoapWSDDServiceName() {
        return sakaiSoapWSDDServiceName;
    }

    public void setsakaiSoapWSDDServiceName(java.lang.String name) {
        sakaiSoapWSDDServiceName = name;
    }

    public za.ac.uwc.www.SakaiSoap getsakaiSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(sakaiSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getsakaiSoap(endpoint);
    }

    public za.ac.uwc.www.SakaiSoap getsakaiSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            za.ac.uwc.www.SakaiSoapStub _stub = new za.ac.uwc.www.SakaiSoapStub(portAddress, this);
            _stub.setPortName(getsakaiSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setsakaiSoapEndpointAddress(java.lang.String address) {
        sakaiSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (za.ac.uwc.www.SakaiSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                za.ac.uwc.www.SakaiSoapStub _stub = new za.ac.uwc.www.SakaiSoapStub(new java.net.URL(sakaiSoap_address), this);
                _stub.setPortName(getsakaiSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("sakaiSoap".equals(inputPortName)) {
            return getsakaiSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.uwc.ac.za/", "sakai");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.uwc.ac.za/", "sakaiSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("sakaiSoap".equals(portName)) {
            setsakaiSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
