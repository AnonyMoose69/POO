import java.io.Serializable;

/** 
 * Classe destinada a designar uma atividade econômica na JavaFatura 
 * 
 * @author Grupo 34
 */
public class Atividade implements Serializable
{
   // Variaveis de instância 
   private String ativ;
   
   /** 
    * Cria uma instância da Atividade 
    */
   public Atividade(){
       this.ativ = "";
   }
   
   /** 
    * Construtor por cópia 
    * @param a
    */
   public Atividade(Atividade a){
       this.ativ = a.getAtiv();
   }
   
   /** 
    * Construtor por parâmetro 
    * @param ativ 
    */
   public Atividade(String ativ){
       this.ativ = ativ;
   }
   
   /** 
    * Obter a atividade 
    * @return 
    */
   public String getAtiv(){
       return this.ativ;
   }
   
   /** 
    * Obter a dedução base de uma atividade 
    * @param valor 
    */
   public double getDeducao(double valor){
       return 0;
   }
   
   /** 
    * Devolve uma cópia desta instância 
    */
   public Atividade clone(){ 
       return new Atividade(this);
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
       
       Atividade a = (Atividade) obj;
       
       return this.ativ.equals(a.getAtiv());
   }
   
   /** 
    * Devolve os parâmetros da Atividade na forma de String 
    * @return 
    */
   public String toString(){      
        return this.ativ;
    }
    
   /** 
    * Retorna a atividade usando uma string como parâmetro 
    * @param a 
    * @return 
    */
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
    
   /** 
    * Retorna a atividade passando como argumento um inteiro 
    * @param natDes 
    * @return 
    */
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
