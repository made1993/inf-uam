/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import con.Beutify;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import webservice.WebServiceServer;
import webservice.ReturnError;

/**
 *
 * @author roberto
 */
@WebServlet(name = "playAction", urlPatterns = {"/playAction"})
public class PlayAction extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CuatroEnRayaServer/WebServiceServer.wsdl")
    private webservice.WebServiceServer_Service service;
    private HttpSession session = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //save port in session
        /*HttpSession session = request.getSession();
        WebServiceServer port = null;*/
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            this.session = request.getSession();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link href=\"css/styles.css\" rel=\"stylesheet\" type=\"text/css\" >");
            out.println("<title>createGameAccion</title>");
            out.println("<script type=\"text/javascript\" src=\"js/jquery-2.1.1.min.js\"></script>");
            out.println("<script type=\"text/javascript\">");
            out.println("   function changeDiv(link) ");
            out.println("{ ");
            out.println("   var contentDiv = $('#board'); ");
            out.println("   contentDiv.load(\"viewGameAction?move=\"+link+\"\"); ");
            out.println("} ");
            out.println("   </script> ");
            out.println("<script type=\"text/javascript\">");
            out.println("var auto = setInterval(function()");
            out.println("{");
            //out.println("     $('#score').load('reload-window.jsp').fadeIn(\"slow\");");
            out.println("   $('#board').load('/connectFourClient/paintBoardAction').fadeIn(\"slow\");");
            out.println("}, 2000);"); // refresh every 2000 milliseconds
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            ReturnError returnError = null;
            if(this.session.getAttribute("user") == null){
                out.println("Not user logged");
            } else{
            Boolean firstPlayer = request.getParameter("firstPlayer").equals("true");
            returnError   = createGame(firstPlayer);
            if (returnError.getErrorCode() != 0)
                out.println("Error creating game");
            else
                out.println("Created game");
                if(!firstPlayer) makeComputerMove();
            }
            
            Beutify beutify = new Beutify();
            out.println("<div id=board>");
            out.println(beutify.toTable(show()));
            out.println("</div>");
            out.println("<div id=\"score\"></div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
    private webservice.WebServiceServer getPort() {
        webservice.WebServiceServer port;
        
        if ((session.getAttribute("port")==null)) {
            port = service.getWebServiceServerPort();
            Map requestContext = ((BindingProvider) port).getRequestContext();
            requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY,
                    Boolean.TRUE);
            session.setAttribute("port", port);
        }
        else
            port = (webservice.WebServiceServer) session.getAttribute("port");  
        return port;
    }

    private ReturnError createGame(boolean humanFirstPlayer) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.WebServiceServer port = getPort();
        return port.createGame(humanFirstPlayer);
    }
    
    private String show() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.WebServiceServer port = getPort();
        return port.show();
    }
    
    private ReturnError makeComputerMove() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.WebServiceServer port = getPort();
        return port.makeComputerMove();
    }
}
