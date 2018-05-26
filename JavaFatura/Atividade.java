import java.io.Serializable;
/**
 * Write a description of class Atividade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Atividade implements Serializable
{
   private String ativ;
   
   public Atividade(){
       this.ativ = "";
    }
   
   public Atividade(Atividade a){
       this.ativ = a.getAtiv();
    }
   
   public Atividade(String ativ){
       this.ativ = ativ;
    }
    
   public String getAtiv(){
       return this.ativ;
    }
   
   public double getDeducao(double valor){
       return 0;
    }
   public Atividade clone(){ 
       return new Atividade(this);
   }
   
   public boolean equals(Object obj){ 
       if(this == obj) 
        return true; 
       if((obj==null) || (this.getClass() != obj.getClass())) 
        return false; 
       
       Atividade a = (Atividade) obj;
       
       return this.ativ.equals(a.getAtiv());
   }
   
   public String toString(){      
        return this.ativ;
    }
    
   public static Atividade fromString(String a){
       switch(a){
            case "Saude": return new Saude();
            case "Educacao": return new Educacao();   
            case "Restauracao": return new Restauracao();           
            case "Transportes": return new Transportes();                
            case "ReparacaodeVeiculos": return new ReparacaodeVeiculos();                           
            case "EletricidadeAgua": return new EletricidadeAgua();                  
                     
        }
       return new Atividade();
    }
    
   public static Atividade fromInt(int natDes){
       switch(natDes){
            case 1: return new Saude();
            case 2: return new Educacao();
            case 3: return new Restauracao();
            case 4: return new Transportes();
            case 5: return new ReparacaodeVeiculos();
            case 6: return new EletricidadeAgua();
        }
        return new Atividade();
    }
}
