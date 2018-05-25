import java.util.List; 
import java.util.ArrayList; 
import java.util.Iterator;  
import java.util.List;  
import java.util.GregorianCalendar;
import java.io.Serializable; 
import java.time.LocalDateTime; 

public class Fatura implements Serializable
{
    // Váriaveis de uma fatura  
    
    /* NIF do emitente */
    private String NIFe; 
    /* Designação do emitente */
    private String DESIGe; 
    /* Data da despesa */
    private  LocalDateTime DataCriada; 
    /* Data da última alteração */
    private LocalDateTime DataAlterada;
    /* NIF do contribuinte */
    private String NIFc; 
    /* Descrição da despesa */
    private String Desc;
    /* Natureza da Despesa - Código da atividade económica */
    private Atividade NatDes; 
    /* Valor da despesa */
    private double ValDes;  
    /* lista com consultas de cada fatura */
    private List<Consulta> consultas;  
    /* identificador de uma fatura */
    private int id;
    /* dedução fiscal */
    private double deducao;

    public Fatura(){ 
        this.NIFe = "n/a"; 
        this.DESIGe = "n/a"; 
        this.DataCriada = LocalDateTime.now(); 
        this.DataAlterada = LocalDateTime.now();
        this.NIFc = "n/a"; 
        this.Desc = "n/a"; 
        this.NatDes = new Atividade();
        this.ValDes = 0;
        this.consultas = new ArrayList<Consulta>();
        this.id = -1;
        this.deducao = 0;
        
    }

    public Fatura(Fatura f){ 
        this.NIFe = f.getNIFe(); 
        this.DESIGe = f.getDESIGe(); 
        this.DataCriada = f.getDataCr(); 
        this.DataAlterada = f.getDataAlt();
        this.NIFc = f.getNIFc(); 
        this.Desc = f.getDesc(); 
        this.NatDes = f.getNatDes(); 
        this.ValDes = f.getValDes(); 
        Iterator<Consulta> it = f.consultas.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            this.consultas.add(consult.clone());
        }
        this.id = f.getId();
        this.deducao = f.getDeducao();
    }

    public Fatura(String NIFe, String DESIGe, LocalDateTime Data, String NIFc, String Desc, Atividade NatDes, double ValDes, ArrayList<Consulta> cons, int id, double deducao){ 
        this.NIFe = NIFe; 
        this.DESIGe = DESIGe; 
        this.DataCriada = Data; 
        this.DataAlterada = Data;
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
        this.id = id;
        this.deducao = deducao;
    }

    public String getNIFe(){ 
        return this.NIFe;
    }
    public String getDESIGe(){ 
        return this.DESIGe;
    }
    public LocalDateTime getDataCr(){ 
        return this.DataCriada;
    }
    public LocalDateTime getDataAlt(){ 
        return this.DataAlterada;
    }
    public String getNIFc(){ 
        return this.NIFc;
    }
    public String getDesc(){ 
        return this.Desc;
    }
    public Atividade getNatDes(){ 
        return this.NatDes;
    }
    public double getValDes(){ 
        return this.ValDes;
    }
    
    public int getId(){
        return this.id;
    }
    
    public double getDeducao(){
        return this.deducao;
    }

    public void setNIFe(String NIFe){ 
        this.NIFe = NIFe;
    }
    public void setDESIGe(String DESIGe){ 
        this.DESIGe = DESIGe;
    }
    public void setDataCr(LocalDateTime Data){ 
        this.DataCriada = Data;
    }
    public void setDataAlt (LocalDateTime Data){ 
        this.DataAlterada = Data;
    }    
    public void setNIFc(String NIFc){ 
        this.NIFc = NIFc;
    }
    public void setDesc(String Desc){ 
        this.Desc = Desc;
    }
    public void setNatDes(Atividade NatDes){ 
        this.NatDes = NatDes;
    }
    public void setValDes(double ValDes){ 
        this.ValDes= ValDes;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setDeducao(double deducao){
        this.deducao = deducao;
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
               && f.getDataCr().equals(this.DataCriada) && f.getDataAlt().equals(this.DataAlterada) && f.getNIFc().equals(this.NIFc) 
               && f.getDesc().equals(this.Desc) && f.getNatDes().equals(this.NatDes) 
               && f.getValDes() == this.ValDes && f.getId() == this.id && f.getDeducao() == this.deducao;
    }

    public String toString(){ 
    StringBuilder str; 
    str = new StringBuilder(); 
    str.append("\n*************** FATURA ***************\n"); 
    str.append("NIF emitente: "); 
    str.append(this.NIFe+"\n"); 
    str.append("Designação do emitente: "); 
    str.append(this.DESIGe+"\n");  
    str.append("Data da despesa criada: "); 
    str.append(this.DataCriada.toString()+"\n"); 
    str.append("Data da última alteração: "); 
    str.append(this.DataAlterada.toString()+"\n");
    str.append("NIF contribuinte: "); 
    str.append(this.NIFc+"\n"); 
    str.append("Descrição da despesa: "); 
    str.append(this.Desc+"\n"); 
    str.append("Natureza da despesa: "); 
    str.append(this.NatDes.toString()+"\n"); 
    str.append("Valor da despesa: "); 
    str.append(this.ValDes+"\n"); 
    str.append("Id da fatura: ");
    str.append(this.id+"\n");
    str.append("Deducao: ");
    str.append(this.deducao + "\n");
    str.append("**************************************\n");
    return str.toString();    
    }
}
