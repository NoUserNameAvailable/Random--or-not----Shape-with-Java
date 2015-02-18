import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Plan {

	private ArrayList<Droite> listeDroite;
	private ArrayList<PointMorellet> PML ;
	private ArrayList<Droite> droiteRouge;
	private ArrayList<PointMorellet> pointsParcourus;
	private ArrayList<Figure> listeFigure;
	private Figure figure;
	
	public Plan(){
		listeDroite=new ArrayList<Droite>();
		PML =new ArrayList<PointMorellet>();
		droiteRouge = new ArrayList<Droite>();
		pointsParcourus = new ArrayList<PointMorellet>();
		figure = new Figure();
		listeFigure = new ArrayList<Figure>();
	}
	
	public void ajouterDroite(Droite d){
		listeDroite.add(d);
	}
	
	public void ajouterDroiteRouge(Droite d){
		droiteRouge.add(d);
	}
	
	public ArrayList<Droite> getListeDroiteRouge(){
		return droiteRouge;
	}
	
	public ArrayList<Droite> getListeDroite(){
		return listeDroite;
	}
	
	public void calculDesLiasons(){
		int i;
		for (i=0; i<listeDroite.size(); i++){
			
			for(int j=0; j<listeDroite.size(); j++){
				if(!listeDroite.get(i).equals(listeDroite.get(j))  && listeDroite.get(i).getDroite().intersectsLine(listeDroite.get(j).getDroite())){
					
					/*System.out.println(listeDroite.get(i).getDroite().getP1()+""+listeDroite.get(i).getDroite().getP2()  
							+" intersecte"+
							listeDroite.get(j).getDroite().getP1() +""+listeDroite.get(j).getDroite().getP2());*/
					listeDroite.get(i).ajouterPoint(calculPointIntersection(listeDroite.get(i).getObjDroite(), listeDroite.get(j).getObjDroite()));
				}
			}
			
			//System.out.println(listeDroite.get(i));
		}
		
		
	}
	public ArrayList<PointMorellet> trouverPointVoisinsADroite(ArrayList<PointMorellet> pointsVoisins, PointMorellet pointActuel, PointMorellet vecteurNormal, PointMorellet vecteurDirecteur){
		ArrayList<PointMorellet> pointsVoisinsADroite = new ArrayList<PointMorellet>();
		for(PointMorellet pointVoisinATester: pointsVoisins){
			PointMorellet vecteurATester = this.calculerVecteurEntreDeuxPoints(pointActuel, pointVoisinATester);
			double cosinusEntreVecteurNormalEtVecteurATester = this.calculerAngleEntreVecteur(vecteurNormal, vecteurATester);
			//System.out.println("Pour le point : X="+pointVoisinATester.getX()+"Y ="+pointVoisinATester.getY()+" angle obtenu :"+ cosinusEntreVecteurNormalEtVecteurATester);
			//System.out.println("Son vecteur depuis PointActuel est : X="+vecteurATester.getX()+"Y ="+vecteurATester.getY()+" angle obtenu :"+ cosinusEntreVecteurNormalEtVecteurATester);

			if(cosinusEntreVecteurNormalEtVecteurATester > 0.10 && cosinusEntreVecteurNormalEtVecteurATester <= (double) 2 ){
				//Condition cosinus inférieur à 1: 
				//Condition de colineratit�  : && vecteurNormal.getX()*vecteurATester.getY()-vecteurNormal.getY()*vecteurATester.getX()!=0
				pointsVoisinsADroite.add(pointVoisinATester);
			}
		}
		//System.out.println("Nombre de points voisins � droite trouv�s :"+pointsVoisinsADroite.size());
		return pointsVoisinsADroite;
	}
	
	public ArrayList<PointMorellet> trouverPointsLesPlusADroite(ArrayList<PointMorellet> pointsVoisinsADroite, PointMorellet pointActuel, PointMorellet pointAncien, ArrayList<PointMorellet> pointsQuiVaEtreParcourusParcourus, PointMorellet vecteurDirecteur ){
		ArrayList<PointMorellet> pointsLesPlusADroite = new ArrayList<PointMorellet>();
		PointMorellet pointLePlusADroite;
		PointMorellet vecteurVersLePointLePlusAdroite;
		double angleEntreVecteurDirecteurEtVecteurLePlusADroite = 0;
		for(PointMorellet pointVoisinADroite: pointsVoisinsADroite){
			if(!pointsQuiVaEtreParcourusParcourus.contains(pointVoisinADroite) && !pointAncien.equals(pointVoisinADroite) && !this.pointsParcourus.contains(pointVoisinADroite)){
				//System.out.println("pointVoisinADroite : X= "+pointVoisinADroite.getX()+" Y="+pointVoisinADroite.getY());
				PointMorellet vecteurATester = this.calculerVecteurEntreDeuxPoints(pointActuel, pointVoisinADroite);
				double angleEntreVecteurDirecteurEtVecteurADroite = this.calculerAngleEntreDeuxVecteursEnDegre(vecteurDirecteur, vecteurATester);
				if(angleEntreVecteurDirecteurEtVecteurLePlusADroite <= (angleEntreVecteurDirecteurEtVecteurADroite + 0.00001)){
					pointLePlusADroite = pointVoisinADroite;
					vecteurVersLePointLePlusAdroite = this.calculerVecteurEntreDeuxPoints(pointActuel, pointLePlusADroite);
					angleEntreVecteurDirecteurEtVecteurLePlusADroite = this.calculerAngleEntreDeuxVecteursEnDegre(vecteurDirecteur, vecteurVersLePointLePlusAdroite);
					pointsLesPlusADroite.add(pointLePlusADroite);
				}
			}
		}
		return pointsLesPlusADroite;
	}
	 
	private PointC calculPointIntersection(Droite d1, Droite d2){
		double resX=0;
		double resY=0;
		if(d1.getType()==-1 && d2.getType()==-1){
			resX=(	(d2.getOrigine()-d1.getOrigine()) / (d1.getCoef()-d2.getCoef())	);
			resY=( d1.getCoef()*resX+d1.getOrigine());
			//System.out.println("\n X="+resX+"  Y="+resY);
			return new PointC(resX, resY);
		}
		
		else if(d1.getType()==-1){
			switch (d2.getType()) {
				case 0:
					resX=(	(-d1.getOrigine()) / (d1.getCoef())	);
					resY=0;
					break;
				
				case 1:	
					resX=400;
					resY=( (d1.getCoef()*400)+d1.getOrigine());
					break;
					
				case 2:
					resX=( (400-d1.getOrigine()) / (d1.getCoef()));
					resY=400;
					break;
					
				case 3:
					resX=0;
					resY=( (d1.getCoef()*0) + d1.getOrigine());
					
				default:
				 break;
			}
			return new PointC(resX, resY);
			
		}

		else if(d2.getType()==-1){
			switch (d1.getType()) {
				case 0:
					resX=(	(-d2.getOrigine()) / (d2.getCoef())	);
					resY=0;
					break;
				
				case 1:	
					resX=400;
					resY=( (d2.getCoef()*400)+d2.getOrigine());
					break;
					
				case 2:
					resX=( (400-d2.getOrigine()) / (d2.getCoef()));
					resY=400;
					break;
					
				case 3:
					resX=0;
					resY=( (d2.getCoef()*0) + d2.getOrigine());
					
				default:
				 break;
			}
			return new PointC(resX, resY);
			
		}
		
		else if((d1.getType()==0 && d2.getType()==1) || (d1.getType()==1 && d2.getType()==0)){
			return new PointC(400, 0);
		}
		
		else if((d1.getType()==1 && d2.getType()==2) || (d1.getType()==2 && d2.getType()==1)){
			return new PointC(400, 400);
		}
		
		else if((d1.getType()==2 && d2.getType()==3) || (d1.getType()==3 && d2.getType()==2)){
			return new PointC(0, 400);
		}
		
		else if((d1.getType()==0 && d2.getType()==3) || (d1.getType()==3 || d2.getType()==0)){
			return new PointC(0,0);
		}
		
		
		return null;
	}
	

	public double distanceEntreDeuxPoints(PointMorellet a, PointMorellet b){
		return Math.sqrt(Math.pow((b.getX()-a.getX()),2)+Math.pow((b.getY()-a.getY()),2));
	}
	
	public double calculerAngleEntreVecteur(PointMorellet a, PointMorellet b){
		return (a.getX()*b.getX()+a.getY()*b.getY())/(Math.sqrt((Math.pow(a.getX(),2)+Math.pow(a.getY(), 2))*((Math.pow(b.getX(),2))+Math.pow(b.getY(),2))));
	}
	
	public PointMorellet calculerVecteurEntreDeuxPoints(PointMorellet a, PointMorellet b){
		return new PointMorellet(b.getX()-a.getX(), b.getY()-a.getY());
	}
	
	public double calculerAngleEntreDeuxVecteursEnDegre(PointMorellet a, PointMorellet b){
		return Math.acos(calculerAngleEntreVecteur(a, b));
	}
	public PointMorellet calculerVecteurNormal(PointMorellet vecteurDirecteur){
		return new PointMorellet(-vecteurDirecteur.getY(), vecteurDirecteur.getX());
	}
	public PointMorellet calculerVecteurNormalVersLaGauche(PointMorellet vecteurDirecteur){
		return new PointMorellet(vecteurDirecteur.getX(), -vecteurDirecteur.getY());
	}
	
	public boolean vecteurDansLePlanDuVecteurNormal(PointMorellet vecteurDirecteur,PointMorellet vecteurArrive){
		return calculerAngleEntreDeuxVecteursEnDegre(vecteurArrive, calculerVecteurNormal(vecteurDirecteur))>0;
	}
	
	//public ArrayList<PointMorellet> pointsVoisinDunPoint(PointMorellet p){
		
	//}
	
	public PointMorellet trouverPointLePlusProche(PointMorellet p){
		PointMorellet pointLePlusProche = null ;
		for(int i =0 ; i<PML.size() ; i++){
			for(int j = 0; j < PML.get(i).getDroitesIntersects().size(); j++){
				if(!PML.get(i).equals(p)){
					for(int k = 0; k < p.getDroitesIntersects().size(); k++){
						if(p.getDroitesIntersects().get(k).equals(PML.get(i).getDroitesIntersects().get(j))){
							if(pointLePlusProche==null || distanceEntreDeuxPoints(p,pointLePlusProche) > distanceEntreDeuxPoints(p, PML.get(i))){
								pointLePlusProche = PML.get(i);

							}
						}
					}
				}
			}
		}
		return pointLePlusProche;
	}
	public ArrayList<PointMorellet> recupererPointsVoisins(PointMorellet p){
		ArrayList<PointMorellet> pointsIntersects = new ArrayList<PointMorellet>();
		for(int i =0 ; i<PML.size() ; i++){
			for(int j = 0; j < PML.get(i).getDroitesIntersects().size(); j++){
				if(!PML.get(i).equals(p)){
					for(int k = 0; k < p.getDroitesIntersects().size(); k++){
						if(p.getDroitesIntersects().get(k).equals(PML.get(i).getDroitesIntersects().get(j))){
							pointsIntersects.add(PML.get(i));
							//System.out.println("PML n�"+i+" : X= "+PML.get(i).getX()+" Y="+PML.get(i).getY());

						}
					}
				}
			}
		}
		//System.out.println("Nombre de points sur des droites lies l'intersection trouvees :"+pointsIntersects.size());
		return pointsIntersects;
	}
	public void remplissagePML(){
		ArrayList<Droite> listeDroiteCreationPML = this.listeDroite;
		PointC nul = new PointC(-1, -1);
		int a=0;
		for(int i=0; i<listeDroiteCreationPML.size(); i++){
			for(int j=0; j<listeDroiteCreationPML.get(i).getListePoints().size(); j++){
				if(listeDroiteCreationPML.get(i).getListePoints().get(j) != nul){
					PML.add(new PointMorellet(listeDroiteCreationPML.get(i).getListePoints().get(j)));
					for(int k=0; k<listeDroiteCreationPML.size(); k++){
						for(int l=0; l<listeDroiteCreationPML.get(k).getListePoints().size(); l++){
							if((listeDroiteCreationPML.get(k).getListePoints().get(l).getX() > PML.get(a).getX()-5 && listeDroiteCreationPML.get(k).getListePoints().get(l).getX() < PML.get(a).getX()+5)
								&& (listeDroiteCreationPML.get(k).getListePoints().get(l).getY() > (PML.get(a).getY()-5) && listeDroiteCreationPML.get(k).getListePoints().get(l).getY() < (PML.get(a).getY()+5)) && listeDroiteCreationPML.get(k).getListePoints().get(l)!=nul){
								
								PML.get(a).ajouterDroite(listeDroiteCreationPML.get(k));
								listeDroiteCreationPML.get(k).getListePoints().set(l, nul);
								
							}
						}
					}
				a++;
				}
				
			}
			
		}
		System.out.println("Nombre de Points d'intersections trouvés  : "+PML.size());
	}
	
	
	public ArrayList<PointMorellet> getListePointMorellet(){
		return PML;
	}
	public void tracerFigureEnAllantADroite() throws PasDePointADroiteException, PointDejaParcourusException{
		PointMorellet pointDefault =  this.getListePointMorellet().get(0);
		ArrayList<PointMorellet> pointsQuiVontEtreParcourusParcourus = new ArrayList<PointMorellet>();
		ArrayList<Droite> futurDroiteRouges = new ArrayList<Droite>();
		
		PointMorellet premierPoint = this.getListePointMorellet().get((int)(Math.random()*(this.recupererPointsVoisins(pointDefault).size()-2)));
		System.out.println("PremierPoint : X= "+premierPoint.getX()+" Y="+premierPoint.getY());
		PointMorellet deuxiemePoint = this.trouverPointLePlusProche(premierPoint);
		System.out.println("deuxiemePoint : X= "+deuxiemePoint.getX()+" Y="+deuxiemePoint.getY());
		this.ajouterDroiteRouge(new Droite(premierPoint.getX(),premierPoint.getY(),deuxiemePoint.getX(), deuxiemePoint.getY(), -2));
	
		
		PointMorellet pointSuivant = null;
		PointMorellet pointActuel = deuxiemePoint;
		PointMorellet pointAncien = premierPoint;
		int i =0;

		while(!pointActuel.equals(premierPoint)){
			pointsQuiVontEtreParcourusParcourus.add(pointActuel);
			//System.out.println(i);
			//System.out.println("pointActuel : X= "+pointActuel.getX()+" Y="+pointActuel.getY());
			//System.out.println("pointAncien : X= "+pointAncien.getX()+" Y="+pointAncien.getY());

			pointSuivant = null;
			ArrayList<PointMorellet> pointsVoisins = this.recupererPointsVoisins(pointActuel);
			
			
			PointMorellet vecteurDirecteur = this.calculerVecteurEntreDeuxPoints(pointAncien, pointActuel);
			PointMorellet vecteurNormal = this.calculerVecteurNormal(vecteurDirecteur);
			//System.out.println("Vecteur Normal : X="+vecteurNormal.getX()+" Y="+vecteurNormal.getY());
			ArrayList<PointMorellet> pointsVoisinsADroite = this.trouverPointVoisinsADroite(pointsVoisins, pointActuel, vecteurNormal, vecteurDirecteur);
			ArrayList<PointMorellet> pointsLesPlusADroite = this.trouverPointsLesPlusADroite(pointsVoisinsADroite, pointActuel,pointAncien, pointsQuiVontEtreParcourusParcourus, vecteurDirecteur);
			if(pointsLesPlusADroite.size()==0){
				this.droiteRouge = new ArrayList<Droite>();
				
				throw new PasDePointADroiteException();
			}
			if(pointsLesPlusADroite.size()==1){
				pointSuivant=pointsLesPlusADroite.get(0);
			}
			if(pointsLesPlusADroite.size()>1){
				for(PointMorellet unPointLePlusADroite:pointsLesPlusADroite){
					if(pointSuivant==null || this.distanceEntreDeuxPoints(pointActuel, unPointLePlusADroite) < this.distanceEntreDeuxPoints(pointActuel, pointSuivant))
						pointSuivant = unPointLePlusADroite;
				}
			}
			
			///System.out.println("pointSuivant : X= "+pointSuivant.getX()+" Y="+pointSuivant.getY());
			//
			futurDroiteRouges.add(new Droite(pointSuivant.getX(),pointSuivant.getY(),pointActuel.getX(),pointActuel.getY(),-2));
			
			pointAncien = pointActuel;
			pointActuel = pointSuivant;
			if(pointSuivant.equals(premierPoint)){
				//System.out.println("Retour au premier point");
			}
			
		}
		for(PointMorellet p : this.pointsParcourus){
			if(pointsQuiVontEtreParcourusParcourus.contains(p))
				throw new PointDejaParcourusException();
		}
		for(PointMorellet p :pointsQuiVontEtreParcourusParcourus){
			this.pointsParcourus.add(p);
		}
		for(Droite d :futurDroiteRouges){
			droiteRouge.add(d);
			figure.ajouterDroite(d);
		}
		listeFigure.add(figure);
		resetFigure();
		
		//System.out.println("\n \n \n les droites de la figure sont :" + figure.toString());
	}
	
	public void resetFigure(){
		figure = new Figure();
	}
	
	public ArrayList<Figure> getListeFigure(){
		return listeFigure;
	}
	
	public Figure getFigure(){
		return figure;
	}
	
}
