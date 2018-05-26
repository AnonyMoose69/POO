
/** 
 * Classe destinada a designar um admistrador do sistema 
 * 
 * @author Grupo 34
 */
public class Admin extends Utilizador 
{
    /** 
     * Cria uma instância de um admistrador
     */
    public Admin(){ 
        super("Admin"," ", " ", " "," ");
    }
    
    /** 
     * Construtor por cópia 
     * @param a 
     */
    public Admin(Admin a){ 
        super(a);         
    }

    /**
     * Construtor por parâmetro 
     * @param NIF
     * @param nome 
     * @param email 
     * @param morada 
     * @param password
     */
    public Admin(String NIF, String nome, String email, String morada, String password){ 
        super(NIF,nome,email,morada,password);
    }
    
    /** 
     * Devolve uma cópia do admistrador 
     * @return 
     */
    public Admin clone(){ 
        return new Admin(this);
    }
    
    /** 
     * Compara a igualdade com outro objeto 
     * @param obj 
     * @return 
     */
    public boolean equals(Object obj){ 
       if(this == obj) 
        return true; 
       if((obj==null) || (this.getClass() != obj.getClass())) 
        return false; 
       Admin a = (Admin) obj; 
        return (super.equals(a));
    }
    
    /** 
     * Devolve os parâmetros do admistrador na forma de String 
     * @return 
     */
    public String toString(){ 
        return super.toString();
    }
}
