package hibernate2;

import javax.persistence.*;

@Entity
@Table(name = "Modulo")
public class Modulo {
	@Id
	@Column(name = "abreviacion", nullable = false)
	private String abreviacion;

	@Basic
	@Column(name = "nombre")
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "id_ciclo", referencedColumnName = "abreviacion", nullable = false)
	private Ciclo ciclo;
	
	public Modulo() {
		super();
	}

	public Modulo(String abreviacion, String nombre, Ciclo ciclo) {
		super();
		this.abreviacion = abreviacion;
		this.nombre = nombre;
		this.ciclo = ciclo;
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

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	@Override
	public String toString() {
		return "Modulo [abreviacion=" + abreviacion + ", nombre=" + nombre + ", ciclo=" + ciclo + "]";
	}
	
	
}
