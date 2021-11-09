package camiones;

import com.mycompany.apppaquetes.JSON;

public class Vehiculo implements JSON {
	private String matricula;
	private int pesoMaximo;
	
	public Vehiculo(String matricula, int pesoMaximo) {
		super();
		this.setMatricula(matricula);
		this.setPesoMaximo(pesoMaximo);
	}

	@Override
	public String toJSON() {
        String[] fields = {"plateNumber", "maxWeight"};
        Object[] vals = {this.matricula, this.pesoMaximo};
        return JSON.objectBuilder(fields, vals);
	}
	
	public Vehiculo getAsVehicle() {
		return this;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public int getPesoMaximo() {
		return pesoMaximo;
	}

	public void setPesoMaximo(int pesoMaximo) {
		this.pesoMaximo = pesoMaximo;
	}

}
