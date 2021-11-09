/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personas;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.mapa.Poblacion;
import com.mycompany.mapa.Provincia;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nacho
 */
public abstract class Persona implements JSON {
    String nombre;
    String apellido;
    String documento;
    String direccion;
    Date nacimiento;
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public Poblacion getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	Provincia provincia;
    Poblacion poblacion;
    String telefono;
    
    public Persona(String nombre, String apellido, String documento, String direccion, Date nacimiento,
            Provincia provincia, Poblacion poblacion, String telefono) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.direccion = direccion;
        this.nacimiento = nacimiento;
        this.provincia = provincia;
        this.poblacion = poblacion;
        this.telefono = telefono;
	}
    
    public Persona(String nombre, String apellido, String documento, Provincia provincia, Poblacion poblacion) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.provincia = provincia;
        this.poblacion = poblacion;
	}
    
	public Persona(String nombre, String apellido) {
            this.nombre = nombre;
            this.apellido = apellido;
        };

        public Map<String, String> getPersonalData() {
            Map<String, String> data = new HashMap<String, String>();
            data.put("name", this.nombre);
            data.put("surname", this.apellido);
            data.put("idNumber", this.documento);
            if(this.nacimiento != null){
                data.put("dateOfBirth", this.nacimiento.toString());
            }
            if(this.provincia != null){
                data.put("state", this.provincia.toString());
            }
            if(this.poblacion != null){
                data.put("city", this.poblacion.toString());
            }
            data.put("phone", this.telefono);
            data.put("address", this.direccion);
            return data;
        }

	@Override
	public String toJSON() {
        String[] fields = {"name", "surname"};
        Object[] vals = {this.nombre, this.apellido};
        return JSON.objectBuilder(fields, vals);
	}
}
