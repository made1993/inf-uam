
package webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para createGame complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="createGame">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="humanFirstPlayer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createGame", propOrder = {
    "humanFirstPlayer"
})
public class CreateGame {

    protected boolean humanFirstPlayer;

    /**
     * Obtiene el valor de la propiedad humanFirstPlayer.
     * 
     */
    public boolean isHumanFirstPlayer() {
        return humanFirstPlayer;
    }

    /**
     * Define el valor de la propiedad humanFirstPlayer.
     * 
     */
    public void setHumanFirstPlayer(boolean value) {
        this.humanFirstPlayer = value;
    }

}
