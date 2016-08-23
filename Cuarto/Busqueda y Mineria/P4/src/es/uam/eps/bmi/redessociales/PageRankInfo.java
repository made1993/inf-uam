package es.uam.eps.bmi.redessociales;

import java.util.*;

/**
 * Clase auxiliar que calcula el page rank de los nodos
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class PageRankInfo {

	private double prVal;
	private int salidas;
	private List<String> links;
	
	public PageRankInfo(){
		this.prVal = 0;
		this.salidas=0;
		this.links = new ArrayList<String>();
	}
	
	public void addLink(String link){
		this.links.add(link);
	}
	
	public double calcularPageRank(double r, HashMap<String, PageRankInfo> links){
		double aux=0;
		for(String s:this.links){
			PageRankInfo prInfo= links.get(s);
			aux+=prInfo.getPrVal()/prInfo.salidas; //Este valor vale 0, revisar
		}
		return r/links.size()+(1-r)*aux;
	}

	public double getPrVal() {
		return prVal;
	}

	public void setPrVal(double prVal) {
		this.prVal = prVal;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}
	
	
	public int getSalidas() {
		return salidas;
	}

	public void setSalidas(int salidas) {
		this.salidas = salidas;
	}

	@Override
	public String toString() {
		return "\nprVal=" + prVal + "\n\tlinks=" + links+"\n";
	}
	
	
	
}
