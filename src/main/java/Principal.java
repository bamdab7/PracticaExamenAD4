import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Principal {

	private static ArrayList<Empleados> listaEmpleados = new ArrayList<Empleados>();
	private static ArrayList<Departamentos> listaDepartamentos = new ArrayList<Departamentos>();
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, SQLException {
		// TODO leer XML, esribir en CSV e insertar en una BBDD
		leerXML();
		escrituraCSV();
		insertarBBDD();
		
	}

	private static void insertarBBDD() throws SQLException {
		// TODO insertar los datos que contienen los array en una base de datos
		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/empresaad","root","");
		
		//	PARA LOS DEPARTAMENTOS (INSERT)
		PreparedStatement psDep = conexion.prepareStatement("INSERT INTO departamentos (codDep,nombreDep) VALUES (?,?)");
		for(int i =0; i< listaDepartamentos.size(); i++) {
			psDep.setInt(1, listaDepartamentos.get(i).getCodigo());
			psDep.setString(2, listaDepartamentos.get(i).getNombre());
			psDep.executeUpdate();
		}
		
		//	PARA LOS EMPLEADOS (INSERT)
		PreparedStatement psEmp = conexion.prepareStatement("INSERT INTO empleados (codigo,nombre) VALUES (?,?)");
		for(int i =0; i< listaEmpleados.size(); i++) {
			psEmp.setInt(1, listaEmpleados.get(i).getCodigo());
			psEmp.setString(2, listaEmpleados.get(i).getNombre());
			psEmp.executeUpdate();
		}
	}

	private static void escrituraCSV() throws IOException {
		// TODO escritura de un archivo CSV mediante el arhivo XML proporcionado
		Path ficheroCSV = Paths.get("C:\\PRUEBAS\\PRACTICA4\\EMPRESACSV.csv");
		
		BufferedWriter bw = Files.newBufferedWriter(ficheroCSV);
		for(int i =0; i<listaEmpleados.size();i++) {
			bw.write(listaEmpleados.get(i).getCodigo() + ";" + listaEmpleados.get(i).getNombre() + "\r");
		}
		
		for(int i =0; i<listaDepartamentos.size();i++) {
			bw.write(listaDepartamentos.get(i).getCodigo() + ";" + listaDepartamentos.get(i).getNombre() + "\r");
		}
		bw.flush();
			

	}

	private static void leerXML() throws IOException, ParserConfigurationException, SAXException {
		// TODO lectura del fichero xml proporcionado y almacenarlo en un arrayList
		FileInputStream ficheroXML = new FileInputStream("C:\\PRUEBAS\\PRACTICA4\\EMPRESA.xml");
		
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = factoria.newDocumentBuilder();
		
		Document documento = db.parse(ficheroXML);
		
		//	PARA LA PRIMERA SECCION DEL XML
		NodeList listaCodigoEmpleado = documento.getElementsByTagName("codigo");
		NodeList listaNombreEmpleado = documento.getElementsByTagName("nombre");
		for(int i =0 ; i < listaCodigoEmpleado.getLength();i++) {
			System.out.println(listaCodigoEmpleado.item(i).getTextContent());
			System.out.println(listaNombreEmpleado.item(i).getTextContent());
			listaEmpleados.add(new Empleados(Integer.parseInt(listaCodigoEmpleado.item(i).getTextContent()),listaNombreEmpleado.item(i).getTextContent()));
		}
		
		//	PARA LA SEGUNDA SECCION DEL XML
		NodeList listacodDep = documento.getElementsByTagName("codDep");
		NodeList listaNombreDepartamento= documento.getElementsByTagName("nombreDep");
		for(int i =0 ; i < listacodDep.getLength();i++) {
			System.out.println(listacodDep.item(i).getTextContent());
			System.out.println(listaNombreDepartamento.item(i).getTextContent());
			listaDepartamentos.add(new Departamentos(Integer.parseInt(listacodDep.item(i).getTextContent()),listaNombreDepartamento.item(i).getTextContent()));
		}
		
		

		
	}

}
