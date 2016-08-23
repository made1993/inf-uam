package particionado;

import java.util.ArrayList;

public class Particion {
	private ArrayList<Integer> indicesTrain;
	private ArrayList<Integer> indicesTest;
	
	public Particion(ArrayList<Integer> indTrain, ArrayList<Integer> indTest) {
		indicesTrain=indTrain;
		indicesTest=indTest;
	}
	
	public ArrayList<Integer> getTrain(){
		return indicesTrain;
	}
	
	public ArrayList<Integer> getTest(){
		return indicesTest;
	}
}