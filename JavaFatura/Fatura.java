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
    /* Lista com a data das últimas alteração */
    private List<LocalDateTime> DataAlterada;
    /* NIF do contribuinte */
    private String NIFc; 
    /* Descrição da despesa */
    private String Desc;
    /* Lista das atividades  */
    private List<Atividade> NatDes; 
    /* Valor da despesa */
    private double ValDes;  
    /* identificador de uma fatura */
    private int id;
    /* booleano de fatura válida */
    private boolean valida;
    
    /** 
     * Cria uma instância de uma fatura
     */
    public Fatura(){ 
        this.NIFe = "n/a"; 
        this.DESIGe = "n/a"; 
        this.DataCriada = LocalDateTime.now(); 
        this.DataAlterada = new ArrayList<>();
        this.NIFc = "n/a"; 
        this.Desc = "n/a"; 
        this.NatDes = new ArrayList<>();
        this.ValDes = 0;
        this.id = -1;
        this.valida = false;
    }
    
    /** 
     * Construtor por côpia 
     * @param f
     */
    public Fatura(Fatura f){ 
        this.NIFe = f.getNIFe(); 
        this.DESIGe = f.getDESIGe(); 
        this.DataCriada = f.getDataCr(); 
        this.DataAlterada = f.getDataAlt();
        this.NIFc = f.getNIFc(); 
        this.Desc = f.getDesc(); 
        this.NatDes = f.getNatDesList(); 
        this.ValDes = f.getValDes(); 
        this.id = f.getId();
        this.valida = f.getValida();
    }
    
    /** 
     * Construtor por parâmetro  
     * @param NIFe 
     * @param DESIGe 
     * @param Data 
     * @param NIFc 
     * @param Desc 
     * @param NatDes 
     * @param ValDes 
     * @param id 
     * @param valida
     */
    public Fatura(String NIFe, String DESIGe, LocalDateTime Data, String NIFc, String Desc, Atividade NatDes, double ValDes, int id, boolean valida){ 
        this.NIFe = NIFe; 
        this.DESIGe = DESIGe; 
        this.DataCriada = Data; 
        this.DataAlterada = new ArrayList<>();
        this.NIFc = NIFc;
        this.Desc = Desc; 
        this.NatDes = new ArrayList<>();
        this.setNatDes(NatDes);
        this.ValDes = ValDes; 
        this.id = id;
        this.valida = valida;
    }
    
    /** 
     * Devolve o NIF do emitente da fatura 
     * @return 
     */
    public String getNIFe(){ 
        return this.NIFe;
    }
    
    /** 
     * Devolve a designação do emitente  
     * @return 
     */
    public String getDESIGe(){ 
        return this.DESIGe;
    }
    
    /** 
     * Devolve a data de criação da fatura 
     * @return 
     */
    public LocalDateTime getDataCr(){ 
        return this.DataCriada;
    }
    
    /** 
     * Devolve a lista das datas das alterações a fatura 
     * @return 
     */
    public List<LocalDateTime> getDataAlt(){ 
        List<LocalDateTime> l = new ArrayList<>();
        
        for(LocalDateTime d : this.DataAlterada){
            l.add(d.plusSeconds(0));
        }
        
        return l;
    }
    
    /** 
     * Devolve o NIF do contribuinte da fatura 
     * @return 
     */
    public String getNIFc(){ 
        return this.NIFc;
    }
    
    /** 
     * Devolve a descrição da fatura 
     * @return 
     */
    public String getDesc(){ 
        return this.Desc;
    }
    
    /** 
     * Devolve a natureza da despesa da fatura 
     * @return 
     */
    public Atividade getNatDes(){ 
        if(this.NatDes.size() != 0) return this.NatDes.get(this.NatDes.size() - 1);
        else return new Atividade(); 
    }
    
    /** 
     * Devolve a lista das ativadades da fatura 
     * @return 
     */
    public List<Atividade> getNatDesList(){
        List<Atividade> l = new ArrayList<>();
        
        for(Atividade a : this.NatDes){
            l.add(Atividade.fromString(a.getAtiv()));
        }
        
        return l;
    }
    
    /** 
     * Devolve o valor da despesa da fatura 
     * @return 
     */
    public double getValDes(){ 
        return this.ValDes;
    }
        
    /** 
     * Devolve o id da fatura 
     * @return 
     */
    public int getId(){
        return this.id;
    }
    
    /** 
     * Devolve o teste se uma fatura é válida 
     * @return 
     */
    public boolean getValida(){
        return this.valida;
    }
    
    /** 
     * Define o NIF emitente da fatura 
     * @param NIFe 
     */
    public void setNIFe(String NIFe){ 
        this.NIFe = NIFe;
    }
    
    /** 
     * Define a designação do emitente 
     * @param DESIGe 
     */
    public void setDESIGe(String DESIGe){ 
        this.DESIGe = DESIGe;
    }
    
    /** 
     * Define a data de criação da fatura 
     * @param Data
     */
    public void setDataCr(LocalDateTime Data){ 
        this.DataCriada = Data;
    }
    
    /** 
     * Define a lista das datas alteradas 
     * @param datas 
     */
    public void setDataAlt (List<LocalDateTime> datas){ 
        List<LocalDateTime> l = new ArrayList<>();
        
        for(LocalDateTime d : datas){
            l.add(d.plusSeconds(0));
        }
        
        this.DataAlterada = l;
    }    
    
    /** 
     * Define o NIF contribuinte da fatura 
     * @param NIFc 
     */
    public void setNIFc(String NIFc){ 
        this.NIFc = NIFc;
    }
    
    /** 
     * Define a descrição da fatura 
     * @param Desc
     */
    public void setDesc(String Desc){ 
        this.Desc = Desc;
    }
    
    /** 
     * Define a natureza da despesa de uma fatura 
     * @param NatDes
     */
    public void setNatDes(Atividade NatDes){ 
        if(!NatDes.getAtiv().equals("")){
            this.NatDes.add(Atividade.fromString(NatDes.getAtiv()));
            this.DataAlterada.add(LocalDateTime.now());
        }
    }
    
    /** 
     * Define o valor da despesa de uma fatura 
     * @param ValDes
     */
    public void setValDes(double ValDes){ 
        this.ValDes= ValDes;
    }
    
    /** 
     * Define o id da fatura 
     * @param id 
     */
    public void setId(int id){
        this.id = id;
    }
    
    /** 
     * Define a validez da fatura 
     * @param valida 
     */
    public void setValida(boolean valida){
        this.valida = valida;
    }
    
    /** 
     * Devolve a cópia da fatura 
     */
    public Fatura clone(){ 
        return new Fatura(this);
    }
    
    /** 
     * Compara a igualdade com outro objeto 
     * @param obj 
     * @return 
     */
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
    
    /** 
     * Devolve os parâmetros da fatura na forma de String 
     * @return 
     */
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
