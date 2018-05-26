import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors; 
import java.io.Serializable;

/** 
 * Classe destinada a designar uma Familia  
 * 
 * @author Grupo 34
 */
public class Familia implements Serializable
{
    // Variaveis de instância 
    private List<String> NIFS;
    private int dependentes;
    private int id;
    
    /** 
     * Cria uma instância da familia 
     */
    public Familia(){
        this.NIFS = new ArrayList<String>();
        this.dependentes = 0;
        this.id = -1;
    }
    
    /** 
     * Construtor por parâmetro  
     * @param NIFS 
     * @param dependentes 
     * @param id 
     */
    public Familia(List<String> NIFS, int dependentes, int id){
        this.NIFS = NIFS;
        this.dependentes = dependentes;
        this.id = id;
    }
    
    /** 
     * Construtor por cópia 
     * @param f
     */
    public Familia(Familia f){
        this.NIFS = f.getNIFS();
        this.dependentes = f.getDependentes();
        this.id = f.getId();
    }
   
    /** 
     * Obter os NIFs da Familia 
     * @return 
     */
    public List<String> getNIFS(){
        Iterator<String> it = this.NIFS.iterator();
        String s;
        List<String> res = new ArrayList<String>();
        
        while(it.hasNext()){
            s = it.next();
            res.add(s);
        }
        
        return res;
    }
    
    /** 
     * Obter o número de dependentes da familia 
     * @return 
     */
    public int getDependentes(){
        return this.dependentes;
    }
    
    /** 
     * Obter o id da familia 
     * @return 
     */
    public int getId(){
        return this.id;
    }
    
    /** 
     * Define um NIF na lista de NIFs da familia 
     * @param s
     */
    public void setNIF(String s){
        this.NIFS.add(s);
    }  
    
    /** 
     * Define o número de dependentes da familia 
     * @param dep
     */
    public void setDependentes(int dep){ 
        this.dependentes = dep;
    }
    
    /**  
     * Define uma lista de NIFs da familia 
     * @param l
     */
    public void setNIFS(List<String> l){
        Iterator<String> it = l.iterator();
        String s;
        this.NIFS = new ArrayList<String>();
        
        while(it.hasNext()){
            s = it.next();
            this.NIFS.add(s);
        }
    }
    
    /** 
     * Define o id da familia 
     * @param id 
     */
    public void setId(int id){
        this.id = id;
    }
    
    /** 
     * Adiciona um membro (individual) a familia 
     * @param NIF 
     * @param depend 
     */
    public void adicionaMembro(String NIF, boolean depend){
        this.NIFS.add(NIF);
        if (depend) this.dependentes++;
    }
    
    /** 
     * Calcula o coeficiente fiscal associada a familia 
     * @return 
     */
    public double getCoefFamilia(){
       double size = this.NIFS.size();
        
       if(size < 7) return this.dependentes/(size * 3);
       else return this.dependentes/(size * 2);
    }
    
    /** 
     * Devolve uma cópia desta instância 
     */
    public Familia clone(){
        return new Familia(this);
    }
    
    /** 
     * Compara a igualdade com outro objeto 
     * @param o 
     */
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Familia f = (Familia) o ;
        
        return (this.NIFS.equals(f.getNIFS()) && this.dependentes == f.getDependentes() && this.id == f.getId());
    }
    
    /** 
     * Devolve os parâmetros da Familia na forma de String 
     * @return 
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n Nº de dependentes: ").append(this.dependentes);
        sb.append("\n Lista de NIFS da sua família").append(this.NIFS.toString());
        sb.append("\n Id da família: " + this.id);
        
        return sb.toString();
    }
}
