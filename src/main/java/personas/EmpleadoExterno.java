package personas;

public class EmpleadoExterno extends Empleado {
	private int tarifa;

	public EmpleadoExterno(String nombre, String apellido, int tarifa) {
		super(nombre, apellido);
		this.setTarifa(tarifa);
	}

	public int getTarifa() {
		return tarifa;
	}

	public void setTarifa(int tarifa) {
		this.tarifa = tarifa;
	}

}
