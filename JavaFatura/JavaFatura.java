import java.util.Set; 
import java.util.TreeSet; 
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator; 
import java.util.HashSet; 
import java.util.Map; 
import java.util.stream.Collectors;
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
import java.time.LocalDateTime;

public class JavaFatura implements Serializable
{
    //Variaveis de instancia 
    private Map<Integer,Fatura> faturas; 
    private Map<String,Utilizador> utilizadores; 
    private Utilizador utilizador; 
    private int id;  
    private Admin adminstrador;

    public JavaFatura(){ 
        this.faturas = new TreeMap<Integer,Fatura>(); 
        this.utilizadores = new TreeMap<String,Utilizador>(); 
        this.utilizador = null; 
        this.id = 0; 
        this.adminstrador = new Admin("Grupo34","admin@javafatura.com","Adminstrador","Uminho","poo2018");
    }

    public JavaFatura(TreeMap<String,Utilizador> u, TreeMap<Integer,Fatura> i){ 
        this.utilizador = null;          
        
        this.faturas = new TreeMap<Integer,Fatura>(); 
        for(Fatura fatura : i.values()){ 
            this.faturas.put(fatura.getId(),fatura.clone());   //mudei NIFe para NIFc
        }
        
        this.utilizadores = new TreeMap<String,Utilizador>(); 
        for(Utilizador utilizador : u.values()){  
            this.utilizadores.put(utilizador.getNIF(),utilizador.clone());
        }    
        this.id = this.faturas.size();
        this.adminstrador = new Admin("Grupo34","admin@javafatura.com","Adminstrador","Uminho","poo2018");
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

    
    public void iniciaSessaoAdmin(String NIF, String password) throws SemAutorizacaoException{ 
        if(adminstrador.getNIF().equals(NIF)){ 
                
                if(password.equals(adminstrador.getPassword())) { 
                    utilizador = this.adminstrador;
                }
                else {                 
                    throw new SemAutorizacaoException("Dados Errados"); 
                }
            }
            else throw new SemAutorizacaoException("Dados Errados");
    } 
    
    
    public void fechaSessao(){ 
        this.utilizador = null;
    }
    
        
    //regista fatura a um dado utilizador mudar para Individual 
    //lista das faturas do individual esta vazia porque vai tudo para empresa (MALLL!!)
    //mudar NIFe para NIFc talvez?
   public void registaFatura(Fatura fat) throws FaturaExisteException , SemAutorizacaoException{ 
        if(this.utilizador.getClass().getSimpleName().equals("Empresa")){ 
            if(this.faturas.containsValue(fat) == false){ 
                fat.setId(this.id);
                this.faturas.put(this.id,fat);
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
                System.out.print(iv.getFatura().toString());                
                
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
    public void setFatura(int id, Atividade natDes) throws FaturaInexistenteException , SemAutorizacaoException , EstadoInvalidoException { 
        
        if(this.utilizador.getClass().getSimpleName().equals("Individual")){ 
            Fatura i = this.faturas.get(id); 
            if (i!=null){ 
                if(!natDes.getAtiv().equals("")){
                    i.setNatDes(natDes);  
                    i.setValida(true);
                }
            } else throw new EstadoInvalidoException("Fatura inválida.");
        } else throw new SemAutorizacaoException("Sem autorizacao para efetuar ação");
    }

    public Fatura getFatura(int id) throws FaturaInexistenteException, SemAutorizacaoException{
       if(id >= 0 && id < this.id){
           Fatura f = this.faturas.get(id);
             if(this.utilizador.getClass().getSimpleName().equals("Individual") && this.utilizador.getNIF().equals(f.getNIFc())) {return f;}
             else throw new SemAutorizacaoException("Não pertence às suas faturas");
        }
       else throw new FaturaInexistenteException("Não existe essa fatura");
        
    }
    
    public Utilizador getUtilizador(){ 
        return this.utilizador;
    }

    public int getId(){ 
        return this.id;
    }
    
    public Utilizador getUtilizador(String NIF){
        return this.utilizadores.get(NIF);
    }
    /* talvez mudar f.getConsultas() para f.getFaturas() ?? */
    public Set<String> getTopFaturas(int n){ 
        Set<String> lista = new HashSet<String>(); 
        Empresa e = (Empresa) this.utilizador; 
        for(Fatura f : e.getFaturas().values()){ 
            if(n < f.getConsultas().size()){ 
                lista.add(f.getNIFc());
            }
        }    
        return lista;
    }
    //Lista faturas empresa - bug faturas desaparecem as vezes
    /*
    public Map <Fatura,Empresa> getMapeamentoFaturas() { 
        Map<Fatura,Empresa> faturas = new HashMap<Fatura,Empresa>(); 
        
        for(Fatura f : this.faturas.values()){ 
            for(Utilizador util : this.utilizadores.values()){ 
                if(util.getClass().getSimpleName().equals("Empresa")){    
                    if (this.utilizador.getNIF().equals(util.getNIF())){
                            Empresa e = (Empresa) util; 
                            if(e.getFaturas().containsValue(f)){ 
                                faturas.put(f,e); 
                                break;
                        }
                    }
                }            
            }
        }
        return faturas;
    }
    */
    
    public List<Fatura> getFaturasIndividual(String NIF){ 
        ArrayList<Fatura> f = new ArrayList<Fatura>(); 
        for(Fatura l: this.faturas.values()) { 
            //o get simple name so da o nome da classe ou seja isto nao interessa para nada porque n ha classe NIF  && l.getClass().getSimpleName().equals("Individual")
            if(l.getNIFc().equals(NIF)){ 
                Fatura nova = (Fatura) l; 
                /*                                              Esta merda ta a foder para caralho lol
                GregorianCalendar data = new GregorianCalendar();                 
                if(this.utilizador != null) l.adicionaConsulta( data);
                else l.adicionaConsulta("N/A", "N/A", data); 
                */
                f.add(nova.clone());
            }
        }
        return f;
    }
    
    public List<Fatura> getFaturasEmpresa(String NIF){ 
        ArrayList<Fatura> f = new ArrayList<Fatura>(); 
        for(Fatura l: this.faturas.values()) { 
            //o get simple name so da o nome da classe ou seja isto nao interessa para nada porque n ha classe NIF  && l.getClass().getSimpleName().equals("Individual")
            if(l.getNIFe().equals(NIF)){ 
                Fatura nova = (Fatura) l; 
                /*                                              Esta merda ta a foder para caralho lol
                GregorianCalendar data = new GregorianCalendar();                 
                if(this.utilizador != null) l.adicionaConsulta(l.getNIFe(),l.getNIFc(), data);
                else l.adicionaConsulta("N/A", "N/A", data); 
                */
                f.add(nova.clone());
            }
        }
        return f;
    }
    
    public List<Fatura> getFaturasPorValor(String NIF){
        List<Fatura> res = this.getFaturasEmpresa(NIF);
    
        res.sort(Comparator.comparingDouble(Fatura::getValDes));
        
        return res;
         
    }
    
    public List<Fatura> getFaturasPorData(String NIF){ 
        List<Fatura> res = this.getFaturasEmpresa(NIF); 
        return res;
    }
    
    public List<Fatura> getFaturasEmpDoContr(List<Fatura> l, String NIFc){
        return l.stream().filter(f -> NIFc.equals(f.getNIFc())).collect(Collectors.toList());
        
    }
    
    public boolean existeCont(List<Fatura> l, String NIFc){
        return l.stream().anyMatch(f -> NIFc.equals(f.getNIFc()));
    }
    
    public List<Fatura> getFaturasPorContribuinte(String NIFe, String NIFc, LocalDateTime begin, LocalDateTime end) throws UtilizadorExistenteException, SemAutorizacaoException{
        List<Fatura> fatemp = this.getFaturasPorData(NIFe);
       
        if(fatemp.size() !=0){
            if(existeCont(fatemp, NIFc)){
                List<Fatura> r = this.getFaturasEmpDoContr(fatemp, NIFc);
        
                return r.stream().filter(f -> f.getDataCr().isAfter(begin) && f.getDataCr().isBefore(end)).collect(Collectors.toList());
            }
            else  throw new UtilizadorExistenteException("Este utilizador individual não existe ou não tem faturas desta empresa!");
        }
        else throw new SemAutorizacaoException("Esta empresa não tem faturas associadas a si mesma!");
    }
    
    public List<Fatura> getFaturasPorContribuinteOrdV(String NIFe, String NIFc) throws UtilizadorExistenteException, SemAutorizacaoException{
        List<Fatura> fatemp = this.getFaturasPorValor(NIFe);
       
        if(fatemp.size() != 0){
            if(existeCont(fatemp, NIFc)){
                List<Fatura> r = this.getFaturasEmpDoContr(fatemp, NIFc);
        
                return r.stream().sorted(Comparator.comparing(Fatura::getValDes).reversed()).collect(Collectors.toList());
            }
            else  throw new UtilizadorExistenteException("Este utilizador individual não existe ou não tem faturas desta empresa!");
        }
        else throw new SemAutorizacaoException("Esta empresa não tem faturas associadas a si mesma!");
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

    /** poderá estar aqui o erro **/
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