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
	 * Método que retorna el valor del atributo isbn
	 * 
	 * @author Jeison Julián Barbosa Serna<br>
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
	 * Método que retorna el valor del atributo titulo
	 * 
	 * @author Jeison Julián Barbosa Serna<br>
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
	 * Método que retorna el valor del atributo anio
	 * 
	 * @author Jeison Julián Barbosa Serna<br>
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
