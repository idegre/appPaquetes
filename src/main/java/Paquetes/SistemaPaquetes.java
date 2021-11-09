package Paquetes;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.mycompany.apppaquetes.JSON;
import com.mycompany.apppaquetes.MainController;
import com.mycompany.mapa.Poblacion;

import personas.Cliente;


public class SistemaPaquetes {
	private static SistemaPaquetes instance = null;
    private ArrayList<Pedido> pedidos;
    private MainController mainController;

    public static SistemaPaquetes getInstance(MainController mainController) {
        if (instance == null) {
        	System.out.println("creating instance" + " " + instance);
            instance = new SistemaPaquetes(mainController);
        }
        return instance;
    }
    
    public Pedido findOrder(int ID) {
		for(Pedido p : this.pedidos) {
			if(p.equals(ID)) {
				return p;
			}
			
		}
    	return null;
    }

    public SistemaPaquetes(MainController mainController) {
        System.out.println("creating system");
        setPedidos(new ArrayList<Pedido>());
        this.mainController = mainController;
    }
    
    public Response handleRequest(UriInfo info){
        String path = info.getPath();
        switch (path) {
            case "pedidos/create":
                return createNewOrder(info);
            case "pedidos/createPackage":
                return createNewPackage(info);
            case "pedidos/find":
                return findOrder(info);
            case "pedidos/deliver":
                return deliverPackage(info);
        }
        return Response.status(500).build();
    }
    
    public Response createNewOrder(UriInfo info) {
    	Cliente cli = this.mainController.getClientController().getClient(Integer.parseInt(info.getQueryParameters().get("clientId").get(0)));
    	if(cli == null) {
    		return Response.status(500).entity("client not found").build();
    	}
        Pedido newOrder = new Pedido();
    	this.pedidos.add(newOrder);
    	cli.addOrder(newOrder);
    	String[] fields = {"entry"};
        Object[] vals = {newOrder};
        String out = JSON.objectBuilder(fields, vals);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
	}
    
    public Response createNewPackage(UriInfo info) {
    	Pedido ord = this.findOrder(Integer.parseInt(info.getQueryParameters().get("orderID").get(0)));
    	if(ord == null) {
    		System.out.println("package not found");
    		return Response.status(500).entity("package not found").build();
    	}
    	Cliente cli = this.mainController.getClientController().getClient(Integer.parseInt(info.getQueryParameters().get("recipientID").get(0)));
    	if(cli == null) {
    		System.out.println("client not found");
    		return Response.status(500).entity("client not found").build();
    	}
    	Poblacion origin = null;
    	Poblacion destination = null;
    	try {
	    	origin = Poblacion.valueOf(info.getQueryParameters().get("origin").get(0));
	    	destination = Poblacion.valueOf(info.getQueryParameters().get("destination").get(0));
    	} catch (IllegalArgumentException e) {
    		return Response.status(422).entity("invalid origin or destination").build();
    	}
		Paquete newPackage = new Paquete(
			Float.parseFloat(info.getQueryParameters().get("cost").get(0)),
			Float.parseFloat(info.getQueryParameters().get("weight").get(0)),
			cli,
			destination,
			origin
		);
		HojaDeRuta packageRoute = this.mainController.getTruckController().calcularRuta(newPackage);
		newPackage.addMovement(packageRoute);
		packageRoute.toJSON();
		ord.addPaquete(newPackage);
		String[] fields = {"entry"};
		Object[] vals = {newPackage};
		String out = JSON.objectBuilder(fields, vals);
		return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
	}
    
    public Response findOrder(UriInfo info) {
		return null;
	}
    
    public Response deliverPackage(UriInfo info) {
		return null;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(ArrayList<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}
