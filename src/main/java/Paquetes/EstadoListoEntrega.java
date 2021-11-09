package Paquetes;

public class EstadoListoEntrega extends EstadoPaquete {

	public EstadoListoEntrega() {
		this.estado = EstadoPaqueteEnum.LISTO_PARA_ENTREGAR;
	}

	@Override
	public String toJSON() {
		return this.estado.toString();
	}

	@Override
	public EstadoPaquete next() {
		return this;
	}
}
