/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import con.Beutify;
import con.connectWS;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import webservice.ReturnError;
import webservice.WebServiceServer_Service;


/**
 *
 * @author roberto
 */
@WebServlet(name = "viewGameAction", urlPatterns = {"/viewGameAction"})
public class ViewGameAction extends HttpServlet {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CuatroEnRayaServer/WebServiceServer.wsdl")
    private webservice.WebServiceServer_Service service;
    private HttpSession session = null;

    //@EJB
    connectWS connect4;

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
        //HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        /*connectWS port = null;
        port = (connectWS) session.getAttribute("port");*/
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            this.session = request.getSession();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet visualizeGame</title>");
            //out.println("<script type=\"text/javascript\">");
            //out.println("var auto = setInterval(function()");
            //out.println("{");
            //out.println("   $('#score').load('/connectFourClient/reload-window.jsp').fadeIn(\"slow\");");
            //out.println("}, 10000);"); // refresh every 5000 milliseconds
            //out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>View Game</h1>");

            String move = request.getParameter("move");
            Beutify beutify = new Beutify();
            ReturnError err;
            
                if (!hasWon()){
                    err = makeMove(Integer.parseInt(move));
                }
                if (!hasWon()){
                    err = makeComputerMove();
                    out.println(beutify.toTable(show()));
                } else{
                    out.println(beutify.toTable(show()));
                }

            
            out.println("</body>");
            out.println("</html>");
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
    
    private ReturnError makeMove(int column) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.WebServiceServer port = getPort();
        return port.makeMove(column);
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

    private Boolean hasWon() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        webservice.WebServiceServer port = getPort();
        return port.hasWon();
    }

}