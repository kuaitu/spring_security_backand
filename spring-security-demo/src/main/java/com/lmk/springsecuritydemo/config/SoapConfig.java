package com.lmk.springsecuritydemo.config;

import com.lmk.springsecuritydemo.soap.service.IHelloService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class SoapConfig {

    @Autowired
    private IHelloService helloService;

    @Autowired
    @Qualifier(Bus.DEFAULT_BUS_ID)
    private SpringBus bus;

    /**教程https://www.cnblogs.com/lichmama/p/8728262.html
     * http://localhost:8080/spring_security_demo/soap/helloService?wsdl
     * @return
     */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, helloService);
        endpoint.publish("helloService");
        return endpoint;
    }
}
