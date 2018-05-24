import java.util.Map; 
import java.util.HashMap; 
import java.util.List;
import java.util.stream.Collectors; 
import static java.util.stream.Collectors.toMap; 

public class Empresa extends Utilizador 
{
   /* Lista das faturas da empresa */
   private Map<Integer,Fatura> faturas;  
   /* Atividade económica da empresa */
   private Atividade ativ; 
   /* Informação sobre o fator da empresa na dedução fiscal */
   private String fator;
   
   public Empresa(){ 
       super("Empresa","","","","");  
       this.ativ = new Atividade(); 
       this.fator = "n/a";
       faturas = new HashMap<Integer,Fatura>();
   }

   public Empresa (Empresa e){ 
       super(e); 
       this.faturas = e.getFaturas();
   }

   public Empresa(String NIF, String nome, String email, String morada, String password, Atividade a, String fator, HashMap<Integer,Fatura> f){ 
       super(NIF,nome,email,morada,password); 
       this.ativ = a.clone();
       this.fator = fator;
       faturas = new HashMap<Integer,Fatura>(); 
       if (f!=null)setFaturas(f);
   }

   public Map<Integer,Fatura> getFaturas(){ 
       return this.faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));
   }

   public void setFaturas(Map<Integer,Fatura> faturas){ 
       this.faturas = faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));        
   }
   
   /*
   public List<Fatura> getFaturasNIF(String NIF){ 
       return;
   }
   */
   
  public Atividade getAtiv(){ 
       return this.ativ;
   }
   public String getFator(){ 
       return this.fator; 
   }
   public void setAtiv(Atividade ativ){ 
       this.ativ = ativ.clone();
   }
   public void setFator(String fator){ 
       this.fator = fator;
   }
 
   public Empresa clone(){ 
       return new Empresa(this);
   }

   public boolean equals(Object obj){ 
       if(this == obj) 
           return true;
       if((obj==null) || (this.getClass() != obj.getClass())) 
           return false; 
       Empresa e = (Empresa) obj; 
           return (super.equals(e));
   }

   public void adicionaFatura(Fatura f){ 
       this.faturas.put(f.getId(),f); 
       System.out.println(faturas.size());
   }
}
