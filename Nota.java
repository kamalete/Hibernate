package hibernate2;

import javax.persistence.*;
@Entity
@Table(name = "Nota")
public class Nota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "alumno_id", referencedColumnName = "dni")
	private Alumno alumno;
	
	@ManyToOne
	@JoinColumn(name = "modulo_id", referencedColumnName = "abreviacion")
	private Modulo modulo;
	
	@Basic
	@Column(name="nota")
	private Integer nota;
	
	@Basic
	@Column(name="curso")
	private String curso;
	
	public Nota() {
		super();
	}

	public Nota(Alumno alumno, Modulo modulo, Integer nota, String curso) {
		super();
		this.alumno = alumno;
		this.modulo = modulo;
		this.nota = nota;
		this.curso = curso;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Nota [id=" + id + ", alumno=" + alumno + ", modulo=" + modulo + ", nota=" + nota + ", curso=" + curso + "]";
	}
	
	
}
