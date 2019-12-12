import com.lmk.springsecuritydemo.soap.model.GeneralSoapClient;
import com.lmk.springsecuritydemo.soap.service.IHelloService;
import com.lmk.springsecuritydemo.sys.entity.User;

public class TestSoap {
    public static void main(String[] args) throws Exception {
        String wsdl = "http://localhost:8080/spring_security_demo/soap/helloService?wsdl";
        String namespaceURI = "http://springsecuritydemo.lmk.com/";
        String localPart = "IHelloService";
        GeneralSoapClient soapClient = new GeneralSoapClient(wsdl, namespaceURI, localPart);
        soapClient.setRequireAuth(true);
        soapClient.setUsername("lichmama");
        soapClient.setPassword("123456");

        IHelloService service = soapClient.create(IHelloService.class);
        User user = service.getUserById(1);
        System.out.println(user.toString());
    }
}
