package it.calverDesktopSE.gui;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import it.calverDesktopSE.bo.GestioneMisuraBO;
import it.calverDesktopSE.bo.GestioneStampaBO;
import it.calverDesktopSE.bo.GestioneStrumentoBO;
import it.calverDesktopSE.bo.SerialSicurezzaElettrica;
import it.calverDesktopSE.bo.SessionBO;
import it.calverDesktopSE.dao.SQLiteDAO;
import it.calverDesktopSE.dto.ClassificazioneDTO;
import it.calverDesktopSE.dto.LuogoVerificaDTO;
import it.calverDesktopSE.dto.ProvaMisuraDTO;
import it.calverDesktopSE.dto.SicurezzaElettricaDTO;
import it.calverDesktopSE.dto.StrumentoDTO;
import it.calverDesktopSE.dto.TipoRapportoDTO;
import it.calverDesktopSE.dto.TipoStrumentoDTO;
import it.calverDesktopSE.utl.Costanti;
import it.calverDesktopSE.utl.Utility;
import net.miginfocom.swing.MigLayout;


public class PannelloMisuraMaster extends JPanel  implements ChangeListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField denominazioneField;
	private JTextField cod_int_field;
	private JTextField construct_field;
	private JTextField model_field;
	private JTextField matricola_field;
	private JTextField freq_field;
	private JTextField res_field;
	private JTextField campo_misura_field;
	private JTextField id_str_field;
	private JTextField proc_field;
	private JTextArea textAreaNote;
	private JButton btnSalva;
	static JTree tree =null;
	JPopupMenu popupMenu,popupMenuMisura;
	JMenuItem jmit,jmit1,jmitMisura;
	int current;
	
	private JSlider slider_1=null;
	private JSlider slider_2=null;
	private JSlider slider_3=null;
	private JSlider slider_4=null;
	private JSlider slider_5=null;
	private JSlider slider_6=null;
	

	JLabel lblTipoStrumento;
	private JTextField id_field;
	private JTextField address_field;
	private JTextField reparto_field;
	private JTextField utilizzatore_field;
	private JTextField textField_com_port;
	private JTextField textField_delay;
	
	private Splash spl=null;

	JPanel panel_header;
	JPanel panel_dati_str;
	JPanel panel_dati_misura;
	JPanel panel_struttura;
	JPanel panel_avvio;
	JPanel panel_stampa;
	private JPanel panel_tabella,panel_controllo_visuale;
	JSplitPane splitPane;


	StrumentoDTO strumento;
	private ProvaMisuraDTO misura;
	JComboBox<TipoRapportoDTO> comboBox_tipoRapporto;
	JComboBox<ClassificazioneDTO> comboBox_classificazione;
	JComboBox<TipoStrumentoDTO> comboBox_tipo_strumento;
	JComboBox<LuogoVerificaDTO> comboBox_luogoVerifica;

	ArrayList<SicurezzaElettricaDTO>listaSicurezza=null;
	
	Vector<TipoRapportoDTO> vectorTipoRapporto = null;
	Vector<ClassificazioneDTO> vectorClassificazione = null;
	Vector<TipoStrumentoDTO> vectorTipoStrumento = null;
	Vector<LuogoVerificaDTO> vectorLuogoVerifica = null;
	private BufferedImage img;
	static JFrame myFrame=null;
	JTable table_dati_strumento=null;
	JTable tabellaSecurTest=null;
	JFrame f;
	
	ModelTabella model;
	private JTextField textField_classe;
	private JTextField textField_data_ora;
	private JTextField textField_ID_PROVA;
	private JTextField textField_pa;
	private JTextField textField_altro;
	

	public PannelloMisuraMaster(String id) throws Exception
	{
		SessionBO.prevPage="PSS";
		current=-1;
		strumento=GestioneStrumentoBO.getStrumento(SessionBO.idStrumento);


		myFrame=SessionBO.generarFrame;
		

		this.setLayout(new MigLayout("","[grow]","[grow]"));

		JPanel masterPanel = new JPanel();
		
		masterPanel.setLayout(new MigLayout("", "[70%][20%][10%]", "[12%][73%][15%]"));


		
		panel_controllo_visuale=costruisciPanelVisuale();
		panel_header = costruisciPanelHeader();
		panel_tabella = costruisciTabella();
		panel_dati_str = costruisciPanelDatiStr(id,misura);	
		panel_struttura =creaPanelStruttura();
		

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addTab("Misura",panel_tabella);
		
		tabbedPane.addTab("Controllo Visivo", panel_controllo_visuale);
		
		masterPanel.add(panel_header,"cell 0 0,grow");
		masterPanel.add(tabbedPane, "cell 0 1,grow");
		masterPanel.add(panel_dati_str,"cell 1 0 1 3,grow");
		masterPanel.add(panel_struttura,"cell 0 2,grow");


		double height=(SessionBO.heightFrame*73)/100;
		double width=(SessionBO.widthFrame*70)/100;

		masterPanel.setPreferredSize(new Dimension((int)width-50,(int) height/2));

		JScrollPane scroll= new JScrollPane(masterPanel);

		this.add(scroll, "cell 0 0,grow");



		//	btnNewButton.setBounds(225, 40, 184, 23);


	}

	
	private JPanel costruisciPanelVisuale() {
		
		JPanel pannello= new JPanel();
		
		pannello.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Condizioni visive", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannello.setBackground(Color.WHITE);
		pannello.setLayout(new MigLayout("", "[][grow][50px:n][][50:pref:pref,grow]", "[grow][35px][35][35][35][35][35][grow]"));
		
		JLabel lblConduzioneDiProtezione = new JLabel("Conduttore di protezione (solo classe I)");
		lblConduzioneDiProtezione.setFont(new Font("Arial", Font.BOLD, 12));
		pannello.add(lblConduzioneDiProtezione, "cell 0 1,width : 150:");
		
		 slider_1 = new JSlider();
		slider_1.setPaintLabels(true);
		
		slider_1.setPaintTicks(true);
		slider_1.setBackground(Color.WHITE);

		slider_1.setValue(1);
		slider_1.setMinorTickSpacing(1);
		slider_1.setMaximum(2);
		
		  Hashtable<Integer, JLabel> labels = new Hashtable<>();
		   Font font = new Font("Arial", Font.BOLD, 12);
		   
		  JLabel ko= new JLabel("KO");
		  JLabel ok= new JLabel("OK");
		  JLabel na= new JLabel("N/A");
		  
		  ok.setFont(font);
		  ko.setFont(font);
		  na.setFont(font);
		  
		  ok.setForeground(Color.GREEN);
		  ko.setForeground(Color.RED);
		  na.setForeground(Color.ORANGE);
	       
		    labels.put(0, ko);
	        labels.put(1, ok);
	        labels.put(2, na);
	  
	        slider_1.setLabelTable(labels);
	        
	        pannello.add(slider_1, "cell 3 1,width : 120:,alignx center");
		
		JLabel lblInvolucroEParti = new JLabel("Involucro e parti meccaniche");
		lblInvolucroEParti.setFont(new Font("Arial", Font.BOLD, 12));
		pannello.add(lblInvolucroEParti, "cell 0 2");
		
		slider_2 = new JSlider();
		slider_2.setValue(1);
		slider_2.setPaintTicks(true);
		slider_2.setPaintLabels(true);
		slider_2.setMinorTickSpacing(1);
		slider_2.setMaximum(2);
		slider_2.setBackground(Color.WHITE);
		pannello.add(slider_2, "cell 3 2,width :120:,alignx center");
		
		JLabel lblPartiIsolanti = new JLabel("Parti isolanti / Fusibili");
		lblPartiIsolanti.setFont(new Font("Arial", Font.BOLD, 12));
		pannello.add(lblPartiIsolanti, "cell 0 3");
		
		slider_3 = new JSlider();
		slider_3.setValue(1);
		slider_3.setPaintTicks(true);
		slider_3.setPaintLabels(true);
		slider_3.setMinorTickSpacing(1);
		slider_3.setMaximum(2);
		slider_3.setBackground(Color.WHITE);
		pannello.add(slider_3, "cell 3 3,width : 120:,alignx center");
		
		JLabel lblConnettoriEPrese = new JLabel("Connettori e prese");
		lblConnettoriEPrese.setFont(new Font("Arial", Font.BOLD, 12));
		pannello.add(lblConnettoriEPrese, "cell 0 4");
		
		slider_4 = new JSlider();
		slider_4.setValue(1);
		slider_4.setPaintTicks(true);
		slider_4.setPaintLabels(true);
		slider_4.setMinorTickSpacing(1);
		slider_4.setMaximum(2);
		slider_4.setBackground(Color.WHITE);
		pannello.add(slider_4, "cell 3 4,width : 120:,alignx center");
		
		JLabel lblMarchiature = new JLabel("Marchiature");
		lblMarchiature.setFont(new Font("Arial", Font.BOLD, 12));
		pannello.add(lblMarchiature, "cell 0 5");
		
		slider_5 = new JSlider();
		slider_5.setValue(1);
		slider_5.setPaintTicks(true);
		slider_5.setPaintLabels(true);
		slider_5.setMinorTickSpacing(1);
		slider_5.setMaximum(2);
		slider_5.setBackground(Color.WHITE);
		pannello.add(slider_5, "cell 3 5,width : 120:,alignx center");
		
		JLabel lblAltro = new JLabel("Altro");
		lblAltro.setFont(new Font("Arial", Font.BOLD, 12));
		pannello.add(lblAltro, "cell 0 6,alignx left");
		
		textField_altro = new JTextField();
		textField_altro.setFont(new Font("Arial", Font.PLAIN, 12));
		pannello.add(textField_altro, "cell 1 6,growx");
		textField_altro.setColumns(10);
		
		slider_6 = new JSlider();
		slider_6.setValue(1);
		slider_6.setPaintTicks(true);
		slider_6.setPaintLabels(true);
		slider_6.setMinorTickSpacing(1);
		slider_6.setMaximum(2);
		slider_6.setBackground(Color.WHITE);
		pannello.add(slider_6, "cell 3 6,width : 120:,alignx center");
		
		JButton btnSalva_1 = new JButton("Salva");
		
		btnSalva_1.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/save.png")));
		btnSalva_1.setFont(new Font("Arial", Font.BOLD, 14));
		pannello.add(btnSalva_1, "cell 0 7 4 1,alignx center");
		
		slider_2.setLabelTable(labels);
		slider_3.setLabelTable(labels);
		slider_4.setLabelTable(labels);
		slider_5.setLabelTable(labels);
		slider_6.setLabelTable(labels);
		
		slider_1.addChangeListener(this);
		
		
		btnSalva_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					SicurezzaElettricaDTO sicurezza = GestioneMisuraBO.getMisuraSicurezza(SessionBO.idStrumento);
					
					if(slider_1.getValue()==0) 
					{
						sicurezza.setCOND_PROT("KO");
					}
					else if(slider_1.getValue()==1) 
					{
						sicurezza.setCOND_PROT("OK");
					}
					else 
					{
						sicurezza.setCOND_PROT("N/A");
					}
					
					if(slider_2.getValue()==0) 
					{
						sicurezza.setINVOLUCRO("KO");
					}
					else if(slider_2.getValue()==1) 
					{
						sicurezza.setINVOLUCRO("OK");
					}
					else 
					{
						sicurezza.setINVOLUCRO("N/A");
					}
					
					if(slider_3.getValue()==0) 
					{
						sicurezza.setFUSIBILI("KO");
					}
					else if(slider_3.getValue()==1) 
					{
						sicurezza.setFUSIBILI("OK");
					}
					else 
					{
						sicurezza.setFUSIBILI("N/A");
					}
					
					if(slider_4.getValue()==0) 
					{
						sicurezza.setCONNETTORI("KO");
					}
					else if(slider_4.getValue()==1) 
					{
						sicurezza.setCONNETTORI("OK");
					}
					else 
					{
						sicurezza.setCONNETTORI("N/A");
					}
					
					if(slider_5.getValue()==0) 
					{
						sicurezza.setMARCHIATURE("KO");
					}
					else if(slider_5.getValue()==1) 
					{
						sicurezza.setMARCHIATURE("OK");
					}
					else 
					{
						sicurezza.setMARCHIATURE("N/A");
					}
					
					if(slider_6.getValue()==0) 
					{
						sicurezza.setALTRO("KO@"+textField_altro.getText());
					}
					else if(slider_6.getValue()==1) 
					{
						sicurezza.setALTRO("OK@"+textField_altro.getText());
					}
					else 
					{
						sicurezza.setALTRO("N/A@"+textField_altro.getText());
					}
					
					GestioneMisuraBO.updateMisuraSicurezzaElettrica(SessionBO.idStrumento, sicurezza);
					JOptionPane.showMessageDialog(null,"Controllo visivo salvato con successo","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));	
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		
		
		
		return pannello;
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
	
		this.setBounds(0, 0, myFrame.getWidth()-32, myFrame.getHeight()-272);
		g.drawImage(img, 5, 5, myFrame.getWidth()-32, myFrame.getHeight()-272,this);


	}




	private JPanel costruisciPanelHeader() {
		JPanel panel_header = new JPanel();
		panel_header.setLayout(new MigLayout("", "[]15[pref!,grow][10][][grow]", "[top][]"));
		panel_header.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Dati Clienti e Certificato", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_header.setBackground(Color.WHITE);
		panel_header.setBounds(10, 10, 540, 65);

		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Arial", Font.BOLD, 16));
		lblID.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_header.add(lblID, "cell 0 0,alignx right");

		id_field = new JTextField();
		id_field.setFont(new Font("Arial", Font.PLAIN, 16));
		id_field.setEditable(false);
		id_field.setText("0");
		id_field.setColumns(10);
		panel_header.add(id_field,"cell 1 0,width 50:70:,aligny top");

		JLabel lblIndirizzo = new JLabel("Indirizzo");
		lblIndirizzo.setFont(new Font("Arial", Font.BOLD, 16));
		panel_header.add(lblIndirizzo, "cell 3 0");

		address_field = new JTextField();
		address_field.setFont(new Font("Arial", Font.PLAIN, 16));
		address_field.setEditable(false);
		address_field.setText((String) null);
		address_field.setColumns(10);
		panel_header.add(address_field,"flowx,cell 4 0,growx,width 200:300:,aligny top");

		JButton btnGrafico_1 = new JButton("Carica da Strumento");
		btnGrafico_1.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/graf2.png")));
		btnGrafico_1.setFont(new Font("Arial", Font.BOLD, 14));
		btnGrafico_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable(){
					public void run() 
					{
						try
						{
							f=new JFrame();
							f.setSize(800,400);
							f.setTitle("Seleziona");
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							int x = (dim.width-800) / 2;
							int y = (dim.height-400) / 2;
							f.setLocation(x, y);

							f.getContentPane().add(costruisciLetturaStrumento());

							URL iconURL = this.getClass().getResource("/image/logo.png");


							ImageIcon img = new ImageIcon(iconURL);
							f.setIconImage(img.getImage());

							f.setDefaultCloseOperation(1);
							f.setVisible(true);

						}
						catch(Exception ex)
						{
							//	GeneralGUI.printException(ex);
							ex.printStackTrace();
						}
					}



				});
			}
		});
		panel_header.add(btnGrafico_1, "cell 4 0,aligny top");


		vectorTipoRapporto=SQLiteDAO.getVectorTipoRapporto();

		id_str_field = new JTextField();
		id_str_field.setEditable(false);
		id_str_field.setColumns(10);

		vectorLuogoVerifica=SQLiteDAO.getVectorLuogoVerifica();
		//	panel_header.add(id_str_field,"cell 4 1,width 50:100:");

		return panel_header;

	}

	private JPanel costruisciTabella() {


		JPanel panel_tabella = new JPanel();
		panel_tabella.setBorder(new TitledBorder(new LineBorder(new Color(215, 23, 29), 2, true), "Tabella Misura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(215, 23, 29)));
		panel_tabella.setBackground(Color.WHITE);
		panel_tabella.setLayout(new MigLayout("", "[grow][grow]", "[][][grow]"));

		tabellaSecurTest = new JTable();
		tabellaSecurTest.setDefaultRenderer(Object.class, new MyCellRenderer());
		tabellaSecurTest.setFont(new Font("Arial", Font.BOLD, 12));
		tabellaSecurTest.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		tabellaSecurTest.setRowHeight(21);
	
		
		model = new ModelTabella();
		
		JLabel lblNewLabel_1 = new JLabel("Data Ora Misura");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		panel_tabella.add(lblNewLabel_1, "flowx,cell 1 0");
		
		textField_data_ora = new JTextField();
		textField_data_ora.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_data_ora.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_data_ora.setEditable(false);
		panel_tabella.add(textField_data_ora, "cell 1 0,width :150:");
		textField_data_ora.setColumns(10);
		
		JLabel lblIdProva = new JLabel("ID PROVA");
		lblIdProva.setFont(new Font("Arial", Font.BOLD, 14));
		panel_tabella.add(lblIdProva, "flowx,cell 0 0");
		
		textField_ID_PROVA = new JTextField();
		textField_ID_PROVA.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_ID_PROVA.setEditable(false);
		panel_tabella.add(textField_ID_PROVA, "cell 0 0,width : 150:");
		textField_ID_PROVA.setColumns(10);
		
		tabellaSecurTest.setModel(model);
		TableColumn column = tabellaSecurTest.getColumnModel().getColumn(tabellaSecurTest.getColumnModel().getColumnIndex("Misurazione"));
		column.setPreferredWidth(300);
		
		JLabel lblClasse = new JLabel("Classe");
		lblClasse.setFont(new Font("Arial", Font.BOLD, 16));
		panel_tabella.add(lblClasse, "cell 0 1");
		
		textField_classe = new JTextField();
		textField_classe.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_tabella.add(textField_classe, "flowx,cell 0 1,width : 300:");
		textField_classe.setColumns(10);
		
	
		
		JScrollPane scroll = new JScrollPane(tabellaSecurTest);
		panel_tabella.add(scroll,"cell 0 2 2 1,grow");
		
		JLabel lblPartiApplicate = new JLabel("Parti Applicate");
		lblPartiApplicate.setFont(new Font("Arial", Font.BOLD, 16));
		panel_tabella.add(lblPartiApplicate, "cell 1 1");
		
		textField_pa = new JTextField();
		textField_pa.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_tabella.add(textField_pa, "cell 1 1,width : 300:");
		textField_pa.setColumns(10);
		
		try 
		{
		SicurezzaElettricaDTO sicurezza = GestioneMisuraBO.getMisuraSicurezza(SessionBO.idStrumento);
		
		if(sicurezza!=null && sicurezza.getID_PROVA()!=null) 
		{
			textField_ID_PROVA.setText(sicurezza.getID_PROVA());
			textField_classe.setText(sicurezza.getSK());
			textField_pa.setText(sicurezza.getPARTI_APPLICATE());
			textField_data_ora.setText(sicurezza.getDATA());
			riempiModel(sicurezza);
			
		}
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	
		
		
		
	
		
	
		
		return panel_tabella;

	}

	public void riempiModel(SicurezzaElettricaDTO sicurezza) {
		
		int size=model.getRowCount();
		
		for (int i = 0; i < size; i++) {
		
			model.removeRow(0);
		}
		
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore Resistenza Conduttore di protezione", 0, 0);
		model.setValueAt(toString(sicurezza.getR_SL()), 0, 1);
		model.setValueAt(toString(sicurezza.getR_SL_GW()), 0, 2);
		model.setValueAt(returnEsit(sicurezza.getR_SL(),sicurezza.getR_SL_GW(),0), 0, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore Resistenza di isolamento", 1, 0);
		model.setValueAt(toString(sicurezza.getR_ISO()), 1, 1);
		model.setValueAt(toString(sicurezza.getR_ISO_GW()), 1, 2);
		model.setValueAt(returnEsit(sicurezza.getR_ISO(),sicurezza.getR_ISO_GW(),1), 1, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore tensione di verifica Resistenza di isolamento",2, 0);
		model.setValueAt(toString(sicurezza.getU_ISO()), 2, 1);
		model.setValueAt(toString(sicurezza.getU_ISO_GW()), 2, 2);
		model.setValueAt(returnEsit(sicurezza.getU_ISO(),sicurezza.getU_ISO_GW(),1), 2, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente differenziale tra L e N", 3, 0);
		model.setValueAt(toString(sicurezza.getI_DIFF()), 3, 1);
		model.setValueAt(toString(sicurezza.getI_DIFF_GW()), 3, 2);
		model.setValueAt(returnEsit(sicurezza.getI_DIFF(),sicurezza.getI_DIFF_GW(),0), 3, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente dispersione involucro", 4, 0);
		model.setValueAt(toString(sicurezza.getI_EGA()), 4, 1);
		model.setValueAt(toString(sicurezza.getI_EGA_GW()), 4, 2);
		model.setValueAt(returnEsit(sicurezza.getI_EGA(),sicurezza.getI_EGA_GW(),0), 4, 3);
		
		
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente dispersione parte applicata", 5, 0);
		model.setValueAt(toString(sicurezza.getI_EPA()), 5, 1);
		model.setValueAt(toString(sicurezza.getI_EPA_GW()), 5, 2);
		model.setValueAt(returnEsit(sicurezza.getI_EPA(),sicurezza.getI_EPA_GW(),0), 5, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente AC dispersione involucro metodo diretto (in funzione)", 6, 0);
		model.setValueAt(toString(sicurezza.getI_GA()), 6, 1);
		model.setValueAt(toString(sicurezza.getI_GA_GW()), 6, 2);
		model.setValueAt(returnEsit(sicurezza.getI_GA(),sicurezza.getI_GA_GW(),0), 6, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente AC dispersione involucro metodo diretto (rete invertita)", 7, 0);
		model.setValueAt(toString(sicurezza.getI_GA_SFC()), 7, 1);
		model.setValueAt(toString(sicurezza.getI_GA_SFC_GW()), 7, 2);
		model.setValueAt(returnEsit(sicurezza.getI_GA_SFC(),sicurezza.getI_GA_SFC_GW(),0), 7, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente AC dispersione parte applicata (in funzione)", 8, 0);
		model.setValueAt(toString(sicurezza.getI_PA_AC()), 8, 1);
		model.setValueAt(toString(sicurezza.getI_PA_AC_GW()), 8, 2);
		model.setValueAt(returnEsit(sicurezza.getI_PA_AC(),sicurezza.getI_PA_AC_GW(),0), 8, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Valore corrente DC dispersione parte applicata (in funzione)", 9, 0);
		model.setValueAt(toString(sicurezza.getI_PA_DC()), 9, 1);
		model.setValueAt(toString(sicurezza.getI_PA_DC_GW()), 9, 2);
		model.setValueAt(returnEsit(sicurezza.getI_PA_DC(),sicurezza.getI_PA_DC_GW(),0), 9, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Tensione di verifica", 10, 0);
		model.setValueAt(toString(sicurezza.getPSPG()), 10, 1);
		model.setValueAt("-", 10, 2);
		model.setValueAt("-", 10, 3);
		
		model.addRow(new Object[0]);
		model.setValueAt("Tensione nominale", 11, 0);
		model.setValueAt(toString(sicurezza.getUBEZ_GW()), 11, 1);
		model.setValueAt("-", 11, 2);
		model.setValueAt("-", 11, 3);
		
		
		if(sicurezza.getCOND_PROT().equals("OK"))
		{
			slider_1.setValue(1);
		}
		else if(sicurezza.getCOND_PROT().equals("KO"))
		{
			slider_1.setValue(0);
		}
		else 
		{
			slider_1.setValue(2);
		}
		
		if(sicurezza.getINVOLUCRO().equals("OK"))
		{
			slider_2.setValue(1);
		}
		else if(sicurezza.getINVOLUCRO().equals("KO"))
		{
			slider_2.setValue(0);
		}else 
		{
			slider_2.setValue(2);
		}
		
		if(sicurezza.getFUSIBILI().equals("OK"))
		{
			slider_3.setValue(1);
		}
		else if(sicurezza.getFUSIBILI().equals("KO"))
		{
			slider_3.setValue(0);
		}
		else
		{
			slider_3.setValue(2);
		}
		
		if(sicurezza.getCONNETTORI().equals("OK"))
		{
			slider_4.setValue(1);
		}
		else if(sicurezza.getCONNETTORI().equals("KO"))
		{
			slider_4.setValue(0);
		}
		else 
		{
			slider_4.setValue(2);	
		}
		
		if(sicurezza.getMARCHIATURE().equals("OK"))
		{
			slider_5.setValue(1);
		}
		else if(sicurezza.getMARCHIATURE().equals("KO"))
		{
			slider_5.setValue(0);
		}
		else 
		{
			slider_5.setValue(2);
		}
		
		String[] altro=sicurezza.getALTRO().split("@");
		
		if(sicurezza.getALTRO().indexOf("OK")>=0)
		{
			slider_6.setValue(1);
		
			if(altro.length>1) 
			{
				textField_altro.setText(altro[1]);
			}
		}
		else if(sicurezza.getALTRO().indexOf("KO")>=0)
		{
			slider_6.setValue(0);
			
			if(altro.length>1) 
			{
				textField_altro.setText(altro[1]);
			}
		}
		else 
		{
			slider_6.setValue(2);
			
			if(altro.length>1) 
			{
				textField_altro.setText(altro[1]);
			}
		}
		
	}


	private Object toString(String text) {
	
		if(text ==null || text.length()==0) 
		{
			return "-";
		}
		return text;
	}


	private String returnEsit(String r_SL, String r_SL_GW ,int i) {
	/*
	 * 0 - confronto limite>valore
	 * 1 - confronto limite<valore 
	 */
		
		Double valore=null;
		Double limite = null;
		if(r_SL!=null && r_SL.length()>0 && r_SL_GW!=null && r_SL_GW.length()>0 ) 
		{
			
			 valore = getNumber(r_SL);
			 limite = getNumber(r_SL_GW);
		
		
		if(i==0) 
		{
			if(r_SL.indexOf("µ")>1) 
			{
				valore=valore/1000;
			}
			if(valore<=limite) 
			{
				return "OK";
			}else 
			{
				return "KO";
			}
		}else 
		{
			if(valore>limite) 
			{
				return "OK";
			}else 
			{
				return "KO";
			}
		}
	 }
		return "-";
	}


	private Double getNumber(String test) {
	try {
		Pattern p = Pattern.compile("(-?[0-9]+(?:[,.][0-9]+)?)");
		Matcher m = p.matcher(test);
		while (m.find()) {
		  return Double.parseDouble(m.group());
		}
	}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	


	private JPanel costruisciPanelDatiStr(String id, ProvaMisuraDTO misura) {
		JPanel panel_dati_str= new JPanel();
		panel_dati_str.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Dati Strumento", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_dati_str.setBackground(Color.white);
		panel_dati_str.setBounds(10, 85, 540, 250);

		panel_dati_str.setLayout(new MigLayout("", "[]15[grow][10][][]", "[][][][][][][][][][][][][][][][][]"));

		JLabel lblNewLabel = new JLabel("Denominazione");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblNewLabel.setBounds(5, 10, 78, 14);
		panel_dati_str.add(lblNewLabel, "cell 0 0,aligny baseline");

		vectorClassificazione=SQLiteDAO.getVectorClassificazione();

		vectorTipoStrumento=SQLiteDAO.getVectorTipoStrumento();

		denominazioneField = new JTextField();
		denominazioneField.setFont(new Font("Arial", Font.PLAIN, 16));
		denominazioneField.setEditable(false);
		//	denominazioneField.setBounds(85, 9, 180, 17);
		panel_dati_str.add(denominazioneField,"cell 1 0,growx,width 100:180:");
		denominazioneField.setColumns(10);

		JLabel lblCodiceInterno = new JLabel("Codice interno");
		lblCodiceInterno.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblCodiceInterno.setBounds(5, 35, 71, 14);
		panel_dati_str.add(lblCodiceInterno, "cell 0 1");

		cod_int_field = new JTextField();
		cod_int_field.setDocument(new JTextFieldLimit(22));
		cod_int_field.setFont(new Font("Arial", Font.PLAIN, 16));
		cod_int_field.setEditable(false);
		cod_int_field.setColumns(10);
		//	cod_int_field.setBounds(85, 34, 180, 17);
		panel_dati_str.add(cod_int_field,"cell 1 1,width 100:180:250,grow");

		int indiceTipoStrumento=Utility.getIndicaTipoStrumento(vectorTipoStrumento,strumento.getId_tipoStrumento());
		int indiceTipoRapporto=Utility.getIndicaTipoRapporto(vectorTipoRapporto,strumento.getTipoRapporto());
		int indiceClass=Utility.getIndicaClassificazione(vectorClassificazione,strumento.getClassificazione());
		int indiceLuogoVerifica=Utility.getIndicaLuogoVerifica(vectorLuogoVerifica,strumento.getLuogoVerifica());

		JLabel lblMatricola = new JLabel("Matricola");
		lblMatricola.setFont(new Font("Arial", Font.BOLD, 14));
		lblMatricola.setHorizontalAlignment(SwingConstants.RIGHT);
		//	lblMatricola.setBounds(275, 10, 43, 14);
		panel_dati_str.add(lblMatricola, "cell 0 2");

		matricola_field = new JTextField();
		matricola_field.setDocument(new JTextFieldLimit(22));
		matricola_field.setFont(new Font("Arial", Font.PLAIN, 16));
		matricola_field.setEditable(false);
		matricola_field.setColumns(10);
		//	matricola_field.setBounds(349, 9, 180, 17);
		panel_dati_str.add(matricola_field,"cell 1 2,width 100:180:250,grow");
		matricola_field.setText(strumento.getMatricola());

		JLabel lblCostruttore = new JLabel("Costruttore");
		lblCostruttore.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblCostruttore.setBounds(5, 60, 71, 14);
		panel_dati_str.add(lblCostruttore, "cell 0 3");

		construct_field = new JTextField();
		construct_field.setFont(new Font("Arial", Font.PLAIN, 16));
		construct_field.setEditable(false);
		construct_field.setColumns(10);
		//	construct_field.setBounds(85, 59, 180, 17);
		panel_dati_str.add(construct_field,"cell 1 3,width 100:180:,grow");
		construct_field.setText(strumento.getCostruttore());

		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Arial", Font.BOLD, 14));
		//	lblModello.setBounds(5, 85, 36, 14);
		panel_dati_str.add(lblModello, "cell 0 4");

		model_field = new JTextField();
		model_field.setFont(new Font("Arial", Font.PLAIN, 16));
		model_field.setEditable(false);
		//	model_field.setBounds(85, 84, 180, 17);
		panel_dati_str.add(model_field,"cell 1 4,width 100:180:,grow");
		model_field.setColumns(10);
		model_field.setText(strumento.getModello());


		JLabel lblReparto = new JLabel("Reparto");
		lblReparto.setFont(new Font("Arial", Font.BOLD, 14));
		lblReparto.setBounds(5, 110, 46, 14);
		panel_dati_str.add(lblReparto, "cell 0 5");



		reparto_field = new JTextField();
		reparto_field.setFont(new Font("Arial", Font.PLAIN, 16));
		reparto_field.setEditable(false);
		reparto_field.setText((String) null);
		reparto_field.setColumns(10);
		//	reparto_field.setBounds(85, 107, 180, 17);
		panel_dati_str.add(reparto_field,"cell 1 5,width 100:180:,grow");
		reparto_field.setText(strumento.getReparto());

		try 
		{
			denominazioneField.setText(strumento.getDenominazione());
			cod_int_field.setText(strumento.getCodice_interno());
			address_field.setText(strumento.getIndirizzo());
			id_field.setText(""+strumento.get__id());
			id_str_field.setText(strumento.getId_tipoStrumento());

			JLabel lblUtilizzatore = new JLabel("Utilizzatore");
			lblUtilizzatore.setFont(new Font("Arial", Font.BOLD, 14));
			//	lblUtilizzatore.setBounds(5, 135, 53, 14);
			panel_dati_str.add(lblUtilizzatore, "cell 0 6");

			utilizzatore_field = new JTextField();
			utilizzatore_field.setFont(new Font("Arial", Font.PLAIN, 16));
			utilizzatore_field.setEditable(false);
			utilizzatore_field.setText((String) null);
			utilizzatore_field.setColumns(10);
			//	utilizzatore_field.setBounds(85, 132, 180, 17);
			panel_dati_str.add(utilizzatore_field,"cell 1 6,width 100:180:,grow");
			utilizzatore_field.setText(strumento.getUtilizzatore());

			JLabel lblProcedura = new JLabel("Procedura");
			lblProcedura.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblProcedura, "cell 0 7");
			lblProcedura.setBounds(10, 11, 54, 14);




			proc_field = new JTextField();
			proc_field.setFont(new Font("Arial", Font.PLAIN, 16));
			panel_dati_str.add(proc_field, "cell 1 7,grow");
			proc_field.setEditable(false);
			proc_field.setText((String) null);
			proc_field.setColumns(10);
			proc_field.setBounds(85, 12, 75, 17);
			proc_field.setText(strumento.getProcedura());
			btnSalva = new JButton("Salva");
			btnSalva.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/save.png")));
			btnSalva.setFont(new Font("Arial", Font.BOLD, 16));


			btnSalva.setVisible(false);

			JLabel lblFreqTar = new JLabel("Frequenza");
			lblFreqTar.setFont(new Font("Arial", Font.BOLD, 14));
			//	lblFreqTar.setBounds(275, 110, 57, 14);
			panel_dati_str.add(lblFreqTar, "cell 0 8");

			freq_field = new JTextField();
			freq_field.setFont(new Font("Arial", Font.PLAIN, 16));
			freq_field.setEditable(false);
			freq_field.setColumns(10);
			//	freq_field.setBounds(349, 109, 180, 17);
			panel_dati_str.add(freq_field,"cell 1 8,width :50:");
			freq_field.setText(""+strumento.getFreq_taratura());

			JLabel cam_mis_field = new JLabel("Campo Misura");
			cam_mis_field.setFont(new Font("Arial", Font.BOLD, 14));
			//	cam_mis_field.setBounds(274, 35, 67, 14);
			panel_dati_str.add(cam_mis_field, "cell 0 9");

			campo_misura_field = new JTextField();
			campo_misura_field.setFont(new Font("Arial", Font.PLAIN, 16));
			campo_misura_field.setEditable(false);
			campo_misura_field.setColumns(10);
			//	campo_misura_Field.setBounds(349, 34, 180, 17);
			panel_dati_str.add(campo_misura_field,"cell 1 9,width 100:180:");
			campo_misura_field.setText(strumento.getCampo_misura());

			JLabel lblRisoluz = new JLabel("Risoluz.");
			lblRisoluz.setFont(new Font("Arial", Font.BOLD, 14));
			//	lblRisoluz.setBounds(275, 60, 43, 14);
			panel_dati_str.add(lblRisoluz, "cell 0 10");

			res_field = new JTextField();
			res_field.setFont(new Font("Arial", Font.PLAIN, 16));
			res_field.setEditable(false);
			res_field.setColumns(10);
			//	res_field.setBounds(349, 59, 180, 17);
			panel_dati_str.add(res_field,"cell 1 10,width 100:180:200");
			res_field.setText(strumento.getRisoluzione());

			JLabel lblCalss = new JLabel("Classificazione");
			lblCalss.setFont(new Font("Arial", Font.BOLD, 14));
			//	lblCalss.setBounds(275, 85, 69, 14);
			panel_dati_str.add(lblCalss, "cell 0 11");

			comboBox_classificazione = new JComboBox(vectorClassificazione);
			comboBox_classificazione.setFont(new Font("Arial", Font.PLAIN, 16));
			comboBox_classificazione.setRenderer( new ItemRendererClass());
			comboBox_classificazione.setEnabled(false);
			panel_dati_str.add(comboBox_classificazione, "flowx,cell 1 11,width :100:");
			comboBox_classificazione.setSelectedIndex(indiceClass);

			JLabel lblTipoRapporto = new JLabel("Tipo Rapporto");
			lblTipoRapporto.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblTipoRapporto, "cell 0 12");

			comboBox_tipoRapporto = new JComboBox(vectorTipoRapporto);
			comboBox_tipoRapporto.setFont(new Font("Arial", Font.PLAIN, 16));
			panel_dati_str.add(comboBox_tipoRapporto, "flowx,cell 1 12,width :100:");
			comboBox_tipoRapporto.setRenderer( new ItemRendererTipoRapporto());
			comboBox_tipoRapporto.setEnabled(false);
			comboBox_tipoRapporto.setSelectedIndex(indiceTipoRapporto);

			JLabel lblIdts = new JLabel("Luogo Verifica");
			lblIdts.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblIdts, "cell 0 13");

			comboBox_luogoVerifica = new JComboBox(vectorLuogoVerifica);
			comboBox_luogoVerifica.setFont(new Font("Arial", Font.PLAIN, 16));
			panel_dati_str.add(comboBox_luogoVerifica, "cell 1 13,width :100:");
			comboBox_luogoVerifica.setRenderer( new ItemRendererLuogoVerificao());
			comboBox_luogoVerifica.setEnabled(false);
			comboBox_luogoVerifica.setSelectedIndex(indiceLuogoVerifica);

			lblTipoStrumento = new JLabel("Tipo Strumento");
			lblTipoStrumento.setFont(new Font("Arial", Font.BOLD, 14));
			panel_dati_str.add(lblTipoStrumento, "cell 0 14");
			lblTipoStrumento.setVisible(false);

			comboBox_tipo_strumento = new JComboBox<TipoStrumentoDTO>(vectorTipoStrumento);
			comboBox_tipo_strumento.setFont(new Font("Arial", Font.PLAIN, 16));
			comboBox_tipo_strumento.setRenderer( new ItemRendererTipoStrumento());
			panel_dati_str.add(comboBox_tipo_strumento, "cell 1 14,width 100:180:200");

			comboBox_tipo_strumento.setSelectedIndex(indiceTipoStrumento);
			comboBox_tipo_strumento.setVisible(false);

			JLabel lblNote = new JLabel("Note:");
			lblNote.setFont(new Font("Arial", Font.BOLD, 14));
			//		lblNote.setBounds(5, 160, 36, 14);
			panel_dati_str.add(lblNote, "cell 0 15");

			JScrollPane scrollPane = new JScrollPane();
			//		scrollPane.setBounds(85, 160, 444, 79);
			panel_dati_str.add(scrollPane,"cell 1 15,growx,height :100:100");

			textAreaNote = new JTextArea();
			textAreaNote.setFont(new Font("Arial", Font.BOLD, 18));
			textAreaNote.setLineWrap(true);
			textAreaNote.setEditable(false);
			scrollPane.setViewportView(textAreaNote);
			textAreaNote.setText(strumento.getNote());
			panel_dati_str.add(btnSalva, "flowx,cell 1 16,alignx center");

			JButton btnModifica = new JButton("Modifica");
			btnModifica.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/update.png")));
			btnModifica.setFont(new Font("Arial", Font.BOLD, 16));
			btnModifica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					comboBox_tipoRapporto.setEnabled(true);
					denominazioneField.setEditable(true);
					cod_int_field.setEditable(true);
					construct_field.setEditable(true);
					model_field.setEditable(true);
					reparto_field.setEditable(true);
					utilizzatore_field.setEditable(true);
					matricola_field.setEditable(true);
					campo_misura_field.setEditable(true);
					res_field.setEditable(true);
					comboBox_classificazione.setEnabled(true);
					freq_field.setEditable(true);

					proc_field.setEditable(true);

					textAreaNote.setEditable(true);

					lblTipoStrumento.setVisible(true);
					comboBox_tipo_strumento.setVisible(true);
					comboBox_luogoVerifica.setEnabled(true);
					btnSalva.setVisible(true);

				}
			});
			panel_dati_str.add(btnModifica, "cell 1 16,alignx center");

			btnSalva.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) 
				{

					int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi modificare i dati dello strumento ?","Salvataggio",JOptionPane.YES_NO_OPTION,0,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));	

					if(scelta==0)
					{		


						try
						{



							int freq =Integer.parseInt(freq_field.getText());
							comboBox_tipoRapporto.setEnabled(false);
							denominazioneField.setEditable(false);
							cod_int_field.setEditable(false);
							construct_field.setEditable(false);
							model_field.setEditable(false);
							reparto_field.setEditable(false);
							utilizzatore_field.setEditable(false);
							matricola_field.setEditable(false);
							campo_misura_field.setEditable(false);
							res_field.setEditable(false);
							comboBox_classificazione.setEnabled(false);
							freq_field.setEditable(false);

							textAreaNote.setEditable(false);
							btnSalva.setVisible(false);
							lblTipoStrumento.setVisible(false);
							comboBox_tipo_strumento.setVisible(false);
							comboBox_luogoVerifica.setEnabled(false);
							proc_field.setEditable(false);

							TipoRapportoDTO tipoRpp=(TipoRapportoDTO)comboBox_tipoRapporto.getSelectedItem();
							ClassificazioneDTO clas=(ClassificazioneDTO)comboBox_classificazione.getSelectedItem();
							LuogoVerificaDTO luogo =(LuogoVerificaDTO)comboBox_luogoVerifica.getSelectedItem();

							strumento.setIdTipoRappoto(tipoRpp.getId());
							SessionBO.tipoRapporto=tipoRpp.getDescrizione();
							strumento.setTipoRapporto(tipoRpp.getDescrizione());
							strumento.setDenominazione(denominazioneField.getText());
							strumento.setCodice_interno(cod_int_field.getText());
							strumento.setCostruttore(construct_field.getText());
							strumento.setModello(model_field.getText());
							strumento.setReparto(reparto_field.getText());
							strumento.setUtilizzatore(utilizzatore_field.getText());
							strumento.setMatricola(matricola_field.getText());
							strumento.setCampo_misura(campo_misura_field.getText());
							strumento.setRisoluzione(res_field.getText());
							strumento.setIdClassificazione(clas.getId());
							strumento.setLuogoVerifica(luogo.getId());
							strumento.setFreq_taratura(freq);
							strumento.setProcedura(proc_field.getText());
							strumento.setNote(textAreaNote.getText());
							TipoStrumentoDTO item = (TipoStrumentoDTO)comboBox_tipo_strumento.getSelectedItem();
							strumento.setId_tipoStrumento(""+item.getId());

							id_str_field.setText(""+item.getId());



							int toReturn=GestioneStrumentoBO.updateStrumento(strumento);

							if(toReturn==1)
							{
								JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));	
							}

						}
						catch (NumberFormatException nfe) 
						{
							JOptionPane.showMessageDialog(null,"La frequenza può essere solo numerica","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
						}
						catch (Exception e) 
						{
							PannelloConsole.printException(e);
							e.printStackTrace();
						}

					}	

				}
			});

			SessionBO.tipoRapporto=strumento.getTipoRapporto();	

		} catch (Exception e) 
		{
			PannelloConsole.printException(e);
			e.printStackTrace();
		}

		return panel_dati_str;
	}

	private JPanel creaPanelStruttura() {

		final JPanel panel_m_build = new JPanel();
		panel_m_build.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Termina Misura", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_m_build.setBackground(Color.white);
		panel_m_build.setLayout(new MigLayout("", "[50%][grow]", "[grow]"));


		JButton btnStampa = new JButton("Etichetta");
		panel_m_build.add(btnStampa, "cell 0 0,alignx center,height : 50:");
		btnStampa.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/print.png")));
		btnStampa.setFont(new Font("Arial", Font.BOLD, 16));



		btnStampa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{

					if(textField_data_ora.getText().length()!=0 && model.getRowCount()>0) 
					{
						GestioneStampaBO.stampaEtichetta(strumento,textField_data_ora.getText(),esito(model));
					}else 
					{
					JOptionPane.showMessageDialog(null,"Nessuna misura presente","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}

			
			
			private String esito(ModelTabella model) {
				String esito ="IDONEO";
				
				for (int i = 0; i <model.getRowCount(); i++) {
					
					String ex=model.getValueAt(i, 3).toString();
					
					if(ex.equals("KO"))
					{
						return "NON IDONEO";
					}
				}
				return esito;
			}


		});

		final JButton btnChiudiMisura = new JButton("Termina Misura");
		btnChiudiMisura.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		btnChiudiMisura.setFont(new Font("Arial", Font.BOLD, 16));
		panel_m_build.add(btnChiudiMisura, "cell 1 0,alignx center,height : 50:");

		btnChiudiMisura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 

			{

				int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi terminare la misura ?","Salvataggio",JOptionPane.YES_NO_OPTION,0,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));

				if(scelta==0)
				{

					if(true)
					{
						try 
						{
							GestioneMisuraBO.terminaMisura(SessionBO.idStrumento,textField_classe.getText(),textField_pa.getText());
							

							JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));


							
						} catch (Exception e) {
							e.printStackTrace();
						}
						

					}else
					{
						JOptionPane.showMessageDialog(null,"Per terminare la misura, tutti i punti devono essere valorizzati","Salvataggio",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					}
				}

			}

		});




		return panel_m_build;

	}

	private JPanel costruisciLetturaStrumento() {

		final JPanel pannelloMisura = new JPanel();
		
		
		pannelloMisura.setLayout(new MigLayout("", "[grow][grow][grow][][][]", "[][grow][]"));
		
		JLabel lblCom = new JLabel("COM:");
		lblCom.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloMisura.add(lblCom, "cell 0 0,alignx trailing");
		
		textField_com_port = new JTextField();
		textField_com_port.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloMisura.add(textField_com_port, "cell 1 0, width :50:");
		textField_com_port.setColumns(10);
		
		
		JLabel lblBuffers = new JLabel("Buffer (sec)");
		lblBuffers.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloMisura.add(lblBuffers, "cell 2 0,alignx left");
		
		textField_delay = new JTextField("3");
		textField_delay.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloMisura.add(textField_delay, "cell 2 0, width :50:");
		textField_delay.setColumns(10);
		
		JButton btnLeggiDati = new JButton("Leggi Dati");
		btnLeggiDati.setFont(new Font("Arial", Font.BOLD, 12));
		btnLeggiDati.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/search_24.png")));
		pannelloMisura.add(btnLeggiDati, "cell 5 0");
		
		
		MyObjectManager manager = new MyObjectManager();
		table_dati_strumento = new JTable(new ModelTabellaLetturaDati (manager));
		
		table_dati_strumento.setFont(new Font("Arial", Font.PLAIN, 16));
		table_dati_strumento.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

		TableColumn column = table_dati_strumento.getColumnModel().getColumn(0);
		column.setCellEditor(new RadioButtonCellEditorRenderer());
		column.setCellRenderer(new RadioButtonCellEditorRenderer());

		table_dati_strumento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_dati_strumento.setDefaultRenderer(Object.class, new MyCellRenderer());
		table_dati_strumento.setRowHeight(25);
		
		table_dati_strumento.getColumnModel().getColumn(0).setPreferredWidth(50);
		table_dati_strumento.getColumnModel().getColumn(2).setPreferredWidth(50);
		table_dati_strumento.getColumnModel().getColumn(3).setPreferredWidth(50);
		
		JScrollPane scrollPane = new JScrollPane(table_dati_strumento);
		
		pannelloMisura.add(scrollPane, "cell 0 1 6 1,grow");
		
		JButton btnSeleziona = new JButton("Seleziona");
		btnSeleziona.setFont(new Font("Arial", Font.BOLD, 12));
		btnSeleziona.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/continue.png")));
		pannelloMisura.add(btnSeleziona, "cell 0 2 6 1");
		
		
		
		
		btnLeggiDati.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try
				{
					spl =	 new Splash(pannelloMisura);
					
					spl.execute();
					

					Thread thread = new Thread(new ThreadLettura());
					thread.start();
					
				
				}catch(Exception ex)
				{
					ex.printStackTrace();
					PannelloConsole.printException(ex);
				}
				
			}
		});
		
		btnSeleziona.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String id="";
				for (int i = 0; i < table_dati_strumento.getRowCount(); i++) {
					Boolean chked = Boolean.valueOf(table_dati_strumento.getValueAt(i, 0).toString());
				//	String dataCol1 = table_dati_strumento.getValueAt(i, 1).toString();
					if (chked) {
						id=""+i;
						break;
					}
				}
				try {		
					if(!id.equals(""))	
					{			
						SicurezzaElettricaDTO sicurezza=listaSicurezza.get(Integer.parseInt(id));
						
						if(slider_1.getValue()==0) 
						{
							sicurezza.setCOND_PROT("KO");
						}else 
						{
							sicurezza.setCOND_PROT("OK");
						}
						
						if(slider_2.getValue()==0) 
						{
							sicurezza.setINVOLUCRO("KO");
						}else 
						{
							sicurezza.setINVOLUCRO("OK");
						}
						
						if(slider_3.getValue()==0) 
						{
							sicurezza.setFUSIBILI("KO");
						}else 
						{
							sicurezza.setFUSIBILI("OK");
						}
						
						if(slider_4.getValue()==0) 
						{
							sicurezza.setCONNETTORI("KO");
						}else 
						{
							sicurezza.setCONNETTORI("OK");
						}
						
						if(slider_5.getValue()==0) 
						{
							sicurezza.setMARCHIATURE("KO");
						}else 
						{
							sicurezza.setMARCHIATURE("OK");
						}
						
						if(slider_6.getValue()==0) 
						{
							sicurezza.setALTRO("KO");
						}else 
						{
							sicurezza.setALTRO("OK");
						}
						
						
								
						GestioneMisuraBO.updateMisuraSicurezzaElettrica(SessionBO.idStrumento,sicurezza);
						
						textField_ID_PROVA.setText(sicurezza.getID_PROVA());
						textField_classe.setText(sicurezza.getSK());
						textField_pa.setText(sicurezza.getPARTI_APPLICATE());
						textField_data_ora.setText(sicurezza.getDATA()+" - "+sicurezza.getORA());
						riempiModel(sicurezza);
						tabellaSecurTest.repaint();
						
						f.dispose();
						
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Selezionare una scheda","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));	
					}
				} catch (Exception e1) 
				{
					PannelloConsole.printException(e1);
					e1.printStackTrace();
				}
				
			}
		});
		
		return pannelloMisura;
	}


	class ItemRendererTipoRapporto extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				TipoRapportoDTO item = (TipoRapportoDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	class ItemRendererLuogoVerificao extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				LuogoVerificaDTO item = (LuogoVerificaDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	class ItemRendererClass extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				ClassificazioneDTO item = (ClassificazioneDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	class ItemRendererTipoStrumento extends BasicComboBoxRenderer
	{
		public Component getListCellRendererComponent(
				JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus)
		{
			super.getListCellRendererComponent(list, value, index,
					isSelected, cellHasFocus);

			if (value != null)
			{
				TipoStrumentoDTO item = (TipoStrumentoDTO)value;
				setText( item.getDescrizione() );
			}
			return this;
		}
	}
	public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {



		public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {

			final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);


			if (row %2 ==0) 
			{
				cellComponent.setBackground(new Color(224,224,224));
				cellComponent.setForeground(Color.BLACK);

			}else 
			{
				cellComponent.setBackground(Color.white);
				cellComponent.setForeground(Color.BLACK);
			}
			return cellComponent;

			
		}
		
		

	}
	
	class ModelTabella extends DefaultTableModel {


		public ModelTabella() {
			addColumn("Misurazione");
			addColumn("Valore Misurato");
			addColumn("Limite Misura");
			addColumn("Esito");
		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) 
		{
				return false;
		}
	}
	
	class ModelTabellaLetturaDati extends AbstractTableModel implements PropertyChangeListener {


		private final MyObjectManager manager;

		public ModelTabellaLetturaDati(MyObjectManager manager) {
			super();
			this.manager = manager;
			manager.propertyChangeSupport.addPropertyChangeListener(this);
			for (MyObject object : manager.getObjects()) {
				object.getPropertyChangeSupport().addPropertyChangeListener(this);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getSource() == this.manager) {
				// OK, not the cleanest thing, just to get the gist of it.
				if (evt.getPropertyName().equals("objects")) {
					((MyObject) evt.getNewValue()).getPropertyChangeSupport().addPropertyChangeListener(this);
				}
				fireTableDataChanged();
			} else if (evt.getSource() instanceof MyObject) {
				int index = this.manager.getObjects().indexOf(evt.getSource());
				fireTableRowsUpdated(index, index);
			}
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public int getRowCount() {
			return this.manager.getObjects().size();
		}

		public MyObject getValueAt(int row) {
			return this.manager.getObjects().get(row);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch (columnIndex) {
			case 0:
				return getValueAt(rowIndex).isSelected();
			case 1:
				return getValueAt(rowIndex).getValue();
			case 2:
				return getValueAt(rowIndex).getData();
			case 3:
				return getValueAt(rowIndex).getOra();
			}
			return null;
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				getValueAt(rowIndex).setSelected(Boolean.TRUE.equals(value));
			}
			if (columnIndex == 1) {
				getValueAt(rowIndex).setSelected(Boolean.TRUE.equals(value));
			}
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 0;
		}

		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			}
			return Object.class;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Seleziona";
			case 1:
				return "Identificativo";
			case 2:
				return "Data";
			case 3:
				return "Ora";

			}
			return null;
		}
	}
	
	private class MyObjectManager {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
		private List<MyObject> objects = new ArrayList<MyObject>();

		public void addObject(MyObject object) {
			objects.add(object);
			object.setManager(this);
			propertyChangeSupport.firePropertyChange("objects", null, object);
		}

		public List<MyObject> getObjects() {
			return objects;
		}

		public void setAsSelected(MyObject myObject) {
			for (MyObject o : objects) {
				o.setSelected(myObject == o);
			}
		}
	}

	private class MyObject {
		private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

		private MyObjectManager manager;

		private boolean selected;
		private String identificativo ;
		private String data;
		private String ora;
		


		public MyObject(String _id, String _data,String _ora) {
			this.identificativo = _id;
			this.data = _data;
			this.ora=_ora;
		
		}

		public PropertyChangeSupport getPropertyChangeSupport() {
			return propertyChangeSupport;
		}

		public String getValue() {
			return identificativo;
		}


		public MyObjectManager getManager() {
			return manager;
		}

		public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
			this.propertyChangeSupport = propertyChangeSupport;
		}

		public void setManager(MyObjectManager manager) {
			this.manager = manager;
			propertyChangeSupport.firePropertyChange("manager", null, manager);
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			if (this.selected != selected) {
				this.selected = selected;
				if (selected) {
					manager.setAsSelected(this);
				}
				propertyChangeSupport.firePropertyChange("selected", !selected, selected);
			}
		}

		public String getIdentificativo() {
			return identificativo;
		}

		public void setIdentificativo(String identificativo) {
			this.identificativo = identificativo;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public String getOra() {
			return ora;
		}

		public void setOra(String ora) {
			this.ora = ora;
		}
	}
	public class RadioButtonCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

		private JRadioButton radioButton;



		public RadioButtonCellEditorRenderer() {
			this.radioButton = new JRadioButton();
			radioButton.addActionListener(this);

			 radioButton.setHorizontalAlignment(JRadioButton.CENTER);
		}


		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			radioButton.setSelected(Boolean.TRUE.equals(value));
			Integer stato=null;
			try {
				stato=GestioneMisuraBO.getStatoMisura(table.getValueAt(row, 1).toString());  
				if(stato!=null && stato==0)
				{

					radioButton.setBackground(new Color(250,210,51));
				}
				if(stato!=null && stato==1)
				{

					radioButton.setBackground(Color.green);
				}
				if(stato==null)
				{

					radioButton.setBackground(Color.white);

				}
				if(isSelected)
				{
					 radioButton.setBackground(table.getSelectionBackground());
					 radioButton.setForeground(table.getSelectionForeground());
				}

				radioButton.setAlignmentX(CENTER_ALIGNMENT);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return radioButton;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			radioButton.setSelected(Boolean.TRUE.equals(value));
			return radioButton;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			stopCellEditing();
		}

		@Override
		public Object getCellEditorValue() {
			return radioButton.isSelected();
		}

		public void setRadioButton() {
			//	this.radioButton = radioButton;
			this.radioButton.setBackground(Color.orange);
		}

	}
	@Override
	
	public void stateChanged(ChangeEvent e) {
		
		Object obj=e.getSource();
		
		if(obj instanceof JSlider) 
		{
			JSlider source = (JSlider) e.getSource();
	
		}
		
	}
	
	class ThreadLettura implements Runnable {

		@Override
		public void run() 
		{
			try
			{
				String porta=textField_com_port.getText();
				String delay=textField_delay.getText();
			   if(porta!=null && porta.length()>0 && delay!=null && delay.length()>0) 
			   {
				Integer.parseInt(porta);
				Integer.parseInt(delay);
				   
				listaSicurezza= SerialSicurezzaElettrica.listaSicurezzaElettrica(porta, delay);
				
				if(listaSicurezza==null) 
				{
					JOptionPane.showMessageDialog(null,"Non sono presenti dati in lettura su questa porta","Nessun dato",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					   spl.close();
					return;
				}
				MyObjectManager manager = new MyObjectManager();
				for (int i = 0; i < listaSicurezza.size(); i++) {
					SicurezzaElettricaDTO sicurezza=listaSicurezza.get(i);
					MyObject object = new MyObject(""+sicurezza.getID_PROVA(),sicurezza.getDATA(),sicurezza.getORA());
					manager.addObject(object);
				}
				

				table_dati_strumento.setModel(new ModelTabellaLetturaDati (manager));

				
			table_dati_strumento.repaint();

			validate();
			repaint();
			
			spl.close();
			   }
			   else 
			   {
				   JOptionPane.showMessageDialog(null,"I campi porta e delay non possono essere vuoti","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				   spl.close();
			   }
			}
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,"I campi porta e delay devono assumere valori numerici","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				spl.close();
			}
			catch (Exception e) {
				e.printStackTrace();
				spl.close();
			}
		}
	}

}
