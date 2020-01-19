package br.com.domrock.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.domrock.dao.ConnectionFactory;
import br.com.domrock.dao.MovimentacaoDAO;
import br.com.domrock.dao.SaldoItemDAO;
import br.com.domrock.model.BancoDados;
import br.com.domrock.model.MovimentacaoEstoque;
import br.com.domrock.model.SaldoItem;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//import javax.swing.JOptionPane;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class XlsBean implements Serializable {
	
	private MovimentacaoEstoque movimentacaoEstoque;
	private SaldoItem saldoItem;
	private List<MovimentacaoEstoque> estoques;	
	private List<SaldoItem> saldoItems;
	public  String NomeArqMov;
	public  String NomeArqSaldo;
	public  String pathMov;
	public  String pathSaldo;
	public  String pathCsv;
	
	public String getPathSaldo() {
		return pathSaldo;
	}

	public void setPathSaldo(String pathSaldo) {
		this.pathSaldo = pathSaldo;
	}

	public String getPathCsv() {
		return pathCsv;
	}

	public void setPathCsv(String pathCsv) {
		this.pathCsv = pathCsv;
	}

	public void setPathMov(String pathMov) {
		this.pathMov = pathMov;
	}

	public String getNomeArqMov() {
		return NomeArqMov;
	}

	public void setNomeArqMov(String nomeArqMov) {
		NomeArqMov = nomeArqMov;
	}

	public String getNomeArqSaldo() {
		return NomeArqSaldo;
	}

	public void setNomeArqSaldo(String nomeArqSaldo) {
		NomeArqSaldo = nomeArqSaldo;
	}

	public  String getPathMov() {
		return pathMov;
	}
	public MovimentacaoEstoque getMovimentacaoEstoque() {
		return movimentacaoEstoque;
	}

	public void setMovimentacaoEstoque(MovimentacaoEstoque movimentacaoEstoque) {
		this.movimentacaoEstoque = movimentacaoEstoque;
	}

	public SaldoItem getSaldoItem() {
		return saldoItem;
	}

	public void setSaldoItem(SaldoItem saldoItem) {
		this.saldoItem = saldoItem;
	}

	public List<MovimentacaoEstoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<MovimentacaoEstoque> estoques) {
		this.estoques = estoques;
	}
	
	public List<SaldoItem> getSaldoItems() {
		return saldoItems;
	}

	public void setSaldoItems(List<SaldoItem> saldoItems) {
		this.saldoItems = saldoItems;
	}
	@PostConstruct
	public void listar() {
		
		
		MovimentacaoDAO movDao = new MovimentacaoDAO();
		estoques = movDao.listar();
		
		SaldoItemDAO saldoDao = new SaldoItemDAO();
		saldoItems = saldoDao.listar();
	}
	
