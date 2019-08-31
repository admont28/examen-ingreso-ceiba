package dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest {

	private static final String USUARIO_JEISON = "Jeison Barbosa";

	private static final String ISBN_PALINDROMO = "RECONOCER";

	private static final String ISBN_MAYOR_LIMITE = "7Y8U9R889";

	private static final String ISBN_MENOR_LIMITE = "77AA";

	private static final int ISBN_LIMITE = 40;

	private static final int DIAS_PRESTAMO = 15;

	/**
	 * 
	 * Método que permite testear que el libro se encuentra prestado
	 *
	 * @author Jeison Julián Barbosa Serna<br>
	 *         Email: jjbarser@gmail.com<br>
	 * 
	 * @date 31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void esPrestadoTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());

		// assert
		assertTrue(esPrestado);
	}
	
	/**
	 * 
	 * Método que permite testear que el libro no esté prestado
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void libroNoPrestadoTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn())).thenReturn(null);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esPrestado = bibliotecario.esPrestado(libro.getIsbn());

		// assert
		assertFalse(esPrestado);
	}
	
	/**
	 * 
	 * Método que permite testear que lance la excepción si el libro no existe
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void libroNoExisteTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(null);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar(libro.getIsbn(), USUARIO_JEISON);
			fail();

		} catch (PrestamoException e) {
			// assert
			assertEquals(Bibliotecario.LIBRO_NO_EXISTE, e.getMessage());
		}
	}
	
	/**
	 * 
	 * Método que permite testear que lance la excepción si el isbn es palindromo
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void esPalindromoTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.conIsbn(ISBN_PALINDROMO).build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar(ISBN_PALINDROMO, USUARIO_JEISON);
			fail();

		} catch (PrestamoException e) {
			// assert
			assertEquals(Bibliotecario.MENSAJE_PALINDROMO, e.getMessage());
		}
	}
	
	/**
	 * 
	 * Método que permite testear el conteo de los digitos númericos sea mayor o menor a el limite
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void limiteValorIsbnTest() {

		// arrange

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esMayor = bibliotecario.esSumaISBNMayorA(ISBN_MAYOR_LIMITE, ISBN_LIMITE);
		boolean esMenor = bibliotecario.esSumaISBNMayorA(ISBN_MENOR_LIMITE, ISBN_LIMITE);

		// assert
		Assert.assertTrue(esMayor);
		Assert.assertFalse(esMenor);

	}
	
	/**
	 * 
	 * Método que permite testear que la fecha de entrega sea unos días posterior a la solicitud
	 * de acuerdo a las reglas de negocio, no se cuentan los días domingo y si la fecha de entrega cae
	 * un domingo deberá posponerse al siguiente día habil.
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void fechaEntregaMaximaTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		Libro libro = libroTestDataBuilder.conIsbn(ISBN_MAYOR_LIMITE).build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);
		Date fechaSolicitud;
		Date fechaEsperada;
		Date fechaEntrega;
		try {
			fechaSolicitud = new SimpleDateFormat("dd/MM/yyyy").parse("24/05/2017");
			fechaEsperada = new SimpleDateFormat("dd/MM/yyyy").parse("09/06/2017");

			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			// act
			fechaEntrega = bibliotecario.generarFechaEntrega(DIAS_PRESTAMO, fechaSolicitud);
			// assert
			Assert.assertEquals(fechaEsperada, fechaEntrega);
			// ---------------------------------------------------
			fechaSolicitud = new SimpleDateFormat("dd/MM/yyyy").parse("26/05/2017");
			fechaEsperada = new SimpleDateFormat("dd/MM/yyyy").parse("12/06/2017");
			fechaEntrega = bibliotecario.generarFechaEntrega(DIAS_PRESTAMO, fechaSolicitud);
			Assert.assertEquals(fechaEsperada, fechaEntrega);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}
	
	/**
	 * 
	 * Método que permite testear si el isbn es null
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void IsbnNoEntregadoTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar(null, USUARIO_JEISON);
			fail();

		} catch (PrestamoException e) {
			// assert
			assertEquals(Bibliotecario.ISBN_NO_ENTREGADO, e.getMessage());
		}
	}
	
	/**
	 * 
	 * Método que permite testear si el isbn es vacío
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void IsbnVacioTest() {

		// arrange

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar("", USUARIO_JEISON);
			fail();

		} catch (PrestamoException e) {
			// assert
			assertEquals(Bibliotecario.ISBN_NO_ENTREGADO, e.getMessage());
		}
	}
	
	/**
	 * 
	 * Método que permite testear se el usuario es vacío
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void usuarioVacioTest() {

		// arrange

		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar(libro.getIsbn(), "");
			fail();

		} catch (PrestamoException e) {
			// assert
			assertEquals(Bibliotecario.USUARIO_NO_ENVIADO, e.getMessage());
		}
	}
	/**
	 * 
	 * Método que permite testear si el usuario es null
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void usuarioNullTest() {

		// arrange

		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();

		Libro libro = libroTestDataBuilder.build();
		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		try {
			// act
			bibliotecario.prestar(libro.getIsbn(), null);
			fail();

		} catch (PrestamoException e) {
			// assert
			assertEquals(Bibliotecario.USUARIO_NO_ENVIADO, e.getMessage());
		}
	}

}
