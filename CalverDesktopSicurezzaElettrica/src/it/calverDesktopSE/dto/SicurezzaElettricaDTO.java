package it.calverDesktopSE.dto;

public class SicurezzaElettricaDTO {
	
	private int id;
	
	private int id_misura;
	private StrumentoDTO strumento;
	
	private String TIPO_NORMA;
	
	private String ID_PROVA;
	private String DATA;
	private String ORA;
	private String SK;
	
	private String R_SL;
	private String R_SL_GW;
	
	private String R_ISO;
	private String R_ISO_GW;
	
	private String U_ISO;
	private String U_ISO_GW;
	
	private String I_DIFF;
	private String I_DIFF_GW;
	
	private String I_EGA;
	private String I_EGA_GW;
	
	private String I_EPA;
	private String I_EPA_GW;
	
	private String I_GA;
	private String I_GA_GW;
	
	private String I_GA_SFC;
	private String I_GA_SFC_GW;
	
	private String I_PA_AC;
	private String I_PA_AC_GW;
	
	private String I_PA_DC;
	private String I_PA_DC_GW;
	
	private String PSPG;
	private String UBEZ_GW;

	private String COND_PROT;
	private String INVOLUCRO;
	private String FUSIBILI;
	private String CONNETTORI;
	private String MARCHIATURE;
	private String ALTRO;
	private String PARTI_APPLICATE;
	
	private String MAX_POWER_INTAKE_601;
	private String POWER_FACTOR_LF_601;
	private String MAX_SUPPLY_CUR_601;
	private String ENERGY_601;
	private String DURATION_601;
	
	
	
