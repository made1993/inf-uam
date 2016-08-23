package es.uam.eps.bmi.search.indexing.thread;
 import java.util.*;
public class ThreadInsert extends Thread{
	private Set<String> voc;
	private String[] terms;
	private int ini;
	private int fin;
	public ThreadInsert(Set<String> voc, String[] terms, int ini, int fin){
		
		this.voc= voc;
		this.terms= terms;
		this.ini= ini;
		this.fin= fin;
	}
	public void run(){
		
		for(int i=ini; i<fin; i++){
			voc.add(terms[i]);
		}
	}
	
	
	  public static void main(String args[]) {
		  Runnable r = new ThreadInsert(null, null, 0, 0);
		  Thread th=new Thread(r);
		  th.start();
		  try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println("dsds");
		  
	  }
}
