
/** 
 * Classe destinada a designar uma atividade restauração na JavaFatura  
 * 
 * @author Grupo 34
 */
public class Restauracao extends Atividade
{
    /** 
     * Construtor base 
     */
    public Restauracao(){
        super("Restauracao");
    }
    
    /** 
     * Obtem a dedução associada a esta área de atividade 
     * @param valor 
     * @return 
     */
    public double getDeducao(double valor){
        return 0.03*valor;
    }
}
