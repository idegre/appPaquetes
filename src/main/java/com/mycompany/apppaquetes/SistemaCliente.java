/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apppaquetes;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import personas.Cliente;

/**
 *
 * @author Nacho
 */
//@Path("cliente")
public class SistemaCliente {
    private static SistemaCliente instance = null;
    private ArrayList<Cliente> clientes;

    public static SistemaCliente getInstance(MainController mainController) {
        if (instance == null) {
        	System.out.println("creating instance" + " " + instance);
            instance = new SistemaCliente();
        }
        return instance;
    }

    public SistemaCliente() {
        System.out.println("creating system");
        clientes = new ArrayList<Cliente>();
    }
    
    public Response handleRequest(UriInfo info){
        String path = info.getPath();
        switch (path) {
            case "clientes/list":
                return listarClientes();
            case "clientes/get":
                return handleGetClient(info);
        }
        return Response.status(500).build();
    }
    
    private Response handleGetClient(UriInfo info) {
		if(info.getQueryParameters().containsKey("id")) {
			return handleGetClientById(info);
		}
		String name = info.getQueryParameters().containsKey("name") ? info.getQueryParameters().get("name").get(0) : null;
		String surname = info.getQueryParameters().containsKey("surname") ? info.getQueryParameters().get("surname").get(0) : null;
		String idNumber = info.getQueryParameters().containsKey("idNumber") ? info.getQueryParameters().get("idNumber").get(0) : null;
		if((name == null) & (surname == null) & (idNumber == null)) {
			return Response.status(422).build();
		}
		ArrayList<Cliente> result = new ArrayList<Cliente>();
		System.out.println(name + " " + surname + " " + idNumber);
		for(Cliente c : this.clientes) {
			boolean clientShouldBeAdded = true;
			if(idNumber != null)if(!c.getDocumento().equals(idNumber)) {
				clientShouldBeAdded = false;
			}
			if(name != null) if(!c.getNombre().equals(name)) {
				clientShouldBeAdded = false;
			}
			if(surname != null)if(!c.getApellido().equals(surname)) {
				clientShouldBeAdded = false;
			}
			if(clientShouldBeAdded) {
				result.add(c);
			}
		}
        String[] fields = {"clients", "count"};
        Object[] vals = {result, result.size()};
        String out = JSON.objectBuilder(fields, vals);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
	}

	public Response handlePostRequest(UriInfo info, HttpServletRequest request){
        String path = info.getPath();
        switch (path) {
            case "clientes/register":
                return registrarCliente(info, request);
        }
        return Response.status(500).build();
    }
	
//    @POST
//    @Path("/registrar")
   //	pathapram needs to be in path ja
//    public Response registrarCliente(@QueryParam("nombre") String nombre, @QueryParam("apellido") String apellido){
    public Response registrarCliente(UriInfo info, HttpServletRequest request){
        Cliente newClient = new Cliente(
    		info.getQueryParameters().get("nombre").get(0),
    		info.getQueryParameters().get("apellido").get(0)
        );
    	clientes.add(newClient);
        String[] fields = {"valid", "entry"};
        Object[] values = {"true", newClient};
        String out = JSON.objectBuilder(fields, values);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
    }
//    public void buscarCliente();
//    public void obtenerCliente();
    
//    @GET
//    @Path("/list")
    public Response listarClientes(){
        String[] fields = {"length", "clients"};
        Object[] vals = {clientes.size(), clientes};
        String out = JSON.objectBuilder(fields, vals);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
    }
    
    public Cliente getClient(int ID) {
		for(Cliente c : this.clientes) {
			if(c.equals(ID)) {
				return c;
			}
			
		}
    	return null;
    }
    
    public Response handleGetClientById(UriInfo info) {
    	Cliente[] cli = {this.getClient(Integer.parseInt(info.getQueryParameters().get("id").get(0)))};
    	if(cli[0] == null) {
    		return Response.status(404).type(MediaType.APPLICATION_JSON).entity("{\"message\": \"client not found\"}").build(); 
    	}
        String[] fields = {"clients", "count"};
        Object[] vals = {cli, 1};
        String out = JSON.objectBuilder(fields, vals);
        return Response.status(200).type(MediaType.APPLICATION_JSON).entity(out).build();
    }

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}
}
