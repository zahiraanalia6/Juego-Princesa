package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;
import java.awt.Color;
import javax.swing.ImageIcon;


public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	private ImageIcon fondo;
	
	private ImageIcon fondo_Inicio;
	
	private ImageIcon fondo_Final;
	
    private boolean enJuego;
	
	private Princesa princesa;
	
	private Bloque[] bloques;
	
	private Enemigo[] enemigos;
	
	private Bala balaP; //balas princesa
	
	private Gatito gatito;
	
	private int puntos,eliminados;
	
	private BombaEnemigo[] bombasEnemigos; // Bombas lanzadas por los enemigos
	
	private boolean juegoTerminado; // Variable para controlar el estado del juego
	
	private boolean juegoGanado; // Variable para controlar el estado del juego
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "... - Grupo Nº1 - Gudiño - Porta - Vega - V0.01", 800, 600);
		
		//Inicializa el fondo
		this.fondo = new ImageIcon(getClass().getResource("/assets/fondo_volcan_2.png"));
		this.fondo_Inicio = new ImageIcon(getClass().getResource("/assets/fondo_Inicio.png"));
		this.fondo_Final = new ImageIcon(getClass().getResource("/assets/fondo_Final.png"));
		
		// Inicializar lo que haga falta para el juego
		this.princesa = new Princesa(entorno.ancho() / 2, entorno.alto()-80); 	
		
		this.gatito = new Gatito(80,80);
		
		this.bloques = new Bloque[64];
		
		this.enemigos = new Enemigo[8];
		
		this.bombasEnemigos = new BombaEnemigo[enemigos.length]; // Asume una bomba por enemigo
		
		this.puntos = 0; this.eliminados=0;
		
		this.enJuego = false;
		
		this.juegoGanado = false;
		
		this.juegoTerminado = false; // Inicializa el juego como no terminado
		
		
		//inicializar los bloqes
		this.iniciarBloques(4); //el argumento pide cuantas filas se quieren crear en total    
		
		
		//inicializar los enemigos
		 this.iniciarEnemigos();
		
		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	private void iniciarEnemigos() {
		int enemigosPorPiso = 2;	
		for (int i=0; i<enemigos.length; i++) {
			int piso = i / enemigosPorPiso;
			double x =100+(i%enemigosPorPiso)*400;
			double y = 100+(piso*150);
			enemigos[i]=new Enemigo(x,y);
	    }
    }
		
	private void iniciarBloques(int filas) {
		int xInicial = 24;
	    int yInicial = entorno.alto() - xInicial;
	    int yAltura = 150;
	    int totalFilas = filas;
	    int totalBloquesPorFila = bloques.length/totalFilas;
	    int desplazamiento = 3; // desplazamiento para cada fila

	    for (int fila = 0; fila < totalFilas; fila++) {
	        int yFila = yInicial - (fila * yAltura); 
	        int inicioIndestructibles = (fila * desplazamiento) % (totalBloquesPorFila - 4);
	        for (int i = 0; i < totalBloquesPorFila; i++) {
	            int x = xInicial + (i * 50); //separacion entre bloques
	            if(fila==0) {
	            	this.bloques[fila * totalBloquesPorFila+i] = new Bloque(x,yFila,false);
	            }
	            else if (i >= inicioIndestructibles && i < inicioIndestructibles + 4) {
	                this.bloques[fila * totalBloquesPorFila + i] = new Bloque(x, yFila, true); // Bloque indestructible
	            } else {
	                this.bloques[fila * totalBloquesPorFila + i] = new Bloque(x, yFila, false); // Bloque destructible
	            	}
	        	}
	    	}
		}
	
	private void dibujarFondo() {
		entorno.dibujarImagen(fondo.getImage(),entorno.ancho()/2,entorno.alto()/2,0);
	}
	
	
	private void dibujarFondoInicio() {
		entorno.dibujarImagen(fondo_Inicio.getImage(),entorno.ancho()/2,entorno.alto()/2,0);
	}
	
	private void dibujarFondoFinal() {
		entorno.dibujarImagen(fondo_Final.getImage(),entorno.ancho()/2,entorno.alto()/2,0);
	}
	
	private void dibujarMenuInicio() {
        dibujarFondoInicio();
        entorno.cambiarFont("Venice Classic", 60, Color.YELLOW);
        entorno.escribirTexto("Super Elizabeth Sis", entorno.ancho() / 2 - 220, entorno.alto() / 2 - 70);
        entorno.escribirTexto("Volcano Edition", entorno.ancho() / 2 - 190, entorno.alto() / 2 - 10);
        entorno.cambiarFont("Minecraft", 18, Color.YELLOW);
        entorno.escribirTexto("Empezar (Espacio)", entorno.ancho() / 2 - 90, entorno.alto() / 2 + 50);
        entorno.escribirTexto("Salir (S)", entorno.ancho() / 2 - 43, entorno.alto() / 2 + 100);
    }
	
    private void dibujarPuntuacion() {
		entorno.cambiarFont("Minecraft",20,Color.white);
		entorno.escribirTexto("Puntos: " + this.puntos, 20, 585);
		entorno.escribirTexto("Enemigos eliminados: " + this.eliminados, 150, 585);
		
	} 
	
	private void dibujarVictoria() {
		dibujarFondoFinal();
		entorno.cambiarFont("Venice Classic", 60, Color.YELLOW);
	    entorno.escribirTexto("Ganaste", entorno.ancho()/2 - 95, entorno.alto()/2);
	    entorno.cambiarFont("Minecraft", 18, Color.YELLOW);
	    entorno.escribirTexto("reiniciar (V)", entorno.ancho() / 2 - 43, entorno.alto() / 2 + 100);
	    entorno.escribirTexto("Salir (S)", entorno.ancho() / 2 - 43, entorno.alto() / 2 + 120);
	    if (entorno.sePresiono('v')) {
            reiniciarJuego();
        }
        if(entorno.sePresiono('s')) {
        	salir();
        }
	}
	
	private void dibujarDerrota() {
		entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(), entorno.alto(), 0, Color.BLACK);
		entorno.cambiarFont("Venice Classic", 60, Color.RED);
	    entorno.escribirTexto("Perdiste", entorno.ancho()/2 - 95, entorno.alto()/2);
	    entorno.cambiarFont("Minecraft", 18, Color.RED);
	    entorno.escribirTexto("reiniciar (V)", entorno.ancho() / 2 - 43, entorno.alto() / 2 + 100);
	    entorno.escribirTexto("Salir (S)", entorno.ancho() / 2 - 43, entorno.alto() / 2 + 120);
	    if (entorno.sePresiono('v')) {
            reiniciarJuego();
        }
        if(entorno.sePresiono('s')) {
        	salir();
        }
	}
	
	public void iniciarJuego() {
        this.enJuego = true;
    }

    public void salir() {
        System.exit(0);
    }
	
	private void reiniciarJuego() {
        this.princesa = new Princesa(entorno.ancho() / 2, entorno.alto() - 80);   
        this.gatito = new Gatito(80, 80);
        this.bloques = new Bloque[64];
        this.iniciarBloques(4); 
        this.enemigos = new Enemigo[8];
        this.iniciarEnemigos(); 
        this.bombasEnemigos = new BombaEnemigo[enemigos.length]; 
        this.balaP = null;
        this.puntos = 0; 
        this.eliminados = 0;
        this.juegoTerminado = false;
        this.juegoGanado = false;
	}
	
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * caer el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		if(!enJuego) {
			dibujarFondoInicio();
			dibujarMenuInicio();
			if(entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				iniciarJuego();
			} else if (entorno.estaPresionada('s')) {
				salir();
			}
		} else {
		// Dibuja el fondo de la pantalla
	    dibujarFondo();
	    
	    // Hace que la princesa caiga si está en el aire
	    princesa.caer(princesaEnElAire());
	    
	    // Dibuja la princesa en la pantalla
	    princesa.dibujar(entorno);
	    
	    // Dibuja el gatito en la pantalla
	    gatito.dibujar(entorno);
	    
	    // Verifica si el juego ha terminado y muestra un mensaje si es así
	    if (juegoTerminado) {
           dibujarDerrota();           
            return;
        }
        
        if (juegoGanado) {
            dibujarVictoria();
            return;
        }

	    // Movimiento de la princesa a la derecha
	    if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
	        princesa.moverDerecha(entorno);
	    }
	    
	    // Movimiento de la princesa a la izquierda
	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
	        princesa.moverIzquierda();
	    }
	    
	    // Salto de la princesa
	    if (entorno.sePresiono('x')) {
	        princesa.saltar();
	    }
	    
	    // Disparo de bala por la princesa
	    if (entorno.sePresiono('c') && balaP == null) {
	        balaP = princesa.dispararBala();
	    }

	    // Colisión con bloques
	    for (int i = 0; i < bloques.length; i++) {
	        if (bloques[i] != null && !bloques[i].indestructible && princesa.colisionDesdeAbajo(bloques[i])) {
	            bloques[i] = null;  // Elimina el bloque si no es indestructible y hay colisión desde abajo
	            princesa.cancelarSalto();  // Cancela el salto de la princesa
	        }
	        
	        // Dibuja los bloques
	        if (bloques[i] != null) {
	            this.bloques[i].dibujar(entorno);
	            
	            // Cancela el salto si colisiona con un bloque indestructible desde abajo
	            if (bloques[i].indestructible && princesa.colisionDesdeAbajo(bloques[i])) {
	                princesa.cancelarSalto();
	            }
	            
	            // Detiene la caída si colisiona con un bloque desde arriba
	            if (princesa.colisionDesdeArriba(bloques[i])) {
	                princesa.detenerCaida(bloques[i].getY() - 29);
	            }
	        }
	    }

	    // Movimiento y dibujado de los enemigos
	    for (int i = 0; i < enemigos.length; i++) {
	        if (enemigos[i] != null) {
	            enemigos[i].mover(entorno, bloques);
	            enemigos[i].dibujar(entorno);
	            
	            // Cada enemigo dispara una bomba si no tiene una activa
	            if (bombasEnemigos[i] == null) {
	                bombasEnemigos[i] = enemigos[i].dispararBomba();
	            }
	            
	            // Verifica colisión con la princesa
	            for (Enemigo enemigo : enemigos) {
	                if (enemigo != null && princesa.colisionaconEnemigo(enemigo)) {
	                    juegoTerminado = true;
	                    return;
	                }
	            }
	        }
	    }

	    // Movimiento y dibujado de la bala de la princesa
	    if (balaP != null) {
	        balaP.mover();
	        balaP.dibujar(entorno);
	        
	        // Elimina la bala si sale de la pantalla
	        if (balaP.fueraDePantalla(entorno)) {
	            balaP = null;
	        }
	    }

	    // Verifica colisión de la bala con los enemigos
	    if (balaP != null) {
	        for (int j = 0; j < enemigos.length; j++) {
	            Enemigo enemigo = enemigos[j];
	            if (balaP != null && enemigo != null && balaP.colisionaCon(enemigo)) {
	                enemigos[j] = null;  // Elimina al enemigo
	                balaP = null;  // Elimina la bala
	                this.puntos += 2;         
	                this.eliminados += 1;
	            }
	        }
	    }
	    
	    // Dibuja la puntuación en la pantalla
	    dibujarPuntuacion(); 
	    
	    // Verifica si la princesa ha salvado al gatito
	    if (gatito.salvado(princesa)) {
	        juegoGanado = true;
	    }

	    // Movimiento y dibujado de las bombas de los enemigos
	    for (int i = 0; i < bombasEnemigos.length; i++) {
	        if (bombasEnemigos[i] != null) {
	            bombasEnemigos[i].mover();
	            bombasEnemigos[i].dibujar(entorno);

	            // Elimina la bomba si sale de la pantalla
	            if (bombasEnemigos[i].fueraDePantalla(entorno)) {
	                bombasEnemigos[i] = null;
	            }
	        }
	    }

	    // Verifica colisión de la bala de la princesa con las bombas enemigas
	    for (int a = 0; a < bombasEnemigos.length; a++) {
	        if (bombasEnemigos[a] != null && balaP != null && balaP.colisionaCon(bombasEnemigos[a])) {
	            bombasEnemigos[a] = null;  // Elimina la bomba
	            balaP = null;  // Elimina la bala
	        }
	    }

	    // Verifica colisión de la princesa con los enemigos
	    for (Enemigo enemigo : enemigos) {
	        if (enemigo != null && princesa.colisionaconEnemigo(enemigo)) {
	            juegoTerminado = true;
	            return;
	        }
	    }

	    // Verifica colisión de la princesa con las bombas enemigas
	    for (BombaEnemigo bomba : bombasEnemigos) {
	        if (bomba != null && bomba.colisionaCon(princesa)) {
	            juegoTerminado = true;
	            return;
	        		}
	    		}
			}
	}
        
	 //termina tick
	

	private boolean princesaEnElAire() {
        for (int i = 0; i < bloques.length; i++) {
            if (bloques[i] != null &&
                princesa.getX() + princesa.getAncho() / 2 > bloques[i].getX() - bloques[i].getAncho() / 2 &&
                princesa.getX() - princesa.getAncho() / 2 < bloques[i].getX() + bloques[i].getAncho() / 2 &&
                princesa.getY() + princesa.getAlto() / 2 <= bloques[i].getY() - bloques[i].getAlto() / 2 &&
                princesa.getY() + princesa.getAlto() / 2 >= bloques[i].getY() - bloques[i].getAlto() / 2 - 5) {
                return false;
            }
        }
        return true;
    }
    

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}
