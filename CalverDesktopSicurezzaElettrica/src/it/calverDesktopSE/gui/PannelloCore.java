package it.calverDesktopSE.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import it.calverDesktopSE.bo.SessionBO;

public class PannelloCore extends JPanel{
	
	public PannelloCore(Color color,String title, JFrame g)  {
		
		super(new CardLayout());
		setPreferredSize(new Dimension(SessionBO.widthFrame/2,1));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		SessionBO.pannelloCore=this;
		
		String imgLocation = "/image/walpaper.jpg";
		URL imageURL = GeneralGUI.class.getResource(imgLocation);
		
		JPanel panel = new BackgroundedFrame(imageURL, g);
		
		add(panel,"P1");
		panel.setBackground(color);
		panel.add(new JLabel(title));
		
		
	}

}
