

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D.Double;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Peindre extends JPanel{

	private Plan plan;

	
	public Peindre(Plan plan){
		this.plan = plan;
	}
	
	public void paint(Graphics g) {

		
		
	    Graphics2D g2d = (Graphics2D) g;

	    
	   
	
	    
	   
	 // tracer les figures
	    ArrayList<Figure> listeFigure = plan.getListeFigure();
	   g2d.setColor(Color.GREEN);
	   System.out.println("NOMBRE de figures à tracer : "+listeFigure.size());
	   for(int i=0; i<listeFigure.size(); i++){
		  System.out.println("NOMBRE de droites " +listeFigure.get(i).getDroites().size());
		   int [] x = new int[listeFigure.get(i).getDroites().size()*2];
		   int [] y = new int[listeFigure.get(i).getDroites().size()*2];
		   
		   
		   
		   int j=0;
		   
		   System.out.println("Figure " + i);
		   while (j<(listeFigure.get(i).getDroites().size())){  
			   x[j]=(int) listeFigure.get(i).getDroites().get(j).getX1();
			   y[j]=(int) listeFigure.get(i).getDroites().get(j).getY1();
			   
			   x[j+(x.length/2)]=(int) listeFigure.get(i).getDroites().get(j).getX2();
			   y[j+(x.length/2)]=(int) listeFigure.get(i).getDroites().get(j).getY2();
			
			   j++;
			   
			   
		   }
		   
		   for(int k=0; k<x.length; k++){
			   System.out.println("x="+x[k]+" y="+y[k]);
		   }
		   
		   
		   System.out.println();
		   g2d.fillPolygon(x, y, x.length);
		   
	   }
	    
	    
	    
	    
	    
	    
	    
	    g2d.setColor(Color.BLACK);
	    for(int i=0; i<plan.getListeDroite().size()/1.5; i++){
	    		g2d.draw(plan.getListeDroite().get(i).getDroite());
	    }
	    
	    /*
	    g2d.setColor(Color.BLACK);
	    
	    for(int i=0; i<plan.getListeDroiteRouge().size(); i++){
	 
	    	g2d.draw(plan.getListeDroiteRouge().get(i).getDroite());
	    }*/
	   
	    
	    
	  }
	
}
