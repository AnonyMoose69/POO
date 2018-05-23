import java.util.List; 
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.GregorianCalendar; 
import java.util.List; 
import java.io.Serializable; 

public class Fatura implements Serializable
{
    // Váriaveis de uma fatura  
    
    /* NIF do emitente */
    private String NIFe; 
    /* Designação do emitente */
    private String DESIGe; 
    /* Data da despesa */
    private String Data; 
    /* NIF do contribuinte */
    private String NIFc; 
    /* Descrição da despesa */
    private String Desc;
    /* Natureza da Despesa - Código da atividade económica */
    private String NatDes; 
    /* Valor da despesa */
    private int ValDes;  
    /* lista com consultas de cada fatura */
    private List<Consulta> consultas;  

    public Fatura(){ 
        this.NIFe = "n/a"; 
        this.DESIGe = "n/a"; 
        this.Data = "n/a"; 
        this.NIFc = "n/a"; 
        this.Desc = "n/a"; 
        this.NatDes = "n/a"; 
        this.ValDes = 0;
        this.consultas = new ArrayList<Consulta>();
    }

    public Fatura(Fatura f){ 
        this.NIFe = f.getNIFe(); 
        this.DESIGe = f.getDESIGe(); 
        this.Data = f.getData(); 
        this.NIFc = f.getNIFc(); 
        this.Desc = f.getDesc(); 
        this.NatDes = f.getNatDes(); 
        this.ValDes = f.getValDes(); 
        Iterator<Consulta> it = f.consultas.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            this.consultas.add(consult.clone());
        }
    }

    public Fatura(String NIFe, String DESIGe, String Data, String NIFc, String Desc, String NatDes, int ValDes, ArrayList<Consulta> cons){ 
        this.NIFe = NIFe; 
        this.DESIGe = DESIGe; 
        this.Data = Data; 
        this.NIFc = NIFc;
        this.Desc = Desc; 
        this.NatDes = NatDes; 
        this.ValDes = ValDes; 
        this.consultas = new ArrayList<Consulta>(); 
        Iterator<Consulta> it; 
        if(cons!=null){ 
            it = cons.iterator(); 
            while(it.hasNext()){ 
                Consulta consult = it.next(); 
                this.consultas.add(consult.clone());
            }
        }
    }

    public String getNIFe(){ 
        return this.NIFe;
    }
    public String getDESIGe(){ 
        return this.DESIGe;
    }
    public String getData(){ 
        return this.Data;
    }
    public String getNIFc(){ 
        return this.NIFc;
    }
    public String getDesc(){ 
        return this.Desc;
    }
    public String getNatDes(){ 
        return this.NatDes;
    }
    public int getValDes(){ 
        return this.ValDes;
    }

    public void setNIFe(String NIFe){ 
        this.NIFe = NIFe;
    }
    public void setDESIGe(String DESIGe){ 
        this.DESIGe = DESIGe;
    }
    public void setData(String Data){ 
        this.Data = Data;
    }
    public void setNIFc(String NIFc){ 
        this.NIFc = NIFc;
    }
    public void setDesc(String Desc){ 
        this.Desc = Desc;
    }
    public void setNatDes(String NatDes){ 
        this.NatDes = NatDes;
    }
    public void setValDes(int ValDes){ 
        this.ValDes= ValDes;
    }

    /* Obtém consultas das faturas */
    public ArrayList<Consulta> getConsultas(){ 
        ArrayList<Consulta> novo = new ArrayList<Consulta>(); 
        Iterator<Consulta> it = this.consultas.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            novo.add(consult.clone());
        }
        return novo;
    }

    /* Define as consultas de uma fatura */
    public void setConsultas(ArrayList<Consulta> lista){ 
        Iterator<Consulta> it = lista.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            this.consultas.add(consult.clone());
        }
    }

    /* Adiciona uma consulta a uma lista de consultas de uma fatura */ 
    public void adicionaConsulta(String NIFe, String NIFc, GregorianCalendar data){ 
        Consulta nova = new Consulta(NIFe,NIFc,data); 
        this.consultas.add(nova);
    }

    public Fatura clone(){ 
        return new Fatura(this);
    }
    
    public boolean equals(Object obj){ 
        if(obj == this){ 
            return true;
        }
        if((obj == null) || obj.getClass() != this.getClass()){ 
            return false;
        }
        Fatura f = (Fatura) obj; 
        return f.getNIFe().equals(this.NIFe) && f.getDESIGe().equals(this.DESIGe) 
               && f.getData().equals(this.Data) && f.getNIFc().equals(this.NIFc) 
               && f.getDesc().equals(this.Desc) && f.getNatDes().equals(this.NatDes) 
               && f.getValDes() == this.ValDes;
    }

    public String toString(){ 
    StringBuilder str; 
    str = new StringBuilder(); 
    str.append("\n*************** FATURA ***************\n"); 
    str.append("NIF emitente: "); 
    str.append(this.NIFe+"\n"); 
    str.append("Designação do emitente: "); 
    str.append(this.DESIGe+"\n");  
    str.append("Data da despesa: "); 
    str.append(this.Data+"\n"); 
    str.append("NIF contribuinte: "); 
    str.append(this.NIFc+"\n"); 
    str.append("Descrição da despesa: "); 
    str.append(this.Desc+"\n"); 
    str.append("Natureza da despesa: "); 
    str.append(this.NatDes+"\n"); 
    str.append("Valor da despesa: "); 
    str.append(this.ValDes+"\n"); 
    str.append("**************************************\n");
    return str.toString();    
    }
}
