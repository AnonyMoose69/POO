
/** 
 * Classe destinada a designar uma atividade educação na JavaFatura  
 * 
 * @author Grupo 34
 */
public class Educacao extends Atividade
{
    /** 
     * Construtor base 
     */
    public Educacao(){
        super("Educacao");
    }
    
    /** 
     * Obtem a dedução associada a esta área de atividade 
     * @param valor 
     * @return 
     */
    public double getDeducao(double valor){
        return 0.1*valor;
    }
}
