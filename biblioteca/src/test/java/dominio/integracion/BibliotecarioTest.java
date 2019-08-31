package dominio.integracion;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Bibliotecario;
import dominio.Libro;
import dominio.Prestamo;
import dominio.excepcion.PrestamoException;
import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.sistema.SistemaDePersistencia;
import testdatabuilder.LibroTestDataBuilder;

public class BibliotecarioTest {

	private static final String CRONICA_DE_UNA_MUERTA_ANUNCIADA = "Cronica de una muerta anunciada";

	private static final String USUARIO_JEISON = "Jeison Barbosa";

	private static final String ISBN = "1221";

	private static final String ISBN_MAYOR_LIMITE = "7Y8U9R889";

	private static final String ISBN_MENOR_LIMITE = "7Y8";

	private SistemaDePersistencia sistemaPersistencia;

	private RepositorioLibro repositorioLibros;

	private RepositorioPrestamo repositorioPrestamo;

	@Before
	public void setUp() {

		sistemaPersistencia = new SistemaDePersistencia();

		repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();

		sistemaPersistencia.iniciar();
	}

	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}
	/**
	 * 
	 * Método que permite testear si el libro se prestó correctamente
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void prestarLibroTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), USUARIO_JEISON);

		// assert
		Assert.assertTrue(blibliotecario.esPrestado(libro.getIsbn()));
		Assert.assertNotNull(repositorioPrestamo.obtenerLibroPrestadoPorIsbn(libro.getIsbn()));

	}
	/**
	 * 
	 * Método que permite testear si el libro ya se encuentra prestado
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void prestarLibroNoDisponibleTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();

		repositorioLibros.agregar(libro);

		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(libro.getIsbn(), USUARIO_JEISON);
		try {

			blibliotecario.prestar(libro.getIsbn(), USUARIO_JEISON);
			fail();

		} catch (PrestamoException e) {
			// assert
			Assert.assertEquals(Bibliotecario.EL_LIBRO_NO_SE_ENCUENTRA_DISPONIBLE, e.getMessage());
		}
	}
	/**
	 * 
	 * Método que permite testear que se obtenga una fecha para la fecha de entrega
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void conFechaEntregaTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conIsbn(ISBN_MAYOR_LIMITE).conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(ISBN_MAYOR_LIMITE, USUARIO_JEISON);

		Prestamo prestado = repositorioPrestamo.obtener(ISBN_MAYOR_LIMITE);
		// assert
		Assert.assertNotNull(prestado.getFechaEntregaMaxima());
	}
	
	/**
	 * 
	 * Método que permite testear que la fecha de entrega sea null
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void sinFechaEntregaTest() {

		// arrange
		Libro libro = new LibroTestDataBuilder().conIsbn(ISBN_MENOR_LIMITE).conTitulo(CRONICA_DE_UNA_MUERTA_ANUNCIADA).build();
		repositorioLibros.agregar(libro);
		Bibliotecario blibliotecario = new Bibliotecario(repositorioLibros, repositorioPrestamo);

		// act
		blibliotecario.prestar(ISBN_MENOR_LIMITE, USUARIO_JEISON);

		Prestamo prestado = repositorioPrestamo.obtener(ISBN_MENOR_LIMITE);

		Assert.assertNull(prestado.getFechaEntregaMaxima());
	}

}
