package ar.edu.unlu.uno;

import ar.edu.unlu.uno.controlador.Controlador;
import ar.edu.unlu.uno.modelo.Mesa;
import ar.edu.unlu.uno.vista.*;

public class Unomain {

	public static void main(String[] args) throws Exception {
		Mesa modelo = new Mesa();
		VistaC vista = new VistaC();
		Controlador controlador = new Controlador(modelo, vista);

	}

}
