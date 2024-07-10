package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    boolean indestructible;
    private Image destruible;
    private Image indestruible;
        
    public Bloque(double x, double y, boolean indestructible) {
        this.x = x;
        this.y = y;
        this.ancho = 52;
        this.alto = 52;
        this.indestructible = indestructible;
        this.destruible = Herramientas.cargarImagen("assets/destruible.png");
        this.indestruible = Herramientas.cargarImagen("assets/indestruible.png");
    }

    public void dibujar(Entorno e) {
    	if(indestructible) {
            e.dibujarImagen(indestruible, x, y, 0,0.1);
    	} else {
    		 e.dibujarImagen(destruible, x, y, 0,0.1);
    	}
    }

    
    // Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
}