	public String getTIPO_NORMA() {
		return TIPO_NORMA;
	}
	public void setTIPO_NORMA(String tIPO_NORMA) {
		TIPO_NORMA = tIPO_NORMA;
	}
	public String getPARTI_APPLICATE() {
		return PARTI_APPLICATE;
	}
	public void setPARTI_APPLICATE(String pARTI_APPLICATE) {
		PARTI_APPLICATE = pARTI_APPLICATE;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getID_PROVA() {
		return ID_PROVA;
	}
	public void setID_PROVA(String iD_PROVA) {
		ID_PROVA = iD_PROVA;
	}
	

	public String getDATA() {
		return DATA;
	}
	public void setDATA(String dATA) {
		DATA = dATA;
	}
	public String getORA() {
		return ORA;
	}
	public void setORA(String oRA) {
		ORA = oRA;
	}
	public String getSK() {
		return SK;
	}
	public void setSK(String sK) {
		SK = sK;
	}
	public String getR_SL() {
		return R_SL;
	}
	public void setR_SL(String r_SL) {
		R_SL = r_SL;
	}
	public String getR_SL_GW() {
		return R_SL_GW;
	}
	public void setR_SL_GW(String r_SL_GW) {
		R_SL_GW = r_SL_GW;
	}
	public String getR_ISO() {
		return R_ISO;
	}
	public void setR_ISO(String r_ISO) {
		R_ISO = r_ISO;
	}
	public String getR_ISO_GW() {
		return R_ISO_GW;
	}
	public void setR_ISO_GW(String r_ISO_GW) {
		R_ISO_GW = r_ISO_GW;
	}
	public String getU_ISO() {
		return U_ISO;
	}
	public void setU_ISO(String u_ISO) {
		U_ISO = u_ISO;
	}
	public String getU_ISO_GW() {
		return U_ISO_GW;
	}
	public void setU_ISO_GW(String u_ISO_GW) {
		U_ISO_GW = u_ISO_GW;
	}
	public String getI_DIFF() {
		return I_DIFF;
	}
	public void setI_DIFF(String i_DIFF) {
		I_DIFF = i_DIFF;
	}
	public String getI_DIFF_GW() {
		return I_DIFF_GW;
	}
	public void setI_DIFF_GW(String i_DIFF_GW) {
		I_DIFF_GW = i_DIFF_GW;
	}
	public String getI_EGA() {
		return I_EGA;
	}
	public void setI_EGA(String i_EGA) {
		I_EGA = i_EGA;
	}
	public String getI_EGA_GW() {
		return I_EGA_GW;
	}
	public void setI_EGA_GW(String i_EGA_GW) {
		I_EGA_GW = i_EGA_GW;
	}
	public String getI_EPA() {
		return I_EPA;
	}
	public void setI_EPA(String i_EPA) {
		I_EPA = i_EPA;
	}
	public String getI_EPA_GW() {
		return I_EPA_GW;
	}
	public void setI_EPA_GW(String i_EPA_GW) {
		I_EPA_GW = i_EPA_GW;
	}
	public String getI_GA() {
		return I_GA;
	}
	public void setI_GA(String i_GA) {
		I_GA = i_GA;
	}
	public String getI_GA_GW() {
		return I_GA_GW;
	}
	public void setI_GA_GW(String i_GA_GW) {
		I_GA_GW = i_GA_GW;
	}
	public String getI_GA_SFC() {
		return I_GA_SFC;
	}
	public void setI_GA_SFC(String i_GA_SFC) {
		I_GA_SFC = i_GA_SFC;
	}
	public String getI_GA_SFC_GW() {
		return I_GA_SFC_GW;
	}
	public void setI_GA_SFC_GW(String i_GA_SFC_GW) {
		I_GA_SFC_GW = i_GA_SFC_GW;
	}
	public String getI_PA_AC() {
		return I_PA_AC;
	}
	public void setI_PA_AC(String i_PA_AC) {
		I_PA_AC = i_PA_AC;
	}
	public String getI_PA_AC_GW() {
		return I_PA_AC_GW;
	}
	public void setI_PA_AC_GW(String i_PA_AC_GW) {
		I_PA_AC_GW = i_PA_AC_GW;
	}
	public String getI_PA_DC() {
		return I_PA_DC;
	}
	public void setI_PA_DC(String i_PA_DC) {
		I_PA_DC = i_PA_DC;
	}
	public String getI_PA_DC_GW() {
		return I_PA_DC_GW;
	}
	public void setI_PA_DC_GW(String i_PA_DC_GW) {
		I_PA_DC_GW = i_PA_DC_GW;
	}
	public String getPSPG() {
		return PSPG;
	}
	public void setPSPG(String pSPG) {
		PSPG = pSPG;
	}
	public String getUBEZ_GW() {
		return UBEZ_GW;
	}
	public void setUBEZ_GW(String uBEZ_GW) {
		UBEZ_GW = uBEZ_GW;
	}
	public int getId_misura() {
		return id_misura;
	}
	public void setId_misura(int id_misura) {
		this.id_misura = id_misura;
	}
	public StrumentoDTO getStrumento() {
		return strumento;
	}
	public void setStrumento(StrumentoDTO strumento) {
		this.strumento = strumento;
	}
	public String getCOND_PROT() {
		return COND_PROT;
	}
	public void setCOND_PROT(String cOND_PROT) {
		COND_PROT = cOND_PROT;
	}
	public String getINVOLUCRO() {
		return INVOLUCRO;
	}
	public void setINVOLUCRO(String iNVOLUCRO) {
		INVOLUCRO = iNVOLUCRO;
	}
	public String getFUSIBILI() {
		return FUSIBILI;
	}
	public void setFUSIBILI(String fUSIBILI) {
		FUSIBILI = fUSIBILI;
	}
	public String getCONNETTORI() {
		return CONNETTORI;
	}
	public void setCONNETTORI(String cONNETTORI) {
		CONNETTORI = cONNETTORI;
	}
	public String getMARCHIATURE() {
		return MARCHIATURE;
	}
	public void setMARCHIATURE(String mARCHIATURE) {
		MARCHIATURE = mARCHIATURE;
	}
	public String getALTRO() {
		return ALTRO;
	}
	public void setALTRO(String aLTRO) {
		ALTRO = aLTRO;
	}
	
	
	/*Norma 601*/ 

	private String IDIFF;
    private String IDIFF_GW;
    private String IEA_NC;
    private String IEA_NC_GW;
    private String IEA_SFC;
    private String IEA_SFC_GW;
    private String IG_NC;
    private String IG_NC_GW;
    private String IG_SFC;
    private String IG_SFC_GW;
    private String IPAAC_NC;
    private String IPAAC_NC_GW;
    private String IPAAC_SFC;
    private String IPAAC_SFC_GW;
    private String IPADC_NC;
    private String IPADC_NC_GW;
    private String IPADC_SFC;
    private String IPADC_SFC_GW;
    private String IPNAT;
    private String IPNAT_GW;
    private String IPHAC_NC;
    private String IPHAC_NC_GW;
    private String IPHAC_SFC;
    private String IPHAC_SFC_GW;
    private String IPHDC_NC;
    private String IPHDC_NC_GW;
    private String IPHDC_SFC;
    private String IPHDC_SFC_GW;
	

    public String getIDIFF() {
        return IDIFF;
    }

    public void setIDIFF(String IDIFF) {
        this.IDIFF = IDIFF;
    }

    public String getIDIFF_GW() {
        return IDIFF_GW;
    }

    public void setIDIFF_GW(String IDIFF_GW) {
        this.IDIFF_GW = IDIFF_GW;
    }

    public String getIEA_NC() {
        return IEA_NC;
    }

    public void setIEA_NC(String IEA_NC) {
        this.IEA_NC = IEA_NC;
    }

    public String getIEA_NC_GW() {
        return IEA_NC_GW;
    }

    public void setIEA_NC_GW(String IEA_NC_GW) {
        this.IEA_NC_GW = IEA_NC_GW;
    }

    public String getIEA_SFC() {
        return IEA_SFC;
    }

    public void setIEA_SFC(String IEA_SFC) {
        this.IEA_SFC = IEA_SFC;
    }

    public String getIEA_SFC_GW() {
        return IEA_SFC_GW;
    }

    public void setIEA_SFC_GW(String IEA_SFC_GW) {
        this.IEA_SFC_GW = IEA_SFC_GW;
    }

    public String getIG_NC() {
        return IG_NC;
    }

    public void setIG_NC(String IG_NC) {
        this.IG_NC = IG_NC;
    }

    public String getIG_NC_GW() {
        return IG_NC_GW;
    }

    public void setIG_NC_GW(String IG_NC_GW) {
        this.IG_NC_GW = IG_NC_GW;
    }

    public String getIG_SFC() {
        return IG_SFC;
    }

    public void setIG_SFC(String IG_SFC) {
        this.IG_SFC = IG_SFC;
    }

    public String getIG_SFC_GW() {
        return IG_SFC_GW;
    }

    public void setIG_SFC_GW(String IG_SFC_GW) {
        this.IG_SFC_GW = IG_SFC_GW;
    }

    public String getIPAAC_NC() {
        return IPAAC_NC;
    }

    public void setIPAAC_NC(String IPAAC_NC) {
        this.IPAAC_NC = IPAAC_NC;
    }

    public String getIPAAC_NC_GW() {
        return IPAAC_NC_GW;
    }

    public void setIPAAC_NC_GW(String IPAAC_NC_GW) {
        this.IPAAC_NC_GW = IPAAC_NC_GW;
    }

    public String getIPAAC_SFC() {
        return IPAAC_SFC;
    }

    public void setIPAAC_SFC(String IPAAC_SFC) {
        this.IPAAC_SFC = IPAAC_SFC;
    }

    public String getIPAAC_SFC_GW() {
        return IPAAC_SFC_GW;
    }

    public void setIPAAC_SFC_GW(String IPAAC_SFC_GW) {
        this.IPAAC_SFC_GW = IPAAC_SFC_GW;
    }

    public String getIPADC_NC() {
        return IPADC_NC;
    }

    public void setIPADC_NC(String IPADC_NC) {
        this.IPADC_NC = IPADC_NC;
    }

    public String getIPADC_NC_GW() {
        return IPADC_NC_GW;
    }

    public void setIPADC_NC_GW(String IPADC_NC_GW) {
        this.IPADC_NC_GW = IPADC_NC_GW;
    }

    public String getIPADC_SFC() {
        return IPADC_SFC;
    }

    public void setIPADC_SFC(String IPADC_SFC) {
        this.IPADC_SFC = IPADC_SFC;
    }

    public String getIPADC_SFC_GW() {
        return IPADC_SFC_GW;
    }

    public void setIPADC_SFC_GW(String IPADC_SFC_GW) {
        this.IPADC_SFC_GW = IPADC_SFC_GW;
    }

    public String getIPNAT() {
        return IPNAT;
    }

    public void setIPNAT(String IPNAT) {
        this.IPNAT = IPNAT;
    }

    public String getIPNAT_GW() {
        return IPNAT_GW;
    }

    public void setIPNAT_GW(String IPNAT_GW) {
        this.IPNAT_GW = IPNAT_GW;
    }

    public String getIPHAC_NC() {
        return IPHAC_NC;
    }

    public void setIPHAC_NC(String IPHAC_NC) {
        this.IPHAC_NC = IPHAC_NC;
    }

    public String getIPHAC_NC_GW() {
        return IPHAC_NC_GW;
    }

    public void setIPHAC_NC_GW(String IPHAC_NC_GW) {
        this.IPHAC_NC_GW = IPHAC_NC_GW;
    }

    public String getIPHAC_SFC() {
        return IPHAC_SFC;
    }

    public void setIPHAC_SFC(String IPHAC_SFC) {
        this.IPHAC_SFC = IPHAC_SFC;
    }

    public String getIPHAC_SFC_GW() {
        return IPHAC_SFC_GW;
    }

    public void setIPHAC_SFC_GW(String IPHAC_SFC_GW) {
        this.IPHAC_SFC_GW = IPHAC_SFC_GW;
    }

    public String getIPHDC_NC() {
        return IPHDC_NC;
    }

    public void setIPHDC_NC(String IPHDC_NC) {
        this.IPHDC_NC = IPHDC_NC;
    }

    public String getIPHDC_NC_GW() {
        return IPHDC_NC_GW;
    }

    public void setIPHDC_NC_GW(String IPHDC_NC_GW) {
        this.IPHDC_NC_GW = IPHDC_NC_GW;
    }

    public String getIPHDC_SFC() {
        return IPHDC_SFC;
    }

    public void setIPHDC_SFC(String IPHDC_SFC) {
        this.IPHDC_SFC = IPHDC_SFC;
    }

    public String getIPHDC_SFC_GW() {
        return IPHDC_SFC_GW;
    }

    public void setIPHDC_SFC_GW(String IPHDC_SFC_GW) {
        this.IPHDC_SFC_GW = IPHDC_SFC_GW;
    }
	public String getMAX_POWER_INTAKE_601() {
		return MAX_POWER_INTAKE_601;
	}
	public void setMAX_POWER_INTAKE_601(String mAX_POWER_INTAKE_601) {
		MAX_POWER_INTAKE_601 = mAX_POWER_INTAKE_601;
	}
	public String getPOWER_FACTOR_LF_601() {
		return POWER_FACTOR_LF_601;
	}
	public void setPOWER_FACTOR_LF_601(String pOWER_FACTOR_LF_601) {
		POWER_FACTOR_LF_601 = pOWER_FACTOR_LF_601;
	}
	public String getMAX_SUPPLY_CUR_601() {
		return MAX_SUPPLY_CUR_601;
	}
	public void setMAX_SUPPLY_CUR_601(String mAX_SUPPLY_CUR_601) {
		MAX_SUPPLY_CUR_601 = mAX_SUPPLY_CUR_601;
	}
	public String getENERGY_601() {
		return ENERGY_601;
	}
	public void setENERGY_601(String eNERGY_601) {
		ENERGY_601 = eNERGY_601;
	}
	public String getDURATION_601() {
		return DURATION_601;
	}
	public void setDURATION_601(String dURATION_601) {
		DURATION_601 = dURATION_601;
	}
	
    
}
