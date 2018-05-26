import java.io.Serializable;

/**
 * Abstract class Utilizador - Classe relativa á implementação abstrata de um utilizador com a informação comum a todos eles.
 *
 * @author Grupo 34
 */
public abstract class Utilizador implements Serializable
{
  //Variaveis de instância
   private String NIF; 
   private String email; 
   private String nome; 
   private String morada; 
   private String password; 
   
   /** 
    * Cria uma instância de um utilizador 
    */
   public Utilizador(){ 
       this.NIF = "n/a"; 
       this.email = "n/a"; 
       this.nome = "n/a"; 
       this.morada = "n/a"; 
       this.password = "n/a";
   }
      
   /** 
    * Construtor por cópia  
    * @param u 
    */
   public Utilizador(Utilizador u){ 
       this.NIF = u.getNIF(); 
       this.email = u.getEmail(); 
       this.nome = u.getNome(); 
       this.morada = u.getMorada(); 
       this.password = u.getPassword();
   }
   
   /** 
    * Construtor por parâmetro 
    * @param NIF 
    * @param email 
    * @param nome 
    * @param morada 
    * @param password 
    */
   public Utilizador(String NIF, String email, String nome, String morada, String password){ 
       this.NIF = NIF; 
       this.email = email;
       this.nome = nome; 
       this.morada = morada;
       this.password = password;
   }
   
   /** 
    * Obter o NIF de um Utilizador  
    * @return
    */
   public String getNIF(){ 
       return this.NIF;
   }
   
   /** 
    * Obter o email de um Utilizador 
    * @return 
    */
   public String getEmail(){ 
       return this.email;
   }
   
   /** 
    * Obter o nome de um Utilizador 
    * @return 
    */
   public String getNome(){ 
       return this.nome;
   }
   
   /** 
    * Obter a morada de um Utilizador 
    * @return
    */
   public String getMorada(){ 
       return this.morada;
   }
   
   /** 
    * Obter a password de um Utilizador 
    * @return 
    */
   public String getPassword(){ 
       return this.password;
   }
   
   /** 
    * Define a password de um Utilizador 
    * @param pass 
    */
   public void setPassword(String pass){ 
       this.password = pass;
   }  
   
   /** 
    * Define o email de um Utilizador 
    * @param email
    */
   public void setEmail(String email){ 
       this.email = email;
   } 
   
   /** 
    * Define o nome de um Utilizador 
    * @param nome
    */
   public void setNome(String nome){ 
       this.nome = nome; 
   } 
   
   /** 
    * Define a morada de um Utilizador 
    * @param morada
    */
   public void setMorada(String morada){ 
       this.morada = morada;
   }
  
   /** 
    * Define o NIF de um Utilizador 
    * @param NIF 
    */
   public void setNIF(String NIF){ 
       this.NIF = NIF;
   }
   
   /** 
    * Devolve uma cópia desta instância 
    * @return 
    */
   public abstract Utilizador clone(); 
   
   /** 
    * Compara a igualdade com outro objeto 
    * @param obj 
    * @return 
    */
   public boolean equals(Object obj){ 
       if(obj == this) {  
           return true;
        }
       if(obj == null || obj.getClass() != this.getClass()) { 
           return false;
        }
       Utilizador u = (Utilizador) obj; 
       return u.getNIF().equals(this.NIF) && u.getNome().equals(this.nome) && u.getPassword().equals(this.password)
              && u.getMorada().equals(this.morada) && u.getEmail().equals(this.email);
   }
   
   /** 
    * Devolve os parâmetros do Utilizador na forma de String 
    * @return 
    */
   public String toString(){ 
       StringBuilder str; 
       str = new StringBuilder(); 
       str.append("   NIF: "); 
       str.append(this.NIF+"\n"); 
       str.append("   Email: "); 
       str.append(this.email+"\n"); 
       str.append("   Nome: "); 
       str.append(this.nome+"\n"); 
       str.append("   Morada: "); 
       str.append(this.morada+"\n"); 
       str.append("   Password: "); 
       str.append(this.password+"\n");
       return str.toString();
   
    }
}
