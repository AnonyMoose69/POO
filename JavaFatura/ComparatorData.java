import java.util.Comparator; 
import java.io.Serializable; 

public class ComparatorData implements Comparator<Consulta>, Serializable 
{
   public int compare(Consulta c1, Consulta c2){ 
       return c1.getData().compareTo(c2.getData());
    }
}
