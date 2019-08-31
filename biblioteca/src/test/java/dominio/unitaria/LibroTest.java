package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dominio.Libro;
import testdatabuilder.LibroTestDataBuilder;

public class LibroTest {

	private static final int ANIO = 2010;

	private static final String ISBN = "1234";

	private static final String NOMBRE_LIBRO = "Cien años de soledad";
	
	/**
	 * 
	 * Método que permite testear que el libro esté creado con sus atributos
	 *
	 * @author	Jeison Julián Barbosa Serna<br>
	 * 			Email: jjbarser@gmail.com<br>
	 * 
	 * @date	31/08/2019
	 * @version 1.0
	 *
	 */
	@Test
	public void crearLibroTest() {

		// arrange
		LibroTestDataBuilder libroTestDataBuilder = new LibroTestDataBuilder().conTitulo(NOMBRE_LIBRO).conIsbn(ISBN).conAnio(ANIO);

		// act
		Libro libro = libroTestDataBuilder.build();

		// assert
		assertEquals(NOMBRE_LIBRO, libro.getTitulo());
		assertEquals(ISBN, libro.getIsbn());
		assertEquals(ANIO, libro.getAnio());
	}

}
