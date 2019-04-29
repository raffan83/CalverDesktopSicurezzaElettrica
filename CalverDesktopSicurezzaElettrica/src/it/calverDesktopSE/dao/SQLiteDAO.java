package it.calverDesktopSE.dao;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import it.calverDesktopSE.dto.ClassificazioneDTO;
import it.calverDesktopSE.dto.DatiEsterniDTO;
import it.calverDesktopSE.dto.LuogoVerificaDTO;
import it.calverDesktopSE.dto.MisuraDTO;
import it.calverDesktopSE.dto.ProvaMisuraDTO;
import it.calverDesktopSE.dto.StrumentoDTO;
import it.calverDesktopSE.dto.TabellaMisureDTO;
import it.calverDesktopSE.dto.TipoRapportoDTO;
import it.calverDesktopSE.dto.TipoStrumentoDTO;
import it.calverDesktopSE.gui.PannelloConsole;
import it.calverDesktopSE.utl.Costanti;
import it.calverDesktopSE.utl.Utility;

public class SQLiteDAO {
	
	private static final String insertCMP = "INSERT INTO tblCampioni VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	static DatabaseMetaData metadata = null;

	private static  Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+Costanti.PATH_DB);
		
		return con;
	}
	private static  Connection getConnectionExternal(String filename) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+filename);
		
		return con;
	}


	
	public static String[] listaTabelleDB() throws SQLException, ClassNotFoundException {
		 
		String table[] = { "TABLE" };
		 
		         ResultSet rs = null;
		
		         ArrayList<String> tables = null;
		
		         DatabaseMetaData metadata =getConnection().getMetaData();
		
		         rs = metadata.getTables(null, null, null, table);
		 
		         tables = new ArrayList<String>();
		 
		         while (rs.next()) {
		
		             tables.add(rs.getString("TABLE_NAME"));
		
		         }
		
		         return Utility.convertString(tables);

	}



	public static String[] getListaColonne(String tabella) throws SQLException, ClassNotFoundException {				
			
		ArrayList<String> listaColonne= new ArrayList<String>();
		
		ResultSet rs = getConnection().getMetaData().getColumns(null, null, tabella, null);
			
			while (rs.next()) {
				listaColonne.add(rs.getString("COLUMN_NAME"));
			}

		return Utility.convertString(listaColonne);
	}



	public static Object[][] getPlayLoad(int length,String tableName) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		Object[][]data=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM "+tableName);
			
			rs=pst.executeQuery();
			
			int numberRow=getNumberRow(tableName);
		
			data=new Object[numberRow][length];
			int ind=0;
			while(rs.next())
			{
							
				for (int i = 0; i < length; i++) {
					data[ind][i] =rs.getString(i+1);
				}
				ind++;
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return data;
	}



	private static int getNumberRow(String nomeTaballa) throws SQLException, ClassNotFoundException {
		int i=0;
		
		Connection con=getConnection();
		
		PreparedStatement pst=con.prepareStatement("SELECT * FROM "+nomeTaballa);
		ResultSet rs=pst.executeQuery();
		
		while(rs.next())
		{
			i++;
		}
		return i;
	}



	public static ArrayList<StrumentoDTO> getListaStrumenti(int filter) throws Exception {

		
		Connection con=null;
		PreparedStatement pst=null;
		ArrayList<StrumentoDTO> listaStrumenti = new ArrayList<>();
	try{
		con=getConnection();
		
		String sql="";
		if(filter==0)
		{
			sql="SELECT a.* FROM tblStrumenti a";
		}
		if(filter==1)
		{
			sql="select a.* FROM tblStrumenti a join tblMisure b on a.id=b.id_str where b.statoMisura=0";
		}
		if(filter==2)
		{
			sql="select a.* FROM tblStrumenti a join tblMisure b on a.id=b.id_str where b.statoMisura=1";
		}
		if(filter==3)
		{
			sql="select a.* FROM tblStrumenti a left join tblMisure b on a.id=b.id_str where b.id_str is null";
		}
		
		if(filter==4)
		{
			sql="select a.* FROM tblStrumenti a join tblMisure b on a.id=b.id_str where b.statoMisura=2";
		}
		
		pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		StrumentoDTO strumento =null;
		while(rs.next())
		{
			strumento= new StrumentoDTO();
			strumento.set__id(rs.getInt("id"));//
			strumento.setIndirizzo(rs.getString("indirizzo"));
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCodice_interno(rs.getString("codice_interno"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setClassificazione(rs.getString("classificazione"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setRisoluzione(rs.getString("risoluzione"));
			strumento.setCampo_misura(rs.getString("campo_misura"));
			strumento.setFreq_taratura(rs.getInt("freq_verifica_mesi"));
			strumento.setTipoRapporto(rs.getString("tipoRapporto"));
			strumento.setStatoStrumento(rs.getString("StatoStrumento"));
			strumento.setReparto(rs.getString("reparto"));
			strumento.setUtilizzatore(rs.getString("utilizzatore"));
			strumento.setProcedura(rs.getString("procedura"));
			strumento.setId_tipoStrumento(rs.getString("id_tipo_strumento"));
			strumento.setNote(rs.getString("note"));
			strumento.setDataUltimaVerifica(rs.getString("dataUltimaVerifica"));
			strumento.setDataProssimaVerifica(rs.getString("dataProssimaVerifica"));
			strumento.setnCertificato(rs.getString("nCertificato"));
			strumento.setLuogoVerifica(rs.getInt("luogo_verifica"));
			
			listaStrumenti.add(strumento);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
			return listaStrumenti;
		}
	



	public static StrumentoDTO getStrumento(String id) throws Exception {
	Connection con =null;
	PreparedStatement pst= null;
	
	StrumentoDTO strumento =null;	
	try{
		con=getConnection();
		pst=con.prepareStatement("SELECT a.*, b.descrizione as descTR, c.descrizione as descClass FROM tblStrumenti a " +
								 "left join tbl_tipoRapporto b on  b.id =a.tipoRapporto  " +
								 "left join tbl_classificazione c on  c.id =a.classificazione " +
								 "WHERE a.id=?");
		pst.setInt(1,Integer.parseInt(id));
		ResultSet rs=pst.executeQuery();
		
		
		
		
		while(rs.next())
		{
			strumento= new StrumentoDTO();
			strumento.set__id(rs.getInt("id"));//
			strumento.setIndirizzo(rs.getString("indirizzo"));
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCodice_interno(rs.getString("codice_interno"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setClassificazione(rs.getString("descClass"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setRisoluzione(rs.getString("risoluzione"));
			strumento.setCampo_misura(rs.getString("campo_misura"));
			strumento.setFreq_taratura(rs.getInt("freq_verifica_mesi"));
			strumento.setTipoRapporto(rs.getString("descTR"));
			strumento.setStatoStrumento(rs.getString("StatoStrumento"));
			strumento.setReparto(rs.getString("reparto"));
			strumento.setUtilizzatore(rs.getString("utilizzatore"));
			strumento.setProcedura(rs.getString("procedura"));
			strumento.setId_tipoStrumento(rs.getString("id_tipo_strumento"));
			strumento.setNote(rs.getString("note"));
			strumento.setDataUltimaVerifica(rs.getString("dataUltimaVerifica"));
			strumento.setDataProssimaVerifica(rs.getString("dataProssimaVerifica"));
			strumento.setnCertificato(rs.getString("nCertificato"));
			strumento.setCreato(rs.getString("creato"));
			strumento.setLuogoVerifica(rs.getInt("luogo_verifica"));
			
		}
	}catch(Exception ex)
	{
		throw ex;
	}
	finally
	{
		pst.close();
		con.close();
	}
		return strumento;
	}

	public static StrumentoDTO getStrumentoDB(String id) throws Exception {
	Connection con =null;
	PreparedStatement pst= null;
	
	StrumentoDTO strumento =null;	
	try{
		con=getConnection();
		pst=con.prepareStatement("SELECT a.* FROM tblStrumenti a WHERE a.id=?");
		pst.setInt(1,Integer.parseInt(id));
		ResultSet rs=pst.executeQuery();
		
		
		
		
		while(rs.next())
		{
			strumento= new StrumentoDTO();
			strumento.set__id(rs.getInt("id"));//
			strumento.setIndirizzo(rs.getString("indirizzo"));
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCodice_interno(rs.getString("codice_interno"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setClassificazione(rs.getString("classificazione"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setRisoluzione(rs.getString("risoluzione"));
			strumento.setCampo_misura(rs.getString("campo_misura"));
			strumento.setFreq_taratura(rs.getInt("freq_verifica_mesi"));
			strumento.setTipoRapporto(rs.getString("tipoRapporto"));
			strumento.setStatoStrumento(rs.getString("StatoStrumento"));
			strumento.setReparto(rs.getString("reparto"));
			strumento.setUtilizzatore(rs.getString("utilizzatore"));
			strumento.setProcedura(rs.getString("procedura"));
			strumento.setId_tipoStrumento(rs.getString("id_tipo_strumento"));
			strumento.setNote(rs.getString("note"));
			strumento.setDataUltimaVerifica(rs.getString("dataUltimaVerifica"));
			strumento.setDataProssimaVerifica(rs.getString("dataProssimaVerifica"));
			strumento.setnCertificato(rs.getString("nCertificato"));
			strumento.setCreato(rs.getString("creato"));
			strumento.setLuogoVerifica(rs.getInt("luogo_verifica"));
			
			
		}
	}catch(Exception ex)
	{
		throw ex;
	}
	finally
	{
		pst.close();
		con.close();
	}
		return strumento;
	}


	public static int isPresent(String id) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		
		try{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisuraSicurezzaElettrica WHERE id_strumento=?");
			pst.setInt(1,Integer.parseInt(id));
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
			 return rs.getInt(1);
			}
			return 0;
		}catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
			
	}



	public static int insertMisura(String id) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblMisuraSicurezzaElettrica(id_strumento,data,stato) VALUES(?,?,?)",pst.RETURN_GENERATED_KEYS);
			
			pst.setInt(1,Integer.parseInt(id));
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = new Date();
			pst.setString(2,sdf.format(d));
			pst.setString(3,"0");
			pst.execute();
		
		    ResultSet generatedKeys = pst.getGeneratedKeys(); 
		    	
		            if (generatedKeys.next()) {
		               return (int) generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Error insert Misura, no ID obtained.");
		            }
		        
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static int getMaxTablella(int idMisura) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id_tabella) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1,idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}

	public static int getMaxTablellaGeneral() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id) FROM tblTabelleMisura");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	


	public static void inserisciMisuraLineare(int idMisura, int seq_tab,Integer punti, String labelPunti, String calibrazione) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			String calLabel="";
			if(calibrazione!=null && calibrazione.length()>0){calLabel="("+calibrazione+")";}
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,seq_tab);
			pst.setString(3, "L"+"_"+punti);
			
			for (int i = 1; i <= punti; i++) {
			
				pst.setInt(4, 0);
			pst.setInt(5, i);
			pst.setString(6,labelPunti+" "+i+calLabel);
			pst.setString(7,labelPunti);
			pst.setString(8, calibrazione);
			pst.setString(9, "S");
			pst.execute();
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}

	public static void inserisciMisuraDuplicata(int seq_tab, TabellaMisureDTO tab,int idMisura) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,id_ripetizione,ordine,tipoProva,label,tipoVerifica,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			
			ArrayList<MisuraDTO> listaMisure = tab.getListaMisure();
			
			for (MisuraDTO misura : listaMisure) {
				pst.setInt(1, idMisura);
				pst.setInt(2,seq_tab);
				pst.setInt(3,misura.getId_ripetizione());
				pst.setInt(4,misura.getOrdine());
				pst.setString(5, tab.getTipoProva());
				pst.setString(6,misura.getLabel());
				pst.setString(7,misura.getTipoVerifica());
				pst.setString(8, misura.getCalibrazione());
				pst.setString(9, "S");
				pst.execute();
			
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	
public static int inserisciMisuraRDP(int idMisura, String campioniString, String descrizione,
		BigDecimal valore_rilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		int id=0;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,valoreStrumento,esito,desc_campione,applicabile,file_att) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			int ordine=getOrdine(idMisura);
			ordine++;
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,1);
			pst.setString(3, "RDP");
			pst.setInt(4, 0);
			pst.setInt(5,ordine );
			pst.setString(6,descrizione);
			pst.setString(7,descrizione);
			pst.setBigDecimal(8, valore_rilevato);
			pst.setString(9, esito);
			pst.setString(10,campioniString);
			pst.setString(11, "S");
			if(file_att!=null)
			{
				pst.setBytes(12, file_att.toByteArray());
			}
			else
			{
				pst.setBytes(12, null);
			}
			pst.execute();
			ResultSet rs =pst.getGeneratedKeys();
			rs.next();
			id=(int)rs.getLong(1);
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return id;
	}

public static void updateMisuraRDP(int idRecord, String descrizioneCampione, String descrizioneProva,BigDecimal valoreRilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
	
	Connection con=null;
	PreparedStatement pst=null;
	try 
	{
		con=getConnection();
		pst=con.prepareStatement("UPDATE tblTabelleMisura SET tipoVerifica=?,label=?,valoreStrumento=?,esito=?,desc_campione=?,file_att=? WHERE id=?");
		
	
		pst.setString(1,descrizioneProva);
		pst.setString(2,descrizioneProva);
		pst.setBigDecimal(3, valoreRilevato);
		pst.setString(4, esito);
		pst.setString(5,descrizioneCampione);
		if(file_att!=null) 
		{
			pst.setBytes(6, file_att.toByteArray());
		}
		else 
		{
			pst.setBytes(6, null);
		}
		pst.setInt(7, idRecord);
		pst.execute();
		
	}
	catch (Exception e) 
	{
	 e.printStackTrace();	
	 throw e;
	}
	finally
	{
		pst.close();
		con.close();
	}
}

	private static int getOrdine(int idMisura) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(ordine) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1,idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	public static void inserisciMisuraRipetibile(int idMisura, int seq_tab,Integer punti, Integer ripetizioni, String labelPunti, String calibrazione) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			
			String calLabel="";
			if(calibrazione!=null && calibrazione.length()>0){calLabel="("+calibrazione+")";}
			
			pst.setInt(1, idMisura);
			pst.setInt(2,seq_tab);
			pst.setString(3, "R"+"_"+punti+"_"+ripetizioni);
			
			
			int ordine=1;
			
			for (int i = 1; i <= ripetizioni; i++) {
			
				for (int j = 0; j < punti; j++) {
					
					pst.setInt(4,i);
					pst.setInt(5, ordine);
					pst.setString(6,"["+i+" - rp] "+labelPunti+" "+(j+1)+calLabel);
					pst.setString(7,labelPunti);
					pst.setString(8, calibrazione);
					pst.setString(9, "S");
					pst.execute();
					ordine++;
				}
			
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static ProvaMisuraDTO getInfoMisura(String id_str) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		ProvaMisuraDTO prova=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisure WHERE id_str=?");
			pst.setString(1, id_str);
		
			rs=pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while(rs.next())
			{
				prova=new ProvaMisuraDTO();
				 String dataMisura = rs.getString("dataMisura");
	                if(dataMisura != null)
	                {
	                    prova.setDataMisura(sdf.parse(dataMisura));
	                } else
	                {
	                    prova.setDataMisura(new Date());
	                }
	                
				prova.setIdMisura(rs.getInt("id"));
				prova.setTemperatura(rs.getBigDecimal("temperatura"));
				prova.setUmidita(rs.getBigDecimal("umidita"));
				prova.setTipo_firma(rs.getInt("tipoFirma"));
			}
			
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return prova;
	}



	public static int getNumeroTabellePerProva(int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		int numTab=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT max(id_tabella) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1, id);
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				numTab=rs.getInt(1);
			}
			
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return numTab;
	}



	public static ArrayList<TabellaMisureDTO> getListaTabelle(ProvaMisuraDTO provaMisura, int numIdTab_prova) throws Exception {
		Connection con=null;
		
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		ArrayList<TabellaMisureDTO>  listaTabelle= new ArrayList<>();
		TabellaMisureDTO tabella = null;;
		MisuraDTO misura=null;
		try 
		{
		for (int i = 1; i <= numIdTab_prova; i++) {
			
	
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM tblTabelleMisura WHERE id_misura=? AND id_tabella=? ORDER BY ordine");
			pst.setInt(1, provaMisura.getIdMisura());
			pst.setInt(2,i);
			
			rs=pst.executeQuery();
			
			
			
			boolean flag=true;
			while(rs.next())
			{
				if(flag)
				{
					tabella=new TabellaMisureDTO();
					tabella.setTipoProva(rs.getString("tipoProva"));
					tabella.setId_tabella(i);
					flag=false;
				}
				
				misura= new MisuraDTO();
				
				misura.setId(rs.getInt("id"));
				misura.setId_ripetizione(rs.getInt("id_ripetizione"));
				misura.setTipoVerifica(rs.getString("tipoVerifica"));
				misura.setOrdine(rs.getInt("ordine"));
				misura.setLabel(rs.getString("label"));
				misura.setUm(rs.getString("um"));
				misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
				misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
				misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
				misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
				misura.setScostamento(rs.getBigDecimal("scostamento"));
				misura.setAccettabilita(rs.getBigDecimal("accettabilita"));
				misura.setIncertezza(rs.getBigDecimal("incertezza"));
				misura.setEsito(rs.getString("esito"));
				misura.setDescrizioneCampione(rs.getString("desc_campione"));
				misura.setDescrizioneParametro(rs.getString("desc_parametro"));
				misura.setCalibrazione(rs.getString("calibrazione"));
				misura.setApplicabile(rs.getString("applicabile"));
				misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
				misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
				tabella.getListaMisure().add(misura);
			}
			if(tabella!=null && flag==false)
			{
				listaTabelle.add(tabella);
			}
			
			}		
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			
			if(numIdTab_prova>0)
			{
			pst.close();
			con.close();
			}
		}
		return listaTabelle;
	}



	public static void updateRecordMisura(MisuraDTO misura) throws SQLException 
	{
	    Connection con=null;
		PreparedStatement pst=null;
		
		try
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE tblTabelleMisura SET " +
									  "tipoVerifica=?," +
									  "um=?," +
									  "valoreCampione=?," +
									  "valoreStrumento=?," +
									  "scostamento=?," +
									  "accettabilita=?," +
									  "incertezza=?," +
									  "esito=? ," +
									  "desc_campione =? ," +
									  "desc_parametro = ? ," +
									  "misura = ? ," +
									  "um_calc = ?," +
									  "risoluzione_misura = ?," +
									  "risoluzione_campione = ?," +
									  "fondo_scala = ? ," +
									  "interpolazione = ? ," +
									  "fm=?," +
									  "selConversione = ? ," +
									  "letturaCampione = ? ," +
									  "selTolleranza=? ," +
									  "perc_util=? ," +
									  "applicabile=? ,dgt=? " +
									  "WHERE id= ?");
			pst.setString(1, misura.getTipoVerifica());
			pst.setString(2, misura.getUm());
			pst.setBigDecimal(3, misura.getValoreCampione());
			pst.setBigDecimal(4, misura.getValoreStrumento());
			pst.setBigDecimal(5, misura.getScostamento());
			pst.setBigDecimal(6, misura.getAccettabilita());
			pst.setBigDecimal(7, misura.getIncertezza());
			
			pst.setString(8, misura.getEsito());
			pst.setString(9,misura.getDescrizioneCampione());
			pst.setString(10,misura.getDescrizioneParametro());
			pst.setBigDecimal(11, misura.getMisura());
			pst.setString(12, misura.getUm_calc());
			pst.setBigDecimal(13, misura.getRisoluzione_misura());
			pst.setBigDecimal(14, misura.getRisoluzione_campione());
			pst.setBigDecimal(15, misura.getFondoScala());
			pst.setInt(16, misura.getInterpolazione());
			pst.setString(17, misura.getFm());
			pst.setInt(18, misura.getSelConversione());
			pst.setBigDecimal(19, misura.getLetturaCampione());
			pst.setInt(20, misura.getSelTolleranza());
			pst.setBigDecimal(21, misura.getPercentuale());
			pst.setString(22, misura.getApplicabile());
			pst.setBigDecimal(23, misura.getDgt());
			pst.setInt(24, misura.getId());
			
			
			
			int i = pst.executeUpdate();
			
		}
		catch (Exception e) 
		{
			PannelloConsole.printException(e);
			e.printStackTrace();
		}
		finally
		{
			pst.close();
			con.close();
		}
		
		
	}



	public static Integer getStatoMisura(String id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		Integer toRet=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM tblMisuraSicurezzaElettrica WHERE id_strumento=?");
			pst.setString(1, id);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
							
				toRet=rs.getInt("stato");
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	public static ArrayList<String> getListaCampioniPerStrumento(String idStrumento) throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni " +
					"left join tbl_ts_tg on tbl_ts_tg.id_tipo_grandezza=tblCampioni.id_tipo_grandezza " +
					"left join tblStrumenti on tbl_ts_tg.id_tipo_strumento=tblStrumenti.id_tipo_strumento " +
					"where tblStrumenti.id=?  ORDER BY codice ASC");
			
			pst.setString(1, idStrumento);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaCampioniCompleta() throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni ");
			

			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	
	public static ArrayList<String> getListaCampioniCompletaNoInterpolabili() throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni WHERE interpolazione_permessa='0'");
			

			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaParametriTaratura(String codiceCampione, ArrayList<String> listaTipiGrandezza) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			String tipoGrandezza=preparaTipiGrandezza(listaTipiGrandezza);
			
			pst=con.prepareStatement("select parametri_taratura FROM tblCampioni WHERE codice=? AND ("+tipoGrandezza);
					
			pst.setString(1, codiceCampione);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	private static String preparaTipiGrandezza(ArrayList<String> listaTipiGrandezza) {
		String tipoGrandezza="";
		
		for (int i = 0; i < listaTipiGrandezza.size(); i++) {
			
			tipoGrandezza=tipoGrandezza+" OR id_tipo_grandezza ="+listaTipiGrandezza.get(i);
		}
		
		return tipoGrandezza.substring(4,tipoGrandezza.length())+")";
	}


	public static boolean isInterpolabile(String codiceCampione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		boolean toRet=false;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select interpolazione_permessa FROM  tblCampioni WHERE codice=?");
			
			pst.setString(1, codiceCampione);
			
			rs=pst.executeQuery();		
			rs.next();
			
				if(rs.getInt(1)==1)
				{
					toRet=true;
				}
				
		
		
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
		
		
	}



	public static ArrayList<String> getListaParametriTaraturaDistinct(String codiceCampione, ArrayList<String> listaTipiGrandezza) throws Exception {
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rs =null;
			ArrayList<String> toRet=new  ArrayList<String>();
			toRet.add("Seleziona Parametro....");
			try
			{
				con=getConnection();
				
				String tipoGrandezza=preparaTipiGrandezza(listaTipiGrandezza);
				
				pst=con.prepareStatement("select DISTINCT(parametri_taratura) FROM tblCampioni WHERE codice=? AND ("+tipoGrandezza);
						
				pst.setString(1, codiceCampione);
				
				rs=pst.executeQuery();
				
				while(rs.next())
				{
					toRet.add(rs.getString(1));			
					
				}
				
			}catch (Exception e) {
				throw e;
			}finally
			{
				pst.close();
				con.close();
			}
			return toRet;
		
	}



	




	

	public static ArrayList<String> getListaFattoriMoltiplicativi() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_fattori_moltiplicativi ");
					
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1)+" ("+rs.getString(2)+") | "+rs.getBigDecimal("fm").toEngineeringString());			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	public static double getPotenza(String str) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		double potenza=0;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select potenza FROM tbl_fattori_moltiplicativi WHERE sigla=? ");
			pst.setString(1, str);		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				potenza= rs.getDouble(1);
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return potenza;
	}

	public static double getPotenzaPerClasse(String cls) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		double potenza=0;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select potenza FROM tbl_fattori_moltiplicativi WHERE descrizione=? ");
			pst.setString(1, cls);		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				potenza= rs.getDouble(1);
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return potenza;
	}





	public static BigDecimal[] getMinMaxScala(String codice,String parametro) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		BigDecimal[] minMax =new BigDecimal[2];
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT MIN(valore_taratura),MAX(valore_taratura)  FROM tblCampioni WHERE codice=? AND parametri_Taratura=?");
			pst.setString(1, codice);
			pst.setString(2, parametro);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				minMax[0]=rs.getBigDecimal(1);
				minMax[1]=rs.getBigDecimal(2);
			
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return minMax;
	}



	public static ArrayList<BigDecimal> getValoriMediCampione(int idMisura,int id_ripetizione, int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<BigDecimal> listaValoriMedi =new ArrayList<BigDecimal>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT valoreCampione  FROM tblTabelleMisura WHERE id_ripetizione=?  AND id_misura=? AND id<>? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			
			pst.setInt(1,id_ripetizione);
			pst.setInt(2, idMisura);
			pst.setInt(3, id);
			pst.setInt(4, id);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				Object obj =rs.getObject(1);
				if(obj!=null && obj.toString().length()>0)
				{
					listaValoriMedi.add(rs.getBigDecimal(1));
				}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaValoriMedi;
	}



	public static void setValoriMediCampione(int idMisura, int id_ripetizioni,BigDecimal valoreMedioCampione, int id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET valoreMedioCampione=? WHERE id_ripetizione = ? AND id_misura=? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			
			pst.setBigDecimal(1, valoreMedioCampione);
			pst.setInt(2, id_ripetizioni);
			pst.setInt(3, idMisura);
			pst.setInt(4, id);
		
		
			pst.execute();
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}

	}



	public static ArrayList<BigDecimal> getValoriMediStumento(int idMisura,int id_ripetizione, int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<BigDecimal> listaValoriMedi =new ArrayList<BigDecimal>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT valoreStrumento  FROM tblTabelleMisura WHERE id_ripetizione=?  AND id_misura=? AND id<>? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			pst.setInt(1, id_ripetizione);
			pst.setInt(2, idMisura);
			pst.setInt(3, id);
			pst.setInt(4, id);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				Object obj =rs.getObject(1);
				if(obj!=null && obj.toString().length()>0)
				{
					listaValoriMedi.add(rs.getBigDecimal(1));
				}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaValoriMedi;
	}



	public static void setValoriMediStrumento(int idMisura, int id_ripetizione,BigDecimal valoreMedioStrumento, int id) throws Exception {
	
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET valoreMedioStrumento=? WHERE id_ripetizione=? AND id_misura=? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?) ");
			
			pst.setBigDecimal(1, valoreMedioStrumento);
			pst.setInt(2, id_ripetizione);
			pst.setInt(3, idMisura);
			pst.setInt(4, id);
		
		
			pst.execute();
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}

	
	public static MisuraDTO getMisura(int id) throws Exception
	{
		MisuraDTO misura=null;
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("Select * from  tblTabelleMisura WHERE id =?");
			pst.setInt(1, id);
		
		
			rs=pst.executeQuery();
			
		while (rs.next()) {
		
			misura = new MisuraDTO();
			misura.setId(id);
			misura.setId_ripetizione(rs.getInt("id_ripetizione"));
			misura.setOrdine(rs.getInt("ordine"));
			misura.setTipoVerifica(rs.getString("tipoVerifica"));
			misura.setUm(rs.getString("um"));
			misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
			misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
			misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
			misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
			misura.setScostamento(rs.getBigDecimal("scostamento"));
			misura.setAccettabilita(rs.getBigDecimal("Accettabilita"));
			misura.setEsito(rs.getString("esito"));
			misura.setDescrizioneCampione(rs.getString("desc_campione"));
			misura.setDescrizioneParametro(rs.getString("desc_parametro"));
			misura.setMisura(rs.getBigDecimal("misura"));
			misura.setUm_calc(rs.getString("um_calc"));
			misura.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
			misura.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));

			misura.setInterpolazione(rs.getInt("interpolazione"));
			misura.setFm(rs.getString("fm"));
			misura.setSelConversione(rs.getInt("selConversione"));
			misura.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
			misura.setSelTolleranza(rs.getInt("selTolleranza"));
			
			String fs=rs.getString("fondo_scala");
			
			if(fs!=null && fs.length()>0)
			{
				misura.setFondoScala(new BigDecimal(fs));
			}else
			{
				misura.setFondoScala(null);
			}
			
			String perc=rs.getString("perc_util");
			
			if(perc!=null && perc.length()>0)
			{
				misura.setPercentuale(new BigDecimal(perc));
			}else
			{
				misura.setPercentuale(null);
			}
			
			misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
			misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
			misura.setApplicabile(rs.getString("applicabile"));
			misura.setDgt(rs.getBigDecimal("dgt"));
			
			misura.setProvaPrecedente(rs.getString("val_descrizione_prec"));
			misura.setEsitoPrecedente(rs.getString("val_esito_prec"));
			misura.setFile_att(rs.getBytes("file_att"));
			misura.setFile_att_prec(rs.getBytes("file_att_prec"));
			
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return misura;
	}

	public static ArrayList<MisuraDTO> getListaRecordTabellaEsterna(String filename,int idMisura) throws Exception
	{
		ArrayList<MisuraDTO> listaMisure = new ArrayList<>();
		
		MisuraDTO misura=null;
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnectionExternal(filename);
			
			pst=con.prepareStatement("Select * from  tblTabelleMisura WHERE id_misura =?");
			pst.setInt(1, idMisura);
		
		
			rs=pst.executeQuery();
			
		while (rs.next()) {
		
			misura = new MisuraDTO();
			misura.setId(idMisura);
			misura.setIdTabella(rs.getInt("id_tabella"));
			misura.setId_ripetizione(rs.getInt("id_ripetizione"));
			misura.setOrdine(rs.getInt("ordine"));
			misura.setTipoProva(rs.getString("tipoProva"));
			misura.setTipoVerifica(rs.getString("tipoVerifica"));
			misura.setLabel(rs.getString("label"));
			misura.setUm(rs.getString("um"));
			misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
			misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
			misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
			misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
			misura.setScostamento(rs.getBigDecimal("scostamento"));
			misura.setAccettabilita(rs.getBigDecimal("Accettabilita"));
			misura.setEsito(rs.getString("esito"));
			misura.setDescrizioneCampione(rs.getString("desc_campione"));
			misura.setDescrizioneParametro(rs.getString("desc_parametro"));
			misura.setMisura(rs.getBigDecimal("misura"));
			misura.setUm_calc(rs.getString("um_calc"));
			misura.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
			misura.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));

			misura.setInterpolazione(rs.getInt("interpolazione"));
			misura.setFm(rs.getString("fm"));
			misura.setSelConversione(rs.getInt("selConversione"));
			misura.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
			misura.setSelTolleranza(rs.getInt("selTolleranza"));
			
			String fs=rs.getString("fondo_scala");
			
			if(fs!=null && fs.length()>0)
			{
				misura.setFondoScala(new BigDecimal(fs));
			}else
			{
				misura.setFondoScala(null);
			}
			
			String perc=rs.getString("perc_util");
			
			if(perc!=null && perc.length()>0)
			{
				misura.setPercentuale(new BigDecimal(perc));
			}else
			{
				misura.setPercentuale(null);
			}
			
			misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
			misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
			misura.setApplicabile(rs.getString("applicabile"));
			misura.setDgt(rs.getBigDecimal("dgt"));
			
			
			listaMisure.add(misura);
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return listaMisure;
	}

	public static boolean isPresentCampione(int idTabella, int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs;
		boolean isPresent=false;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * from tblCampioniUtilizzati WHERE id_tabellaMisura=? AND id_misura=?");
			
			pst.setInt(1,idTabella);
			pst.setInt(2, idMisura);

			rs=pst.executeQuery();
			
		while(rs.next())
		{
			isPresent=true;
		}
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return isPresent;
	}



	public static void insertCampioneUtilizzato(int idTabella, int idMisura,String campione, String parametro) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO tblCampioniUtilizzati(id_tabellaMisura,id_misura,desc_parametro,desc_campione) VALUES(?,?,?,?)");
			
			pst.setInt(1,idTabella);
			pst.setInt(2, idMisura);
			pst.setString(3, campione);
			pst.setString(4, parametro);

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		
	}



	public static void updateCampioneUtilizzato(int idTabella, int idMisura,String campione, String parametro) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblCampioniUtilizzati SET desc_campione=? , desc_parametro=? WHERE id_tabellaMisura=? AND id_misura=?");
			
			pst.setString(1, campione);
			pst.setString(2, parametro);
			pst.setInt(3,idTabella);
			pst.setInt(4, idMisura);
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static void terminaMisura(String idStrumento, BigDecimal temperatura,BigDecimal umidita, int sr, int firma) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblMisure SET dataMisura=? ,temperatura=? , umidita=? , statoRicezione=? ,statoMisura=1, tipoFirma=? WHERE id_str=?");
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = new Date();
			pst.setString(1,sdf.format(d));
			
			pst.setBigDecimal(2, temperatura);
			pst.setBigDecimal(3, umidita);
			pst.setInt(4,sr);
			pst.setInt(5, firma);
			pst.setInt(6, Integer.parseInt(idStrumento));
			
		

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}

	}
	
	public static ArrayList<MisuraDTO> getListaPunti(int id, int idMisura,int id_ripetizione) throws Exception
	{
		
		ArrayList<MisuraDTO> listaMisura=new ArrayList<>();
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * from tblTabelleMisura where id_Tabella = (select id_tabella from tblTabelleMisura where id=?) AND id_misura=? and id_ripetizione = ?");
			pst.setInt(1, id);
			pst.setInt(2, idMisura);
			pst.setInt(3, id_ripetizione);
		
		
			rs=pst.executeQuery();
		MisuraDTO misura=null;
		
		while (rs.next()) {
		
			misura = new MisuraDTO();
			misura.setId(rs.getInt("id"));
			misura.setId_ripetizione(rs.getInt("id_ripetizione"));
			misura.setOrdine(rs.getInt("ordine"));
			misura.setTipoVerifica(rs.getString("tipoVerifica"));
			misura.setUm(rs.getString("um"));
			misura.setValoreCampione(rs.getBigDecimal("valoreCampione"));
			misura.setValoreMedioCampione(rs.getBigDecimal("valoreMedioCampione"));
			misura.setValoreStrumento(rs.getBigDecimal("valoreStrumento"));
			misura.setValoreMedioStrumento(rs.getBigDecimal("valoreMedioStrumento"));
			misura.setScostamento(rs.getBigDecimal("scostamento"));
			misura.setAccettabilita(rs.getBigDecimal("Accettabilita"));
			misura.setEsito(rs.getString("esito"));
			misura.setDescrizioneCampione(rs.getString("desc_campione"));
			misura.setDescrizioneParametro(rs.getString("desc_parametro"));
			misura.setMisura(rs.getBigDecimal("misura"));
			misura.setUm_calc(rs.getString("um_calc"));
			misura.setRisoluzione_campione(rs.getBigDecimal("risoluzione_campione"));
			misura.setRisoluzione_misura(rs.getBigDecimal("risoluzione_misura"));
			misura.setFondoScala(rs.getBigDecimal("fondo_scala"));
			misura.setInterpolazione(rs.getInt("interpolazione"));
			misura.setIncertezza(rs.getBigDecimal("incertezza"));
			misura.setFm(rs.getString("fm"));
			misura.setSelConversione(rs.getInt("selConversione"));
			misura.setLetturaCampione(rs.getBigDecimal("letturaCampione"));
			misura.setSelTolleranza(rs.getInt("selTolleranza"));
			misura.setPercentuale(rs.getBigDecimal("perc_util"));
			misura.setMisuraPrecedente(rs.getString("val_misura_prec"));
			misura.setMisuraCampionePrecedente(rs.getString("val_campione_prec"));
			
			listaMisura.add(misura);
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return listaMisura;
		
	}



	public static void updateValoriRipetibilita(ArrayList<MisuraDTO> listaMisure) {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET scostamento=? , incertezza=? , esito=? WHERE id=?");
			
			for (int i = 0; i < listaMisure.size(); i++) {
			MisuraDTO misura=listaMisure.get(i);
				if(misura.getValoreStrumento()!=null && !misura.getValoreStrumento().equals(""))
				{
					pst.setBigDecimal(1, misura.getScostamento());
					pst.setBigDecimal(2, misura.getIncertezza());
					pst.setString(3, misura.getEsito());
					pst.setInt(4, misura.getId());
					pst.execute();
				}
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			}
		
	}



	public static Vector<ClassificazioneDTO> getVectorClassificazione() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<ClassificazioneDTO> model = new Vector<ClassificazioneDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_classificazione");
			rs=pst.executeQuery();
			
			ClassificazioneDTO classificazione=null;
			while(rs.next())
			{
				classificazione= new ClassificazioneDTO();
				classificazione.setId(rs.getInt("id"));
				classificazione.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(classificazione);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	} 
	
	public static Vector<LuogoVerificaDTO> getVectorLuogoVerifica() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<LuogoVerificaDTO> model = new Vector<LuogoVerificaDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_luogoVerifica");
			rs=pst.executeQuery();
			
			LuogoVerificaDTO luogo=null;
			while(rs.next())
			{
				luogo= new LuogoVerificaDTO();
				luogo.setId(rs.getInt("id"));
				luogo.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(luogo);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	} 
	
	public static Vector<TipoRapportoDTO> getVectorTipoRapporto() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<TipoRapportoDTO> model = new Vector<TipoRapportoDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_tipoRapporto ");
			rs=pst.executeQuery();
			
			TipoRapportoDTO tipoRapporto=null;
			while(rs.next())
			{
				tipoRapporto= new TipoRapportoDTO();
				tipoRapporto.setId(rs.getInt("id"));
				tipoRapporto.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(tipoRapporto);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	} 
	
	public static Vector<TipoStrumentoDTO> getVectorTipoStrumento() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		Vector<TipoStrumentoDTO> model = new Vector<TipoStrumentoDTO>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_tipoStrumento ORDER BY descrizione ASC");
			rs=pst.executeQuery();
			
			TipoStrumentoDTO tipoStrumento=null;
			
			TipoStrumentoDTO ts = new TipoStrumentoDTO();
			ts.setDescrizione("Seleziona tipo strumento");
			ts.setId(0);
			model.addElement(ts);
			while(rs.next())
			{
				tipoStrumento= new TipoStrumentoDTO();
				tipoStrumento.setId(rs.getInt("id"));
				tipoStrumento.setDescrizione(rs.getString("descrizione"));
				
				model.addElement(tipoStrumento);
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return model;
	}



	public static int insertStrumento(StrumentoDTO strumento, String nomeSede) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO tblStrumenti(indirizzo,denominazione,codice_interno,costruttore,modello,classificazione,matricola," +
															   "risoluzione,campo_misura,freq_verifica_mesi,tipoRapporto,statoStrumento," +
																"reparto,utilizzatore,procedura,id_tipo_strumento,note,creato,importato,luogo_verifica) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
																Statement.RETURN_GENERATED_KEYS);
			
			
			pst.setString(1, nomeSede);
			pst.setString(2, strumento.getDenominazione());
			pst.setString(3, strumento.getCodice_interno());
			pst.setString(4, strumento.getCostruttore());
			pst.setString(5, strumento.getModello());
			pst.setString(6, strumento.getClassificazione());
			pst.setString(7, strumento.getMatricola());
			pst.setString(8, strumento.getRisoluzione());
			pst.setString(9, strumento.getCampo_misura());
			pst.setInt(10, strumento.getFreq_taratura());
			pst.setString(11, strumento.getTipoRapporto());
			pst.setString(12, strumento.getStatoStrumento());
			pst.setString(13, strumento.getReparto());
			pst.setString(14, strumento.getUtilizzatore());
			pst.setString(15, strumento.getProcedura());
			pst.setString(16, strumento.getId_tipoStrumento());
			pst.setString(17, strumento.getNote());
			pst.setString(18, "S");
			pst.setString(19, "N");
			pst.setString(20, ""+strumento.getLuogoVerifica());
		
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
		    return rs.getInt(1);
				
		
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
	}



	public static String getNomeSede() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		String nomeSede="";
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select sede FROM tbl_general WHERE id=1");
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				nomeSede= rs.getString(1);
				
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return nomeSede;
	}



	public static boolean checkExecute(String pATH_DB) {
		boolean toReturn=true;

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
	
		
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_general WHERE upload='S'");
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
			toReturn=false;
				
			}
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		}	
		
		return toReturn;
		}



	public static void chiudiMisura(String pATH_DB) {
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("UPDATE tbl_general SET upload='S'");
			
		   pst.execute();	
		

		
		}catch (Exception e) 
		 {
			e.printStackTrace();
		
		 }	
		
		
		}



	public static void deleteRecordMisura(int id) {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("DELETE FROM tblTabelleMisura WHERE id=?");
		  pst.setInt(1, id);	
		   pst.execute();	
		
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		}	
		
		}



	public static void assegnaASFound(ArrayList<MisuraDTO> listaMisure) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("Update tblTabelleMisura SET tipoVerifica=?,calibrazione=? WHERE id=?");
		  
		 for (int i = 0; i < listaMisure.size(); i++) 
		 {
			pst.setString(1,listaMisure.get(i).getTipoVerifica()+" (ASF)");
			pst.setString(2,"ASF");
			pst.setInt(3,listaMisure.get(i).getId() );
			
			pst.execute();
		 }
		
			
		}
		catch (Exception e) 
		 {
			e.printStackTrace();
		}
		finally
		{
			pst.close();
			con.close();
		}	
		
		}

	





	public static void salvaCertificato(int id, String code) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("Update tblStrumenti set nCertificato=? WHERE id=?");
		  pst.setString(1, code);
		  pst.setInt(2, id);
		  
		  pst.execute();
		 
		}
		catch (Exception e) 
		 {
			e.printStackTrace();
			
		}finally
		{
			pst.close();
			con.close();
		}	
		
	}


	public static ArrayList<String> getListaGrandezze() throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		ArrayList<String> listaRitorno= new ArrayList<String>();
		listaRitorno.add("Selezione grandezza");
		try {
			con =getConnection();
			pst=con.prepareStatement("SELECT COUNT(tipo_misura) AS count,tipo_misura FROM tbl_conversione GROUP BY tipo_misura");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				int numero =rs.getInt("count");
				if(numero>0)
				{
					listaRitorno.add(rs.getString("tipo_misura"));
				}
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		return listaRitorno;
	}


	public static ArrayList<String> getListaUM(String param) throws Exception {
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		ArrayList<String> listaRitorno= new ArrayList<String>();
		listaRitorno.add("Selezione Unit� Misura");
		try {
			con =getConnection();
			pst=con.prepareStatement("select distinct (um_a),um from tbl_conversione where tipo_misura=?");
			pst.setString(1, param);
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				listaRitorno.add(rs.getString(1)+" @ "+rs.getString(2));			
				
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return listaRitorno;
	}


	public static boolean controllaMisuraCertificato() throws Exception {
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;

		try {
			con =getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisure a, tblStrumenti b where a.id_str=b.id AND (b.nCertificato='' OR b.nCertificato is null) AND statoMisura=1");
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				
				return false;
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return true;
	}


	public static int updateStrumento(StrumentoDTO strumento) throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		int toReturn=0;
		
		try {
			con =getConnection();
			pst=con.prepareStatement("UPDATE tblStrumenti set denominazione=?,codice_interno=?,costruttore=?,modello=?," +
															  "classificazione=?,matricola=?,risoluzione=?,campo_misura=?," +
															  "freq_verifica_mesi=?,tipoRapporto=?,reparto=?,utilizzatore=?," +
															  "procedura=?,id_tipo_strumento=?,note=?,strumentoModificato='S',luogo_verifica=? WHERE id=?");
		
			pst.setString(1,strumento.getDenominazione());
			pst.setString(2,strumento.getCodice_interno());
			pst.setString(3,strumento.getCostruttore());
			pst.setString(4,strumento.getModello());
			pst.setInt(5,strumento.getIdClassificazione());
			pst.setString(6,strumento.getMatricola());
			pst.setString(7,strumento.getRisoluzione());
			pst.setString(8,strumento.getCampo_misura());
			pst.setInt(9,strumento.getFreq_taratura());
			pst.setInt(10, strumento.getIdTipoRappoto());
			pst.setString(11,strumento.getReparto());
			pst.setString(12,strumento.getUtilizzatore());
			pst.setString(13,strumento.getProcedura());
			pst.setString(14, strumento.getId_tipoStrumento());
			pst.setString(15, strumento.getNote());
			pst.setInt(16, strumento.getLuogoVerifica());
			pst.setInt(17, strumento.get__id());
			
			
			toReturn=pst.executeUpdate();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return toReturn;
	}


	public static void removeTabellaMisura(int idMisura, int id_tabella) throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("DELETE FROM tblTabelleMisura WHERE id_misura=? AND id_tabella=?");
			pst.setInt(1, idMisura);
			pst.setInt(2, id_tabella);
			
			
			pst.execute();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		
	}


	public static HashMap<String, String> getListaIDCampioni() throws Exception {
		HashMap<String, String> listaCampioni = new HashMap<>();
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT DISTINCT(ID_CAMP) FROM tblCampioni");
			
			rs= pst.executeQuery();
			
			while(rs.next())
			{
				listaCampioni.put(rs.getString(1),"");
			}
			
		}catch(Exception e)
		{
			throw e;
		}

		return listaCampioni;
	}



	public static void cambiaStatoMisura(String idStrumento, int stato) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblMisure SET statoMisura=? WHERE id_str=?");
			
			pst.setInt(1, stato);
			pst.setString(2, idStrumento);
		
			
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}


	public static ArrayList<String> getListaTipoGrandezzeByStrumento(String idStrumento) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<String> listaTipoGrandezza= new ArrayList<>();
		
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select id_tipo_grandezza FROM  tbl_ts_tg AS a " +
									 "left join tblStrumenti AS b on a.id_tipo_strumento=b.id_tipo_strumento " +
									 "where b.id=?");
			
			pst.setString(1, idStrumento);
			rs = pst.executeQuery();		
			
			while(rs.next())
			{
				listaTipoGrandezza.add(rs.getString(1));
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaTipoGrandezza;
	}


	public static int getMaxIDStrumento() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id) FROM tblStrumenti");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}



	public static void inserisciRigaTabellaDuplicata(MisuraDTO misura,int idTabella , int idMisura,String tipoProva) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,id_ripetizione,ordine,tipoProva,label,tipoVerifica,applicabile,val_misura_prec,val_campione_prec,dgt,val_descrizione_prec,val_esito_prec,perc_util,selTolleranza,fondo_scala) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,idTabella);
			pst.setInt(3, misura.getId_ripetizione());
			pst.setInt(4, misura.getOrdine());
			pst.setString(5, tipoProva);
			pst.setString(6,misura.getLabel());
			pst.setString(7,misura.getTipoVerifica());
			pst.setString(8, misura.getApplicabile());
			if(misura.getValoreStrumento()!=null)
			{
				pst.setString(9,misura.getValoreStrumento().toPlainString());
			}else 
			{
				pst.setString(9,"");
			}
			pst.setString(10, misura.getDescrizioneCampione());
			
			if(misura.getDgt()!=null) 
			{
				pst.setString(11, misura.getDgt().toPlainString());
			}
			if(misura.getDescrizioneCampione()!=null) 
			{
				pst.setString(12, misura.getDescrizioneCampione());
			}
			if(misura.getEsito()!=null) 
			{
				pst.setString(13, misura.getEsito());
			}
			if(misura.getPercentuale()!=null) 
			{
				pst.setString(14, misura.getPercentuale().toPlainString());
			}
			
			pst.setInt(15, misura.getSelTolleranza());
			
			if(misura.getFondoScala()!=null) 
			{
				pst.setString(16, misura.getFondoScala().toPlainString());
			}
			
			pst.execute();
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static ArrayList<DatiEsterniDTO> getDatiEsterni(String filename) throws Exception {
		ArrayList<DatiEsterniDTO> listaMisure = new ArrayList<>();
		
		DatiEsterniDTO dati=null;
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		try
		{
			con=getConnectionExternal(filename);
			
			pst=con.prepareStatement("select a.id , a.id_str ,b.codice_interno ,b.matricola,b.denominazione,b.modello from tblMisure a " + 
					"left join tblStrumenti b on a.id_str=b.id where statoMisura=1");
			
			rs=pst.executeQuery();
			
		while (rs.next()) {
		
			dati = new DatiEsterniDTO();
			dati.setIdMisura(rs.getInt(1));
			dati.setIdStrumento(rs.getInt(2));
			dati.setCodiceInterno(rs.getString(3));
			dati.setMatricola(rs.getString(4));
			dati.setDenominazione(rs.getString(5));
			dati.setModello(rs.getString(6));
			
			listaMisure.add(dati);
			
		}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return listaMisure;
	}
	public static void riapriMisura() throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE tbl_general set upload='N'");
			pst.execute();
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}	
		
	}
	
}
