package br.com.domrock.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MovimentacaoEstoque implements Serializable  {
	
	private Integer id;
	private String Item;
	private String Tipo;
	private Date data;
	private Double quantidade;
	private Double valor;
	private Double Total;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItem() {
		return Item;
	}
	public void setItem(String item) {
		Item = item;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getTotal() {
		return Total;
	}
	public void setTotal(Double total) {
		Total = total;
	}
}
