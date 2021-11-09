package camiones;

import com.mycompany.apppaquetes.WithID;
import com.mycompany.mapa.Poblacion;
import com.mycompany.mapa.Provincia;

import personas.Empleado;

public class Conductor extends Empleado implements WithID {
	
	public Conductor(String nombre, String apellido, String documento, Provincia provincia, Poblacion poblacion) {
		super(nombre, apellido, documento, provincia, poblacion);
	}

	public Conductor(String nombre, String apellido) {
		super(nombre, apellido);
	}

	@Override
	public boolean equals(int ID) {
		return this.getID() == ID;
	}

}
