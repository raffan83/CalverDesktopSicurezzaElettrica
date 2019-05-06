package it.calverDesktopSE.bo;

import java.util.ArrayList;

import it.calverDesktopSE.dto.SicurezzaElettricaDTO;
import jssc.SerialPort;
import jssc.SerialPortList;

public class SerialSicurezzaElettrica {

public static ArrayList<SicurezzaElettricaDTO> listaSicurezzaElettrica(String com,String delay) {

        SerialPort serialPort = new SerialPort("COM"+com);
        
        ArrayList<SicurezzaElettricaDTO> listaSicurezza= null;
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

            
            Integer i = Integer.parseInt(delay);
            Thread.sleep(i*1000);
            
            String s  = new String(serialPort.readBytes());
           
           listaSicurezza=goTo(s);
            
           serialPort.closePort();
            
        } catch (Exception e) {
            System.out.println("There are an error on writing string to port т: " + e);
        }
        
        return listaSicurezza;
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
			
			sicurezza.setID_PROVA(normalizza(play[2]));
			sicurezza.setSK(normalizza(play[3]));
			sicurezza.setDATA(normalizza(play[4]));
			sicurezza.setORA(normalizza(play[5]));
			sicurezza.setR_SL(normalizza(play[6]));
			sicurezza.setR_SL_GW(normalizza(play[7]));
			sicurezza.setR_ISO(normalizza(play[10]));
			sicurezza.setR_ISO_GW(normalizza(play[11]));
			sicurezza.setU_ISO(normalizza(play[12]));
			sicurezza.setU_ISO_GW(normalizza(play[13]));
			sicurezza.setI_DIFF(normalizza(play[14]));
			sicurezza.setI_DIFF_GW(normalizza(play[15]));
			
			sicurezza.setI_EGA(normalizza(play[20]));
			sicurezza.setI_EGA_GW(normalizza(play[21]));
			sicurezza.setI_EPA(normalizza(play[22]));
			sicurezza.setI_EPA_GW(normalizza(play[23]));
			
			sicurezza.setI_GA(normalizza(play[28]));
			sicurezza.setI_GA_GW(normalizza(play[29]));
			sicurezza.setI_GA_SFC(normalizza(play[30]));
			sicurezza.setI_GA_SFC_GW(normalizza(play[31]));
			sicurezza.setI_PA_AC(normalizza(play[32]));
			sicurezza.setI_PA_AC_GW(normalizza(play[33]));
			sicurezza.setI_PA_DC(normalizza(play[36]));
			sicurezza.setI_PA_DC_GW(normalizza(play[37]));
			sicurezza.setPSPG(normalizza(play[50]));
			sicurezza.setUBEZ_GW(normalizza(play[51]));
			
			listaSicurezzaElettrica.add(sicurezza);
			
			System.out.println("S.T.I."+data);
		}
	}
	System.out.println("***  [FINE LETTURA DATI]  ***");
	
	return listaSicurezzaElettrica;
}


private static String normalizza(String string) {
	
	string =string.replaceAll("ê", "Ohm");
	string=string.replaceAll("æ", "µ");
	return string;
}

}


