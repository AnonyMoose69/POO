
/**
 * Write a description of class ReparacaodeVeiculos here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ReparacaodeVeiculos extends Atividade
{
    public ReparacaodeVeiculos(){
        super("ReparacaodeVeiculos");
    }
    
    public double getDeducao(double valor){
        return 0.02*valor;
    }
}
