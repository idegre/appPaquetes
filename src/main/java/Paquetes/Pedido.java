package Paquetes;

import java.util.ArrayList;
import java.util.Date;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.WithID;

public class Pedido implements JSON, WithID {
	private ArrayList<Paquete> paquetes;
	private int ID;
	private Date fecha;
	
	public Pedido() {
		paquetes = new ArrayList<Paquete>();
        this.setID(WithID.IDGen());
        this.setFecha(new Date());
	}

	public ArrayList<Paquete> getPaquetes() {
		return paquetes;
	}

	public void addPaquete(Paquete paquete) {
		this.paquetes.add(paquete);
	}

	@Override
	public String toJSON() {
		String[] fields = {"ID", "paquetes", "fecha"};
		Object[] values = {this.ID, this.paquetes, this.fecha};
		return JSON.objectBuilder(fields, values);
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public boolean equals(int ID) {
		return this.ID == ID;
	}
}
