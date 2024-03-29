package ar.edu.unlu.uno.modelo.carta;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Eventos;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaRobaDos extends Carta {

	public CartaRobaDos(Colores color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) throws Exception {
		mesa.getPozoDescarte().agregarCartasExtra(2);
		mesa.getMazoPrincipal().setPuedeRobar(false); //si hay cartas extras acumuladas el siguiente turno no puedo robar del mazo
	}

	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		return super.esJugadaValida(pozo);
	}

	@Override
	public String nombre() {
		return "ROBA 2";
	}
	
}
