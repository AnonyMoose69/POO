import java.util.Map; 
import java.util.HashMap; 
import java.util.stream.Collectors;  
import java.util.List; 
import java.util.ArrayList;
import static java.util.stream.Collectors.toMap; 

/** 
 * Classe destinada a designar um individual na JavaFatura 
 * 
 * @author Grupo 34
 */
public class Individual extends Utilizador
{
   /* Lista com todas as faturas associadas ao individuo */ 
   private Map<Integer,Fatura> faturas; 
   /* Números de identificação fiscal do agregado familiar */
   private String NIFs; 
   /* Códigos da atividade económica */
   private String CodigosAtiv;
   /* Número de dependentes do agregado familiar */
   private int Dependentes;
   /* Coeficiente fiscal para efeitos de dedução */
   private int COEFiscal; 
   
   /** 
    * Cria uma instância de um individual 
    */
   public Individual(){ 
       super("Individual","","","",""); 
       this.NIFs = "n/a";
       this.CodigosAtiv = "n/a"; 
       this.Dependentes = 0; 
       this.COEFiscal = 0;
       this.faturas = new HashMap<Integer,Fatura>();
   }
   
   /** 
    * Construtor por cópia 
    * @param i 
    */
   public Individual(Individual i){ 
       super(i); 
       this.faturas = i.getFatura();
   }
   
   /** 
    * Construtor por parâmetro 
    * @param NIF 
    * @param nome 
    * @param email 
    * @param morada 
    * @param password 
    * @param NIFs 
    * @param codigos 
    * @param Depend 
    * @param Coef 
    * @param f 
    */
   public Individual(String NIF, String nome, String email, String morada, String password, String NIFs, String codigos, int Depend, int Coef, HashMap<Integer,Fatura> f){ 
       super(NIF,nome,email,morada,password);
       this.NIFs = NIFs;         
       this.CodigosAtiv = codigos; 
       this.COEFiscal = Coef; 
       this.Dependentes = Depend;
       this.faturas = new HashMap<Integer,Fatura>(); 
       if(f!=null)this.setFatura(f);
   }
   
   /** 
    * Devolve a lista de faturas de um individual  
    * @return
    */
   public Map<Integer,Fatura> getFatura(){ 
       return this.faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));
   }
   
   /** 
    * Define a lista de faturas de um individual 
    * @param faturas
    */
   public void setFatura(Map<Integer,Fatura> faturas){ 
       this.faturas = faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue())); 
   }
   
   /** 
    * Devolve os NIFs do individual 
    * @return 
    */
   public String getNIFs(){ 
       return this.NIFs;
   }
   
   /** 
    * Devolve os códigos de atividade 
    * @return 
    */
   public String getCodigosAtiv(){ 
       return this.CodigosAtiv;
   }
   
   /** 
    * Devolve o coeficiente fiscal 
    * @return 
    */
   public int getCOEFiscal(){ 
       return this.COEFiscal;
   }
   
   /** 
    * Devolve o número de dependentes do individual 
    * @return 
    */
   public int getDependentes(){ 
       return this.Dependentes;
   }
      
   /** 
    * Devolve uma cópia do individual 
    * @return 
    */
   public Individual clone(){ 
       return new Individual(this);
   }

   /** 
    * Compara a igualdade com outro objeto 
    * @param obj 
    * @return 
    */
   public boolean equals(Object obj){ 
       if(this == obj) 
        return true; 
       if((obj==null) || (this.getClass() != obj.getClass())) 
        return false; 
       Individual i = (Individual) obj; 
        return (super.equals(i));
   }
   
   /** 
    * Adiciona fatura a um individual 
    * @param f 
    */
   public void adicionaFatura(Fatura f){ 
       this.faturas.put(f.getId(),f);
   }
   
   /** 
    * Remove fatura de um individual 
    * @param f
    */
   public void removeFatura(Fatura f){ 
       this.faturas.remove(f);
   }
   
   /** 
    * Define os NIFs de um individual 
    * @param NIFs 
    */
   public void setNIFs(String NIFs){ 
       this.NIFs = NIFs;
   }
   
   /** 
    * Define os códigos de atividade de um individual 
    * @param CodigosAtiv
    */
   public void setCodigosAtiv(String CodigosAtiv){ 
       this.CodigosAtiv = CodigosAtiv;
   }
   
   /** 
    * Define o coeficiente fiscal de um indvidual 
    * @param CoeFiscal 
    */
   public void setCOEFiscal(int CoeFiscal){ 
       this.COEFiscal = CoeFiscal;
   } 
   
   /** 
    * Define os dependentes de um individual 
    * @param Dependentes
    */
   public void setDependentes(int Dependentes){ 
       this.Dependentes = Dependentes;
   }
}
