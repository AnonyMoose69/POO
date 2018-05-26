
/** 
 * Classe destinada a designar uma atividade transportes na JavaFatura  
 * 
 * @author Grupo 34
 */
public class Transportes extends Atividade
{
    /** 
     * Construtor base 
     */
    public Transportes(){
        super("Transportes");
    }
    
    /** 
     * Obtem a dedução associada a esta área de atividade 
     * @param valor 
     * @return 
     */
    public double getDeducao(double valor){
        return 0.04*valor;
    }
}
