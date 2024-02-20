package ar.edu.unlu.uno;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.vista.*;
import ar.edu.unlu.uno.vista.VistaConsola.VistaConsola;

public class Unomain {

	public static void main(String[] args) throws Exception {
		Mesa modelo = new Mesa();
		//IVista vista = new VistaC();
		IVista vista = new VistaConsola();
		IVista vista2 = new VistaConsola();
		Controlador controlador = new Controlador(modelo, vista);
		Controlador controlador2 = new Controlador(modelo, vista2);
		
	}

}
