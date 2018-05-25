
/**
 * Write a description of class Transportes here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Transportes extends Atividade
{
    public Transportes(){
        super("Transportes");
    }
    
    public double getDeducao(double valor){
        return 0.04*valor;
    }
}
