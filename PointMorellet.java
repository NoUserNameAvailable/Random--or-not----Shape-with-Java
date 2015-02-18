import java.awt.geom.Point2D;
import java.util.ArrayList;


public class PointMorellet extends Point2D.Double {

	@Override
	public String toString() {
		return "pointMorellet [droitesIntersects=" + droitesIntersects + ", x="
				+ x + ", y=" + y + ", getDroitesIntersects()="
				+ getDroitesIntersects() + ", getX()=" + getX() + ", getY()="
				+ getY() + ", toString()=" + super.toString() + ", hashCode()="
				+ hashCode() + ", getClass()=" + getClass() + "]";
	}

	private ArrayList<Droite> droitesIntersects;
	
	public PointMorellet(double x, double y) {
		super(x, y);
		droitesIntersects = new ArrayList<Droite>();
	}
	
	public PointMorellet(Point2D.Double p){
		super(p.getX(), p.getY());
		droitesIntersects = new ArrayList<Droite>();
	}

	public void ajouterDroite(Droite d){
		droitesIntersects.add(d);
	}
	
	public ArrayList<Droite> getDroitesIntersects() {
		return droitesIntersects;
	}

	public void setDroitesIntersects(ArrayList<Droite> droitesIntersects) {
		this.droitesIntersects = droitesIntersects;
	}
	
	

}
