
/** 
 * Classe destinada a designar uma atividade sáude na JavaFatura  
 * 
 * @author Grupo 34
 */
public class Saude extends Atividade
{
    /** 
     * Construtor base 
     */
    public Saude(){
        super("Saude");
    }
    
    /** 
     * Obtem a dedução associada a esta área de atividade 
     * @param valor 
     * @return 
     */
    public double getDeducao(double valor){
        return 0.08*valor;
    }
}