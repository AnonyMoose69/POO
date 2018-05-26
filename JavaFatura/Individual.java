import java.util.Map; 
import java.util.HashMap; 
import java.util.stream.Collectors;  
import java.util.List; 
import java.util.ArrayList;
import java.util.Collection;
import static java.util.stream.Collectors.toMap; 

/** 
 * Classe destinada a designar um individual na JavaFatura 
 * 
 * @author Grupo 34
 */
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
   
   /** 
    * Cria uma instância de um individual 
    */
   public Individual(){ 
       super("Individual","","","",""); 
       this.CodigosAtiv = new ArrayList<>();
       this.COEFiscal = 0;
       this.faturas = new ArrayList<>(); 
       this.depend = false;
       this.idfam = -1;
   }
   
   /** 
    * Construtor por cópia 
    * @param i 
    */
   public Individual(Individual i){ 
       super(i); 
       this.faturas = i.getFatura(); 
       this.COEFiscal = i.getCOEFiscal();
       this.depend = i.getDepend();        
       this.idfam = i.getIDfam();
   }

   /** 
    * Construtor por parâmetro 
    * @param NIF 
    * @param nome 
    * @param email 
    * @param morada 
    * @param password 
    * @param codigos 
    * @param Coef
    * @param f 
    * @param dep 
    * @param id
    */
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
   
   /** 
    * Devolve a lista de faturas de um individual  
    * @return
    */
   public List<Fatura> getFatura(){ 
       return this.faturas.stream().map(Fatura::clone).collect(Collectors.toList());
   }
   
   /** 
    * Define a lista de faturas de um individual 
    * @param faturas
    */
   public void setFatura(List<Fatura> faturas){ 
       this.faturas = faturas.stream().map(Fatura::clone).collect(Collectors.toList());
   }
   
   /** 
    * Devolve a lista de códigos de atividade escolhidos de um individual 
    * @return
    */
   public List<Integer> getCodigosAtiv(){ 
       List<Integer> codigos = new ArrayList<Integer>();
       for(Integer a : this.CodigosAtiv){
           codigos.add(a);
        }
       
       return codigos;
   }
   
   /** 
    * Devolve o coefieciente fiscal associado a um individual 
    * @return 
    */
   public double getCOEFiscal(){ 
       return this.COEFiscal;
   }
   
   /** 
    * Obter o teste caso seja individual dependente de outros ou não
    * @return 
    */
   public boolean getDepend(){
       return this.depend;
    }
   
   /** 
    * Devolve o ID da família do individual 
    * @return 
    */
   public int getIDfam(){ 
       return this.idfam;
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
       this.faturas.add(f);
   }
   
   /** 
    * Remove fatura de um individual 
    * @param f
    */
   public void removeFatura(Fatura f){ 
       this.faturas.remove(f);
   }
   
   /** 
    * Define os códigos de atividade de um individuo 
    * @param codigos
    */
   public void setCodigosAtiv(List<Integer> codigos){ 
       for(Integer a : codigos){
           this.CodigosAtiv.add(a);
        }
       
   }
   
   /** 
    * Define o coeficiente fiscal associado a um individuo 
    * @param CoeFiscal
    */
   public void setCOEFiscal(double CoeFiscal){ 
       this.COEFiscal = CoeFiscal;
   } 
   
   /** 
    * Define se um individual é dependente de outros ou não 
    * @param t
    */
   public void setDepend(boolean t){
       this.depend = t;
   }
    
   /** 
    * Define o id da família (agregado) de um individual  
    * @param id
    */
   public void setIDfam(int id){ 
       this.idfam = id;
   }
   
   /** 
    * Testa se uma dada atividade pertence ao individual 
    * @param s 
    * @return
    */
   public boolean pertenceAtiv(String s){
       for(Integer i : this.CodigosAtiv){
           if (Atividade.fromInt(i).getAtiv().equals(s)) return true;
        }
       return false;
   }
   
   /** 
    * Devolve o valor da despesa total gasta por um individual 
    * @return 
    */
   public double getValDesTotal(){
       return this.faturas.stream().mapToDouble(Fatura::getValDes).sum();
   }
    
   /** 
    * Obter a dedução total de um individual e do seu agregado  
    * @param coeficienteFam  
    * @return 
    */
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
