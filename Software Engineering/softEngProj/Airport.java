
public class Airport {

    private String code;
    private int localOffset;

    public Airport(String airCode){
        code = airCode;
        if(code.equalsIgnoreCase("LGW")) this.localOffset = 0;      //London, England
        else if(code.equalsIgnoreCase("HER")) this.localOffset = 2; //Heraklion, Greece
        else if(code.equalsIgnoreCase("MLA")) this.localOffset = 1; //Gudja, Malta
        else if(code.equalsIgnoreCase("RHO")) this.localOffset = 2; //Rhodes, Greece
        else if(code.equalsIgnoreCase("ALC")) this.localOffset = 1; //Alicante, Spain
        else if(code.equalsIgnoreCase("PFO")) this.localOffset = 2; //Paphos, Cyprus
        else if(code.equalsIgnoreCase("FUE")) this.localOffset = 0; //Puerto del Rosario, Canary Islands, Spain
        else if(code.equalsIgnoreCase("PVK")) this.localOffset = 2; //Preveza, Greece
        else if(code.equalsIgnoreCase("SMI")) this.localOffset = 2; //Samos, Greece
        else if(code.equalsIgnoreCase("DLM")) this.localOffset = 2; //Dalaman, Turkey
        else if(code.equalsIgnoreCase("TLV")) this.localOffset = 2; //Tel Aviv, Israel
        else if(code.equalsIgnoreCase("MAH")) this.localOffset = 1; //Menorca, Spain
        else if(code.equalsIgnoreCase("AGP")) this.localOffset = 1; //Malaga, Spain
        else if(code.equalsIgnoreCase("FNC")) this.localOffset = 0; //Funchal, Portugal
        else if(code.equalsIgnoreCase("FAO")) this.localOffset = 0; //Faro, Portugal
        else if(code.equalsIgnoreCase("CFU")) this.localOffset = 2; //Kerkyra, Greece
        else if(code.equalsIgnoreCase("IBZ")) this.localOffset = 1; //Ibiza, Spain
        else if(code.equalsIgnoreCase("ZTH")) this.localOffset = 2; //Zakinthos, Greece
        else if(code.equalsIgnoreCase("LPA")) this.localOffset = 0; //Las Palmas, Canary Islands, Spain
        else if(code.equalsIgnoreCase("PXO")) this.localOffset = 0; //Porto Santo, Portugal
        else if(code.equalsIgnoreCase("LXR")) this.localOffset = 2; //Luxor, Egypt
        else if(code.equalsIgnoreCase("CHQ")) this.localOffset = 2; //Chania, Greece
        else if(code.equalsIgnoreCase("BOJ")) this.localOffset = 2; //Bourgas, Bulgaria
        else if(code.equalsIgnoreCase("SSH")) this.localOffset = 2; //Sharm el Sheikh, Egypt
        else if(code.equalsIgnoreCase("TFS")) this.localOffset = 0; //Tenerife, Canary Islands, Spain
        else if(code.equalsIgnoreCase("JTR")) this.localOffset = 2; //Thira, Greece
        else if(code.equalsIgnoreCase("ACE")) this.localOffset = 0; //Lanzarote, Canary Islands, Spain
        else if(code.equalsIgnoreCase("MJT")) this.localOffset = 2; //Mytilene, Greece
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLocalOffset() {
        return localOffset;
    }

    public void setLocalOffset(int localTime) {
        this.localOffset = localTime;
    }

    
}
