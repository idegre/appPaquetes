package Paquetes;

public class EstadoEnViaje extends EstadoPaquete {
	
	public EstadoEnViaje() {
		this.estado = EstadoPaqueteEnum.EN_VIAJE;
	}

	@Override
	public String toJSON() {
		return this.estado.toString();
	}

	@Override
	public EstadoPaquete next() {
		return new EstadoListoEntrega();
	}

}
