/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apppaquetes;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import Paquetes.SistemaPaquetes;
import camiones.SistemaCamiones;
/**
 *
 * @author Nacho
 */
@Path("/")
public class MainController {
    private SistemaCliente clientController;
    public SistemaCliente getClientController() {
		return clientController;
	}

	public SistemaPaquetes getPackageController() {
		return packageController;
	}

	public SistemaCamiones getTruckController() {
		return truckController;
	}

	private SistemaPaquetes packageController;
    private SistemaCamiones truckController;
    
    public MainController () {
        clientController = SistemaCliente.getInstance(this);
        packageController = SistemaPaquetes.getInstance(this);
        truckController = SistemaCamiones.getInstance(this);
    }
    
    @GET
    @Path("clientes/{any : .*}")
    public Response clientHandler (@Context UriInfo info) {
        System.out.println("request found" + " " + info.getPath());
        return clientController.handleRequest(info);
    }
    
    @POST
    @Path("clientes/{any : .*}")
    public Response clientHandlerPost (@Context UriInfo info, @Context HttpServletRequest request) {
        System.out.println("request found" + " " + info.getPath());
        return clientController.handlePostRequest(info, request);
    }
    
    @POST
    @Path("pedidos/{any : .*}")
    public Response packageHandler (@Context UriInfo info) {
        System.out.println("request found" + " " + info.getPath());
        return packageController.handleRequest(info);
    }
    
    @POST
    @Path("camion/{any : .*}")
    public Response truckHandler (@Context UriInfo info) {
        System.out.println("request found" + " " + info.getPath());
        return truckController.handleRequest(info);
    }
}
