package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Gatito {
	private double x;
    private double y;
    private Image gato;
    
    
    public Gatito(double x, double y) {
    	this.x=x;
    	this.y=y;
    	this.gato=Herramientas.cargarImagen("assets/gatito.png");
    }

    
    public void dibujar(Entorno e) {
        	 e.dibujarImagen(gato, x, y, 0, 0.085);
        }
    
    
    public boolean salvado(Princesa p) {
    	if(p.getX()==x&&p.getY()+8==y) {
    		return true;
    	}
    	return false;
    	
    }

}
