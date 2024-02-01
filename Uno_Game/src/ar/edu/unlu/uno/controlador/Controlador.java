package ar.edu.unlu.uno.controlador;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Jugador;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.observer.Observable;
import ar.edu.unlu.uno.observer.Observador;
import ar.edu.unlu.uno.vista.IVista;
import ar.edu.unlu.uno.vista.VistaC;

public class Controlador implements Observador {
	private Mesa modelo;
	private IVista vista;

	public Controlador(Mesa modelo, VistaC vista) throws Exception {
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
		this.modelo.agregarObservador(this);
		this.vista.iniciar();
	}

	public String imprimirListaJugadores() {
		return this.modelo.imprimirListaJugadores();
	}

	public boolean haySuficientesJugadores() {
		return this.modelo.getListaJugadores().size() > 1;
	}

	public void agregarJugador(String nombre) {
		this.modelo.agregarJugador(nombre);
	}

	public String imprimirPuntajes() {
		return this.modelo.imprimirTablaPuntuaciones();
	}

	public Jugador jugadorTurnoActual() {
		return this.modelo.getJugador(this.modelo.getManejadorTurnos().getTurnoActual());
	}

	public String mostrarManoJugador() {
		return this.jugadorTurnoActual().mostrarMano();
	}

	public int getCartasExtra() {
		return this.modelo.getPozoDescarte().getCartasExtra();
	}
	
	public boolean puedeRobar() {
		return this.modelo.getMazoPrincipal().puedeRobar();
	}

	public String getTopePozo() {
		return this.modelo.getPozoDescarte().verTope().toString();
	}

	public Colores getColorActual() {
		return this.modelo.getPozoDescarte().getColorPartida();
	}
	
	public int tamañoManoJugador() {
		return this.jugadorTurnoActual().getMano().size();
	}

	public void descartarCarta(int idJugador, int iCarta) {
		this.modelo.descartarCarta(idJugador, iCarta);
	}

	public void robarParaJugador(int idJugador) {
		this.modelo.robarParaJugador(idJugador);
	}
	
	public void descartarTurno(int idJugador) {
		this.modelo.descartarTurno(idJugador);
	}

	public int calcularPuntaje(int idJugador) {
		return this.modelo.calcularPuntajeFinal(idJugador);
	}
	
	public Jugador getJugador(int id) {
		return this.modelo.getJugador(id);
	}
	
	// Metodos de Observador
	@Override
	public void actualizar(Eventos evento, Observable observable) {
		switch (evento) {
		case JUGADOR_AGREGADO:
			this.vista.imprimirCartel("-Jugador agregado!-");
			break;
		case CARTA_JUGADA:
			this.vista.imprimirCartel("-Carta jugada!-");
			break;
		case CARTA_INVALIDA:
			this.vista.imprimirCartel("-ERROR... Carta incompatible-");
			break;
		case CAMBIAR_COLOR:
			Colores nuevoColor = this.vista.elegirNuevoColor();
			this.modelo.getPozoDescarte().setColorPartida(nuevoColor);;
			break;
		default:
			break;
		}	
	}

}
