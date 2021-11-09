/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package camiones;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.MainController;
import com.mycompany.mapa.Mapa;
import com.mycompany.mapa.Poblacion;
import com.mycompany.mapa.Provincia;

import Paquetes.EstadoViaje;
import Paquetes.HojaDeRuta;
import Paquetes.Paquete;
import personas.Cliente;

/**
 *
 * @author Nacho
 */
public class SistemaCamiones {
    private ArrayList<Camion> flota;
    private ArrayList<Trailer> trailers;
    private ArrayList<Conductor> conductores;
    private ArrayList<HojaDeRuta> hojasDeRuta;
	private static SistemaCamiones instance = null;

    public static SistemaCamiones getInstance(MainController mainController) {
        if (instance == null) {
        	System.out.println("creating instance" + " " + instance);
            instance = new SistemaCamiones();
        }
        return instance;
    }
    
    public Response handleRequest(UriInfo info){
        String path = info.getPath();
        switch (path) {
	        case "camion/altaConductor":
	            return this.altaConductor(info);
	        case "camion/altaCamion":
	            return this.altaCamion(info);
	        case "camion/altaTrailer":
	        	return this.altaTrailer(info);
	        case "camion/salidaViaje":
	        	return this.salidaViaje(info);
	        case "camion/llegadaViaje":
	        	return this.llegadaViaje(info);
        }
        return Response.status(500).build();
    }
    
    public Response salidaViaje(UriInfo info){
    	int ID = Integer.parseInt(info.getQueryParameters().get("ID").get(0));
		for(HojaDeRuta hdr : hojasDeRuta) {
			if(hdr.equals(ID)) {
				hdr.startTrip();
				return Response.status(200).build(); 
			}
		}
		return Response.status(404).build();
    } 
    
    public Response llegadaViaje(UriInfo info){
    	int ID = Integer.parseInt(info.getQueryParameters().get("ID").get(0));
    	for(HojaDeRuta hdr : hojasDeRuta) {
			if(hdr.equals(ID)) {
				hdr.endTrip();
				return Response.status(200).build();
			}
		}
    	return Response.status(404).build();
    } 
    
    public SistemaCamiones(){
    	this.flota = new ArrayList<Camion>();
    	this.conductores = new ArrayList<Conductor>();
    	this.hojasDeRuta = new ArrayList<HojaDeRuta>();
    	this.trailers = new ArrayList<Trailer>();
    }
    
    public Response altaConductor(UriInfo info){
    	Poblacion poblacion = null;
    	Provincia provincia = null;
        try {
                poblacion = Poblacion.valueOf(info.getQueryParameters().get("city").get(0));
                provincia = Provincia.valueOf(info.getQueryParameters().get("state").get(0));
        } catch (IllegalArgumentException e) {
                return Response.status(422).entity("invalid origin or destination").build();
        }
        Conductor newDriver = new Conductor(
    		info.getQueryParameters().get("nombre").get(0), 
    		info.getQueryParameters().get("apellido").get(0),
    		info.getQueryParameters().get("documento").get(0),
    		provincia,
            poblacion
        );
    	conductores.add(newDriver);
        String[] fields = {"valid", "entry"};
        Object[] values = {"true", newDriver};
        String out = JSON.objectBuilder(fields, values);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
    }
    
    public Response altaCamion(UriInfo info){
        Camion newTruck = new Camion(
    		info.getQueryParameters().get("matricula").get(0), 
    		Integer.parseInt(info.getQueryParameters().get("pesoMax").get(0))
        );
    	flota.add(newTruck);
        String[] fields = {"valid", "entry"};
        Object[] values = {"true", newTruck};
        String out = JSON.objectBuilder(fields, values);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
    }
    
    public Response altaTrailer(UriInfo info){
        Trailer newTrailer = new Trailer(
    		info.getQueryParameters().get("matricula").get(0), 
    		Integer.parseInt(info.getQueryParameters().get("pesoMax").get(0))
        );
    	trailers.add(newTrailer);
        String[] fields = {"valid", "entry"};
        Object[] values = {"true", newTrailer};
        String out = JSON.objectBuilder(fields, values);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
    }

	public ArrayList<Camion> getFlota() {
		return flota;
	}

	public void setFlota(ArrayList<Camion> flota) {
		this.flota = flota;
	}

	public ArrayList<Conductor> getConductores() {
		return conductores;
	}

	public void setConductores(ArrayList<Conductor> conductores) {
		this.conductores = conductores;
	};
	
	//it should be an array, but no
	public HojaDeRuta calcularRuta(Paquete paquete) {
		//pedir a mapa una ruta
		//check for a suitable trip
		for(HojaDeRuta hdr : hojasDeRuta) {
			if(
				hdr.getPartida() == paquete.getPartida() &&
				hdr.getDestino() == paquete.getDestino() &&
				hdr.getEstado() == EstadoViaje.ESPERANDO &&
				hdr.getCamion().getPesoMaximo() <= (paquete.getWeight() + hdr.totalWeight()) &&
				hdr.getTrailer().getPesoMaximo() <= (paquete.getWeight() + hdr.totalWeight())
			) {
				hdr.addCarga(paquete);
				return hdr;
			}
		}
		System.out.println("generando nuevo viaje");
		HojaDeRuta nuevoViaje = Mapa.obtenerRuta(paquete.getPartida(), paquete.getDestino());
		//got to check truck avaliability and assign
		for(Camion cam : this.flota) {
			if(this.isTruckAvaliable(cam.getMatricula(), nuevoViaje.getFechaSalida(), nuevoViaje.getFechaLlegada())) {
				nuevoViaje.setCamion(cam);
				break;
			}
		}
		for(Trailer tr : this.trailers) {
			if(this.isTrailerAvaliable(tr.getMatricula(), nuevoViaje.getFechaSalida(), nuevoViaje.getFechaLlegada())) {
				nuevoViaje.setTrailer(tr);
				break;
			}
		}
		for(Conductor con : this.conductores) {
			if(con.getPoblacion() == nuevoViaje.getPartida()) {
				System.out.println("conductor asignado");
				nuevoViaje.setConductor(con);
				break;
			}
		}
		this.hojasDeRuta.add(nuevoViaje);
		nuevoViaje.addCarga(paquete);
		return nuevoViaje;
	}
	
	private boolean isTruckAvaliable(String matricula, Date fechaInicio, Date fechaFin) {
		System.out.println("checking truck avaliability");
		for(HojaDeRuta hdr : hojasDeRuta) {
			if(hdr.getCamion().getMatricula() == matricula) {
				if(hdr.getFechaSalida().before(fechaFin) && hdr.getFechaLlegada().after(fechaInicio)) {
					return false;
				}
			}
		}
		return true;
	}
	private boolean isTrailerAvaliable(String matricula, Date fechaInicio, Date fechaFin) {
		System.out.println("checking trailer avaliability");
		for(HojaDeRuta hdr : hojasDeRuta) {
			if(hdr.getTrailer().getMatricula() == matricula) {
				if(hdr.getFechaSalida().before(fechaFin) && hdr.getFechaLlegada().after(fechaInicio)) {
					return false;
				}
			}
		}
		return true;
	}
    
}
