

public class Admin extends Utilizador 
{
    public Admin(){ 
        super("Admin"," ", " ", " "," ");
    }
    
    public Admin(Admin a){ 
        super(a);         
    }

    public Admin(String NIF, String nome, String email, String morada, String password){ 
        super(NIF,nome,email,morada,password);
    }

    public Admin clone(){ 
        return new Admin(this);
    }

    public boolean equals(Object obj){ 
       if(this == obj) 
        return true; 
       if((obj==null) || (this.getClass() != obj.getClass())) 
        return false; 
       Admin a = (Admin) obj; 
        return (super.equals(a));
    }

    public String toString(){ 
        return super.toString();
    }
}
