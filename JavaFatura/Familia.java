import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

/** 
 * Classe destinada a designar uma Familia sendo extendida até ao Individual 
 * 
 * @author Grupo 34
 */
public class Familia extends Individual
{
    //Variaveis de instância 
    private List<String> NIFS;
    private int agregado;
    
    /** 
     * Cria uma instância da familia 
     */
    public Familia(){
        this.NIFS = new ArrayList<String>();
        this.agregado = 0;
    }
    
    /** 
     * Construtor por parâmetro  
     * @param NIFS 
     * @param agregado 
     */
    public Familia(List<String> NIFS, int agregado){
        this.NIFS = NIFS;
        this.agregado = agregado;
    }
    
    /** 
     * Construtor por cópia 
     * @param f
     */
    public Familia(Familia f){
        this.NIFS = f.getNIFS();
        this.agregado = f.getAgregado();
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
     * Obter o agregado da familia 
     * @return 
     */
    public int getAgregado(){
        return this.agregado;
    }
    
    /** 
     * Define um NIF na lista de NIFs da familia 
     * @param s 
     */
    public void setNIF(String s){
        this.NIFS.add(s);
        this.agregado++;
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
        
        return (this.NIFS.equals(f.getNIFS()) && this.agregado == f.getAgregado());
    }
    
    /** 
     * Devolve os parâmetros da Familia na forma de String 
     * @return 
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n Nº do agregado familiar: ").append(this.agregado);
        for(String s : this.NIFS)
            sb.append("\n").append(s);
        
        return sb.toString();
    }
}
