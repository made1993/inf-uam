package clasesAlgoritmoGentecio;

import java.util.*;

public class Regla {

	ArrayList<BitSet> regla;
	BitSet prediccion;
	
	public Regla (){
		regla= new ArrayList<BitSet>();
	}
	public Regla(ArrayList<Integer>  natr) {
		
		regla= new ArrayList<BitSet>();
		Random val= new Random();
		
		for(int i=0; i<natr.size() ; i++){
			BitSet atr=new BitSet(natr.get(i));
			do{
				
				for (int j=0; j<natr.get(i); j++){
					atr.set(j, val.nextBoolean());
				}
			}while (atr.cardinality()==0);
			regla.add(atr);
		}
		
		BitSet atr=new BitSet(2);
		if(val.nextBoolean()==true)
			atr.set(0, true);
		else
			atr.set(1, true);
		this.prediccion=atr;
	}
	public Regla(ArrayList<Integer>  natr, int allone) {
		regla= new ArrayList<BitSet>();
		
		for(int i=0; i<natr.size() ; i++){
			BitSet atr=new BitSet(natr.get(i));
			do{
				
				for (int j=0; j<natr.get(i); j++){
					atr.set(j, true);
				}
			}while (atr.cardinality()==0);
			regla.add(atr);
		}
		
		BitSet atr=new BitSet(2);
		/*
		if(val.nextBoolean()==true)
			atr.set(0, true);
		else
			atr.set(1, true);
			*/
		atr.set(1, true);
		this.prediccion=atr;
	}
	public ArrayList<BitSet> getRegla() {
		return regla;
	}
	public void setRegla(ArrayList<BitSet> regla) {
		this.regla.clear();
		for(BitSet a: regla){
			this.regla.add(a);
		}
	}
	public BitSet getPrediccion(){
		return prediccion;
	}
	public void setPrediccion(BitSet bs){
		prediccion=(BitSet)bs.clone();
	}
	public Regla copiaRegla(){
		Regla r = new Regla();
		r.prediccion=(BitSet) this.prediccion.clone();
		for(BitSet s: this.regla){
			r.regla.add((BitSet)s.clone());
		}
		return r;
	}
}
