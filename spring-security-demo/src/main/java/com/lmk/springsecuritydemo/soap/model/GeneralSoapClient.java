package com.lmk.springsecuritydemo.soap.model;


import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;

public class GeneralSoapClient {
    private String WSDL;

    private String namespaceURI;

    private String localPart;

    public GeneralSoapClient() {

    }

    public GeneralSoapClient(String WSDL, String namespaceURI, String localPart) {
        this.WSDL = WSDL;
        this.namespaceURI = namespaceURI;
        this.localPart = localPart;
    }

    public String getWSDL() {
        return WSDL;
    }

    public void setWSDL(String WSDL) {
        this.WSDL = WSDL;
    }

    public String getNamespaceURI() {
        return namespaceURI;
    }

    public void setNamespaceURI(String namespaceURI) {
        this.namespaceURI = namespaceURI;
    }

    public String getLocalPart() {
        return localPart;
    }

    public void setLocalPart(String localPart) {
        this.localPart = localPart;
    }

    private boolean requireAuth = false;

    private String username;

    private String password;

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 创建Soap Service实例
     * @param serviceInterface
     * @return
     * @throws Exception
     */
    public <T> T create(Class<T> serviceInterface) throws Exception {
        URL url = new URL(WSDL);
        QName qname = new QName(namespaceURI, localPart);
        Service service = Service.create(url, qname);
        T port = service.getPort(serviceInterface);
        if (requireAuth) {
            BindingProvider prov = (BindingProvider) port;
            prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, username);
            prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, password);
        }
        return port;
    }
}
