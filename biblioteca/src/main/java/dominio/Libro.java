package dominio;

public class Libro {

	private String isbn;

	private String titulo;

	private int anio;

	public Libro(String isbn, String titulo, int anio) {

		this.isbn = isbn;
		this.titulo = titulo;
		this.anio = anio;
	}

	/**
	 * M�todo que retorna el valor del atributo isbn
	 * 
	 * @author Jeison Juli�n Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 *
	 * @date 31/08/2019
	 * @version 1.0
	 * 
	 * @return isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * M�todo que retorna el valor del atributo titulo
	 * 
	 * @author Jeison Juli�n Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 *
	 * @date 31/08/2019
	 * @version 1.0
	 * 
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * M�todo que retorna el valor del atributo anio
	 * 
	 * @author Jeison Juli�n Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 *
	 * @date 31/08/2019
	 * @version 1.0
	 * 
	 * @return anio
	 */
	public int getAnio() {
		return anio;
	}

}
