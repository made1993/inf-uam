/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import controller.JugadoresJpaController;
import controller.MovimientosJpaController;
import controller.PartidasJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import model.*;


/**
 *
 * @author e282868
 */
@WebServlet(name = "CuatroEnRaya", urlPatterns = {"/CuatroEnRaya"})








public class CuatroEnRaya extends HttpServlet {
    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CuatroEnRaya</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CuatroEnRaya at " + request.getContextPath() + "</h1>");
            try{
                JugadoresJpaController jugadoresController = new JugadoresJpaController(utx, emf);
                List<Jugadores> findJugadoresEntities = jugadoresController.findJugadoresEntities();
                out.println("Usuario");
                for(Jugadores j : findJugadoresEntities){
                    out.println(j.getNombre()+" "+j.getPass());
                }
                
                PartidasJpaController partidasController = new PartidasJpaController(utx,emf);
                List<Partidas> findPartidasEntities = partidasController.findPartidasEntities();
                /*out.println("Movimientos");
                for(Partidas p:findPartidasEntities){
                    if(p.getId()==2)
                        for(Movimientos m:p.getMovimientosCollection()){
                             out.println(m.getMovimientosPK().toString());
                        }
                    
                }*/
                
                out.println("Jugadores de la partida 2");
                for (Partidas p : findPartidasEntities) {
                    for (Jugadores j : findJugadoresEntities) {
                        if (p.getId() == 2 && p.getJugador1().getId() == j.getId()){
                            out.println(j.getNombre());
                        }

                    }
                }
                out.println("Usuario y contraseña");
                if(jugadoresController.check_password("Dani", "hola"))
                    out.println("usuario registrado");
                else{
                    out.println ("usuario no registrado");
                }
                List<Partidas> l = partidasController.getPartidasTerminadas();
                for(Partidas p : l){
                    out.println(p.toString());
                }
                

                MovimientosJpaController movimientosController = new MovimientosJpaController(utx, emf);
                out.println("Último movimiento de la partida 2");
                Movimientos m = movimientosController.getMovimientosLast();
                out.println(m);
                
            }
            catch(Exception e){
                out.println("Exception");
                
            } finally{
                emf.close();
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

}
