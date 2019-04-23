package it.calverDesktop.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import gnu.io.PortInUseException;
import it.calverDesktop.bo.GestioneSonda;
import it.calverDesktop.bo.PortReader;
import it.calverDesktop.bo.Serial;
import it.calverDesktop.dto.SondaDTO;
import jssc.SerialPort;

public class DASM_GUI extends JFrame {
	
	JFrame g=null;
	JPanel initPanel;
	JScrollPane scrollPane;
	PortReader portReader;

	
	
	public static HashMap<Long, String> msgsOrd = new HashMap<Long, String>();
	public ArrayList<SondaDTO> listaSonde= new ArrayList<SondaDTO>();
	private JTextField txtCom;
	private JTextField textField_1;
	
	public DASM_GUI()
	{
		setTitle("DASM  �");
		setSize(250,650);
		setResizable(false);
		g=this;
		
	
			costruisciFrame();
	}

	private void costruisciFrame() {
		
		initPanel = new JPanel();
		initPanel.setSize(250,650);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (dim.width - initPanel.getWidth()) / 2;
		int y = (dim.height - initPanel.getHeight()) / 2;
		setLocation(x, y);
		
		initPanel.setLayout(null);
		
		scrollPane = new JScrollPane(initPanel);
		scrollPane.setSize(new Dimension(240,  630));
		scrollPane.setBackground(Color.red);
	
		JButton btnConnect = new JButton("Connect");
		btnConnect.setFont(new Font("Arial", Font.BOLD, 14));
		btnConnect.setBounds(10, 53, 104, 25);
		initPanel.add(btnConnect);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setFont(new Font("Arial", Font.BOLD, 14));
		lblPort.setBounds(10, 11, 37, 14);
		initPanel.add(lblPort);
		
		txtCom = new JTextField();
		txtCom.setFont(new Font("Arial", Font.PLAIN, 14));
		txtCom.setText("COM4");
		txtCom.setBounds(47, 9, 62, 20);
		initPanel.add(txtCom);
		txtCom.setColumns(10);
		
		JLabel lblRate = new JLabel("Rate");
		lblRate.setFont(new Font("Arial", Font.BOLD, 14));
		lblRate.setBounds(115, 11, 49, 14);
		initPanel.add(lblRate);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_1.setText("115200");
		textField_1.setBounds(152, 9, 80, 20);
		initPanel.add(textField_1);
		textField_1.setColumns(10);
		
		
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				int rate=0;
				try
				{
					
					rate=Integer.parseInt(textField_1.getText());
				}catch (Exception e) {
					
					JOptionPane.showMessageDialog(null, "Il campo Rate accetta solo numeri","Errore",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					return;
				}
				

				try
				{
					 jssc.SerialPort serialPort =Serial.getConnection(txtCom.getText(), rate);
					   
					   portReader = new PortReader(serialPort);

					   serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
					   

					   Thread.sleep(1000);

						listaSonde=GestioneSonda.getListaSonde(portReader);
				}
			
			
				
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null, "Porta gi� in uso o inesistente","Errore",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					System.out.println(e.getMessage());
				}
				int index=100;
				
				for (Iterator iterator = listaSonde.iterator(); iterator.hasNext();) 
				{
					SondaDTO sonda = (SondaDTO) iterator.next();
					
					JButton btnSnd= new JButton("["+sonda.getId_sonda()+"] "+sonda.getLabel());
					btnSnd.setBounds(10, index, 200,30);
					index=index+40;
					
					
					btnSnd.addActionListener(new ActionListener() {
					
						@Override
						public void actionPerformed(final ActionEvent e) {	
						
								    	  String command = ((JButton) e.getSource()).getActionCommand();
											
											String pv=command.substring(1,command.indexOf("]"));
										
											SondaDTO sonda=getSonda(listaSonde,pv);
											 
											GuiWorker1 d =	 new GuiWorker1(sonda,portReader,g);
											 d.execute();
											
									
											 
		
						}

				

					});
					
					
					initPanel.add(btnSnd);
				}
				
				initPanel.update(g.getGraphics());
				g.update(g.getGraphics());
					
			}
		});	
		
		g.getContentPane().add(scrollPane);
	}
	
	public static  SondaDTO getSonda(ArrayList<SondaDTO> listaSonde, String pv) {
		
		for (int i = 0; i < listaSonde.size(); i++) {
			
			if(listaSonde.get(i).getId_sonda().equalsIgnoreCase(pv))
			{
				return listaSonde.get(i);
			}
		}
		return null;
	}
}
