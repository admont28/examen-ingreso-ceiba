package dominio;

import java.util.Date;

public class Prestamo {

	private Date fechaSolicitud;
	private Libro libro;
	private Date fechaEntregaMaxima;
	private String nombreUsuario;

	public Prestamo(Libro libro) {
		this.fechaSolicitud = new Date();
		this.libro = libro;
	}
	
	public Prestamo(Date fechaSolicitud, Libro libro, Date fechaEntregaMaxima, String nombreUsuario) {
		this.fechaSolicitud = fechaSolicitud;
		this.libro = libro;
		this.fechaEntregaMaxima = fechaEntregaMaxima;
		this.nombreUsuario = nombreUsuario;
	}

	
	/**
	 * M�todo que retorna el valor del atributo fechaSolicitud
	 * 
	 * @author	Jeison Juli�n Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 *
	 * @date	31/08/2019
	 * @version 1.0
	 * 
	 * @return 	fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	
	/**
	 * M�todo que retorna el valor del atributo libro
	 * 
	 * @author	Jeison Juli�n Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 *
	 * @date	31/08/2019
	 * @version 1.0
	 * 
	 * @return 	libro
	 */
	public Libro getLibro() {
		return libro;
	}

	
	/**
	 * M�todo que retorna el valor del atributo fechaEntregaMaxima
	 * 
	 * @author	Jeison Juli�n Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 *
	 * @date	31/08/2019
	 * @version 1.0
	 * 
	 * @return 	fechaEntregaMaxima
	 */
	public Date getFechaEntregaMaxima() {
		return fechaEntregaMaxima;
	}

	
	/**
	 * M�todo que retorna el valor del atributo nombreUsuario
	 * 
	 * @author	Jeison Juli�n Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 *
	 * @date	31/08/2019
	 * @version 1.0
	 * 
	 * @return 	nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	

	

}
