package br.com.domrock.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	private static final String USUARIO = "root";
	private static final String SENHA = "12345";
	private static final String URL = "jdbc:mysql://localhost/domrock?useTimezone=true&serverTimezone=UTC";
	
	public static Connection connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
		
		return conn;
	}
	

}
