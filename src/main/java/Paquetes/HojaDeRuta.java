package Paquetes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.WithID;
import com.mycompany.mapa.Poblacion;

import camiones.Camion;
import camiones.Conductor;
import camiones.Trailer;

public class HojaDeRuta implements JSON, WithID {
	private int ID;
	private Date fechaSalida;
	private Date fechaLlegada;
	private EstadoViaje estado;
	private Poblacion partida;
	private Poblacion destino;
	private ArrayList<Paquete> carga;
	private Camion camion;
	private Trailer trailer;
	private Conductor conductor;

	public HojaDeRuta(Date fechaSalida, Date fechaLlegada, Poblacion partida, Poblacion destino) {
		super();
		this.ID = WithID.IDGen();
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.estado = EstadoViaje.ESPERANDO;
		this.partida = partida;
		this.destino = destino;
		this.carga = new ArrayList<Paquete>();
	}
	
	public void startTrip() {
		System.out.println("viaje saliendo");
		this.estado = EstadoViaje.EN_VIAJE;
		this.carga.forEach(p -> {
			p.setEstado(p.getEstado().next());
		});
	}
	
	public void endTrip() {
		System.out.println("viaje llegando");
		this.estado = EstadoViaje.EN_DESTINO;
		this.carga.forEach(p -> {
			p.setEstado(p.getEstado().next());
		});
	}
	
	public float totalWeight() {
		float total = 0;
		Iterator<Paquete> it = this.carga.iterator();
		while (it.hasNext()) {
			total = total + it.next().getWeight();
		}
		return total;
	}

	@Override
	public boolean equals(int ID) {
		return this.ID == ID;
	}

	@Override
	public String toJSON() {
		String[] fields = {"ID", "departureDate", "arrivalDate", "state", "from", "to", "totalWeight", "packageCount", "truck", "trailer", "driver"};
		Object[] values = {this.ID, this.fechaSalida, this.fechaLlegada, this.estado,this.partida,this.destino, this.totalWeight(), this.carga.size(), this.camion, this.trailer, this.conductor};
		return JSON.objectBuilder(fields, values);
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public EstadoViaje getEstado() {
		return estado;
	}

	public void setEstado(EstadoViaje estado) {
		this.estado = estado;
	}

	public Poblacion getPartida() {
		return partida;
	}

	public void setPartida(Poblacion partida) {
		this.partida = partida;
	}

	public Poblacion getDestino() {
		return destino;
	}

	public void setDestino(Poblacion destino) {
		this.destino = destino;
	}

	public ArrayList<Paquete> getCarga() {
		return carga;
	}

	public void addCarga(Paquete carga) {
		this.carga.add(carga);
	}

	public Camion getCamion() {
		return camion;
	}

	public void setCamion(Camion camion) {
		this.camion = camion;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public Trailer getTrailer() {
		return trailer;
	}

	public void setTrailer(Trailer trailer) {
		this.trailer = trailer;
	}

}
