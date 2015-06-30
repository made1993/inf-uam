/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package con;

/**
 *
 * @author roberto
 */
public class Beutify {

    public String toTable(String ss) {
        String Aux, Aux1;
        Aux = ss.replace(".", "<td class=\"empty\"></td>");
        Aux1 = Aux.replace("@", "<td class=\"red\"></td>");
        Aux = Aux1.replace("0", "<td class=\"yellow\"></td>");
        Aux1 = "<tr>"
                + Aux.replace("<br>", "</tr><tr>")
                + "</tr>";
        Aux1 = "<table>" 
                + "<tr> "
                + "<td> <a id=\"link00\" onclick=\"changeDiv('0')\">0</a> </td>"
                + "<td> <a id=\"link11\" onclick=\"changeDiv('1')\">1</a> </td>"
                + "<td> <a id=\"link22\" onclick=\"changeDiv('2')\">2</a> </td>"
                + "<td> <a id=\"link33\" onclick=\"changeDiv('3')\">3</a> </td>"
                + "<td> <a id=\"link44\" onclick=\"changeDiv('4')\">4</a> </td>"
                + "<td> <a id=\"link55\" onclick=\"changeDiv('5')\">5</a> </td>"
                + "<td> <a id=\"link66\" onclick=\"changeDiv('6')\">6</a> </td>"
                + "</tr>" 
                + Aux1 
                + "</table>";
        return Aux1;
    }
}
