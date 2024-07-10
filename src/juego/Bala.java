package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Bala {
    private double x;
    private double y;
    private Image imagenDerecha;
    private Image imagenIzquierda;
    private int VELOCIDAD = 5;
    private final double ESCALA_IMAGEN = 0.10;
    private final int ancho = 10;
    private final int alto = 10; 
    int direccion;

    public Bala(double x, double y, int d) {
        this.x = x;
        this.y = y;
        this.imagenDerecha = Herramientas.cargarImagen("assets/bala_der.png");
        this.imagenIzquierda = Herramientas.cargarImagen("assets/bala_iz.png");
        direccion=d;
    }
    
    // Dibuja la bala en el entorno
    public void dibujar(Entorno entorno) {
        if(direccion==1) { 
            entorno.dibujarImagen(imagenDerecha, x, y, 0, ESCALA_IMAGEN);
        } else {
            entorno.dibujarImagen(imagenIzquierda, x, y, 0, ESCALA_IMAGEN);
        }
    }


 // Mueve la bala en la dirección actual
    public void mover() {
        if (direccion==1) {
                this.x += VELOCIDAD;
            } else {
                this.x -= VELOCIDAD;
            }
    }
    
//Verifica si la bala está fuera de la pantalla
    public boolean fueraDePantalla(Entorno e) {
        return (x >e.ancho()|| x < 0);
    }

    // Verifica si la bala colisiona con un enemigo
    public boolean colisionaCon(Enemigo enemigo) {
        return (x + ancho / 2 > enemigo.getX() - enemigo.getAncho() / 2 &&
                x - ancho / 2 < enemigo.getX() + enemigo.getAncho() / 2 &&
                y + alto / 2 > enemigo.getY() - enemigo.getAlto() / 2 &&
                y - alto / 2 < enemigo.getY() + enemigo.getAlto() / 2);
    }
    public boolean colisionaCon(BombaEnemigo bomba) {
        return (x + ancho / 2 > bomba.getX() - bomba.getAncho() / 2 &&
                x - ancho / 2 < bomba.getX() + bomba.getAncho() / 2 &&
                y + alto / 2 > bomba.getY() - bomba.getAlto() / 2 &&
                y - alto / 2 < bomba.getY() + bomba.getAlto() / 2);
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
