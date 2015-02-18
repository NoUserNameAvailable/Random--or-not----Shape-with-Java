import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class Figure {

	private ArrayList<Droite> listeDroite;
	
	public Figure(){
		listeDroite = new ArrayList<Droite>();
	}
	
	public void ajouterDroite(Droite d){
		listeDroite.add(d);
	}
	
	public ArrayList<Droite> getDroites(){
		return listeDroite;
	}
	
	public void supprimerOccurence(){
		
		for(int i=0; i<listeDroite.size(); i++){
			listeDroite.removeAll(Collections.singleton(listeDroite.get(0)));
		}
	}
	
	public String toString(){
		String s="";
		for (Droite d:listeDroite){
			s+="\n "+d.toString();
		}
		
		return s;
	}
	
}
