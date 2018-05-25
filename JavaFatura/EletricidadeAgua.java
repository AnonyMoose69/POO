
/**
 * Write a description of class EletricidadeAgua here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class EletricidadeAgua extends Atividade
{
    public EletricidadeAgua(){
        super("EletricidadeAgua");
    }
    
    public double getDeducao(double valor){
        return 0.06*valor;
    }
}
