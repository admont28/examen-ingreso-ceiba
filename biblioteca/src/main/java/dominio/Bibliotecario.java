package dominio;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String MENSAJE_PALINDROMO="los libros palíndromos solo se pueden utilizar en la biblioteca";
	public static final String USUARIO_JEISON = "Jeison Barbosa";
	public static final int LIMITE_SUMA_USBN = 30;

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}
	
	/**
	 * Método que permite solicitar un prestamo a un usuario
	 * @param isbn
	 * @param usuario
	 */
	public void prestar(String isbn, String nombreUsuario) {
		
		Libro libroPrestado = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		// Validar si el libro ya está prestado, si es así el libroPrestado es diferente de null
		if(libroPrestado != null) {
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		// TODO: Validar si el libro existe en el repositorio de libros
		
		// Validar si el libro a prestar es palindromo, si es así, no se puede prestar el libro.
		else if (esPalindromo(isbn)){
			throw new PrestamoException(MENSAJE_PALINDROMO);
		}
		// Si el libro está disponible para prestamo y no es palíndromo, se puede proceder con el préstamo.
		else {
			// TODO: Realizar el calculo de la fecha máxima de entrega.
			Date fechaMaximaEntrega = null;
			if(esSumaISBNMayorA(isbn, LIMITE_SUMA_USBN)) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				//calendar.add(Calendar., amount);
				fechaMaximaEntrega = new Date();
			}
			Prestamo prestamo = new Prestamo(new Date(), repositorioLibro.obtenerPorIsbn(isbn), fechaMaximaEntrega, USUARIO_JEISON);
			repositorioPrestamo.agregar(prestamo);
		}
		//throw new UnsupportedOperationException("Método pendiente por implementar");

	}
	
	/**
	 * Método para validar si el libro ya se encuentra prestado
	 * @param isbn
	 * @return
	 */
	public boolean esPrestado(String isbn){
		return repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) != null ? true : false;
	}
	
	/**
	 * Método que valida si el texto ingresado es un palindromo
	 * @param str
	 * @return
	 */
	public boolean esPalindromo(String str) {
	    return str.equals(new StringBuilder(str).reverse().toString());
	}
	
	public boolean esSumaISBNMayorA(String isbn, int limite){
		char [] isbnChar = isbn.toCharArray();
		int suma = 0;
		for (int i = 0; i < isbnChar.length; i++) {
			int caracter = isbnChar[i];
			if(Character.isDigit(caracter)) {
				suma += caracter;
			}
			if(suma > limite) {
				return true;
			}
		}
		return false;
	}

}
