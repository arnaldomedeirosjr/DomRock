package br.com.domrock.model;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class SaldoItem implements Serializable {
	
 private String item;
 private Date data_inicio;
 private Date data_final;
 private Double qtd_inicio;
 private Double qtd_final;
 private Double valor_inicio;
 private Double valor_final;
 private Double saldo_final;
 
public String getItem() {
	return item;
}
public Date getData_inicio() {
	return data_inicio;
}
public void setData_inicio(Date data_inicio) {
	this.data_inicio = data_inicio;
}
public Date getData_final() {
	return data_final;
}
public void setData_final(Date data_final) {
	this.data_final = data_final;
}
public void setItem(String item) {
	this.item = item;
}
public Double getQtd_inicio() {
	return qtd_inicio;
}
public void setQtd_inicio(Double qtd_inicio) {
	this.qtd_inicio = qtd_inicio;
}
public Double getQtd_final() {
	return qtd_final;
}
public void setQtd_final(Double qtd_final) {
	this.qtd_final = qtd_final;
}
public Double getSaldo_final() {
	return saldo_final;
}
public Double getValor_inicio() {
	return valor_inicio;
}
public void setValor_inicio(Double valor_inicio) {
	this.valor_inicio = valor_inicio;
}
public Double getValor_final() {
	return valor_final;
}
public void setValor_final(Double valor_final) {
	this.valor_final = valor_final;
}
public void setSaldo_final(Double saldo_final) {
	this.saldo_final = saldo_final;
}

}
