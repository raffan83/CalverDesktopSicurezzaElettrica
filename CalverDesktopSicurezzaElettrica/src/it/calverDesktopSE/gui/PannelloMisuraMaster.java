package it.calverDesktopSE.gui;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.commons.io.FilenameUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import it.calverDesktopSE.bo.GestioneMisuraBO;
import it.calverDesktopSE.bo.GestioneStampaBO;
import it.calverDesktopSE.bo.GestioneStrumentoBO;
import it.calverDesktopSE.bo.SessionBO;
import it.calverDesktopSE.dao.SQLiteDAO;
import it.calverDesktopSE.dto.ClassificazioneDTO;
import it.calverDesktopSE.dto.LuogoVerificaDTO;
import it.calverDesktopSE.dto.MisuraDTO;
import it.calverDesktopSE.dto.ProvaMisuraDTO;
import it.calverDesktopSE.dto.StrumentoDTO;
import it.calverDesktopSE.dto.TabellaMisureDTO;
import it.calverDesktopSE.dto.TipoRapportoDTO;
import it.calverDesktopSE.dto.TipoStrumentoDTO;
import it.calverDesktopSE.utl.Costanti;
import it.calverDesktopSE.utl.Utility;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTable;


public class PannelloMisuraMaster extends JPanel 
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


	JLabel lblTipoStrumento;
	private JTextField id_field;
	private JTextField address_field;
	private JTextField reparto_field;
	private JTextField utilizzatore_field;

	JPanel panel_header;
	JPanel panel_dati_str;
	JPanel panel_dati_misura;
	JPanel panel_struttura;
	JPanel panel_avvio;
	JPanel panel_stampa;
	private JPanel panel_tabella;
	JSplitPane splitPane;


	StrumentoDTO strumento;
	private ProvaMisuraDTO misura;
	JComboBox<TipoRapportoDTO> comboBox_tipoRapporto;
	JComboBox<ClassificazioneDTO> comboBox_classificazione;
	JComboBox<TipoStrumentoDTO> comboBox_tipo_strumento;
	JComboBox<LuogoVerificaDTO> comboBox_luogoVerifica;

	Vector<TipoRapportoDTO> vectorTipoRapporto = null;
	Vector<ClassificazioneDTO> vectorClassificazione = null;
	Vector<TipoStrumentoDTO> vectorTipoStrumento = null;
	Vector<LuogoVerificaDTO> vectorLuogoVerifica = null;
	private BufferedImage img;
	private int widthImg;
	private int heightImg;
	static JFrame myFrame=null;

	public PannelloMisuraMaster(String id,  ProvaMisuraDTO _misura) throws Exception
	{
		SessionBO.prevPage="PSS";
		current=-1;
		misura=_misura;
		strumento=GestioneStrumentoBO.getStrumento(SessionBO.idStrumento);


		myFrame=SessionBO.generarFrame;
		URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
		//	setImage(ImageIO.read(imageURL));

		this.setLayout(new MigLayout("","[grow]","[grow]"));

		JPanel masterPanel = new JPanel();

		//masterPanel.setBackground(Color.BLUE);

		//masterPanel.setBorder(new LineBorder(new Color(255, 255, 255), 3, true));

		masterPanel.setLayout(new MigLayout("", "[70%][20%][10%]", "[12%][73%][15%]"));


		panel_header = costruisciPanelHeader();
		panel_tabella = costruisciTabella();
		panel_dati_str = costruisciPanelDatiStr(id,misura);	
		panel_struttura =creaPanelStruttura();


		masterPanel.add(panel_header,"cell 0 0,grow");
		masterPanel.add(panel_tabella, "cell 0 1,grow");
		masterPanel.add(panel_dati_str,"cell 1 0 1 3,grow");
		masterPanel.add(panel_struttura,"cell 0 2,grow");


		double height=(SessionBO.heightFrame*73)/100;
		double width=(SessionBO.widthFrame*70)/100;

		masterPanel.setPreferredSize(new Dimension((int)width-50,(int) height/2));

		JScrollPane scroll= new JScrollPane(masterPanel);

		this.add(scroll, "cell 0 0,grow");



		//	btnNewButton.setBounds(225, 40, 184, 23);


	}







	public void setImage(BufferedImage img){
		this.img = img;
		widthImg = img.getWidth();
		heightImg = img.getHeight();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		// System.out.println(myFrame.getHeight()+myFrame.getWidth());
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
							JFrame f=new JFrame();
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
		panel_tabella.setLayout(new MigLayout("", "[grow]", "[grow]"));

		JTable tabellaSecurTest = new JTable();
		
		JScrollPane scroll = new JScrollPane(tabellaSecurTest);
		panel_tabella.add("cell 0 0 grow",scroll);
		
		return panel_tabella;

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


				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
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
						//	BigDecimal temp=new BigDecimal(temperatura);	
						//	BigDecimal umd=new BigDecimal(umidita);	
						//	GestioneMisuraBO.terminaMisura(SessionBO.idStrumento,temp,umd,sr,firma);
						JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));



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

		JPanel pannelloMisura = new JPanel();

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
}
