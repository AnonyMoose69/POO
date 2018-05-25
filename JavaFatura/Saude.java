
/**
 * Write a description of class Saude here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Saude extends Atividade
{
    public Saude(){
        super("Saude");
    }
    
    public double getDeducao(double valor){
        return 0.08*valor;
    }
}