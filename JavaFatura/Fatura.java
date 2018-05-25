import java.util.List; 
import java.util.ArrayList; 
import java.util.Iterator;  
import java.util.List;  
import java.util.GregorianCalendar;
import java.io.Serializable; 
import java.time.LocalDateTime; 

/** 
 * Classe destinada a designar uma fatura na JavaFatura 
 * 
 * @author Grupo 34
 */
public class Fatura implements Serializable
{
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
    
    /** 
     * Cria uma instância de uma fatura
     */
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
    
    /** 
     * Construtor por parâmetro  
     * @param NIFe 
     * @param DESIGe 
     * @param Data 
     * @param NIFc 
     * @param Desc 
     * @param NatDes 
     * @param ValDes 
     * @param cons 
     * @param id 
     * @param deducao
     */
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
     * Devolve a data da alteração da fatura 
     * @return 
     */
    public LocalDateTime getDataAlt(){ 
        return this.DataAlterada;
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
    public Atividade getNatDes(){ 
        return this.NatDes;
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
     * Devolve a dedução associada da fatura 
     * @return 
     */
    public double getDeducao(){
        return this.deducao;
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
     * Define a data de alteração da fatura 
     * @param Data
     */
    public void setDataAlt (LocalDateTime Data){ 
        this.DataAlterada = Data;
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
     * Define a natureza da despesa da fatura
     * @param NatDes
     */
    public void setNatDes(Atividade NatDes){ 
        this.NatDes = NatDes;
    }
    
    /** 
     * Define o valor da despesa da fatura 
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
     * Define a dedução associada a uma fatura 
     * @param deducao 
     */
    public void setDeducao(double deducao){
        this.deducao = deducao;
    }

    /** 
     * Obtem a lista de consultas de uma fatura 
     * @return 
     */
    public ArrayList<Consulta> getConsultas(){ 
        ArrayList<Consulta> novo = new ArrayList<Consulta>(); 
        Iterator<Consulta> it = this.consultas.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            novo.add(consult.clone());
        }
        return novo;
    }

    /** 
     * Define a lista de consultas de uma fatura 
     * @param lista
     */
    public void setConsultas(ArrayList<Consulta> lista){ 
        Iterator<Consulta> it = lista.iterator(); 
        while(it.hasNext()){ 
            Consulta consult = it.next(); 
            this.consultas.add(consult.clone());
        }
    }

    /** 
     * Adiciona uma consulta a uma fatura 
     * @param NIFe 
     * @param NIFc 
     * @param data 
     */
    public void adicionaConsulta(String NIFe, String NIFc, GregorianCalendar data){ 
        Consulta nova = new Consulta(NIFe,NIFc,data); 
        this.consultas.add(nova);
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
        return f.getNIFe().equals(this.NIFe) && f.getDESIGe().equals(this.DESIGe) 
               && f.getDataCr().equals(this.DataCriada) && f.getDataAlt().equals(this.DataAlterada) && f.getNIFc().equals(this.NIFc) 
               && f.getDesc().equals(this.Desc) && f.getNatDes().equals(this.NatDes) 
               && f.getValDes() == this.ValDes && f.getId() == this.id && f.getDeducao() == this.deducao;
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