//	public static void main(String[] args) throws BiffException, IOException, ParseException, SQLException {
//		// TODO Auto-generated method stub		
//		
////		lerMovimento();
////		lerSaldo();
////		gravaMovimentacao();	
////		System.out.println("Processamento Finalizado.");
//		
//
//        BancoDados db = new BancoDados();
//        Connection conn = db.conectar();
//
//      db.exportMovEstoque(conn,"D:/Projetos/DomRock/export.csv");
//      db.exportEstoque(conn,"D:/Projetos/DomRock/export.csv");
//	}
		
	public void processar() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, BiffException, IOException, ParseException {
	
	if(pathMov == null && pathSaldo == null){
		Messages.addGlobalWarn("Anexe o(s) arquivos para continuar.");
		return;
	}
	
	lerMovimento();
	lerSaldo();
	gravaMovimentacao();	
	System.out.println("Processamento Finalizado.");
	}
	
	public void exportar()
	{
		try {
			
		    BancoDados db = new BancoDados();
		    Connection conn = db.conectar();
		    
		    if(getPathCsv() == "") {
		    	Messages.addGlobalWarn("Digite apenas o caminho para exportação.");
		    	return;
		    }
		    
			db.exportEstoque(conn, pathCsv+"/export.csv");
			db.exportMovEstoque(conn, pathCsv+"/export.csv");
			
		}catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao tentar exportar tabelas."
					+ "Erro: "+ e.toString());
		}
	}
	
	public void lerMovimento() throws BiffException, IOException, ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		if (pathMov== null) {
			Messages.addGlobalWarn("Carregue o arquivo de Movimentação de Estoque para continuar.");
			return;
		}
		String path_m = pathMov; //+"/"+getNomeArqMov();

		File file_m = new File(path_m);

		Workbook wb_m = Workbook.getWorkbook(file_m);

		DecimalFormat decimalFormat = new DecimalFormat("#######.00");
		
		decimalFormat.setRoundingMode(RoundingMode.UP);
		
		Sheet sheet_m = wb_m.getSheet(0);
		int linhas_m = sheet_m.getRows();

		System.out.println("Iniciando leitura do documento.");
		
		List<MovimentacaoEstoque> movmts = new ArrayList<>();

		for (Long i = (long) 1; i < linhas_m; i++) {
			Cell A = sheet_m.getCell(0, i.intValue());
			Cell B = sheet_m.getCell(1, i.intValue());
			Cell C = sheet_m.getCell(2, i.intValue());
			Cell D = sheet_m.getCell(3, i.intValue());
			Cell E = sheet_m.getCell(4, i.intValue());

			String ITEM = A.getContents();
			String MOVTO = B.getContents();
			String DATA_MOVTO = C.getContents();
			String QTDE = D.getContents();
			String VALOR = E.getContents();

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			java.util.Date data = format.parse(DATA_MOVTO);

			VALOR = VALOR.replace(",", ".");
			QTDE = QTDE.replace(",", ".");
			Double qtd = Double.parseDouble(QTDE);
			Double valor = Double.parseDouble(VALOR);

			MovimentacaoEstoque estoque = new MovimentacaoEstoque();

			estoque.setItem(ITEM);
			estoque.setTipo(MOVTO);
			estoque.setData(data);
			estoque.setQuantidade(qtd);
			estoque.setValor(valor);
			estoque.setTotal(Double.parseDouble(decimalFormat.format(qtd * valor).replace(",", ".")));
			
			movmts.add(estoque);
		}
		wb_m.close();
		
		MovimentacaoDAO dao = new MovimentacaoDAO();
		dao.save(movmts);	
	}
	
	public  void lerSaldo() throws ParseException, BiffException, IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		if (pathSaldo == null) {
			Messages.addGlobalWarn("Carregue o arquivo de Saldo para continuar.");
			return;
		}
		
		System.out.println("Iniciando a leitura do arquivo de Saldos.");


		List<SaldoItem> itens = new ArrayList<>();

		String path_s = pathSaldo;//+"/"+getNomeArqSaldo();

		File file_s = new File(path_s);
		
		Workbook wb_s = Workbook.getWorkbook(file_s);
		
		Sheet sheet_s = wb_s.getSheet(0);
		int linhas_s = sheet_s.getRows();
		
		Date data_i = null;
		Date data_f = null;
		Double qtd_i = 0.0;
		Double qtd_f = 0.0;
		Double vlr_i = 0.0;
		Double vlr_f = 0.0;

		for (Long i = (long) 1; i < linhas_s; i++) {
			
			itens = new ArrayList<SaldoItem>();
			Cell A = sheet_s.getCell(0, i.intValue());
			Cell B = sheet_s.getCell(1, i.intValue());
			Cell C = sheet_s.getCell(2, i.intValue());
			Cell D = sheet_s.getCell(3, i.intValue());
			Cell E = sheet_s.getCell(4, i.intValue());
			Cell F = sheet_s.getCell(5, i.intValue());
			Cell G = sheet_s.getCell(6, i.intValue());

			String ITEM = A.getContents();
			String DATA_I = B.getContents();
			String QTD_I = C.getContents();
			String VLR_I = D.getContents();
			String DATA_F = E.getContents();
			String QTD_F = F.getContents();
			String VLR_F = G.getContents();

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
			java.util.Date di = format.parse(DATA_I);
			java.util.Date df  = format.parse(DATA_F);
			
			data_i = new java.sql.Date(di.getTime());
			data_f = new java.sql.Date(df.getTime());

			QTD_I = QTD_I.replace(",", ".");
			QTD_F = QTD_F.replace(",", ".");
			VLR_I = VLR_I.replace(",", ".");
			VLR_F = VLR_F.replace(",", ".");
			qtd_i = Double.parseDouble(QTD_I);
			qtd_f = Double.parseDouble(QTD_F);
			vlr_i = Double.parseDouble(VLR_I);
			vlr_f = Double.parseDouble(VLR_F);
			

			SaldoItem saldoItem = new SaldoItem();

			saldoItem.setItem(ITEM);
			saldoItem.setData_inicio(data_i);
			saldoItem.setData_final( data_f);
			saldoItem.setQtd_inicio(qtd_i);
			saldoItem.setQtd_final(qtd_f);
			saldoItem.setValor_inicio(vlr_i);
			saldoItem.setValor_final(vlr_f);
			itens.add(saldoItem);
			 
			gravaSaldo(itens);
		}
		wb_s.close();
	}

	public static void gravaSaldo(List<SaldoItem> itens) throws InstantiationException, IllegalAccessException, ClassNotFoundException {		
		try {
			DecimalFormat decimalFormat = new DecimalFormat("#######.00");
			decimalFormat.setRoundingMode(RoundingMode.UP);

			Connection connection = ConnectionFactory.connect();

			String sql = "INSERT INTO estoque(item, data_inicio, qtd_inicio, valor_inicio, data_final, qtd_final, valor_final, qtd_saldo, saldo_final)"
					+ "VALUES(?,?,?,?,?,?,?,?,?) ";

			PreparedStatement ps = connection.prepareStatement(sql);
			
			for (SaldoItem saldoItem : itens) {
				ps.setString(1, saldoItem.getItem());
				ps.setDate(2,saldoItem.getData_inicio());
				ps.setDouble(3, Double
						.parseDouble(decimalFormat.format(saldoItem.getQtd_inicio()).replace(",", ".")));
				ps.setDouble(4, Double
						.parseDouble(decimalFormat.format(saldoItem.getValor_inicio()).replace(",", ".")));
				ps.setDate(5, saldoItem.getData_final());
				ps.setDouble(6, saldoItem.getQtd_final());
				ps.setDouble(7, Double
						.parseDouble(decimalFormat.format(saldoItem.getValor_final()).replace(",", ".")));
				ps.setDouble(8, 0.00);
				ps.setDouble(9, 0.00);	
				
				ps.execute();
			}
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void gravaMovimentacao() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		System.out.println("Iniciando Processamento.");

		Double qtd_i = 0.0;
		Double qtd_f = 0.0;
		Double vlr_i = 0.0;
		Double vlr_f = 0.0;
		Date data_i = null;
		Date data_f = null;

		DecimalFormat decimalFormat = new DecimalFormat("#######.00");
		decimalFormat.setRoundingMode(RoundingMode.UP);

		List<String> itens = new ArrayList<String>();

		Connection connection;
		PreparedStatement ps;
		ResultSet rs;

		try {

			String sql = "SELECT distinct(item) FROM movestoque;";

			connection = ConnectionFactory.connect();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				itens.add(rs.getString(1));
			}

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			for (int i = 0; i < itens.size(); i++) {

				String sql = "SELECT sum(quantidade) as qtd_ent, sum(valor) as saldo_ent, min(data)  FROM movestoque WHERE "
						+ "item = ? and tipo = 'Ent';";

				connection = ConnectionFactory.connect();
				ps = connection.prepareStatement(sql);
				ps.setString(1, itens.get(i));
				rs = ps.executeQuery();

				while (rs.next()) {
					qtd_i = rs.getDouble(1);
					vlr_i = rs.getDouble(2);
					data_i = rs.getDate(3);
				}
				connection.close();

				sql = "";
				sql = "SELECT sum(quantidade) as qtd_sai, sum(valor) as saldo_sai, max(data)  FROM movestoque WHERE "
						+ "item = ? and tipo = 'Sai' ;";

				connection = ConnectionFactory.connect();
				ps = connection.prepareStatement(sql);
				ps.setString(1, itens.get(i));
				rs = ps.executeQuery();

				while (rs.next()) {
					qtd_f = rs.getDouble(1);
					vlr_f = rs.getDouble(2);
					data_f = rs.getDate(3);
				}

				connection.close();
				
				//Busca a qtd e o valor inicial do item.
				
				sql = "";
				sql = "SELECT qtd_inicio, valor_inicio  FROM estoque WHERE item = ?;";

				connection = ConnectionFactory.connect();
				ps = connection.prepareStatement(sql);
				ps.setString(1, itens.get(i));
				rs = ps.executeQuery();
				
				Double quantidade_inicial = 0.0;
				Double valor_inicial = 0.0;

				while (rs.next()) {
					quantidade_inicial = rs.getDouble(1);
					valor_inicial = rs.getDouble(2);
				}

				connection.close();			
				
				//Grava o Saldo.
			
				sql = "";
				sql = "INSERT INTO estoque(item, data_inicio, qtd_inicio, valor_inicio, data_final, qtd_final,"
						+ " valor_final, qtd_saldo, saldo_final) VALUES(?,?,?,?,?,?,?,?,?) ";

				connection = ConnectionFactory.connect();
				ps = connection.prepareStatement(sql);
				ps.setString(1, itens.get(i));
				ps.setDate(2, data_i);
				ps.setDouble(3, qtd_i);
				ps.setDouble(4, Double.parseDouble(decimalFormat.format(vlr_i).replace(",", ".")));
				ps.setDate(5, data_f);
				ps.setDouble(6, qtd_f);
				ps.setDouble(7, Double.parseDouble(decimalFormat.format(vlr_f).replace(",", ".")));
				ps.setDouble(8, Double.parseDouble(decimalFormat.format(quantidade_inicial + qtd_i - qtd_f).replace(",", ".")));
				ps.setDouble(9, Double.parseDouble(decimalFormat.format(valor_inicial + vlr_i - vlr_f).replace(",", ".")));
				ps.execute();
				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadMov(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			
			pathMov = "";
			pathMov = arquivoTemp.toString();
			
			Messages.addGlobalInfo("Upload realizado com sucesso.");

		} catch (IOException erro) {

			Messages.addGlobalError("Erro ao tentar realizar upload de arquivo.");
			erro.printStackTrace();
		}

	}
	

	public void uploadSaldo(FileUploadEvent evento) {
		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			
			pathSaldo = "";
			pathSaldo = arquivoTemp.toString();
			
			Messages.addGlobalInfo("Upload realizado com sucesso.");

		} catch (IOException erro) {

			Messages.addGlobalError("Erro ao tentar realizar upload de arquivo.");
			erro.printStackTrace();
		}

	}
}

/*
	
*/
