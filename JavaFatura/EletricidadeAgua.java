
/** 
 * Classe destinada a designar uma atividade eletrecidade e água na JavaFatura  
 * 
 * @author Grupo 34
 */
public class EletricidadeAgua extends Atividade
{
    /** 
     * Construtor base 
     */
    public EletricidadeAgua(){
        super("EletricidadeAgua");
    }
    
    /** 
     * Obtem a dedução associada a esta área de atividade 
     * @param valor 
     * @return 
     */
    public double getDeducao(double valor){
        return 0.06*valor;
    }
}
