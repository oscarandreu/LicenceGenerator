package log;

import java.io.Serializable;

public class Identificador implements Serializable{

	private static final long serialVersionUID = 1L;
	private String numSerie;
	private String remesa;
	
	public String getNumSerie() {
		return numSerie;
	}

	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}

	public String getRemesa() {
		return remesa;
	}

	public void setRemesa(String remesa) {
		this.remesa = remesa;
	}

	public Identificador() {}



}
