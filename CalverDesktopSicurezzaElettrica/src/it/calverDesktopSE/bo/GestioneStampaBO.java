package it.calverDesktopSE.bo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import it.calverDesktopSE.dao.SQLiteDAO;
import it.calverDesktopSE.dto.StrumentoDTO;
import it.calverDesktopSE.utl.Costanti;



public class GestioneStampaBO {

	
	
	public static int stampaEtichetta(StrumentoDTO strumento, String data, String esito)
	{
		
		String printerType=GestioneRegistro.getStringValue(Costanti.COD_PRINT);
	try{	
	
		
	  PrinterJob pj = PrinterJob.getPrinterJob();
		
		if (pj.printDialog()) {
		PageFormat pf = pj.defaultPage();
		Paper paper = pf.getPaper(); 
		double width = 0;
		double height = 0; 
		
		
		if(printerType.equals("1")) {
		width = fromCMToPPI(5);
		height = fromCMToPPI(5); 
		}else {
			width = fromCMToPPI(11);
			height = fromCMToPPI(2); 
		}
		
		paper.setSize(width, height);
		

		if(printerType.equals("1")) {
		paper.setImageableArea(
		12, 
		12, 
		fromCMToPPI(5), 
		fromCMToPPI(5)); 		
		}else {
			paper.setImageableArea(
					0, 
					0, 
					fromCMToPPI(11), 
					fromCMToPPI(11)); 
		}
	//	System.out.println("Before- " + dump(paper)); 


		pf.setOrientation(PageFormat.PORTRAIT);
	//	pf.setOrientation(PageFormat.LANDSCAPE);
		
		pf.setPaper(paper); 
	//	System.out.println("After- " + dump(paper));
	//	System.out.println("After- " + dump(pf)); 
	//	dump(pf); 
		PageFormat validatePage = pj.validatePage(pf);
	//  System.out.println("Valid- " + dump(validatePage)); 
		
		pj.setPrintable(new MyPrintable(strumento,data,esito), validatePage);
		
		pj.print();
		
		}
		
	} catch 
		
		(Exception ex) {
			ex.printStackTrace();
		} 

		return 0;
		
	}

protected static double fromCMToPPI(double cm) { 
	return toPPI(cm * 0.393700787); 
	}


	protected static double toPPI(double inch) { 
	return inch * 72d; 
	}


	protected static String dump(Paper paper) { 
	StringBuilder sb = new StringBuilder(64);
	sb.append(paper.getWidth()).append("x").append(paper.getHeight())
	.append("/").append(paper.getImageableX()).append("x").
	append(paper.getImageableY()).append(" - ").append(paper
	.getImageableWidth()).append("x").append(paper.getImageableHeight()); 
	return sb.toString(); 
	}


	protected static String dump(PageFormat pf) { 
	Paper paper = pf.getPaper(); 
	return dump(paper); 
	}
	
	public static class MyPrintable implements Printable {

		StrumentoDTO strumento;
		String data;
		String esito;
		
		public MyPrintable(StrumentoDTO _strumento,String _data,String _esito)
		{
			strumento=_strumento;
			data=_data;
			esito=_esito;
			
		}
		
		@Override
		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException { 
		
		int result = NO_SUCH_PAGE; 	
		
		String printerType=GestioneRegistro.getStringValue(Costanti.COD_PRINT);
		try
		{	
		
	
		if (pageIndex < 1) { 
		Graphics2D g2d = (Graphics2D) graphics; 

		g2d.translate((int) pageFormat.getImageableY(), (int) pageFormat.getImageableX()); 
		FontMetrics fm = g2d.getFontMetrics();
		
		int x = 120;
		int corAscent=0;
		int fontSize=0;
		
		if(printerType.equals("1")) 
		{
		corAscent=40;
		
		fontSize=18;
		
		}else {
			corAscent=fm.getAscent()+9;
			fontSize=5;
		}
		
		
		g2d.setFont(new Font("Arial", Font.BOLD, fontSize)); 
		
		Image img1Header = Toolkit.getDefaultToolkit().getImage(GestioneRegistro.getStringValue(Costanti.COD_IMG_PATH)+"/logo.png");
		
		AffineTransform a = AffineTransform.getRotateInstance(Math.PI, 230	, 100);
		    
		g2d.setTransform(a);
		
		
		if(printerType.equals("1")) {
	    g2d.drawImage(img1Header, x-50,0,140, 70, null);
	    
	    g2d.drawString("VERIFICA",x+150,corAscent);
	    g2d.drawString("SICUREZZA ELETTRICA",x+100,corAscent+25);
		
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
	    g2d.drawString("CODICE INTERNO:",x-15,corAscent+55);
	    
	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    g2d.drawString(strumento.getCodice_interno(),x+150,corAscent+55);
	    
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
	    g2d.drawString("MATRICOLA:",x-15,corAscent+75);
	    
	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    g2d.drawString(strumento.getMatricola(),x+100,corAscent+75);
	    
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
	    g2d.drawString("DATA:",x-15,corAscent+95);
	    
	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
	    g2d.drawString(getData(data),x+45,corAscent+95);
	    
	    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
	    g2d.drawString("ESITO:",x-15,corAscent+120);

	    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));	    
	    g2d.drawString(esito,x+45,corAscent+120);
		
		}
		
		else 
		
		{
			/*
			 * In attesa di modifica per stampante DYMO
			 */
		    g2d.drawImage(img1Header, x-50,0,140, 70, null);
		    
		    g2d.drawString("VERIFICA",x+150,corAscent);
		    g2d.drawString("SICUREZZA ELETTRICA",x+100,corAscent+25);
			
		    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
		    g2d.drawString("CODICE INTERNO:",x-15,corAscent+70);
		    
		    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
		    g2d.drawString(strumento.getCodice_interno(),x+150,corAscent+70);
		    
		    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
		    g2d.drawString("DATA:",x-15,corAscent+95);
		    
		    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
		    g2d.drawString(getData(data),x+45,corAscent+95);
		    
		    g2d.setFont(new Font("Arial", Font.ITALIC, fontSize));
		    g2d.drawString("ESITO:",x-15,corAscent+120);

		    g2d.setFont(new Font("Arial", Font.BOLD, fontSize));	    
		    g2d.drawString("IDONEO",x+45,corAscent+120);
		}
		

		result = PAGE_EXISTS;
		pageIndex=1;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result; 
		}

		private String getData(String date) {
			String dataReturn="";
			
			if(data.length()>=8) 
			{
				dataReturn="20"+data.substring(6, 8)+"/"+data.substring(3, 5);
			}
			
			return dataReturn;
		}

		}
}
