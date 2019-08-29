package dominio;

import dominio.repositorio.RepositorioLibro;
import dominio.repositorio.RepositorioPrestamo;
import persistencia.sistema.SistemaDePersistencia;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SistemaDePersistencia sistemaPersistencia = new SistemaDePersistencia();
		
		RepositorioLibro repositorioLibros = sistemaPersistencia.obtenerRepositorioLibros();
		RepositorioPrestamo repositorioPrestamo = sistemaPersistencia.obtenerRepositorioPrestamos();
		
		sistemaPersistencia.iniciar();
		
		// Agrego un libro al repositorio.
		repositorioLibros.agregar(new Libro("T878B85Z", "Programación Orientada a Objetos", 2018));

		Bibliotecario b = new Bibliotecario(repositorioLibros, repositorioPrestamo);
		repositorioLibros.listar();
		boolean bandera = b.esPrestado("T878B85Z");
		System.out.println(bandera);
		b.prestar("T878B85Z", "David");
		repositorioLibros.listar();
	}

}
