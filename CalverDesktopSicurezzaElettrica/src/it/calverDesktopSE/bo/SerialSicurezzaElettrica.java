package it.calverDesktopSE.bo;

import java.util.ArrayList;

import it.calverDesktopSE.dto.SicurezzaElettricaDTO;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialSicurezzaElettrica {

public static ArrayList<SicurezzaElettricaDTO> listaSicurezzaElettrica(String com,String delay) throws SerialPortException {

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
            System.out.println("There are an error on writing string to port: " + e);
            serialPort.closePort();
        }
        
        return listaSicurezza;
    }
 

private static ArrayList<SicurezzaElettricaDTO> goTo(String totalLine) {
	
	ArrayList<SicurezzaElettricaDTO> listaSicurezzaElettrica = new ArrayList<SicurezzaElettricaDTO>();
	
	String[] tmp =totalLine.split("\n");
	String[] playLoad =tmp[2].split("S.T.I.");
	String[] energ =tmp[1].substring(2,tmp[1].length()).split("\\$24;");
	
	
	System.out.println("***  [LETTURA DATI]  ***");
	
	SicurezzaElettricaDTO sicurezza=null;
	for (String data : playLoad) {
		
		data=data.replaceAll("�", "Ohm");
		if(data.length()>2) 
		{
			sicurezza = new SicurezzaElettricaDTO();
			
			String[] play =data.split(";");
			
			String tipoProva=play[1];
			
			if(tipoProva.indexOf("A0E0000050101240300")>0) 
			{
				sicurezza.setTIPO_NORMA("601");
				
				
				sicurezza.setID_PROVA(normalizza(play[2]));
				sicurezza.setSK(normalizza(play[3]));
				sicurezza.setDATA(normalizza(play[4]));
				sicurezza.setORA(normalizza(play[5]));
				
				sicurezza.setR_SL(normalizza(play[6]));
				sicurezza.setR_SL_GW(normalizza(play[7]));
				sicurezza.setR_ISO(normalizza(play[10]));
				sicurezza.setR_ISO_GW(normalizza(play[11]));

				sicurezza.setIDIFF(normalizza(play[14]));
				sicurezza.setIDIFF_GW(normalizza(play[15]));

				sicurezza.setIEA_NC(normalizza(play[24]));
				sicurezza.setIEA_NC_GW(normalizza(play[25]));

				sicurezza.setIEA_SFC(normalizza(play[26]));
				sicurezza.setIEA_SFC_GW(normalizza(play[27]));

				sicurezza.setIG_NC(normalizza(play[28]));
				sicurezza.setIG_NC_GW(normalizza(play[29]));

				sicurezza.setIG_SFC(normalizza(play[30]));
				sicurezza.setIG_SFC_GW(normalizza(play[31]));

				sicurezza.setIPAAC_NC(normalizza(play[32]));
				sicurezza.setIPAAC_NC_GW(normalizza(play[33]));

				sicurezza.setIPAAC_SFC(normalizza(play[34]));
				sicurezza.setIPAAC_SFC_GW(normalizza(play[35]));

				sicurezza.setIPADC_NC(normalizza(play[36]));
				sicurezza.setIPADC_NC_GW(normalizza(play[37]));

				sicurezza.setIPADC_SFC(normalizza(play[38]));
				sicurezza.setIPADC_SFC_GW(normalizza(play[39]));

				/*Da controllare*/
				sicurezza.setIPNAT("");
				sicurezza.setIPNAT_GW("");

				sicurezza.setIPHAC_NC("");
				sicurezza.setIPHAC_NC_GW("");

				sicurezza.setIPHAC_SFC("");
				sicurezza.setIPHAC_SFC_GW("");

				sicurezza.setIPHDC_NC("");
				sicurezza.setIPHDC_NC_GW("");

				sicurezza.setIPHDC_SFC("");
				sicurezza.setIPHDC_SFC_GW("");
				
				
				controlloDatiEnergetici(play[74],energ,sicurezza);
			}
			else 
			{
				
			sicurezza.setTIPO_NORMA("62535");
			
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
			}
			
			listaSicurezzaElettrica.add(sicurezza);
			
			System.out.println("S.T.I."+data);
		}
	}
	System.out.println("***  [FINE LETTURA DATI]  ***");
	
	return listaSicurezzaElettrica;
}


private static void controlloDatiEnergetici(String identifier, String[] energ, SicurezzaElettricaDTO sicurezza) {
	
	try {
		int indiceMisura=Integer.parseInt(identifier.substring(0,4));
		
		String dati=energ[indiceMisura-1];
		
		if(!dati.equals("")) 
		{
			String[] dt=dati.split(";");
			
			sicurezza.setMAX_POWER_INTAKE_601(dt[0]);
			sicurezza.setPOWER_FACTOR_LF_601(dt[1]);
			sicurezza.setMAX_SUPPLY_CUR_601(dt[2]);
			sicurezza.setENERGY_601(dt[3]);
			sicurezza.setDURATION_601(dt[4].substring(0, 8));
		}
			
		
	} catch (Exception e) {
		System.out.println("Errore compilazione dati energetici");
		e.printStackTrace();
	}
	
}


private static String normalizza(String string) {
	
	string =string.replaceAll("ê", "Ohm");
	string=string.replaceAll("æ", "µ");
	return string;
}

}


