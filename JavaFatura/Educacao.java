
/**
 * Write a description of class Educacao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Educacao extends Atividade
{
    public Educacao(){
        super("Educacao");
    }
    
    public double getDeducao(double valor){
        return 0.1*valor;
    }
}
