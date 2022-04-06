package hibernate2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

public class clasesexamen {

	public Alumno getAlumno(String dni) {
		Session sesion = HibernateSession.getSession();
		Alumno alumno = sesion.find(Alumno.class, dni);
		return alumno;
	}

	public Ciclo getCiclo(String abreviacion) {
		Session sesion = HibernateSession.getSession();
		Ciclo ciclo = sesion.find(Ciclo.class, abreviacion);
		return ciclo;
	}

	public Modulo getModulo(String abreviacion, Ciclo ciclo) {
		Modulo modulo = null;
		Session sesion = HibernateSession.getSession();
		modulo = sesion.createQuery("FROM Modulo where abreviacion like '" + abreviacion + "' and id_ciclo like '"
				+ ciclo.getAbreviacion() + "'", Modulo.class).uniqueResult();
		return modulo;
	}

	public Nota getNota(String dni, Modulo modulo, String curso) {
		Session sesion = HibernateSession.getSession();
		Modulo moduloRec = this.getModulo(modulo.getAbreviacion(), modulo.getCiclo());
		Nota nota = null;

		return nota;
	}

	public void saveorupdate(Alumno alumno) {
		Session sesion = HibernateSession.getSession();
		sesion.beginTransaction();
		// si el id es autogenerado hay q hacer un setID dentro del if
		Alumno alumnoRec = this.getAlumno(alumno.getDni());
		if (alumnoRec != null) {
			sesion.update(alumno);
		} else {
			sesion.save(alumno);

		}

		sesion.getTransaction().commit();
	}

	public void saveorupdate(Ciclo ciclo) {
		Ciclo cicloRec = this.getCiclo(ciclo.getAbreviacion());
		Session sesion = HibernateSession.getSession();
		sesion.beginTransaction();
		if (cicloRec != null) {
//			lo hicieron con id autogenerado
//			ciclo.setId(cicloRec.getId);
			sesion.update(ciclo);
		} else {
			sesion.save(ciclo);
		}
		sesion.getTransaction().commit();
	}

	public void saveorupdate(Modulo modulo) {
		Modulo moduloRec = this.getModulo(modulo.getAbreviacion(), modulo.getCiclo());

		Session sesion = HibernateSession.getSession();
		sesion.beginTransaction();
		if (moduloRec != null) {
//			lo hicieron con id autogenerado
//			modulo.setId(moduloRec.getId);
			sesion.update(modulo);
		} else {
			if (this.getCiclo(modulo.getCiclo().getAbreviacion()) != null) {
				sesion.save(modulo);
			} else {
				System.out.println("El ciclo aun no existe. Crealo primero");
			}
		}
		sesion.getTransaction().commit();
	}

	public void matricularAlumno(Nota nota) {

	}

	public void matricularAlumno(Alumno alumno, Modulo modulo, String curso) {
		Alumno alumnoRec = this.getAlumno(alumno.getDni());
		Modulo moduloRec = this.getModulo(modulo.getAbreviacion(), modulo.getCiclo());

		if (alumnoRec != null && moduloRec != null) {

			if (this.getNota(alumno.getDni(), modulo, curso) != null) {
				Session sesion = HibernateSession.getSession();
				sesion.beginTransaction();
				sesion.save(new Nota(alumno, modulo, null, curso));
				sesion.getTransaction().commit();
			} else {
				System.out.println("Ya está matriculado");
			}
		}
	}

	public void desmatricular(Alumno alumno, Modulo modulo, String curso) {
		Nota notaRec = this.getNota(curso, modulo, curso);
		if (notaRec != null) {
			Session sesion = HibernateSession.getSession();
			sesion.beginTransaction();
			sesion.delete(notaRec);
			sesion.getTransaction().commit();
		} else {
			// Realmente por el MVC no se puede hacer prints, es una forma cutre de
			// representar los errores
			System.out.println("No existe la matricula");
		}
	}

	public void ponerNota(Alumno alumn, Modulo modulo, String curso, Integer puntuacion) {
		Nota notaRec = this.getNota(curso, modulo, curso);
		if (notaRec != null) {
			Session sesion = HibernateSession.getSession();
			notaRec.setNota(puntuacion);
			sesion.beginTransaction();
			sesion.update(notaRec);
			sesion.getTransaction().commit();
		} else {
			// Realmente por el MVC no se puede hacer prints, es una forma cutre de
			// representar los errores
			System.out.println("No existe la matricula");
		}
	}

	public void aaa() {
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		// args es de String[] args
//		String input = args.length == 0 ? "21/06/1995" : args[0];
		Date t = null;
		try {
//			t = ft.parse(input);
			System.out.println(t);
		} catch (Exception e) {
			// Realmente es ParseException
			e.printStackTrace();
		}
	}

	public List<Alumno> listaAlumno() {
		Session sesion = HibernateSession.getSession();
		return sesion.createQuery("FROM Alumno", Alumno.class).list();
	}

	public List<Modulo> listaModulo() {
		Session sesion = HibernateSession.getSession();
		return sesion.createQuery("FROM Modulo", Modulo.class).list();
	}

	public List<Ciclo> listaCiclo() {
		Session sesion = HibernateSession.getSession();
		return sesion.createQuery("FROM Ciclo", Ciclo.class).list();
	}

	public List<Modulo> listaModulosAlumnoCurso(String dni, String curso) {
		List<Modulo> modulos = new ArrayList<>();
		Session sesion = HibernateSession.getSession();
		List<Nota> notas = sesion
				.createQuery("FROM Nota WHERE curso LIKE '" + curso + "' AND alumno_id LIKE '" + dni + "'", Nota.class)
				.list();
		for (Nota n : notas) {
			modulos.add(n.getModulo());
		}
		return modulos;
	}

	public List<Nota> listaNotasAlumno(String dni) {
		Session sesion = HibernateSession.getSession();
		List<Nota> notas = sesion.createQuery("FROM Nota WHERE alumno_id LIKE '" + dni + "'", Nota.class).list();
		notas.sort(new Comparator<Nota>() {

			@Override
			public int compare(Nota nota1, Nota nota2) {
				Ciclo ciclo1 = nota1.getModulo().getCiclo();
				Ciclo ciclo2 = nota2.getModulo().getCiclo();
				if (ciclo1.getAbreviacion().compareTo(ciclo2.getAbreviacion()) > 0) {

					return 1;
				} else if (ciclo1.getAbreviacion().compareTo(ciclo2.getAbreviacion()) < 0) {
					return -1;
				} else {
					return 0;
				}
			}

		});
		return notas;
	}

	public List<Alumno> listarAlumnosCurso(String curso) {
		Session sesion = HibernateSession.getSession();
		List<Alumno> alumnos = new ArrayList<>();
		List<Nota> notas = sesion.createQuery("FROM Nota WHERE curso LIKE '" + curso + "'", Nota.class).list();
		return alumnos;
	}

}
