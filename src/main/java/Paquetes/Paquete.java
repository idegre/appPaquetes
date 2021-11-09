package Paquetes;

import java.util.ArrayList;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.WithID;
import com.mycompany.mapa.Poblacion;

import personas.Cliente;

public class Paquete implements JSON{
	public Poblacion getDestino() {
		return destino;
	}

	public void setDestino(Poblacion destino) {
		this.destino = destino;
	}

	public Poblacion getPartida() {
		return partida;
	}

	public void setPartida(Poblacion partida) {
		this.partida = partida;
	}

	private float cost;
	private float weight;
	private Cliente receptor;
	private int ID;	
	private Poblacion destino;
	private Poblacion partida;
	private EstadoPaquete estado;
	private ArrayList<HojaDeRuta> movimientos;
	
	public Paquete(float cost, float weight, Cliente receptor, Poblacion destino, Poblacion partida) {
		super();
		this.cost = cost;
		this.weight = weight;
		this.receptor = receptor;
		this.setID(WithID.IDGen());
		this.destino = destino;
		this.partida = partida;
		this.movimientos = new ArrayList<HojaDeRuta>();
		this.estado = new EstadoEsperando();
	}

	public Paquete(float cost, float weight, Cliente receptor) {
		this.cost = cost;
		this.weight = weight;
		this.receptor = receptor;
        this.setID(WithID.IDGen());
	}
	
	public void addMovement(HojaDeRuta hdr) {
		System.out.println("adding movement");
		this.movimientos.add(hdr);
	}
	
	private void setID(int i) {
		this.ID = i;
	}

	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public Cliente getReceptor() {
		return receptor;
	}
	public void setReceptor(Cliente receptor) {
		this.receptor = receptor;
	}

	@Override
	public String toJSON() {
		String[] fields = {"ID", "cost", "weight", "receptor", "origin", "destination", "trip", "state"};
		Object[] values = {this.ID, this.cost, this.weight, this.receptor.getID(), this.partida, this.destino, this.movimientos, this.estado};
		return JSON.objectBuilder(fields, values);
	}

	public EstadoPaquete getEstado() {
		return estado;
	}

	public void setEstado(EstadoPaquete estado) {
		this.estado = estado;
	}
}
