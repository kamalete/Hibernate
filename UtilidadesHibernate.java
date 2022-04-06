package hibernate2;

import org.hibernate.Session;
import org.hibernate.resource.transaction.spi.TransactionStatus;

public class UtilidadesHibernate {

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
		modulo = sesion.createQuery("FROM Modulo where abreviacion like '" + abreviacion + "' and id_ciclo like '" + ciclo.getAbreviacion() + "'", Modulo.class).uniqueResult();
		return modulo;
	}
	
	public Nota getNota(String dni, String abreviacion, String curso) {
		Nota nota = null;
		Session sesion = HibernateSession.getSession();
		nota = sesion.createQuery("FROM Nota where alumno_id like '" + dni + "' and modulo_id like '" + abreviacion + "' and curso like '" + curso + "'", Nota.class).uniqueResult();
		return nota;
	}
	
	public boolean saveOrUpdate(Alumno alumno) {
		Session sesion = HibernateSession.getSession();
		Alumno alumnoRecuperado = this.getAlumno(alumno.getDni());
		sesion.beginTransaction();
		if (alumnoRecuperado != null) {
			sesion.update(alumnoRecuperado);
		} else {
			sesion.save(alumnoRecuperado);
		}
		sesion.getTransaction().commit();
		return sesion.getTransaction().getStatus().equals(TransactionStatus.COMMITTED);
	}
	
	public boolean saveOrUpdate(Modulo modulo) {
		Session sesion = HibernateSession.getSession();
		Ciclo cicloRecuperado = this.getCiclo(modulo.getCiclo().getAbreviacion());
		Modulo moduloRecuperado = this.getModulo(modulo.getAbreviacion(), cicloRecuperado);
		sesion.beginTransaction();
		if (cicloRecuperado != null) {
			if (moduloRecuperado != null) {
				sesion.update(moduloRecuperado);
			}
		} else {
			sesion.save(cicloRecuperado);
			sesion.save(moduloRecuperado);
		}
		sesion.getTransaction().commit();
		
		return sesion.getTransaction().getStatus().equals(TransactionStatus.COMMITTED);
	}
	
	public boolean saveOrUpdate(Ciclo ciclo) {
		Session sesion = HibernateSession.getSession();
		Ciclo cicloRecuperado = this.getCiclo(ciclo.getAbreviacion());
		sesion.beginTransaction();
		if(cicloRecuperado != null) {
			sesion.update(cicloRecuperado);
		} else {
			sesion.save(cicloRecuperado);
		}
		sesion.getTransaction().commit();
		return sesion.getTransaction().getStatus().equals(TransactionStatus.COMMITTED);
		
	}
	
	public boolean matricularAlumno(String dni, String abreviacion, String curso, Ciclo ciclo) {
		Session sesion = HibernateSession.getSession();
		Alumno alumno = this.getAlumno(dni);
		Modulo modulo = this.getModulo(abreviacion, ciclo);
		Nota nota = new Nota(alumno, modulo, null, curso);
		sesion.beginTransaction();
		if((alumno != null) && (modulo != null) && ("".equals(curso)) && (curso != null)) {
			sesion.save(nota);
		}
		sesion.getTransaction().commit();
		return sesion.getTransaction().getStatus().equals(TransactionStatus.COMMITTED);
	}
	
	public boolean desmatricularAlumno(String dni, String abreviacion, String curso) {
		Session sesion = HibernateSession.getSession();
		Nota nota = this.getNota(dni, abreviacion, curso);
		sesion.beginTransaction();
		if(nota != null) {
			sesion.delete(nota);
		}
		sesion.getTransaction().commit();
		return sesion.getTransaction().getStatus().equals(TransactionStatus.COMMITTED);
	}
	
	public boolean setNota(String dni, String abreviacion, String curso, Integer valorNota) {
		Session sesion = HibernateSession.getSession();
		Nota nota = this.getNota(dni, abreviacion, curso);
		sesion.beginTransaction();
		if (nota != null) {
			nota.setNota(valorNota);
			sesion.update(nota);
		}
		sesion.getTransaction().commit();
		return sesion.getTransaction().getStatus().equals(TransactionStatus.COMMITTED);
	}
}
