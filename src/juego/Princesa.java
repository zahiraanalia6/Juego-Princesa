package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
    private double x;
    private double y;
    private Image imagenDerecha;
    private Image imagenIzquierda;
    private int ancho;
    private int alto;
    private int velocidad = 4;
    private double escalaImg = 0.10;
    private double gravedad = 0.5;
    private double velocidadSalto = -12;
    private double velocidadVertical;
    private boolean saltando;
    private boolean mirandoDerecha;

    public Princesa(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 45;
        this.alto = 50;
        this.imagenDerecha = Herramientas.cargarImagen("assets/princesa.png");
        this.imagenIzquierda = Herramientas.cargarImagen("assets/princesa_2.png");
        this.velocidadVertical = 0;
        this.saltando = false;
        this.mirandoDerecha = true;
    }

    public void dibujar(Entorno e) {
        if(mirandoDerecha) {
        	 e.dibujarImagen(imagenDerecha, x, y, 0, escalaImg);
        } else {
        	e.dibujarImagen(imagenIzquierda, x, y, 0, escalaImg);
        }
     }

    
    public void moverDerecha(Entorno e) {
        if (this.x + this.ancho / 2 < e.ancho()) {
            this.x += velocidad;
        }
        this.mirandoDerecha = true;
    }

    public void moverIzquierda() {
        if (this.x - this.ancho / 2 >= 0) {
            this.x -= velocidad; 
        }
        this.mirandoDerecha = false;
    }

    public void saltar() {
        if (!saltando) {  //Solo puede saltar si no está ya en el aire
            velocidadVertical = velocidadSalto;
            saltando = true;
        }
    }
    
    public void cancelarSalto() {
    	velocidadVertical =2;
    }

    public void caer(boolean enElAire) {
        if (saltando||enElAire) {
            y += velocidadVertical;
            velocidadVertical += gravedad;
        }
    }

    public void detenerCaida(double nuevaY) {
        this.y = nuevaY - this.alto / 2;
        this.velocidadVertical = 0;
        this.saltando = false;
    }
    
    boolean colisionDesdeAbajo(Bloque bloque) {
        // Detectar colisión con la parte inferior del bloque
		return (x + ancho / 2 > bloque.getX() - bloque.getAncho() / 2 &&
                x - ancho / 2 < bloque.getX() + bloque.getAncho() / 2 &&
                y - alto / 2 <= bloque.getY() + bloque.getAlto() / 2 &&
                y - alto / 2 >= bloque.getY());
    }
	
    boolean colisionDesdeArriba(Bloque bloque) {
    	return (x + ancho/ 2 > bloque.getX() - bloque.getAncho() / 2 &&
                x - ancho / 2 < bloque.getX() + bloque.getAncho() / 2 &&
                y + ancho / 2 >= bloque.getY() - bloque.getAlto() / 2 &&
                y + ancho / 2 <= bloque.getY());
    }
    
    public boolean colisionaconEnemigo(Enemigo enemigo) {
    	return x < enemigo.getX() + enemigo.getAncho() &&
    		   x + ancho > enemigo.getX() &&
    	       y < enemigo.getY() + enemigo.getAlto() &&
    	       y + alto > enemigo.getY();
    	}
    
    public Bala dispararBala() {
		if( this.mirandoDerecha == true) {
		return new Bala(this.x,this.y,1);
		} else {
			return new Bala(this.x,this.y,0);
		}
	}
		
    // Getters y Setters 
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}

