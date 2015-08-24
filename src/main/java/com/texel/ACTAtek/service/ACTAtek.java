/**
 * ACTAtek.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.texel.ACTAtek.service;

public interface ACTAtek extends javax.xml.rpc.Service {

/**
 * WebService API for ACTAtek
 */
    public java.lang.String getACTAtekAddress();

    public com.texel.ACTAtek.service.ACTAtekPortType getACTAtek() throws javax.xml.rpc.ServiceException;

    public com.texel.ACTAtek.service.ACTAtekPortType getACTAtek(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
