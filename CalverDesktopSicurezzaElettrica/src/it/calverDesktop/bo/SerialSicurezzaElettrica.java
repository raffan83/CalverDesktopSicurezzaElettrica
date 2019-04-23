package it.calverDesktop.bo;

import java.util.ArrayList;

import it.calverDesktop.dto.SicurezzaElettricaDTO;
import jssc.SerialPort;
import jssc.SerialPortList;

public class SerialSicurezzaElettrica {

public static void main(String[] args) {
    String[] portNames = null;
    portNames = SerialPortList.getPortNames();

    if (portNames.length == 0) {
        System.out.println("There are no serial-ports");
    } else {

        SerialPort serialPort = new SerialPort("COM9");
        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
            
        //    Thread.sleep(500);
            
            byte[] array = {0x49, 0x44, 0x4E, 0x30, 0x3F, 0x24, 0x36, 0x45,0x0D};
            serialPort.writeBytes(array);
            
            Thread.sleep(2500);
            byte[] array1 = {0x46, 0x4B, 0x54, 0x3F, 0x24, 0x34, 0x38,0x0D};
            serialPort.writeBytes(array1);
            
            Thread.sleep(2500);
            byte[] array2 = {0x57, 0x45, 0x52, 0x54, 0x45, 0x3F, 0x24, 0x45,0x41,0x0D};
            serialPort.writeBytes(array2);

            Thread.sleep(3000);
            
            String s  = new String(serialPort.readBytes());
           
            goTo(s);
            
          //  System.out.println(s);
            
        } catch (Exception e) {
            System.out.println("There are an error on writing string to port т: " + e);
        }
    }
 }

private static ArrayList<SicurezzaElettricaDTO> goTo(String totalLine) {
	
	ArrayList<SicurezzaElettricaDTO> listaSicurezzaElettrica = new ArrayList<SicurezzaElettricaDTO>();
	
	String[] tmp =totalLine.split("\n");
	String[] playLoad =tmp[2].split("S.T.I.");
	System.out.println("***  [LETTURA DATI]  ***");
	
	SicurezzaElettricaDTO sicurezza=null;
	for (String data : playLoad) {
		
		data=data.replaceAll("�", "Ohm");
		if(data.length()>2) 
		{
			sicurezza = new SicurezzaElettricaDTO();
			String[] play =data.split(";");
			
			sicurezza.setID(play[2]);
			sicurezza.setSK(play[3]);
			sicurezza.setDATA(play[4]);
			sicurezza.setORA(play[5]);
			sicurezza.setR_SL(play[6]);
			sicurezza.setR_SL_GW(play[7]);
			sicurezza.setR_ISO(play[10]);
			sicurezza.setR_ISO_GW(play[11]);
			sicurezza.setU_ISO(play[12]);
			sicurezza.setU_ISO_GW(play[13]);
			sicurezza.setI_DIFF(play[14]);
			sicurezza.setI_DIFF_GW(play[15]);
			sicurezza.setI_EGA(play[19]);
			sicurezza.setI_EGA_GW(play[20]);
			sicurezza.setI_EPA(play[21]);
			sicurezza.setI_EPA_GW(play[22]);
			sicurezza.setI_GA(play[28]);
			sicurezza.setI_GA_GW(play[29]);
			sicurezza.setI_GA_SFC(play[30]);
			sicurezza.setI_GA_SFC_GW(play[31]);
			sicurezza.setI_PA_AC(play[32]);
			sicurezza.setI_PA_AC_GW(play[33]);
			sicurezza.setI_PA_DC(play[36]);
			sicurezza.setI_PA_DC_GW(play[37]);
			sicurezza.setPSPG(play[50]);
			sicurezza.setUBEZ_GW(play[51]);
			
			listaSicurezzaElettrica.add(sicurezza);
			
			System.out.println("S.T.I."+data);
		}
	}
	System.out.println("***  [FINE LETTURA DATI]  ***");
	
	return listaSicurezzaElettrica;
}

}


