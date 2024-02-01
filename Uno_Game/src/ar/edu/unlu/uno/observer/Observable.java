package ar.edu.unlu.uno.observer;

import ar.edu.unlu.uno.modelo.Eventos;

public interface Observable {
	public void notificar(Eventos evento);

	public void agregarObservador(Observador observador);
}

