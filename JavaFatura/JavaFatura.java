import java.util.Set; 
import java.util.TreeSet; 
import java.util.Collections; 
import java.util.Iterator; 
import java.util.HashSet; 
import java.util.Map; 
import java.util.HashMap; 
import java.util.ArrayList; 
import java.util.TreeMap; 
import java.util.List; 
import java.util.GregorianCalendar;
import java.io.Serializable;
import java.io.IOException;  
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream; 
import java.io.FileInputStream; 
import java.io.ObjectInputStream; 
import java.io.FileWriter; 
import java.io.FileOutputStream;

public class JavaFatura
{
    //Variaveis de instancia 
    private Map<String,Fatura> faturas; 
    private Map<String,Utilizador> utilizadores; 
    private Utilizador utilizador; 
    private int id;

    public JavaFatura(){ 
        this.faturas = new TreeMap<String,Fatura>(); 
        this.utilizadores = new TreeMap<String,Utilizador>(); 
        this.utilizador = null; 
        this.id = 0;
    }

    public JavaFatura(TreeMap<String,Utilizador> u, TreeMap<String,Fatura> i){ 
        this.utilizador = null; 
        
        this.faturas = new TreeMap<String,Fatura>(); 
        for(Fatura fatura : i.values()){ 
            this.faturas.put(fatura.getNIFe(),fatura.clone());
        }
        
        this.utilizadores = new TreeMap<String,Utilizador>(); 
        for(Utilizador utilizador : u.values()){  
            this.utilizadores.put(utilizador.getEmail(),utilizador.clone());
        }    
    }

    public void registarUtilizador (Utilizador utilizador) throws UtilizadorExistenteException{ 
    
        if(this.utilizadores.containsKey(utilizador.getNIF())){ 
            throw new UtilizadorExistenteException ("Já existe este utilizador");
        }
        else { 
            this.utilizadores.put(utilizador.getNIF(),utilizador);
        }
    }

    public void iniciaSessao(String NIF, String password) throws SemAutorizacaoException{ 
        if(this.utilizador == null){ 
        
            if(utilizadores.containsKey(NIF)){ 
                Utilizador user = utilizadores.get(NIF); 
                if(password.equals(user.getPassword())) { 
                    utilizador = user;
                }
                else {                 
                    throw new SemAutorizacaoException("Dados Errados"); 
                }
            }
            else throw new SemAutorizacaoException("Dados Errados");
        }
        else {  
            throw new SemAutorizacaoException("Já tem uma sessão iniciada");
        }
    }

    public void fechaSessao(){ 
        this.utilizador = null;
    }

    public void registaFatura(Fatura fat) throws FaturaExisteException , SemAutorizacaoException{ 
        if(this.utilizador.getClass().getSimpleName().equals("Empresa")){ 
            if(this.faturas.containsValue(fat) == false){ 
                this.faturas.put(fat.getNIFe(),fat); 
                Empresa e1 = (Empresa) this.utilizador; 
                e1.adicionaFatura(fat); 
                this.id++;
            } else throw new FaturaExisteException("Fatura já existe.");           
        }  else throw new SemAutorizacaoException("Apenas empresas estão autorizadas a passar faturas.");    
    }

