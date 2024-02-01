package ar.edu.unlu.uno.vista;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.modelo.Colores;

public interface IVista {
	public void iniciar() throws Exception;

	public void comenzarJuego() throws Exception;

	public void mostrarMenuPrincipal();

	public void formularioJugador();

	public void mostrarTablaPuntuaciones();
	
	public void setControlador(Controlador controlador);
	
	public void imprimirCartel(String s);
	
	public Colores elegirNuevoColor();
}
