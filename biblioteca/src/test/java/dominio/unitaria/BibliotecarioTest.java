package dominio.unitaria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
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
	private static final String ISBN_VALOR_LIMITE = "7Y8U9R889";
	private static final int ISBN_LIMITE = 40;

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

	@Test
	public void limiteValorIsbnTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		Libro libro = libroTestDataBuilder.conIsbn(ISBN_VALOR_LIMITE).build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		boolean esMayor = bibliotecario.esSumaISBNMayorA(ISBN_VALOR_LIMITE, ISBN_LIMITE);

		// assert
		Assert.assertTrue(esMayor);

	}

	@Test
	public void fechaEntregaTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder();
		Libro libro = libroTestDataBuilder.build();

		RepositorioPrestamo repositorioPrestamo = mock(RepositorioPrestamo.class);
		RepositorioLibro repositorioLibro = mock(RepositorioLibro.class);

		when(repositorioLibro.obtenerPorIsbn(libro.getIsbn())).thenReturn(libro);

		Bibliotecario bibliotecario = new Bibliotecario(repositorioLibro, repositorioPrestamo);

		// act
		Date fechaFinalEntrega = bibliotecario.generarFechaEntrega();
		LocalDate localDate = fechaFinalEntrega.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// assert
		Assert.assertEquals("2019-SEPTEMBER-16",
				localDate.getYear() + "-" + localDate.getMonth() + "-" + localDate.getDayOfMonth());

	}

}
