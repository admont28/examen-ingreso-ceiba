package dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";
	public static final String MENSAJE_PALINDROMO = "los libros palï¿½ndromos solo se pueden utilizar en la biblioteca";
	public static final String LIBRO_NO_EXISTE = "El libro no existe";
	public static final int LIMITE_SUMA_ISBN = 30;
	public static final int DIAS_PRESTAMO_RESTRICCION = 15;

	private RepositorioLibro repositorioLibro;
	private RepositorioPrestamo repositorioPrestamo;

	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	/**
	 * Permite solicitar un prestamo de un libro por medio del isbn
	 * Valida que exista el libro
	 * Valida que no se encuentre prestado
	 * Valida si el isbn es palindromo
	 * Valida el el valor limite de los digitos del isbn 
	 * Setea fecha de entrega de acuerdo al numero de digitos
	 * @param isbn, código que identifica el libro
	 * @param usuario, usurio quien va a solicitar el prestamo del libro
	 */
	public void prestar(String isbn, String nombreUsuario) {

		Libro libroPrestado = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		Libro libroExiste = repositorioLibro.obtenerPorIsbn(isbn);

		// Validar si el libro ya está prestado, si es asï¿½ el libroPrestado
		// es
		// diferente de null
		if (libroPrestado != null) {
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		// TODO: Validar si el libro existe en el repositorio de libros

		else if (libroExiste == null) {
			throw new PrestamoException(LIBRO_NO_EXISTE);
		}

		// Validar si el libro a prestar es palindromo, si es asï¿½, no se puede
		// prestar el libro.
		else if (esPalindromo(isbn)) {
			throw new PrestamoException(MENSAJE_PALINDROMO);
		}
		// Si el libro estï¿½ disponible para prestamo y no es palï¿½ndromo, se
		// puede proceder con el prï¿½stamo.
		else {
			// Realiza el calculo de la fecha mï¿½xima de entrega.
			Date fechaMaximaEntrega = null;
			if (esSumaISBNMayorA(isbn, LIMITE_SUMA_ISBN)) {

				fechaMaximaEntrega = generarFechaEntrega();

			}
			Prestamo prestamo = new Prestamo(new Date(), repositorioLibro.obtenerPorIsbn(isbn), fechaMaximaEntrega,
					nombreUsuario);
			repositorioPrestamo.agregar(prestamo);
		}

	}

	/**
	 * Permite validar si el libro ya se encuentra prestado
	 * 
	 * @param isbn, código que identifica el libro
	 * @return boolean
	 */
	public boolean esPrestado(String isbn) {
		return repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) != null ? true : false;
	}

	/**
	 * Permite validar si el texto ingresado es un palindromo
	 * 
	 * @param isbn
	 * @return verdadero cuando el isbn es palindromo ej: 1221
	 */
	public boolean esPalindromo(String isbn) {
		return isbn.equals(new StringBuilder(isbn).reverse().toString());
	}

	/**
	 * Mï¿½todo que valida si el isbn cumple con la cantidad numerica mï¿½xima
	 * como restricciï¿½n de fecha de entrega
	 * 
	 * @param isbn, código que identifica el libro
	 * @param limit
	 * @return boolean
	 */
	public boolean esSumaISBNMayorA(String isbn, int limit) {
		int sum = 0;
		for (int i = 0; i < isbn.length(); i++) {
			char caracter = isbn.charAt(i);
			if (Character.isDigit(caracter)) {
				int b = Integer.parseInt(String.valueOf(caracter));
				sum = sum + b;

				if (sum > limit) {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * Valida que la fecha de entrega sea un numero de días posterior a el día
	 * de solicitud sin contar domingos
	 * 
	 * @return
	 */
	public Date generarFechaEntrega() {

		Calendar cal = Calendar.getInstance();
		int i = 1;
		while (i < DIAS_PRESTAMO_RESTRICCION) {

			if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				i++;
				cal.get(Calendar.DAY_OF_WEEK);
			}
			cal.add(Calendar.DATE, 1);

		}
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			cal.add(Calendar.DATE, 1);
		}

		return cal.getTime();
	}
}
