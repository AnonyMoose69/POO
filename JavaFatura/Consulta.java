import java.util.GregorianCalendar; 
import java.io.Serializable; 

public class Consulta implements Serializable
{
    private String NIFc; 
    private String NIFe; 
    private GregorianCalendar data; 
    
    public Consulta(){ 
        NIFc = " "; 
        NIFe = " "; 
        data = new GregorianCalendar();
    }

    public Consulta(Consulta c){ 
        NIFc = c.getNIFc(); 
        NIFe = c.getNIFe(); 
        data = (GregorianCalendar) c.getData().clone();
    }

    public Consulta(String NIFc, String NIFe, GregorianCalendar c){ 
        this.NIFc = NIFc; 
        this.NIFe = NIFe; 
        this.data = (GregorianCalendar) c.clone();    
    }

    public String getNIFc(){ 
        return this.NIFc;
    }
    public String getNIFe(){ 
        return this.NIFe;
    }
    public GregorianCalendar getData(){ 
        return this.data;
    }
    
    public Consulta clone(){ 
        return new Consulta(this);
    }

    public String toString(){ 
        StringBuilder str; 
        str = new StringBuilder(); 
        str.append("NIF do contribuinte: "); 
        str.append(this.NIFc).append("\n"); 
        str.append("NIF do emitente: "); 
        str.append(this.NIFe).append("\n"); 
        str.append("Data: "); 
        str.append(this.data.getTime().toString()); 
        return str.toString();
    }
}
