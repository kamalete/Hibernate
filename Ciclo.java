package hibernate2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "Ciclo")
public class Ciclo {
	
	@Id
	@Column(name = "abreviacion", nullable = false)
	private String abreviacion;
	
	
	@Basic
	@Column(name = "nombre")
	private String nombre;

	
	public Ciclo() {
		super();
	}
	
	public Ciclo(String abreviacion, String nombre) {
		super();
		this.abreviacion = abreviacion;
		this.nombre = nombre;
	}

	public String getAbreviacion() {
		return abreviacion;
	}

	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Ciclo [abreviacion=" + abreviacion + ", nombre=" + nombre + "]";
	}

	
}
