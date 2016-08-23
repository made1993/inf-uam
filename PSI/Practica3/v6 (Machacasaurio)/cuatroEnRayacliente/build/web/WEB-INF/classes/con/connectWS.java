/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package con;

import com.sun.xml.ws.developer.servlet.HttpSessionScope;
import java.util.Map;
import javax.ejb.Stateful;
import javax.xml.ws.BindingProvider;
import webservice.WebServiceServer;
import webservice.WebServiceServer_Service;

/**
 *
 * @author roberto
 */
@HttpSessionScope
//@Stateful
public class connectWS {

    public webservice.WebServiceServer port;

    public WebServiceServer getPort() {
        return port;
    }

    public void setPort(WebServiceServer port) {
        this.port = port;
    }


    public void initSession()  {
        webservice.WebServiceServer_Service service = new webservice.WebServiceServer_Service();
        port = service.getWebServiceServerPort();
        Map requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY,
                Boolean.TRUE);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


