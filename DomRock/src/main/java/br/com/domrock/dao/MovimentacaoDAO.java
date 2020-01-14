package br.com.domrock.dao;

import java.sql.Connection;
import java.sql.Date;
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

	public void save(MovimentacaoEstoque estoque) {

		try {

			String sql = "INSERT INTO movestoque(item, tipo, data, quantidade, valor, saldo) " + "VALUES(?,?,?,?,?,?)";

			connection = ConnectionFactory.connect();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, estoque.getItem());
			preparedStatement.setString(2, estoque.getTipo());
			preparedStatement.setDate(3, new java.sql.Date(estoque.getData().getTime()));
			preparedStatement.setDouble(4, estoque.getQuantidade());
			preparedStatement.setDouble(5, estoque.getValor());
			preparedStatement.setDouble(6, estoque.getSaldo());

			preparedStatement.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(MovimentacaoEstoque estoque) {
		try {
			String sql = "UPDATE movestoque SET item = ?, tipo = ?, data = ?, quantidade = ?, valor = ?, saldo = ?"
					+ "WHERE id = ?) ";

			connection = ConnectionFactory.connect();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, estoque.getItem());
			preparedStatement.setString(2, estoque.getTipo());
			preparedStatement.setDouble(3, estoque.getQuantidade());
			preparedStatement.setDate(4, new java.sql.Date(estoque.getData().getDate()));
			preparedStatement.setDouble(5, estoque.getValor());
			preparedStatement.setDouble(6, estoque.getSaldo());
			preparedStatement.setInt(7, estoque.getId());

			preparedStatement.executeUpdate();

			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<MovimentacaoEstoque> list() {
		List<MovimentacaoEstoque> movimentacoes = new ArrayList<MovimentacaoEstoque>();
		try {

			String sql = "SELECT * FROM movestoque";

			connection = ConnectionFactory.connect();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				MovimentacaoEstoque estoque = new MovimentacaoEstoque();
				estoque.setId(resultSet.getInt(1));
				estoque.setItem(resultSet.getString(2));
				estoque.setTipo(resultSet.getString(3));
				estoque.setData(resultSet.getDate(4));
				estoque.setQuantidade(resultSet.getDouble(5));
				estoque.setValor(resultSet.getDouble(6));
				estoque.setSaldo(resultSet.getDouble(7));

				movimentacoes.add(estoque);
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movimentacoes;
	}
}
