import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors; 
import java.io.Serializable;

public class Familia implements Serializable
{
    private List<String> NIFS;
    private int dependentes;
    private int id;
    
    public Familia(){
        this.NIFS = new ArrayList<String>();
        this.dependentes = 0;
        this.id = -1;
    }
    
    public Familia(List<String> NIFS, int dependentes, int id){
        this.NIFS = NIFS;
        this.dependentes = dependentes;
        this.id = id;
    }
    
    public Familia(Familia f){
        this.NIFS = f.getNIFS();
        this.dependentes = f.getDependentes();
        this.id = f.getId();
    }
   
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
    
    
    public int getDependentes(){
        return this.dependentes;
    }
    
    public int getId(){
        return this.id;
    }
    public void setNIF(String s){
        this.NIFS.add(s);
    }  
    
    public void setDependentes(int dep){ 
        this.dependentes = dep;
    }
    
    public void setNIFS(List<String> l){
        Iterator<String> it = l.iterator();
        String s;
        this.NIFS = new ArrayList<String>();
        
        while(it.hasNext()){
            s = it.next();
            this.NIFS.add(s);
        }
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void adicionaMembro(String NIF, boolean depend){
        this.NIFS.add(NIF);
        if (depend) this.dependentes++;
    }
    
    public double getCoefFamilia(){
       double size = this.NIFS.size();
        
       if(size < 7) return this.dependentes/(size * 3);
       else return this.dependentes/(size * 2);
    }
    
    
    public Familia clone(){
        return new Familia(this);
    }
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Familia f = (Familia) o ;
        
        return (this.NIFS.equals(f.getNIFS()) && this.dependentes == f.getDependentes() && this.id == f.getId());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n Nº de dependentes: ").append(this.dependentes);
        sb.append("\n Lista de NIFS da sua família").append(this.NIFS.toString());
        sb.append("\n Id da família: " + this.id);
        
        return sb.toString();
    }
}
