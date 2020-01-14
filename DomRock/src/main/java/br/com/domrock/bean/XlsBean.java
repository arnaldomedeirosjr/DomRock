package br.com.domrock.bean;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import br.com.domrock.dao.MovimentacaoDAO;
import br.com.domrock.model.MovimentacaoEstoque;
import jxl.Cell;
import jxl.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//import javax.swing.JOptionPane;

public class XlsBean {

	public static void main(String[] args) throws BiffException, IOException, ParseException, SQLException {
		// TODO Auto-generated method stub
		String path = "D:/Projetos/DomRock/MovtoItem.xls";	
		File file = new File(path);
		Workbook wb = Workbook.getWorkbook(file);
		
		Sheet sheet = wb.getSheet(0);
		int linhas = sheet.getRows();
		
//		Double Qtd = 0.0;
		Double saldo = 0.0;
		
		System.out.println("Iniciando leitura do documento.");	
		
		MovimentacaoEstoque estoque = new MovimentacaoEstoque();
	
		for(Long i=(long) 1; i<linhas; i++)
		{
			Cell A =  sheet.getCell(0, i.intValue());
			Cell B =  sheet.getCell(1, i.intValue());
			Cell C =  sheet.getCell(2, i.intValue());
			Cell D =  sheet.getCell(3, i.intValue());
			Cell E =  sheet.getCell(4, i.intValue());
	
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
			
			estoque.setItem(ITEM);
			estoque.setTipo(MOVTO);
			estoque.setData(data);
			estoque.setQuantidade(qtd);
			estoque.setValor(valor);
			estoque.setSaldo(saldo);
			
			MovimentacaoDAO dao = new MovimentacaoDAO();
			dao.save(estoque);
			
		}
		
		wb.close();
		
	}

}

/*
 * 
 * 			
			
			System.out.println("Coluna 1: "+ITEM);
			System.out.println("Coluna 2: "+MOVTO);
			System.out.println("Coluna 3: "+DATA_MOVTO);
			System.out.println("Coluna 4: "+QTDE);
			System.out.println("Coluna 5: "+VALOR);	
			
			
 		if(i==1) {
				
				switch (MOVTO) {
				case "Ent":
					Valor = Valor + valor;
					Qtd = Qtd + qtd;
					break;
					
				case "Sai":
					Valor = Valor - valor;
					Qtd = Qtd - qtd;
					break;
				}
				
			}else {				
				Cell AA = sheet.getCell(0, i-1);
				String PA = AA.getContents();
				
				if(PA == ITEM) {
					switch (MOVTO) {
					case "Ent":
						Valor = Valor + valor;
						Qtd = Qtd + qtd;
						break;
						
					case "Sai":
						Valor = Valor - valor;
						Qtd = Qtd - qtd;
						break;
					}
				}
				
			}
*/
