package camiones;

import java.util.Date;

import com.mycompany.apppaquetes.JSON;

public class Trailer extends Vehiculo implements JSON {

private Date fechaDeAlta;
	
	@Override
	public String toJSON() {
		String[] fields = {"numberPlate", "maxWeight", "date"};
        Object[] vals = {this.getMatricula(), this.getPesoMaximo(), this.fechaDeAlta};
        return JSON.objectBuilder(fields, vals);
	}

	public Trailer(String matricula, int pesoMaximo) {
        super(matricula, pesoMaximo);
        this.fechaDeAlta = new Date();
	}

	public Date getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(Date fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}

}
