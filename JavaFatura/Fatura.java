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
    private LocalDateTime DataCriada; 
    /* Data da última alteração */
    private List<LocalDateTime> DataAlterada;
    /* NIF do contribuinte */
    private String NIFc; 
    /* Descrição da despesa */
    private String Desc;
    /* Natureza da Despesa - Código da atividade económica */
    private List<Atividade> NatDes; 
    /* Valor da despesa */
    private double ValDes;  
    /* lista com consultas de cada fatura */
    private List<Consulta> consultas;  
    /* identificador de uma fatura */
    private int id;
    /* booleano de fatura válida */
    private boolean valida;

    public Fatura(){ 
        this.NIFe = "n/a"; 
        this.DESIGe = "n/a"; 
        this.DataCriada = LocalDateTime.now(); 
        this.DataAlterada = new ArrayList<>();
        this.NIFc = "n/a"; 
        this.Desc = "n/a"; 
        this.NatDes = new ArrayList<>();
        this.ValDes = 0;
        this.consultas = new ArrayList<Consulta>();
        this.id = -1;
        this.valida = false;
    }

    public Fatura(Fatura f){ 
        this.NIFe = f.getNIFe(); 
        this.DESIGe = f.getDESIGe(); 
        this.DataCriada = f.getDataCr(); 
        this.DataAlterada = f.getDataAlt();
        this.NIFc = f.getNIFc(); 
        this.Desc = f.getDesc(); 
        this.NatDes = f.getNatDesList(); 
        this.ValDes = f.getValDes(); 
        Iterator<Consulta> it = f.consultas.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            this.consultas.add(consult.clone());
        }
        this.id = f.getId();
        this.valida = f.getValida();
    }

    public Fatura(String NIFe, String DESIGe, LocalDateTime Data, String NIFc, String Desc, Atividade NatDes, double ValDes, ArrayList<Consulta> cons, int id, boolean valida){ 
        this.NIFe = NIFe; 
        this.DESIGe = DESIGe; 
        this.DataCriada = Data; 
        this.DataAlterada = new ArrayList<>();
        this.NIFc = NIFc;
        this.Desc = Desc; 
        this.NatDes = new ArrayList<>();
        this.setNatDes(NatDes);
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
        this.valida = valida;
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
    public List<LocalDateTime> getDataAlt(){ 
        List<LocalDateTime> l = new ArrayList<>();
        
        for(LocalDateTime d : this.DataAlterada){
            l.add(d.plusSeconds(0));
        }
        
        return l;
    }
    public String getNIFc(){ 
        return this.NIFc;
    }
    public String getDesc(){ 
        return this.Desc;
    }
    public Atividade getNatDes(){ 
        if(this.NatDes.size() != 0) return this.NatDes.get(this.NatDes.size() - 1);
        else return new Atividade(); 
    }
    
    public List<Atividade> getNatDesList(){
        List<Atividade> l = new ArrayList<>();
        
        for(Atividade a : this.NatDes){
            l.add(Atividade.fromString(a.getAtiv()));
        }
        
        return l;
    }
    public double getValDes(){ 
        return this.ValDes;
    }
    
    public int getId(){
        return this.id;
    }
    
    public boolean getValida(){
        return this.valida;
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
    public void setDataAlt (List<LocalDateTime> datas){ 
        List<LocalDateTime> l = new ArrayList<>();
        
        for(LocalDateTime d : datas){
            l.add(d.plusSeconds(0));
        }
        
        this.DataAlterada = l;
    }    
    public void setNIFc(String NIFc){ 
        this.NIFc = NIFc;
    }
    public void setDesc(String Desc){ 
        this.Desc = Desc;
    }
    public void setNatDes(Atividade NatDes){ 
        if(!NatDes.getAtiv().equals("")){
            this.NatDes.add(Atividade.fromString(NatDes.getAtiv()));
            this.DataAlterada.add(LocalDateTime.now());
        }
    }
    public void setValDes(double ValDes){ 
        this.ValDes= ValDes;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setValida(boolean valida){
        this.valida = valida;
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
        
        List<LocalDateTime> ld = f.getDataAlt();
        if (ld.size() != this.DataAlterada.size()) return false;
        
        for(int i = 0; i < ld.size(); i++)
            if(!ld.get(i).equals(this.DataAlterada.get(i))) return false;
        
        List<Atividade> nd = f.getNatDesList();
        if (nd.size() != this.NatDes.size()) return false;
        
        for(int i = 0; i < nd.size(); i++)
            if(!nd.get(i).equals(this.NatDes.get(i))) return false;
        
        return f.getNIFe().equals(this.NIFe) && f.getDESIGe().equals(this.DESIGe) 
               && f.getDataCr().equals(this.DataCriada) && f.getNIFc().equals(this.NIFc) 
               && f.getDesc().equals(this.Desc) 
               && f.getValDes() == this.ValDes && f.getId() == this.id && this.valida == f.getValida();
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
        str.append("Natureza da despesa: "); 
    str.append(this.NatDes.toString()+"\n"); 
    str.append("NIF contribuinte: "); 
    str.append(this.NIFc+"\n"); 
    str.append("Descrição da despesa: "); 
    str.append(this.Desc+"\n"); 
    str.append("Valor da despesa: "); 
    str.append(this.ValDes+"\n"); 
    str.append("Id da fatura: ");
    str.append(this.id+"\n");
    str.append("Válida: ");
    str.append(this.valida + "\n");
    str.append("**************************************\n");
    return str.toString();    
    }
}
