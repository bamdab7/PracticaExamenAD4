
public class Departamentos {

	protected int codDep;
	protected String nombre;
	
	
	public Departamentos() {
		super();
	}
	public Departamentos(int codigo, String nombre) {
		super();
		this.codDep = codigo;
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codDep;
	}
	public void setCodigo(int codigo) {
		this.codDep = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	
}
