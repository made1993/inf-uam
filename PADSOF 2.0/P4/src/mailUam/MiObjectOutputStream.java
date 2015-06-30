/**
 * Javier Abell�n, 20 Marzo 2006
 * Redefinicion de la clase ObjectOutputStream para que no escriba una cabecera
 * al principio del Stream.
 */
package mailUam;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Redefinici�n de la clase ObjectOuputStream para que no escriba una cabecera
 * al inicio del Stream.
 * @author Javier Abell�n.
 *
 */
public class MiObjectOutputStream extends ObjectOutputStream
{
    /**
     *  Constructor que recibe OutputStream
     * @param out out 
     * @throws IOException excepcion
     */
    public MiObjectOutputStream(OutputStream out) throws IOException
    {
        super(out);
    }

    /**
     *  Constructor sin par�metros 
     * @throws IOException ecpecion
     * @throws SecurityException excepcion
     */
    protected MiObjectOutputStream() throws IOException, SecurityException
    {
        super();
    }

    /**
     *  Redefinici�n del m�todo de escribir la cabecera para que no haga nada. 
     */
    protected void writeStreamHeader() throws IOException
    {
    }

}
