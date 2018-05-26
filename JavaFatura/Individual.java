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
   private List<Fatura> faturas; 
   /* Códigos da atividade económica */
   private List<Integer> CodigosAtiv;
   /* Coeficiente fiscal para efeitos de dedução */
   private double COEFiscal; 
   /* Se o utilizador se é dependente ou não */
   private boolean depend; 
   /* Guarda o id da familia */
   private int idfam;
   
   public Individual(){ 
       super("Individual","","","",""); 
       this.CodigosAtiv = new ArrayList<>();
       this.COEFiscal = 0;
       this.faturas = new ArrayList<>(); 
       this.depend = false;
       this.idfam = -1;
   }

   public Individual(Individual i){ 
       super(i); 
       this.faturas = i.getFatura(); 
       this.COEFiscal = i.getCOEFiscal();
       this.depend = i.getDepend();        
       this.idfam = i.getIDfam();
    }

   public Individual(String NIF, String nome, String email, String morada, String password, List<Integer> codigos,double Coef, List<Fatura> f, boolean dep, int id){ 
       super(NIF,nome,email,morada,password);         
       this.CodigosAtiv = new ArrayList<Integer>(); 
       for(Integer a : codigos){
           this.CodigosAtiv.add(a);
        }
       this.COEFiscal = Coef; 
       this.faturas = new ArrayList<Fatura>(); 
       this.depend = dep;
       if(f!=null)this.setFatura(f);
       this.idfam = id;
    }

   public List<Fatura> getFatura(){ 
       return this.faturas.stream().map(Fatura::clone).collect(Collectors.toList());
   }

   public void setFatura(List<Fatura> faturas){ 
       this.faturas = faturas.stream().map(Fatura::clone).collect(Collectors.toList());
   }
   
   public List<Integer> getCodigosAtiv(){ 
       List<Integer> codigos = new ArrayList<Integer>();
       for(Integer a : this.CodigosAtiv){
           codigos.add(a);
        }
       
       return codigos;
   }
   
   public double getCOEFiscal(){ 
       return this.COEFiscal;
   }
   
   public boolean getDepend(){
       return this.depend;
    }
   
   public int getIDfam(){ 
       return this.idfam;
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
       this.faturas.add(f);
   }

   public void removeFatura(Fatura f){ 
       this.faturas.remove(f);
   }

   public void setCodigosAtiv(List<Integer> codigos){ 
       for(Integer a : codigos){
           this.CodigosAtiv.add(a);
        }
       
   }
   public void setCOEFiscal(double CoeFiscal){ 
       this.COEFiscal = CoeFiscal;
   } 
   
   public void setDepend(boolean t){
       this.depend = t;
   }
    
   public void setIDfam(int id){ 
       this.idfam = id;
   }
    
   public boolean pertenceAtiv(String s){
       for(Integer i : this.CodigosAtiv){
           if (Atividade.fromInt(i).getAtiv().equals(s)) return true;
        }
       return false;
   }

   public double getValDesTotal(){
       return this.faturas.stream().mapToDouble(Fatura::getValDes).sum();
   }
    
   public double getDeducaoTotal(double coeficienteFam){
       List<Fatura> l = this.faturas;
       Double res = 0.0;
       
       for(Fatura f : l){
            Atividade a = f.getNatDes();
            String s = a.getAtiv();
            if(f.getValida() && this.pertenceAtiv(s)) res += (Atividade.fromString(s).getDeducao(f.getValDes())) + (f.getValDes() * this.COEFiscal);
            if(f.getValida()) res += f.getValDes() * coeficienteFam;
        }
       
       return res;
   }
}
