
/** 
 * Classe destinada a designar uma atividade reparacao de veiculos na JavaFatura  
 * 
 * @author Grupo 34
 */
public class ReparacaodeVeiculos extends Atividade
{
    /** 
     * Construtor base 
     */
    public ReparacaodeVeiculos(){
        super("ReparacaodeVeiculos");
    }
    
    /** 
     * Obtem a dedução associada a esta área de atividade 
     * @param valor 
     * @return 
     */
    public double getDeducao(double valor){
        return 0.02*valor;
    }
}