    public List<Consulta> getConsultas() throws SemAutorizacaoException{ 
        int i = 0; 
        ArrayList<Consulta> lista = new ArrayList<Consulta>();
        if(this.utilizador.getClass().getSimpleName().equals("Individual")) { 
               Individual iv = (Individual) this.utilizador; 
               for(Fatura fat: iv.getFatura().values()){ 
                   Iterator<Consulta> it; 
                   it = fat.getConsultas().iterator();
                   while(it.hasNext()){ 
                       Consulta consult = it.next(); 
                       lista.add(consult.clone());
                   }
                }
               Collections.sort(lista, new ComparatorData()); 
               if(lista.size() <= 10) return lista;
               else {  
                   ArrayList<Consulta> lista_final = new ArrayList<Consulta>(lista.subList(0,11));
                   return lista_final;
                } 
        }    
        else if(this.utilizador.getClass().getSimpleName().equals("Empresa")) { 
               Empresa e = (Empresa) this.utilizador; 
               for(Fatura fat: e.getFaturas().values()){ 
                   Iterator<Consulta> it; 
                   it = fat.getConsultas().iterator();
                   while(it.hasNext()){ 
                       Consulta consult = it.next(); 
                       lista.add(consult.clone());
                   }
                }
               Collections.sort(lista, new ComparatorData()); 
               if(lista.size() <= 10) return lista; 
               else {  
                   ArrayList<Consulta> lista_final = new ArrayList<Consulta>(lista.subList(0,11));
                   return lista_final;
                }
            } 
        else throw new SemAutorizacaoException("Apenas empresas estão autorizadas a aceder.");
        }
    /* muda o estado de uma fatura */
    public void setFatura(String NIF, String ativ) throws FaturaInexistenteException , SemAutorizacaoException , EstadoInvalidoException { 
        
        if(this.utilizador.getClass().getSimpleName().equals("Individual")){ 
            Fatura i = this.faturas.get(NIF); 
            if (i!=null){ 
                i.setDesc(ativ); 
            } else throw new EstadoInvalidoException("Fatura inválida.");
        } else throw new SemAutorizacaoException("Sem autorizacao para efetuar ação");
    }

    public Utilizador getUtilizador(){ 
        return this.utilizador;
    }

    public int getId(){ 
        return this.id;
    }
    /* talvez mudar f.getConsultas() para f.getFaturas() ?? */
    public Set<String> getTopFaturas(){ 
        int n = 10; 
        Set<String> lista = new HashSet<String>(); 
        Empresa e = (Empresa) this.utilizador; 
        for(Fatura f : e.getFaturas().values()){ 
            if(n < f.getConsultas().size()){ 
                lista.add(f.getNIFc());
            }
        }    
        return lista;
    }
    
    public List<Fatura> getFaturas(String classe){ 
        ArrayList<Fatura> f = new ArrayList<Fatura>(); 
        for(Fatura l: this.faturas.values()) { 
            if((l.getClass().getSimpleName().equals(classe))){ 
                Fatura nova = (Fatura) l; 
                GregorianCalendar data = new GregorianCalendar(); 
                if(this.utilizador != null) l.adicionaConsulta(this.utilizador.getNIF(),data);
                else l.adicionaConsulta("N/A",data); 
                f.add(nova.clone());
            }
        }
        return f;
    }

    /** grava o estado da applic num determinado ficheiro */
    public void gravaObj(String fich) throws IOException, FileNotFoundException { 
        FileOutputStream fos = new FileOutputStream(fich);
        ObjectOutputStream oos = new ObjectOutputStream(fos); 
        oos.writeObject(this); 
        
        oos.flush(); 
        oos.close();
    }

    public static JavaFatura leObj(String fich) throws IOException , ClassNotFoundException, FileNotFoundException{ 
        FileInputStream fos = new FileInputStream(fich);
        ObjectInputStream ois = new ObjectInputStream(fos); 
        
        JavaFatura fe = (JavaFatura) ois.readObject(); 
        
        ois.close(); 
        return fe;   
    }

    public void log(String f, boolean ap) throws IOException{ 
        FileWriter fw = new FileWriter(f,ap); 
        fw.write("\n-------------------------- LOG --------------------------\n"); 
        fw.write(this.toString()); 
        fw.write("\n-------------------------- LOG --------------------------\n"); 
        fw.flush(); 
        fw.close();
    }

    public String toString(){ 
        StringBuilder str; 
        str = new StringBuilder(); 
        str.append("Faturas: "); 
        for(Fatura f: this.faturas.values()) 
            str.append(f.getNIFe() + " "); 
        str.append("\n"); 
        str.append("Utilizadores: "); 
        for(Utilizador u: this.utilizadores.values()) 
            str.append(u.getNIF() + " "); 
        str.append("\n"); 
        str.append("Utilizador logado: \n"); 
        str.append(this.utilizador); 
        return str.toString();
    }
}