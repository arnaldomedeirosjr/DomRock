package br.com.domrock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.domrock.model.MovimentacaoEstoque;

public class MovimentacaoDAO {

	PreparedStatement preparedStatement;
	ResultSet resultSet;
	private Connection connection;

	public void save(List<MovimentacaoEstoque> movs) throws InstantiationException, IllegalAccessException, ClassNotFoundException {  
	  	try {  
	  		connection = ConnectionFactory.connect();  
	  		String sql = "INSERT INTO movestoque(item, tipo, data, quantidade, valor, total) " +   
	                               "VALUES(?,?,?,?,?,?)";  
	  		preparedStatement = connection.prepareStatement(sql);  
	  		// percorre a lista movs e adiciona a linha do insert no batch  
	                  for (MovimentacaoEstoque estoque : movs) {  
	  			preparedStatement.setString(1, estoque.getItem());  
	  			preparedStatement.setString(2, estoque.getTipo());  
	  			preparedStatement.setDate(3, new java.sql.Date(estoque.getData().getTime()));  
	  			preparedStatement.setDouble(4, estoque.getQuantidade());  
	  			preparedStatement.setDouble(5, estoque.getValor());  
	  			preparedStatement.setDouble(6, estoque.getTotal());  
	  			preparedStatement.addBatch();  
	  		}  
	                  //ao passar por todas as linhas executa o batch e assim todos os inserts  
	  		preparedStatement.executeBatch();  
	  		preparedStatement.clearBatch();  
	  		preparedStatement.close();  
	  		connection.close();  
	  		System.out.println("Gravação finalizada com sucesso.");  
	  	} catch (SQLException e) {  
	  		e.printStackTrace();  
	  	} 	  	
	  	
	  }
	
	public List<MovimentacaoEstoque> listar()
	{
		List<MovimentacaoEstoque> movs = new ArrayList<MovimentacaoEstoque>();
		
		String sql = "SELECT item, tipo, data, valor, quantidade, total "
				   + "FROM movestoque;";
	
		try {
			
		connection = ConnectionFactory.connect();
		preparedStatement = connection.prepareStatement(sql);
		resultSet = preparedStatement.executeQuery();
		
		while (resultSet.next()) {

			MovimentacaoEstoque estoque = new MovimentacaoEstoque();
			
			estoque.setItem(resultSet.getString(1));
			estoque.setTipo(resultSet.getString(2));
			estoque.setData(resultSet.getDate(3));
			estoque.setValor(resultSet.getDouble(4));
			estoque.setQuantidade(resultSet.getDouble(5));
			estoque.setTotal(resultSet.getDouble(6));
			movs.add(estoque);			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return movs;
	}
}
