package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Jugadores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-10T22:49:22")
@StaticMetamodel(Partidas.class)
public class Partidas_ { 

    public static volatile SingularAttribute<Partidas, Integer> id;
    public static volatile SingularAttribute<Partidas, Boolean> primero;
    public static volatile SingularAttribute<Partidas, Integer> terminada;
    public static volatile SingularAttribute<Partidas, Jugadores> jugador1;

}