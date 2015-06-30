package p3;

import java.io.*;
import java.util.*;

public class Simulacion {
	
	private ArrayList<Posada> posadas;
	private ArrayList<Camino> caminos;
	private Explorador explorador;

	public Simulacion(String p, String c, String e) throws IOException {

			this.posadas = new ArrayList<Posada>();
			String linea, nombre_posada, energia;
			int ener_rec;
			String[] pvalores;
	
			FileInputStream fichero_pos = new FileInputStream(p);
			InputStreamReader preader = new InputStreamReader(fichero_pos);
			BufferedReader pbuffer = new BufferedReader(preader);

			while ((linea = pbuffer.readLine()) != null) {
				pvalores = linea.split(" ");
				nombre_posada = pvalores[0];
				energia = pvalores[1];
				ener_rec = Integer.parseInt(energia);
				posadas.add(new Posada(nombre_posada, ener_rec));
			}

			fichero_pos.close();
			
			this.caminos = new ArrayList<Camino>();
			String clinea, nombre_pos_origen, nombre_pos_destino, energia_camino;
			int ener_camino;
			Posada pos_origen = null, pos_destino = null;
			String[] cvalores;

			FileInputStream fichero_cam = new FileInputStream(c);
			InputStreamReader creader = new InputStreamReader(fichero_cam);
			BufferedReader cbuffer = new BufferedReader(creader);

			while ((clinea = cbuffer.readLine()) != null) {
				cvalores = clinea.split(" ");
				nombre_pos_origen = cvalores[0];
				nombre_pos_destino = cvalores[1];
				energia_camino = cvalores[2];
				ener_camino = Integer.parseInt(energia_camino);

				for (Posada pos : posadas) {
					if (pos.getNombre().compareToIgnoreCase(nombre_pos_origen)==0) {
						pos_origen = pos;
					}
					if (pos.getNombre().compareToIgnoreCase(nombre_pos_destino)==0) {
						pos_destino = pos;
					}
				}

				caminos.add(new Camino(pos_origen, pos_destino, ener_camino));
				pos_origen.addCamino(caminos.get(caminos.size()-1));
			}
			
			fichero_cam.close();
						
			String elinea, nombre_exp, ener, posicion, pos_dest_exp;
			int ener_exp;
			String[] evalores;

			FileInputStream fichero_exp = new FileInputStream(e);
			InputStreamReader ereader = new InputStreamReader(fichero_exp);
			BufferedReader ebuffer = new BufferedReader(ereader);

			while ((elinea = ebuffer.readLine()) != null) {
				if (elinea.length() != 1) {
					evalores = elinea.split(" ");
					nombre_exp = evalores[0];
					ener = evalores[1];
					ener_exp = Integer.parseInt(ener);
					posicion = evalores[2];
					
					for (Posada p1 : posadas) {
						if (p1.getNombre().compareToIgnoreCase(posicion)==0) {
							explorador = new Explorador(nombre_exp, ener_exp, p1);
						}
					}
				} else {
					pos_dest_exp = elinea;
					for (Posada p2 : posadas) {
						if (p2.getNombre().compareToIgnoreCase(pos_dest_exp)==0) {
							explorador.recorre(explorador.getPos().getCamino(p2));
							System.out.println(explorador);
						}
					}
				}
			}
			
			fichero_exp.close();
			
			return;
	}
}
