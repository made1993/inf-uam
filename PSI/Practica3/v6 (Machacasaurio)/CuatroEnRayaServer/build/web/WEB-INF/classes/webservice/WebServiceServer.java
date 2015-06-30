/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice;

import AI.C4Heuristica;
import AI.MiniMax;
import AI.NegaMax;
import com.sun.xml.ws.developer.servlet.HttpSessionScope;
import controller.JugadoresJpaController;
import controller.MovimientosJpaController;
import controller.PartidasJpaController;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import juego.Heuristica4Raya;
import juego.Juego4Raya;
import juego.Jugador;
import juego.Movimiento;
import juego.Tablero;
import model.Jugadores;
import model.Movimientos;
import model.Partidas;

/**
 *
 * @author e282868
 */

@HttpSessionScope
@Stateful
@WebService(serviceName = "WebServiceServer")
public class WebServiceServer {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    
    private Jugadores user;
    private Partidas partida;
    private Connect4 juego;
    private C4Heuristica c4h;
    private Tablero t;
    private MiniMax<Tablero, Jugador, Movimiento, Juego4Raya, Heuristica4Raya> m;
    private Juego4Raya jrt;
    private Heuristica4Raya hrt;
    int skynet = 0;
    

    /**
     * Web service operation
     * @param userName
     * @param password
     * @return 
     * @throws java.lang.Exception
     */
    @WebMethod(operationName = "createUser")
    public ReturnError createUser(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) throws Exception {
        Jugadores temp = new Jugadores();
        
        temp.setNombre(userName);
        temp.setPass(password);
        JugadoresJpaController jugadoresController = new JugadoresJpaController(utx, emf);
        
        
        if(jugadoresController.existeJugador(userName)){
            jugadoresController.create(temp);
            this.user = temp;
            return new ReturnError();
        } else{
            return new ReturnError(-1, "Fallo al crear usuario.");
        }
    }

    /**
     * Web service operation
     * @param userName
     * @param password
     * @return 
     */
    @WebMethod(operationName = "login")
    public ReturnError login(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        JugadoresJpaController jugadoresController = new JugadoresJpaController(utx, emf);
        
        if(jugadoresController.check_password(userName, password)){
            this.user = new Jugadores();
            this.user.setNombre(userName);
            this.user.setPass(password);
            int id = jugadoresController.getJugadorID(userName);
            
            if(id > 0){
                this.user.setId(id);
            } else{
                return new ReturnError(-1, "Fallo al logear.");
            }
            
            return new ReturnError();
        } else{
            return new ReturnError(-1, "Fallo al logear.");
        }
    
    }

    /**
     * Web service operation
     * @param humanFirstPlayer
     * @return 
     */
    @WebMethod(operationName = "createGame")
    public ReturnError createGame(@WebParam(name = "humanFirstPlayer") boolean humanFirstPlayer) {
        Partidas temp = new Partidas();
        
        temp.setJugador1(user);
        temp.setPrimero(humanFirstPlayer);
        temp.setTerminada(0); 
        PartidasJpaController partidasController = new PartidasJpaController(utx,emf);
        
        try {
            partidasController.create(temp);
            this.partida = new Partidas(temp.getId());
            
            this.juego = new Connect4();
            this.juego.reset();
            this.jrt = new Juego4Raya();
            this.hrt = new Heuristica4Raya();
            this.t = new Tablero();
            return new ReturnError();
        } catch (Exception ex) {
            return new ReturnError(-1, "Fallo al crear la partida.");
        }
    }

    /**
     * Web service operation
     * @param column
     * @return 
     */
    @WebMethod(operationName = "makeMove")
    public ReturnError makeMove(@WebParam(name = "column") int column) {
        Movimientos temp = new Movimientos();
        temp.setColumna(column);
        //System.out.println(this.partida.getId()+" "+this.user.getNombre());
        
        try{
        
        temp.setPartida(this.partida.getId());
        temp.setUsuario(this.user.getId());
        }catch(Exception ex){
            System.out.println("Fallo al inicializar movimiento.");
            return new ReturnError(-1, "Fallo al inicializar movimiento.");
        }
        MovimientosJpaController movimientosController = new MovimientosJpaController(utx,emf);
        
        try{
            if (this.juego.isplayable(column)) {
                int valid = 0;
                int col = -1;
                int fila = -1;
                Jugador j;
                
                if(skynet == 1){
                    j = Jugador.NEGRO;
                } else{
                    j = Jugador.BLANCO;
                }
                skynet = 0;
                
                List<Movimiento> posiblesMovimientos = jrt.posiblesMovimientos(t, j);
                for(Movimiento mov : posiblesMovimientos){
                    if(mov.getCol() == column){
                        t = jrt.estadoResultante(t, mov);
                        valid = 1;
                        fila = mov.getFila();
                        col = mov.getCol();
                        
                    }
                }
                if(valid != 1) return new ReturnError(-1, "Fallo al crear movimiento.");
                
                movimientosController.create(temp);
                this.juego.makemove(column);
                return new ReturnError(0, t.toString()+valid+fila+col);
            } else{
                return new ReturnError(-1, "Fallo al crear movimiento.");
            }   
        } catch (Exception ex){
            return new ReturnError(-1, "Fallo al crear movimiento.");
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "logout")
    @Oneway
    public void logout() {
        if(this.partida != null) this.partida = null;
        this.user = null;
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "show")
    public String show() {
        return this.juego.toString();
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "makeComputerMove")
    public ReturnError makeComputerMove() {
        ReturnError ret = new ReturnError(-1,"");
        
        while(ret.errorCode == -1){
            Movimiento mov;
            m = new MiniMax<Tablero, Jugador, Movimiento, Juego4Raya, Heuristica4Raya> (jrt, hrt);
            mov = m.mejorMovimiento(t, Jugador.NEGRO, 5);
            //t = jrt.estadoResultante(t, mov);
            
            skynet = 1;
            ret = makeMove(mov.getCol());
        }
        
        
        return ret;
    }

    /**
     * Web service operation
     * @return 
     */
    @WebMethod(operationName = "hasWon")
    public Boolean hasWon() {
        //TODO write your implementation code here:
        return this.juego.haswon(this.juego.color[0]) || this.juego.haswon(this.juego.color[1]);
    }

    
    
}
