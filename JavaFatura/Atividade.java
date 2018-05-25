import java.io.Serializable;
/**
 * Write a description of class Atividade here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Atividade implements Serializable
{
   private boolean saude;
   private boolean educacao;
   private boolean restauracao;
   private boolean transporte;
   private boolean repveiculos;
   private boolean eletricidadeagua;
   
   public Atividade(){
       this.saude = false;
       this.educacao = false;
       this.restauracao = false;
       this.transporte = false;
       this.repveiculos = false;
       this.eletricidadeagua = false;
    }
   
   public Atividade(Atividade a){
       this.saude = a.getSaude();
       this.educacao = a.getEducacao();
       this.restauracao = a.getRestauracao();
       this.transporte = a.getTransportes();
       this.repveiculos = a.getVeiculos();
       this.eletricidadeagua = a.getElet();
    }
   
   public boolean getSaude(){
       return this.saude;
    }
    
   public boolean getEducacao(){
       return this.educacao;
    }
    
   public boolean getRestauracao(){
       return this.restauracao;
    }
   
   public boolean getTransportes(){
       return this.transporte;
   }
   
   public boolean getVeiculos(){
       return this.repveiculos;
   }
   
   public boolean getElet(){
       return this.eletricidadeagua;
   }
   
   public void setBoolInfo(int n){
       if (n>=1 && n<=6){
           if (n == 1) {this.saude = true; return;}
           if (n == 2) {this.educacao = true; return;}
           if (n == 3) {this.restauracao = true; return;}
           if (n == 4) {this.transporte = true; return;}
           if (n == 5) {this.repveiculos = true; return;}
           if (n == 6) {this.eletricidadeagua = true; return;}
        }
       
    }
    
   public void setSaude(boolean saude){
       this.saude = saude;
   }
   
   public void setEducacao(boolean educacao){
       this.educacao = educacao;
   }
   
   public void setRestauracao(boolean restauracao){
       this.restauracao = restauracao;
   }
   
   public void setTransportes(boolean transporte){
       this.transporte = transporte;
   }
   
   public void setVeiculos(boolean veiculos){
       this.repveiculos = veiculos;
   }
   
   public void setElet(boolean elet){
       this.eletricidadeagua = elet;
   }
   
   public boolean temAtividadeOuMaisQueUma(){
       int r = 0;
       if (this.saude) r++;
       if (this.educacao) r++;
       if (this.restauracao) r++;
       if (this.transporte) r++;
       if (this.repveiculos) r++;
       if (this.eletricidadeagua) r++;
       
       return (r >= 2 || r == 0);
       
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
       
       return this.saude == a.getSaude() && this.educacao == a.getEducacao() 
                                         && this.restauracao == a.getRestauracao()
                                         && this.transporte == a.getTransportes()
                                         && this.repveiculos == a.getVeiculos()
                                         && this.eletricidadeagua == a.getElet();
   }
   
   public String toString(){ 
        StringBuilder str; 
        str = new StringBuilder(); 
        str.append("Atividades: "); 
        
        if(this.saude) str.append("\nSaude");
        if(this.educacao) str.append("\nEducacao");
        if(this.restauracao) str.append("\nRestauracao");
        if(this.transporte) str.append("\nTransporte");
        if(this.repveiculos) str.append("\nReparacao de veiculos");
        if(this.eletricidadeagua) str.append("\nEletricidade e agua");
        
        return str.append("\n").toString();
    }
}
