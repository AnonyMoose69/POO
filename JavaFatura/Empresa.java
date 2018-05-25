import java.util.Map; 
import java.util.HashMap; 
import java.util.List;
import java.util.stream.Collectors; 
import static java.util.stream.Collectors.toMap; 

/** 
 * Classe destinada a designar uma empresa na JavaFatura 
 * 
 * @author Grupo 34
 */
public class Empresa extends Utilizador 
{
   /* Lista das faturas da empresa */
   private Map<Integer,Fatura> faturas;  
   /* Atividade económica da empresa */
   private Atividade ativ; 
   /* Informação sobre o fator da empresa na dedução fiscal */
   private String fator;
   
   /** 
    * Cria uma instância de uma Empresa
    */
   public Empresa(){ 
       super("Empresa","","","","");  
       this.ativ = new Atividade(); 
       this.fator = "n/a";
       faturas = new HashMap<Integer,Fatura>();
   }
   
   /** 
    * Construtor por cópia 
    * @param e 
    */
   public Empresa (Empresa e){ 
       super(e); 
       this.faturas = e.getFaturas();
   }

   /** 
    * Construtor por parâmetro 
    * @param NIF 
    * @param nome 
    * @param email 
    * @param morada 
    * @param password 
    * @param a 
    * @param fator 
    * @param f
    */
   public Empresa(String NIF, String nome, String email, String morada, String password, Atividade a, String fator, HashMap<Integer,Fatura> f){ 
       super(NIF,nome,email,morada,password); 
       this.ativ = a.clone();
       this.fator = fator;
       faturas = new HashMap<Integer,Fatura>(); 
       if (f!=null)setFaturas(f);
   }
   
   /** 
    * Devolve a lista de faturas de uma empresa 
    * @return 
    */
   public Map<Integer,Fatura> getFaturas(){ 
       return this.faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));
   }
   
   /** 
    * Define a lista de faturas de uma empresa  
    * @param faturas
    */
   public void setFaturas(Map<Integer,Fatura> faturas){ 
       this.faturas = faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));        
   }
   
   /*
   public List<Fatura> getFaturasNIF(String NIF){ 
       return;
   }
   */
   /** 
    * Devolve a atividade económica da empresa
    */
   public Atividade getAtiv(){ 
       return this.ativ;
   }
   
   /** 
    * Devolve a informação acerca do fator da dedução fiscal da empresa
    */
   public String getFator(){ 
       return this.fator; 
   }
   
   /** 
    * Define a atividade económica da empresa 
    * @param ativ 
    */
   public void setAtiv(Atividade ativ){ 
       this.ativ = ativ.clone();
   }
   
   /** 
    * Define o fator de dedução fiscal da empresa 
    */
   public void setFator(String fator){ 
       this.fator = fator;
   }
 
   /** 
    * Devolve uma cópia da empresa 
    * @return 
    */
   public Empresa clone(){ 
       return new Empresa(this);
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
       Empresa e = (Empresa) obj; 
           return (super.equals(e));
   }
   
   /** 
    * Adiciona uma fatura a uma empresa 
    * @param f
    */
   public void adicionaFatura(Fatura f){ 
       this.faturas.put(f.getId(),f); 
       System.out.println(faturas.size());
   }
}
