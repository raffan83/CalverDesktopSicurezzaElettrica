package it.calverDesktop.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import it.calverDesktop.bo.SessionBO;
import it.calverDesktop.utl.Costanti;
import net.miginfocom.swing.MigLayout;


public class PannelloConsole extends JPanel{
	
	public static JTextArea area;
	
	public PannelloConsole(Color color,String title) {
		
		setPreferredSize(new Dimension(SessionBO.widthFrame/2,1));
		setBackground(Costanti.backgroundGrey);

		setLayout(new MigLayout("fill"));
		
		area = new JTextArea();
		area.setBackground(Costanti.backgroundGreyLight);
		
		JScrollPane scroll = new JScrollPane(area);
	
		scroll.getViewport().add(area);	
		
		
		Border border = BorderFactory.createLineBorder(new Color(255, 255, 255));
		area.setFont(new Font("Arial", Font.BOLD, 14));
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		area.setBorder(border);
		
		printArea("Init");

		this.add(scroll,"growx,growy");
		
		JButton btnCls = new JButton();
		    URL imageURL = GeneralGUI.class.getResource("/image/pennello.png");
		    ImageIcon imgIcon = new ImageIcon(imageURL);
		    btnCls.setIcon(imgIcon);
		    btnCls.setBackground(new Color(242, 242, 242));
		    btnCls.setOpaque(true);
		    btnCls.setBorderPainted(false);
		    
		    this.add(btnCls, "cell 0 0,width :40:,growy");
		     
		    
		    btnCls.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
				
					PannelloConsole.cleanArea();
					
				}
			});
	}

	
	public static void printArea(final String text)  
	{     
		Runnable updateGUI = new Runnable() {  
			public void run() {  
				area.append("CAL>"+text+"\n"); 
				area.setCaretPosition(area.getText().length() - 1);
			}  
		};  
		try{
				Thread t = new Thread(updateGUI);  
				t.start();
				Thread.sleep(10);
		}catch 
		(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanArea()  
	{     
		Runnable updateGUI = new Runnable() {  
			public void run() {
				area.setText("");
				area.append("CAL>\n"); 
				area.setCaretPosition(area.getText().length() - 1);
			}  
		};  
		try{
				Thread t = new Thread(updateGUI);  
				t.start();
				Thread.sleep(10);
		}catch 
		(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printException(final Exception ex)    
	{     
		Runnable updateGUI = new Runnable() {  
			public void run() {  
				StackTraceElement[] element=ex.getStackTrace();
				String[] buff= new String[element.length+1];
				buff[0]=ex.toString();
				area.append(ex.getMessage()+"\n");
				for(int i=0;i<element.length;i++)
				{
					area.append((element[i].getClassName()+"."+element[i].getMethodName()+"("+element[i].getFileName()+":"+element[i].getLineNumber()+")\n"));
				}
				
			}  
		};  
				Thread t = new Thread(updateGUI);  
				t.start();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				

	}  
}
