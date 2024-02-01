package ar.edu.unlu.uno.modelo.carta;

import ar.edu.unlu.uno.modelo.Colores;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.modelo.PozoDescarte;

public class CartaNormal extends Carta {

	private int numero; // (0-9)

	public CartaNormal(Colores color, int numero) {
		super(color);
		this.numero = numero;
	}

	@Override
	public boolean esJugadaValida(PozoDescarte pozo) {
		if (pozo.hayCartasExtra())
			return false;
		if (super.esJugadaValida(pozo))
			return true;
		if (!pozo.verTope().tieneColor())
			return false;
		if (this.esCompatible((CartaNormal) pozo.verTope())) // Ya se sabe que es una carta normal (No comodin)
			return true;
		return false;
	}

	private boolean esCompatible(CartaNormal c) {
		return (this.numero == c.getNumero() || this.getColor().equals(c.getColor())); // Comparo dos cartas normales
	}

	@Override
	public void aplicarEfecto(Mesa mesa, int idJugador) {
		mesa.getMazoPrincipal().setPuedeRobar(true);
		mesa.agregarCartasExtra(idJugador);
	}

	@Override
	public boolean esComodin() {
		return false;
	}

	public int getNumero() {
		return numero;
	}

	@Override
	public String nombre() {
		return String.valueOf(this.numero);
	}
	
	

}
