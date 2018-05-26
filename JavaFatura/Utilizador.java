import java.io.Serializable;
public abstract class Utilizador implements Serializable
{
   private String NIF; 
   private String email; 
   private String nome; 
   private String morada; 
   private String password; 
   
   public Utilizador(){ 
       this.NIF = "n/a"; 
       this.email = "n/a"; 
       this.nome = "n/a"; 
       this.morada = "n/a"; 
       this.password = "n/a";
   }

   public Utilizador(Utilizador u){ 
       this.NIF = u.getNIF(); 
       this.email = u.getEmail(); 
       this.nome = u.getNome(); 
       this.morada = u.getMorada(); 
       this.password = u.getPassword();
   }

   public Utilizador(String NIF, String email, String nome, String morada, String password){ 
       this.NIF = NIF; 
       this.email = email;
       this.nome = nome; 
       this.morada = morada;
       this.password = password;
   }

   public String getNIF(){ 
       return this.NIF;
   }

   public String getEmail(){ 
       return this.email;
   }

   public String getNome(){ 
       return this.nome;
   }

   public String getMorada(){ 
       return this.morada;
   }

   public String getPassword(){ 
       return this.password;
   }

   public void setPassword(String pass){ 
       this.password = pass;
   } 
   
   public void setNIF(String NIF){ 
       this.NIF = NIF;
   }

   public abstract Utilizador clone(); 
   
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
