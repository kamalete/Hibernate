package hibernate2;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="Alumno")
public class Alumno {

	@Id
	@Column(name="dni")
	private String dni;
	
	@Basic
	@Column(name="nombre")
	private String nombre;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaNacimiento")
	private Date fechaNacimiento;
	
	public Alumno() {
		
	}

	public Alumno(String dni, String nombre, Date fechaNacimiento) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Alumno [dni=" + dni + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + "]";
	}
	
	
}
