package Paquetes;

import com.mycompany.apppaquetes.JSON;

public abstract class EstadoPaquete implements JSON {
	public EstadoPaqueteEnum estado;
	
	public abstract EstadoPaquete next();
}
