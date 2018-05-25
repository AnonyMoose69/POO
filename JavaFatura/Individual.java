import java.util.Map; 
import java.util.HashMap; 
import java.util.stream.Collectors;  
import java.util.List; 
import java.util.ArrayList;
import java.util.Collection;
import static java.util.stream.Collectors.toMap; 

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
   
   
   public Individual(){ 
       super("Individual","","","",""); 
       this.NIFs = "n/a";
       this.CodigosAtiv = "n/a"; 
       this.Dependentes = 0; 
       this.COEFiscal = 0;
       this.faturas = new HashMap<Integer,Fatura>();
   }

   public Individual(Individual i){ 
       super(i); 
       this.faturas = i.getFatura();
   }

   public Individual(String NIF, String nome, String email, String morada, String password, String NIFs, String codigos, int Depend, int Coef, HashMap<Integer,Fatura> f){ 
       super(NIF,nome,email,morada,password);
       this.NIFs = NIFs;         
       this.CodigosAtiv = codigos; 
       this.COEFiscal = Coef; 
       this.Dependentes = Depend;
       this.faturas = new HashMap<Integer,Fatura>(); 
       if(f!=null)this.setFatura(f);
   }

   public Map<Integer,Fatura> getFatura(){ 
       return this.faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue().clone()));
   }

   public void setFatura(Map<Integer,Fatura> faturas){ 
       this.faturas = faturas.entrySet().stream().collect(toMap(e->e.getKey(), e->e.getValue())); 
   }
   
   public String getNIFs(){ 
       return this.NIFs;
   }
   public String getCodigosAtiv(){ 
       return this.CodigosAtiv;
   }
   public int getCOEFiscal(){ 
       return this.COEFiscal;
   }
   public int getDependentes(){ 
       return this.Dependentes;
   }
   
   public Individual clone(){ 
       return new Individual(this);
   }

   
   public boolean equals(Object obj){ 
       if(this == obj) 
        return true; 
       if((obj==null) || (this.getClass() != obj.getClass())) 
        return false; 
       Individual i = (Individual) obj; 
        return (super.equals(i));
   }

   public void adicionaFatura(Fatura f){ 
       this.faturas.put(f.getId(),f);
   }

   public void removeFatura(Fatura f){ 
       this.faturas.remove(f);
   }

   public void setNIFs(String NIFs){ 
       this.NIFs = NIFs;
   }
   public void setCodigosAtiv(String CodigosAtiv){ 
       this.CodigosAtiv = CodigosAtiv;
   }
   public void setCOEFiscal(int CoeFiscal){ 
       this.COEFiscal = CoeFiscal;
   } 
   public void setDependentes(int Dependentes){ 
       this.Dependentes = Dependentes;
   }
   
   public double getDeducaoTotal(){
       Collection<Fatura> l = this.faturas.values();
       Double res = 0.0;
       
       for(Fatura f : l){
            
            if(f.getValida()) res += f.getNatDes().getDeducao(f.getValDes());
        }
       
       return res;
    }
}
