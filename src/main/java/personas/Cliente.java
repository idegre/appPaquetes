/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personas;

import java.util.ArrayList;
import java.util.Date;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.WithID;
import com.mycompany.mapa.Poblacion;
import com.mycompany.mapa.Provincia;

import Paquetes.Pedido;


/**
 *
 * @author Nacho
 */
public class Cliente extends Persona implements JSON, WithID {
    private int ID;
    private ArrayList<Pedido> pedidos;
	
    public Cliente(String nombre, String apellido) {
        super(nombre, apellido);
        this.setID(WithID.IDGen());
        this.pedidos = new ArrayList<Pedido>();
    }
    
    public Cliente(String nombre, String apellido, String documento, String direccion, Date nacimiento,
            Provincia provincia, Poblacion poblacion, String telefono) {
    	super(nombre,apellido,documento,direccion,nacimiento,provincia,poblacion,telefono);
    	this.setID(WithID.IDGen());
        this.pedidos = new ArrayList<Pedido>();
    }
    
    @Override
    public String toJSON() {
        String[] fields = {"ID", "personalData", "orders"};
        Object[] vals = {ID, this.getPersonalData(), this.pedidos};
        return JSON.objectBuilder(fields, vals);
    }
    
    public boolean addOrder(Pedido pedido) {
    	this.pedidos.add(pedido);
    	return true;
    }

    public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public int getID() {
    	return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

	@Override
	public boolean equals(int ID) {
		return this.ID == ID;
	}
}
