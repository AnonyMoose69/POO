
/**
 * Write a description of class Restauracao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Restauracao extends Atividade
{
    public Restauracao(){
        super("Restauracao");
    }
    
    public double getDeducao(double valor){
        return 0.03*valor;
    }
}
