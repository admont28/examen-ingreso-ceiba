package dominio;

import java.util.Calendar;
import java.util.Date;

import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;

public class Bibliotecario {

	public static final String EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE = "El libro no se encuentra disponible";

	public static final String MENSAJE_PALINDROMO = "los libros palindromos solo se pueden utilizar en la biblioteca";

	public static final String ISBN_NO_ENTREGADO = "El ISBN no fue entregado para el prestamo del libro";

	public static final String USUARIO_NO_ENVIADO = "El usuario no fue entregado para el prestamo del libro";

	public static final String LIBRO_NO_EXISTE = "El libro no existe";

	public static final int LIMITE_SUMA_ISBN = 30;

	public static final int DIAS_PRESTAMO_RESTRICCION = 15;

	private RepositorioLibro repositorioLibro;

	private RepositorioPrestamo repositorioPrestamo;

	/**
	 * 
	 * Método constructor de la clase Bibliotecario en cuestión.
	 * 
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 *
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 */
	public Bibliotecario(RepositorioLibro repositorioLibro, RepositorioPrestamo repositorioPrestamo) {
		this.repositorioLibro = repositorioLibro;
		this.repositorioPrestamo = repositorioPrestamo;

	}

	/**
	 * 
	 * Permite solicitar un prestamo de un libro por medio del isbn Valida que exista el libro Valida que no se encuentre prestado Valida si el isbn es palindromo Valida el el valor limite de los digitos del isbn Setea fecha de entrega de acuerdo al numero de digitos
	 *
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 * 
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 * @param isbn, código que identifica el libro
	 * @param nombreUsuario, usurio quien va a solicitar el prestamo del libro
	 */
	public void prestar(String isbn, String nombreUsuario) {

		Libro libroPrestado = repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn);
		Libro libroExiste = repositorioLibro.obtenerPorIsbn(isbn);
		if (isbn == null || isbn.trim().equals("")) {
			throw new PrestamoException(ISBN_NO_ENTREGADO);
		}

		if (nombreUsuario == null || nombreUsuario.trim().equals("")) {
			throw new PrestamoException(USUARIO_NO_ENVIADO);
		}
		// Validar si el libro ya está prestado, si es asï¿½ el libroPrestado
		// es
		// diferente de null
		if (libroPrestado != null) {
			throw new PrestamoException(EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE);
		}
		// Validar si el libro existe en el repositorio de libros

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
				fechaMaximaEntrega = generarFechaEntrega(DIAS_PRESTAMO_RESTRICCION, new Date());
			}
			Prestamo prestamo = new Prestamo(new Date(), repositorioLibro.obtenerPorIsbn(isbn), fechaMaximaEntrega, nombreUsuario);
			repositorioPrestamo.agregar(prestamo);
		}

	}

	/**
	 * 
	 * Permite validar si el libro ya se encuentra prestado
	 *
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 * 
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 * @param isbn, código que identifica el libro @return, verdadero si el libro ya está prestado
	 */
	public boolean esPrestado(String isbn) {
		return repositorioPrestamo.obtenerLibroPrestadoPorIsbn(isbn) != null ? true : false;
	}

	/**
	 * 
	 * Permite validar si el texto ingresado es un palindromo
	 *
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 * 
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 * @param isbn, código que identifica el libro @return, verdadero cuando el isbn es palindromo ej: 1221
	 */
	public boolean esPalindromo(String isbn) {
		return isbn.equals(new StringBuilder(isbn).reverse().toString());
	}

	/**
	 * 
	 * Permite validar si el isbn cumple con una cantidad máxima en la suma de sus digitos como restricción de fecha de entrega
	 *
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 * 
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 * @param isbn, código que identifica el libro
	 * @param limit, Valor máximo que puede sumar los digitos númericos del códgio isbn @return, verdadero si supera el valor limite
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
	 * 
	 * Genera fecha de entrega de acuerdo a una cantidad de días posterior a el día de solicitud sin contar domingos
	 *
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 * 
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 * @param dias
	 * @param fechaSolicitud
	 * @return la fecha en tipo date que se obtiene del proceso
	 */
	public Date generarFechaEntrega(int dias, Date fechaSolicitud) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaSolicitud);
		int i = 1;
		while (i < dias) {

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
