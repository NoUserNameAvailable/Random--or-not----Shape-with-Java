import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;


public class test extends JFrame{

	public static void main(String[] args) throws PasDePointAGaucheException {
		Plan plan = new Plan();
	    JFrame frame = new JFrame("Points");
	   
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(450, 450);
	    frame.setLocationRelativeTo(null);
	    plan.ajouterDroite(new Droite(0, 0, 400, 0, 0));
		plan.ajouterDroite(new Droite(400, 0, 400, 400, 1));
		plan.ajouterDroite(new Droite(0, 400, 400, 400, 2));
		plan.ajouterDroite(new Droite(0, 0, 0, 400, 3));

		int size = 15;
	    for (int i=0; i<size; i++){
			plan.ajouterDroite(new Droite());
	    }
		
		
		plan.calculDesLiasons();
		
		
		
		plan.remplissagePML();
		int i=0, a=0;
		int nbFigure = 10;
		int nbTentativeMax = 100;
		while(i<nbFigure && a<nbTentativeMax){
			try{
				plan.tracerFigureEnAllantADroite();
			}catch(PasDePointADroiteException e){
				System.out.println("ERREUR: Il n'y a pas de point à droite...");
				i--;
			} catch (PointDejaParcourusException e) {
				System.out.println("ERREUR: Tentative n'aboutissant pas (point d�j� parcourus)");
				i--;
			}

			i++;
			a++;
		}
		
		System.out.println(plan.getListeFigure().size());
		System.out.println("Nombre de tentatives r�alis�es :"+a);
		System.out.println("Figure trac�es :"+i);
		frame.setBackground(Color.WHITE);
		
		
		 for(int k=0; k<plan.getListeFigure().size(); k++){
			    System.out.println("Liste des seg à tracer "+plan.getListeFigure().get(k).getDroites().toString());
		   }
		 
		
		 
		
		frame.add(new Peindre(plan));
	    frame.setVisible(true);
	    
	}
	

}
