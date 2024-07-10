package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
    private double x;
    private double y;
    private Image imagenDerecha;
    private Image imagenIzquierda;
    private int ancho;
    private int alto;
    private double velocidad = 0.5;
    private double gravedad = 0.5;
    private double velocidadX;
    private double velocidadY;
    private double escalaImg = 0.17;
    private boolean moviendoDerecha;
    boolean colisionDetectada;

    public Enemigo(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 45;
        this.alto = 50;
        this.imagenDerecha = Herramientas.cargarImagen("assets/enemigo_der.png");
        this.imagenIzquierda = Herramientas.cargarImagen("assets/enemigo_iz.png");
        this.velocidadX = velocidad;
        this.velocidadY = 0;
        this.moviendoDerecha = true;
        this.colisionDetectada = false;
    }

    public void dibujar(Entorno e) {
        if (moviendoDerecha) {
            e.dibujarImagen(imagenDerecha, x, y-12, 0, escalaImg);
        } else {
            e.dibujarImagen(imagenIzquierda, x, y-12, 0, escalaImg);
        }
    }
  
    public void mover(Entorno e, Bloque[] bloques) {
        velocidadY += gravedad;
        y += velocidadY;
        for (Bloque bloque : bloques) {
            if (bloque != null && colisionDesdeArriba(bloque)) {
                velocidadY = 0;
                y = bloque.getY() - bloque.getAlto() / 2 - alto / 2;
                colisionDetectada = true;
            }
        }   
        if (x + ancho / 2 >= e.ancho()) {
            x = e.ancho() - ancho / 2;
            cambiarDireccion();
        } else if (x - ancho / 2 <= 0) {
            x = ancho / 2;
            cambiarDireccion();
        }     
        if (moviendoDerecha) {
            x += velocidadX;
        } else {
            x -= velocidadX;
        }       
    }

    public void cambiarDireccion() {
        moviendoDerecha = !moviendoDerecha;
    }
    public BombaEnemigo dispararBomba() {
		if( this.moviendoDerecha == true) {
		return new BombaEnemigo(this.x,this.y,1);
		} else {
			return new BombaEnemigo(this.x,this.y,0);
		}
	}

    private boolean colisionDesdeArriba(Bloque bloque) {
        return (x + ancho / 2 > bloque.getX() - bloque.getAncho() / 2 &&
                x - ancho / 2 < bloque.getX() + bloque.getAncho() / 2 &&
                y + alto / 2 >= bloque.getY() - bloque.getAlto() / 2 &&
                y + alto / 2 <= bloque.getY());
    }
    public boolean colisionaCon(Bala bala) {
        return (bala.getX() + bala.getAncho() / 2 > x - ancho / 2 &&
                bala.getX() - bala.getAncho() / 2 < x + ancho / 2 &&
                bala.getY() + bala.getAlto() / 2 > y - alto / 2 &&
                bala.getY() - bala.getAlto() / 2 < y + alto / 2);
    }


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