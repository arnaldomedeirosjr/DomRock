package br.com.domrock.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.domrock.model.SaldoItem;

public class SaldoItemDAO {

	PreparedStatement preparedStatement;
	ResultSet resultSet;
	private Connection connection;

	public List<SaldoItem> listar(){
		
	List<SaldoItem>saldos = new ArrayList<>();
	
	String sql = "SELECT item, data_inicio, qtd_inicio, valor_inicio, "
			+    "data_final, qtd_final, valor_final, saldo_final FROM estoque;";

	try {
	connection = ConnectionFactory.connect();
	preparedStatement = connection.prepareStatement(sql);
	resultSet = preparedStatement.executeQuery();
	
	while (resultSet.next()) {

		SaldoItem saldo = new SaldoItem();
		
		saldo.setItem(resultSet.getString(1));
		saldo.setData_inicio(resultSet.getDate(2));
		saldo.setQtd_inicio(resultSet.getDouble(3));
		saldo.setValor_inicio(resultSet.getDouble(4));
		saldo.setData_final(resultSet.getDate(5));
		saldo.setQtd_final(resultSet.getDouble(6));
		saldo.setValor_final(resultSet.getDouble(7));
		saldo.setSaldo_final(resultSet.getDouble(8));
		saldos.add(saldo);			
	}
	}catch (Exception e) {
		e.printStackTrace();
	}
	
	return saldos;
}

}
