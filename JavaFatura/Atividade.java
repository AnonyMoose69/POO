import java.io.Serializable;

/** 
 * Classe destinada a designar uma atividade de uma empresa na JavaFatura 
 * 
 * @author Grupo 34
 */
public class Atividade implements Serializable
{
   //Variaveis de instância
   private boolean saude;
   private boolean educacao;
   private boolean restauracao;
   private boolean transporte;
   private boolean repveiculos;
   private boolean eletricidadeagua;
   private boolean naoespecificado;
   
   /** 
    * Cria uma instância da Atividade 
    */
   public Atividade(){
       this.saude = false;
       this.educacao = false;
       this.restauracao = false;
       this.transporte = false;
       this.repveiculos = false;
       this.eletricidadeagua = false; 
       this.naoespecificado = false;
   }
   
   /** 
    * Construtor por cópia 
    * @param a
    */
   public Atividade(Atividade a){
       this.saude = a.getSaude();
       this.educacao = a.getEducacao();
       this.restauracao = a.getRestauracao();
       this.transporte = a.getTransportes();
       this.repveiculos = a.getVeiculos();
       this.eletricidadeagua = a.getElet(); 
       this.naoespecificado = a.getNEspecificado();
    }
   
   /** 
    * Obter o setor de sáude 
    * @return 
    */
   public boolean getSaude(){
       return this.saude;
    }
   
   /** 
    * Obter o setor de educação 
    * @return
    */
   public boolean getEducacao(){
       return this.educacao;
   }
    
   /** 
    * Obter o setor de restauração 
    * @return 
    */
   public boolean getRestauracao(){
       return this.restauracao;
   }
   
   /** 
    * Obter o setor de transportes  
    * @return 
    */
   public boolean getTransportes(){
       return this.transporte;
   }
   
   /** 
    * Obter o setor de reparação de véiculos 
    * @return 
    */
   public boolean getVeiculos(){
       return this.repveiculos;
   }
   
   /** 
    * Obter o setor de eletrecidade e água 
    * @return 
    */
   public boolean getElet(){
       return this.eletricidadeagua;
   }
   
   /** 
    * Obter o setor não especificado (outro) 
    * @return 
    */
   public boolean getNEspecificado(){ 
       return this.naoespecificado;
   }
   
   /** 
    * Dada a escolha feita define os setores de atividade  
    * @param n 
    * @return 
    */
   public void setBoolInfo(int n){
       if (n>=1 && n<=7){
           if (n == 1) {this.saude = true; return;}
           if (n == 2) {this.educacao = true; return;}
           if (n == 3) {this.restauracao = true; return;}
           if (n == 4) {this.transporte = true; return;}
           if (n == 5) {this.repveiculos = true; return;}
           if (n == 6) {this.eletricidadeagua = true; return;}
           if (n == 7) {this.naoespecificado = true; return;}
        }
       
    }
   
   /** 
    * Define o setor de sáude 
    * @param saude 
    */
   public void setSaude(boolean saude){
       this.saude = saude;
   }
   
   /** 
    * Define o setor de educação 
    * @param educacao 
    */
   public void setEducacao(boolean educacao){
       this.educacao = educacao;
   }
   
   /** 
    * Define o setor de restauração 
    * @param restauracao
    */
   public void setRestauracao(boolean restauracao){
       this.restauracao = restauracao;
   }
   
   /** 
    * Define o setor de transportes 
    * @param transporte 
    */
   public void setTransportes(boolean transporte){
       this.transporte = transporte;
   }
   
   /** 
    * Define o setor de reparação de veículos 
    * @param veiculos
    */
   public void setVeiculos(boolean veiculos){
       this.repveiculos = veiculos;
   }
   
   /** 
    * Define o setor de eletrecidade e água 
    * @param elet
    */
   public void setElet(boolean elet){
       this.eletricidadeagua = elet;
   }
   
   /** 
    * Define o setor não especificado (outro) 
    * @param nesp
    */
   public void setNEspecificado(boolean nesp){ 
       this.naoespecificado = nesp;
   }
   
   /** 
    * Testa se possui mais uma ou mais atividades associadas 
    * @return 
    */
   public boolean temAtividadeOuMaisQueUma(){
       int r = 0;
       if (this.saude) r++;
       if (this.educacao) r++;
       if (this.restauracao) r++;
       if (this.transporte) r++;
       if (this.repveiculos) r++;
       if (this.eletricidadeagua) r++;
       if (this.naoespecificado) r++;
       
       return (r >= 2 || r == 0);
       
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
       
       return this.saude == a.getSaude() && this.educacao == a.getEducacao() 
                                         && this.restauracao == a.getRestauracao()
                                         && this.transporte == a.getTransportes()
                                         && this.repveiculos == a.getVeiculos()
                                         && this.eletricidadeagua == a.getElet()
                                         && this.naoespecificado == a.getNEspecificado();
   }
   
   /** 
    * Devolve os parâmetros da Atividade na forma de String 
    * @return 
    */
   public String toString(){ 
        StringBuilder str; 
        str = new StringBuilder();          
        
        if(this.saude) str.append("\nSaude");
        if(this.educacao) str.append("\nEducacao");
        if(this.restauracao) str.append("\nRestauracao");
        if(this.transporte) str.append("\nTransporte");
        if(this.repveiculos) str.append("\nReparacao de veiculos");
        if(this.eletricidadeagua) str.append("\nEletricidade e agua");
        if(this.naoespecificado) str.append("\nNão especificado");
        
        return str.append("\n").toString();
    }
}
