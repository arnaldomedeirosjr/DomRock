package br.com.domrock.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.domrock.dao.SaldoItemDAO;
import br.com.domrock.model.SaldoItem;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class SaldoItemBean implements  Serializable{
	private SaldoItem saldoItem;
	private List<SaldoItem> saldos;
	
	public SaldoItem getSaldoItem() {
		return saldoItem;
	}
	public void setSaldoItem(SaldoItem saldoItem) {
		this.saldoItem = saldoItem;
	}
	public List<SaldoItem> getSaldos() {
		return saldos;
	}
	public void setSaldos(List<SaldoItem> saldos) {
		this.saldos = saldos;
	}
	
	@PostConstruct
	public void Listar()
	{
		SaldoItemDAO saldoDao = new SaldoItemDAO();
		saldos = saldoDao.listar();
	}
}
