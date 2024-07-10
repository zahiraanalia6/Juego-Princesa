package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BombaEnemigo {
	 private double x;
	    private double y;
	    private Image imagenDerecha;
	    private Image imagenIzquierda;
	    private int velocidad = 1;
	    private final double escalaImagen = 0.20;
	    private final int ancho = 5;
	    private final int alto = 5;
	    int direccion;

	    public BombaEnemigo (double x, double y, int d) {
	        this.x = x;
	        this.y = y;
	        this.imagenDerecha = Herramientas.cargarImagen("assets/flecha_der.png");
	        this.imagenIzquierda = Herramientas.cargarImagen("assets/flecha_iz.png");
	        direccion=d;
	    }
	    
	    // Dibuja la bomba en el entorno
	    public void dibujar(Entorno entorno) {
	        if(direccion==1) { 
	            entorno.dibujarImagen(imagenDerecha, x, y, 0, escalaImagen);
	        } else {
	            entorno.dibujarImagen(imagenIzquierda, x, y, 0, escalaImagen);
	        }
	    }


	 // Mueve la bala en la dirección actual
	    public void mover() {
	        if (direccion==1) {
	                this.x += velocidad;
	            } else {
	                this.x -= velocidad;
	            }
	    }
	    	    
	//Verifica si la bala está fuera de la pantalla
	    public boolean fueraDePantalla(Entorno e) {
	        return (x >e.ancho()|| x < 0 );
	    }    

	    // Verifica si la bomba coliciona con la princesa
	    public boolean colisionaCon(Princesa princesa) {
	        return (x + ancho / 2 > princesa.getX() - princesa.getAncho() / 2 &&
	                x - ancho / 2 < princesa.getX() + princesa.getAncho() / 2 &&
	                y + alto / 2 > princesa.getY() - princesa.getAlto() / 2 &&
	                y - alto / 2 < princesa.getY() + princesa.getAlto() / 2);
	    }
	    //verifica si la bomba coliciona con la bala de la princesa 
	    public boolean colisionaCon(Bala bala) {
	        return (x + ancho / 2 > bala.getX() - bala.getAncho() / 2 &&
	                x - ancho / 2 < bala.getX() + bala.getAncho() / 2 &&
	                y + alto / 2 > bala.getY() - bala.getAlto() / 2 &&
	                y - alto / 2 < bala.getY() + bala.getAlto() / 2);
	    }
	    
	    // Obtiene la posición x de la bala
	    public double getX() {
	        return x;
	    }

	    // Obtiene la posición y de la bala
	    public double getY() {
	        return y;
	    }

	    // Obtiene el ancho de la bala
	    public int getAncho() {
	        return ancho;
	    }

	    // Obtiene el alto de la bala
	    public int getAlto() {
	        return alto;
	    }
	}