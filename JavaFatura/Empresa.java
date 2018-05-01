import java.util.Map; 
import java.util.HashMap; 
import java.util.stream.Collectors; 
import static java.util.stream.Collectors.toMap; 

public class Empresa extends Utilizador 
{
   /* Lista das faturas da empresa */
   private Map<String,Fatura> faturas;  
   /* Atividade económica da empresa */
   private String ativ; 
   /* Informação sobre o fator da empresa na dedução fiscal */
   private String fator;
   
   public Empresa(){ 
       super("Empresa","","","","");  
       this.ativ = "n/a"; 
       this.fator = "n/a";
       faturas = new HashMap<String,Fatura>();
   }

   public Empresa (Empresa e){ 
       super(e); 
       this.faturas = e.getFaturas();
   }

   public Empresa(String NIF, String nome, String email, String morada, String password, String ativ, String fator, HashMap<String,Fatura> f){ 
       super(NIF,nome,email,morada,password); 
       this.ativ = ativ; 
       this.fator = fator;
       faturas = new HashMap<String,Fatura>(); 
       if (f!=null)setFaturas(f);
   }

   public Map<String,Fatura> getFaturas(){ 
       return this.faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));
   }

   public void setFaturas(Map<String,Fatura> faturas){ 
       this.faturas = faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));        
   }
 
   public String getAtiv(){ 
       return this.ativ;
   }
   public String getFator(){ 
       return this.fator; 
   }
   public void setAtiv(String ativ){ 
       this.ativ = ativ;
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
       this.faturas.put(f.getNIFe(),f);
   }
}
