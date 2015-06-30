/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author skynet
 */
@Entity
@Table(name = "partidas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partidas.findAll", query = "SELECT p FROM Partidas p"),
    @NamedQuery(name = "Partidas.findById", query = "SELECT p FROM Partidas p WHERE p.id = :id"),
    @NamedQuery(name = "Partidas.findByTerminada", query = "SELECT p FROM Partidas p WHERE p.terminada = :terminada"),
    @NamedQuery(name = "Partidas.findByPrimero", query = "SELECT p FROM Partidas p WHERE p.primero = :primero")})
public class Partidas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "terminada")
    private Integer terminada;
    @Column(name = "primero")
    private Boolean primero;
    @JoinColumn(name = "jugador1", referencedColumnName = "id")
    @ManyToOne
    private Jugadores jugador1;

    public Partidas() {
    }

    public Partidas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTerminada() {
        return terminada;
    }

    public void setTerminada(Integer terminada) {
        this.terminada = terminada;
    }

    public Boolean getPrimero() {
        return primero;
    }

    public void setPrimero(Boolean primero) {
        this.primero = primero;
    }

    public Jugadores getJugador1() {
        return jugador1;
    }

    public void setJugador1(Jugadores jugador1) {
        this.jugador1 = jugador1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partidas)) {
            return false;
        }
        Partidas other = (Partidas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Partidas[ id=" + id + " ]";
    }
    
}
