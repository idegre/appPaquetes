package personas;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.WithID;
import com.mycompany.mapa.Poblacion;
import com.mycompany.mapa.Provincia;

public class Empleado extends Persona implements JSON {
	private int ID;
	
	public Empleado(String nombre, String apellido, String documento, Provincia provincia, Poblacion poblacion) {
		super(nombre, apellido, documento, provincia, poblacion);
		this.setID(WithID.IDGen());
	}

	public Empleado(String nombre, String apellido) {
		super(nombre, apellido);
		this.setID(WithID.IDGen());
	}

	@Override
	public String toJSON() {
		String[] fields = {"ID", "personalData"};
		Object[] values = {this.ID, this.getPersonalData()};
		return JSON.objectBuilder(fields, values);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
