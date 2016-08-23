package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Partidas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-12-10T22:49:22")
@StaticMetamodel(Jugadores.class)
public class Jugadores_ { 

    public static volatile SingularAttribute<Jugadores, Integer> id;
    public static volatile SingularAttribute<Jugadores, String> nombre;
    public static volatile SingularAttribute<Jugadores, String> pass;
    public static volatile CollectionAttribute<Jugadores, Partidas> partidasCollection;

}