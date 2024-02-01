package ar.edu.unlu.uno.modelo;

import java.util.ArrayList;

import ar.edu.unlu.uno.modelo.carta.Carta;
import ar.edu.unlu.uno.modelo.carta.CartaNormal;
import ar.edu.unlu.uno.observer.Observable;
import ar.edu.unlu.uno.observer.Observador;

public class Mesa implements Observable {

	private MazoPrincipal mazoPrincipal;
	private PozoDescarte pozoDescarte;
	private ArrayList<Jugador> jugadores; // cada Jugador se identifica por su indice en el array
	private ManejadorTurnos manejadorTurnos;
	private final int cartasIniciales = 3;
	private ArrayList<Observador> observadores;

	public Mesa() {
		this.jugadores = new ArrayList<Jugador>();
		this.mazoPrincipal = new MazoPrincipal();
		this.pozoDescarte = new PozoDescarte();
		this.manejadorTurnos = new ManejadorTurnos(jugadores);
		this.pozoDescarte.agregar(this.mazoPrincipal.sacar());
		this.observadores = new ArrayList<>();
	}

	public void agregarJugador(String nombre) {
		Jugador j = new Jugador(nombre, jugadores.size());
		jugadores.add(j);
		this.repartir(j.getId(), cartasIniciales);
		this.notificar(Eventos.JUGADOR_AGREGADO);
	}

	public void repartir(int idJugador, int n) {
		for (int i = 0; i < n; i++)
			jugadores.get(idJugador).tomarCarta(mazoPrincipal.sacar());
	}

	public void robarParaJugador(int IdJugador) {
		if (this.mazoPrincipal.puedeRobar()) {
			this.getJugador(IdJugador).tomarCarta(this.mazoPrincipal.sacar());
			this.mazoPrincipal.setPuedeRobar(false);
			if (this.mazoPrincipal.estaVacia())
				this.reiniciarPozo();
		}
	}

	public void reiniciarPozo() {
		while (!this.pozoDescarte.estaVacia())
			this.mazoPrincipal.agregar(this.pozoDescarte.sacar());
		this.mazoPrincipal.mezclar();
		this.pozoDescarte.agregar(this.mazoPrincipal.sacar());
	}

	public void agregarCartasExtra(int idJugador) {
		int cartasExtra = this.pozoDescarte.getCartasExtra();
		while (cartasExtra > 0) {
			this.getJugador(idJugador).tomarCarta(this.mazoPrincipal.sacar());
			cartasExtra--;
		}
		this.pozoDescarte.setCartasExtra(0);
	}

	public void descartarCarta(int idJugador, int iCarta) {
		Carta cartaJugador = jugadores.get(idJugador).getCarta(iCarta);
		if (cartaJugador.esJugadaValida(this.pozoDescarte)) {
			cartaJugador.aplicarEfecto(this, idJugador);
			this.getJugador(idJugador).jugarCarta(iCarta);
			this.pozoDescarte.agregar(cartaJugador);
			this.manejadorTurnos.siguienteTurno();
			this.notificar(Eventos.CARTA_JUGADA);
		} else {
			this.notificar(Eventos.CARTA_INVALIDA);
		}
	}

	public void descartarTurno(int idJugador) { // luego de que el jugador tome una carta y decida pasar el turno
		this.agregarCartasExtra(idJugador);
		this.manejadorTurnos.siguienteTurno();
		this.mazoPrincipal.setPuedeRobar(true);
	}

	public int calcularPuntajeFinal(int idJugador) {
		int puntos = 0;
		for (Jugador j : jugadores) {
			for (Carta c : j.getMano()) {
				if (!c.tieneColor()) // CambioColor o CambioColorRoba4
					puntos += 50;
				if (c.esComodin() && c.tieneColor()) // CambioDireccion, Roba2, SaltoTurno,
					puntos += 20;
				if (!c.esComodin()) { // Normal
					CartaNormal cn = (CartaNormal) c; 
					puntos += cn.getNumero();
				}
			}
		}
		this.jugadores.get(idJugador).sumarPuntos(puntos);
		return puntos;
	}

	public PozoDescarte getPozoDescarte() {
		return pozoDescarte;
	}

	public ManejadorTurnos getManejadorTurnos() {
		return manejadorTurnos;
	}

	public ArrayList<Jugador> getListaJugadores() {
		return this.jugadores;
	}

	public MazoPrincipal getMazoPrincipal() {
		return this.mazoPrincipal;
	}

	public String imprimirListaJugadores() {
		String s = "";
		for (Jugador j : this.getListaJugadores())
			s = s + "Jugador " + j.getId() + " : " + j.getNombre() + "\n";
		return s;
	}

	public Jugador getJugador(int i) {
		return jugadores.get(i);
	}

	public String imprimirTablaPuntuaciones() {
		String s = "";
		if (this.jugadores.size() > 0) {
			s = s + "NOMBRE ----- PUNTUACION\n";
			for (Jugador j : jugadores)
				s = s + j.getNombre() + " ------- " + j.getPuntaje() + "pts.\n";
		} else
			s = s + "No hay jugadores...";
		return s;
	}

	// Metodos de Observable
	@Override
	public void notificar(Eventos evento) {
		for (Observador observador : this.observadores)
			observador.actualizar(evento, this);
	}

	@Override
	public void agregarObservador(Observador observador) {
		this.observadores.add(observador);

	}

}
