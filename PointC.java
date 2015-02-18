import java.awt.geom.Point2D;
import java.util.ArrayList;


public class PointC extends Point2D.Double{

	private ArrayList<Liaison> listeLaison;
	
	
	public PointC(double x, double y){
		super(x,y);
		listeLaison = new ArrayList<Liaison>();
	}
	
	
	public String toString(){
		return "X : "+getX()+
				"Y :"+getY();
	}
	
	public void ajouterLiason(Liaison l){
		listeLaison.add(l);
	}
	
}
