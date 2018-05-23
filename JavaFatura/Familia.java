import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

public class Familia
{
    private List<String> NIFS;
    private int agregado;
    
    public Familia(){
        this.NIFS = new ArrayList<String>();
        this.agregado = 0;
    }
    
    public Familia(List<String> NIFS, int agregado){
        this.NIFS = NIFS;
        this.agregado = agregado;
    }
    
    public Familia(Familia f){
        this.NIFS = f.getNIFS();
        this.agregado = f.getAgregado();
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
    
    
    public int getAgregado(){
        return this.agregado;
    }
    
    public void setNIF(String s){
        this.NIFS.add(s);
        this.agregado++;
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
    
    public Familia clone(){
        return new Familia(this);
    }
    
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        
        Familia f = (Familia) o ;
        
        return (this.NIFS.equals(f.getNIFS()) && this.agregado == f.getAgregado());
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n Nº do agregado familiar: ").append(this.agregado);
        for(String s : this.NIFS)
            sb.append("\n").append(s);
        
        return sb.toString();
    }
}
