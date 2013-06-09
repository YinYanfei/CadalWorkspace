/**
 * TranslationSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.cadal.webservice.translation;

public interface TranslationSoap extends java.rmi.Remote {
    public java.lang.String Sentence (java.lang.String lang, java.lang.String text) throws java.rmi.RemoteException;
    public java.lang.String Paragraph (java.lang.String lang, java.lang.String text) throws java.rmi.RemoteException;
    public java.lang.String Text (java.lang.String lang, java.lang.String text) throws java.rmi.RemoteException;
}
