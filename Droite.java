import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Droite extends Line2D.Double{

	private ArrayList<PointC> listePoints;
	private PointC pA;
	private PointC pB;
	private double coef;
	private double origine;
	private int type; 

	public Droite(){
		super(0, 0, 0, 0);
		listePoints=new ArrayList<PointC>();
		creerDroiteRandom();
		calculCoef();
		calculOrigine();
		type=-1;
	}
	
	public Droite(int type){
		listePoints=new ArrayList<PointC>();
		type=-2;
	}
	
	// type 0,1,2,3 permet de situer le coté 0->-
	/*     0
	 *  -----
	 * 3|	 | 1
	 *  -----
	 * 	 2
	 */
	public Droite(double aX, double aY, double bX, double bY, int t){
		super(aX, aY, bX, bY);
		pA = new PointC(aX, aY);
		pB = new PointC(bX, bY);
		type=t;
		listePoints=new ArrayList<PointC>();
	}
	
	private void calculCoef(){
		coef=((pB.getY()-pA.getY())/(pB.getX()-pA.getX()));
	}
	
	private void calculOrigine(){
		origine=-(coef*pA.getX())+pA.getY();
	}
	
	public void creerDroiteRandom(){
		//choix du coté
		int cote1;
		int cote2;
		do {
			cote1 = (int) (Math.random()*4);
			cote2 = (int) (Math.random()*4);
		}while(cote1==cote2);
		
		
		
		switch (cote1) {
			case 0:
				pA = new PointC(((double) (Math.random()*401)), 0);
				break;
		
			case 1:
				pA = new PointC(400, ((double) Math.random()*401));
				break;
			
			case 2:
				pA = new PointC(((double) (Math.random()*401)), 400);
				break;
				
			case 3:
				pA = new PointC(0, ((double) (Math.random()*401)));
				
			default:
				break;
		}
		
		
		switch (cote2) {
			case 0:
				pB = new PointC(((double) (Math.random()*401)), 0);
				break;
		
			case 1:
				pB = new PointC(400, ((double) (Math.random()*401)));
				break;
			
			case 2:
				pB = new PointC(((double) (Math.random()*401)), 400);
				break;
				
			case 3:
				pB = new PointC(0, ((double) (Math.random()*401)));
				
			default:
				break;
		}
		
		super.setLine(pA, pB);
		System.out.println(super.getP1() +" "+super.getP2());
		
	}
	
	public void ajouterPoint(PointC p){
		
		listePoints.add(p);
	}
	
	public Line2D.Double getDroite(){
		return new Line2D.Double(pA, pB);
	}
	
	public Droite getObjDroite(){
		return this;
	}
	
	public int getType(){
		return type;
	}
	
	public double getCoef(){
		return coef;
	}

	public double getOrigine(){
		return origine;
	}
	
	public ArrayList<PointC> getListePoints(){
		return listePoints;
	}
	
	public void setListePoints(int i, PointC p){
		listePoints.set(i, p);
	}
	/*
	public void trierListe(){
		int longueur=listePoints.size();
	    boolean permut;
	 
	    do
	    {
	        
	        permut=false;
	        for (int i=0 ; i<longueur ; i++)
	        {
	            // Teste si 2 éléments successifs sont dans le bon ordre ou non
	            if (listePoints.get(i).getX()> listePoints.get(i).getX())
	            {
	                // s'ils ne le sont pas on échange leurs positions
	                echanger(tableau,i,i+1);
	                list.set(newPosition, list.set(oldPosition, list.get(newPosition)));
	                permut=true;
	            }
	        }
	    }
	    while(permut);
		}
	}*/
	
	public String toString(){
		return "Droite "+pA.getX()+" "+pA.getY()+" --- "+pB.getX()+" "+pB.getY()+
				"   coeff : "+coef+
				"origine : "+origine+
				"la liste des liasons contient"+ listePoints
				;
	}
}
