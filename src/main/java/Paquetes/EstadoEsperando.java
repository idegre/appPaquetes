package Paquetes;

public class EstadoEsperando extends EstadoPaquete {

	public EstadoEsperando() {
		this.estado = EstadoPaqueteEnum.ESPERANDO;
	}

	@Override
	public String toJSON() {
		return this.estado.toString();
	}

	@Override
	public EstadoPaquete next() {
		return new EstadoEnViaje();
	}

}
