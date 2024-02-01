package ar.edu.unlu.uno.modelo;

public enum Colores {
	AZUL("AZUL"), ROJO("ROJO"), AMARILLO("AMARILLO"), VERDE("VERDE"), SIN_COLOR("");

	private final String valor;

	Colores(String v) {
		this.valor = v;
	}

	public String getValor() {
		return valor;
	}
}
