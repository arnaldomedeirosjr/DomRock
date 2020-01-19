package br.com.domrock.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.domrock.dao.MovimentacaoDAO;
import br.com.domrock.model.MovimentacaoEstoque;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MovimentacaoEstoqueBean implements Serializable {
	private MovimentacaoEstoque movimentacaoEstoque;
	private List<MovimentacaoEstoque> movs;

	public MovimentacaoEstoque getMovimentacaoEstoque() {
		return movimentacaoEstoque;
	}

	public void setMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) {
		this.movimentacaoEstoque = movimentacaoEstoque;
	}

	public List<MovimentacaoEstoque> getMovs() {
		return movs;
	}

	public void setMovs(List<MovimentacaoEstoque> movs) {
		this.movs = movs;
	}
	
	@PostConstruct
	public void Listar()
	{
		MovimentacaoDAO movDao = new MovimentacaoDAO();
		movs = movDao.listar();
	}
}
