package br.com.domrock.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.domrock.dao.ConnectionFactory;

public class BancoDados {
	
	public BancoDados() {
		
	}
	
	public Connection conectar() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {         
       
		return ConnectionFactory.connect();
    }
	
	 public void exportMovEstoque(Connection conn, String filename) {
	        Statement stmt;
	        String query;
	        try {
	            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                    ResultSet.CONCUR_UPDATABLE);
	            
	            query = "SELECT * into OUTFILE  '"+filename+
	                    "' FIELDS TERMINATED BY ',' FROM movestoque t";
	            stmt.executeQuery(query);
	            
	        } catch(Exception e) {
	            e.printStackTrace();
	            stmt = null;
	        }
	    }
	 

	 public void exportEstoque(Connection conn, String filename) {
	        Statement stmt;
	        String query;
	        try {
	            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
	                    ResultSet.CONCUR_UPDATABLE);
	            
	            query = "SELECT * into OUTFILE  '"+filename+
	                    "' FIELDS TERMINATED BY ',' FROM estoque e";
	            stmt.executeQuery(query);
	            
	        } catch(Exception e) {
	            e.printStackTrace();
	            stmt = null;
	        }
	    }
}
