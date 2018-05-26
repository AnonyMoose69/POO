import java.util.Map; 
import java.util.HashMap; 
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors; 
import static java.util.stream.Collectors.toMap; 

public class Empresa extends Utilizador 
{
   /* Lista das faturas da empresa */
   private Map<Integer,Fatura> faturas;  
   /* Atividade económica da empresa */
   private List<String> ativ; 
   /* Informação sobre o fator da empresa na dedução fiscal */
   private String fator;
   
   public Empresa(){ 
       super("Empresa","","","","");  
       this.ativ = new ArrayList<>();
       this.fator = "n/a";
       faturas = new HashMap<Integer,Fatura>();
   }

   public Empresa (Empresa e){ 
       super(e); 
       this.faturas = e.getFaturas();
       this.ativ = e.getAtiv();
       this.fator = e.getFator();
   }

   public Empresa(String NIF, String nome, String email, String morada, String password, List<String> la, String fator, HashMap<Integer,Fatura> f){ 
       super(NIF,nome,email,morada,password); 
       List<String> nova = new ArrayList<>();
       for(String s : la){
           nova.add(s);
        }
       this.ativ = nova;
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
   
  public List<String> getAtiv(){ 
       List<String> l = new ArrayList<>();
       for(String s : this.ativ){
           l.add(s);
        }
       return l;
   }
   public String getFator(){ 
       return this.fator; 
   }
   public void setAtiv(List<String> a){ 
       List<String> l = new ArrayList<>();
       for(String s : a){
           l.add(s);
        }
       this.ativ = l;
   }
   public void setFator(String fator){ 
       this.fator = fator;
   }
 
   public double getLucro(){
       return this.faturas.values().stream().mapToDouble(Fatura::getValDes).sum();
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
