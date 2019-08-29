package dominio;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String MENSAJE_PALINDROMO="los libros palíndromos solo se pueden utilizar en la biblioteca";

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	

	public Bibliotecario() {

	}

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}
	
	/**
	 * Método que permite solicitar un prestamo a un usuario
	 * @param isbn
	 * @param usuario
	 */
	public void prestar(String isbn) {

		
		throw new UnsupportedOperationException("Método pendiente por implementar");

	}
	
	/**
	 * Método para validar si el libro ya se encuentra prestado
	 * @param isbn
	 * @return
	 */
	public boolean esPrestado(String isbn){
		repositorioLibro.listar();
		
		return false;
	}
	
	/**
	 * Método que valida si el texto ingresado es un palindromo
	 * @param str
	 * @return
	 */
	public boolean esPalindromo(String str) {
	    return str.equals(new StringBuilder(str).reverse().toString());
	}

}
