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

/** 
 * Classe relativa a JavaFatura a gerir 
 * 
 * @author Grupo 34 
 */
public class JavaFatura implements Serializable
{
    //Variaveis de instancia 
    private Map<Integer,Fatura> faturas;
    private Map<String,Utilizador> utilizadores;
    private Map<Integer, Familia> familias;
    private Utilizador utilizador; 
    private int id;
    private int idfam;
    private Admin adminstrador; 

    /**
     * Construtor vazio de uma JavaFatura
     */
    public JavaFatura(){ 
        this.faturas = new TreeMap<Integer,Fatura>(); 
        this.utilizadores = new TreeMap<String,Utilizador>(); 
        this.familias = new TreeMap<Integer,Familia>();
        this.utilizador = null; 
        this.idfam = 0;
        this.id = 0; 
        this.adminstrador = new Admin("Grupo34","admin@javafatura.com","Adminstrador","Uminho","poo2018");
    }
    
    /**
     * Construtor por parâmetros de uma JavaFatura 
     * @param u
     * @param i  
     * @param f 
     */
    public JavaFatura(TreeMap<String,Utilizador> u, TreeMap<Integer,Fatura> i, TreeMap<Integer,Familia> f){ 
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
        this.familias = new TreeMap<Integer,Familia>();
        for(Familia familia : f.values()){
            this.familias.put(familia.getId(),familia.clone());
        }
        this.adminstrador = new Admin("Grupo34","admin@javafatura.com","Adminstrador","Uminho","poo2018");
    }
    
    /**
     * Registar um utilizador na JavaFatura
     * @param utilizador 
     * @param idfam 
     */
    public void registarIndividual(Utilizador utilizador, int idfam) throws UtilizadorExistenteException, FamiliaInexistenteException {
            Familia f = new Familia();
            
            if (idfam == -1) {
                List<String> fam = new ArrayList<String>();
                int dep;
                fam.add(utilizador.getNIF());
            
                if(((Individual) utilizador).getDepend()) dep = 1; else dep = 0;
            
                f = new Familia(fam, dep, this.idfam++);
                ((Individual) utilizador).setIDfam(this.idfam-1);
            }
            else {
                if(this.familias.containsKey(idfam)) {this.familias.get(idfam).adicionaMembro(utilizador.getNIF(), ((Individual) utilizador).getDepend());}
                else {throw new FamiliaInexistenteException("Não existe família com esse id");}
                
                }
            
            if(this.utilizadores.containsKey(utilizador.getNIF())){ 
                throw new UtilizadorExistenteException ("Já existe este utilizador");
            }
            else { 
                this.utilizadores.put(utilizador.getNIF(),utilizador);
                if (idfam == -1) this.familias.put(this.idfam-1, f);
            }
    }
    
    /** 
     * Registar uma Empresa na JavaFatura 
     * @param utilizador 
     */
    public void registarEmpresa(Utilizador utilizador) throws UtilizadorExistenteException{
        if(this.utilizadores.containsKey(utilizador.getNIF())){ 
            throw new UtilizadorExistenteException ("Já existe este utilizador");
        }
        else { 
            this.utilizadores.put(utilizador.getNIF(),utilizador);
        }
    }
    
    /** 
     * Registar um Individual na JavaFatura 
     * @param utilizador 
     * @param idfam 
     */
    public void registarUtilizador (Utilizador utilizador, int idfam) throws UtilizadorExistenteException, FamiliaInexistenteException{ 
        if(utilizador.getClass().getSimpleName().equals("Individual")) registarIndividual(utilizador, idfam);
        else registarEmpresa(utilizador);
        
    }
    
    /**
     * Iniciar sessão na aplicação.
     * @param email
     * @param password
     */
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

    /**
     * Iniciar sessão na aplicação como adminstrador.
     * @param email
     * @param password
     */     
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
    
    /**
     * Fechar sessão na aplicação.
     */
    public void fechaSessao(){ 
        this.utilizador = null;
    }
    
    /** 
     * Obter um contribuinte dado o NIF de um contribuinte 
     * @param NIFc  
     * @return 
     */
    public Utilizador getContribuinte(String NIFc) throws UtilizadorExistenteException{
        if(this.utilizadores.containsKey(NIFc)){
            return this.utilizadores.get(NIFc);
        }
        else throw new UtilizadorExistenteException("Não existe o contribuinte");
    }
        
    /**
     * Registar uma fatura na aplicação.
     * @param fat
     */
    public void registaFatura(Fatura fat) throws FaturaExisteException , SemAutorizacaoException, UtilizadorExistenteException{ 
        if(this.utilizador.getClass().getSimpleName().equals("Empresa")){ 
            if(this.faturas.containsValue(fat) == false && existeUtilizadorNIF(fat.getNIFc())){ 
                fat.setId(this.id);
                this.faturas.put(this.id,fat);
                Empresa e1 = (Empresa) this.utilizador; 
                e1.adicionaFatura(fat); 
                this.id++;
                Individual ind = (Individual) this.getContribuinte(fat.getNIFc());
                ind.adicionaFatura(fat);
            } else throw new FaturaExisteException("Fatura já existe ou contribuinte inválido");           
        }  else throw new SemAutorizacaoException("Apenas empresas estão autorizadas a passar faturas.");    
    }
                       
    /**
    * Mudar o estado de uma fatura 
    * @param id
    * @param natDes
    */
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
    
    /** 
     * Devolver uma fatura dado a identificação da mesma 
     * @param id 
     * @return 
     */
    public Fatura getFatura(int id) throws FaturaInexistenteException, SemAutorizacaoException{
       if(id >= 0 && id < this.id){
           Fatura f = this.faturas.get(id);
             if(this.utilizador.getClass().getSimpleName().equals("Individual") && this.utilizador.getNIF().equals(f.getNIFc())) {return f;}
             else throw new SemAutorizacaoException("Não pertence às suas faturas");
        }
       else throw new FaturaInexistenteException("Não existe essa fatura");
        
    }
    
    /**
     * Obter o utilizador com sessão iniciada na aplicação.
     * @return
     */
    public Utilizador getUtilizador(){ 
        return this.utilizador;
    } 
    
    /**
     * Obter o id da fatura na JavaFatura 
     * @return
     */
    public int getId(){ 
        return this.id;
    }
    
    /** 
     * Devolve um Utilizador dado o NIF deste mesmo 
     * @param NIF  
     * @return
     */
    public Utilizador getUtilizador(String NIF){
        return this.utilizadores.get(NIF);
    }
    
    /** 
     * Devolve uma Familia dado a identificação desta msma 
     * @param id 
     * @return 
     */
    public Familia getFamilia(int id){
        return this.familias.get(id);
    }
    
    /** 
     * Devolve a lista de faturas de um individual 
     * @param NIF 
     * @return 
     */
    public List<Fatura> getFaturasIndividual(String NIF){ 
        ArrayList<Fatura> f = new ArrayList<Fatura>(); 
        for(Fatura l: this.faturas.values()) {        
            if(l.getNIFc().equals(NIF)){ 
                Fatura nova = (Fatura) l;              
                f.add(nova.clone());
            }
        }
        return f;
    }
    
    /** 
     * Devolve a lista de faturas de uma empresa 
     * @param NIF  
     * @return 
     */    
    public List<Fatura> getFaturasEmpresa(String NIF){ 
        ArrayList<Fatura> f = new ArrayList<Fatura>(); 
        for(Fatura l: this.faturas.values()) { 
            if(l.getNIFe().equals(NIF)){ 
                Fatura nova = (Fatura) l; 
                f.add(nova.clone());
            }
        }
        return f;
    }
    
    /** 
     * Devolve a lista de faturas ordenada por valor 
     * @param NIF 
     * @return 
     */
    public List<Fatura> getFaturasPorValor(String NIF){
        List<Fatura> res = this.getFaturasEmpresa(NIF);
    
        res.sort(Comparator.comparingDouble(Fatura::getValDes));
        
        return res;
         
    }
    
    /** 
     * Devolve a lista de faturas ordenada por data 
     * @param NIF 
     * @return 
     */
    public List<Fatura> getFaturasPorData(String NIF){ 
        List<Fatura> res = this.getFaturasEmpresa(NIF); 
        return res;
    }
    
    /** 
     * Devolve a sub lista de faturas de uma empresa com um determinado contribuinte 
     * @param l 
     * @param NIFc 
     * @return 
     */
    public List<Fatura> getFaturasEmpDoContr(List<Fatura> l, String NIFc){
        return l.stream().filter(f -> NIFc.equals(f.getNIFc())).collect(Collectors.toList());
        
    }
    
    /** 
     * Testa se existe um dado contribuinte na lista de faturas 
     * @param l 
     * @param NIFc 
     * @return 
     */
    public boolean existeCont(List<Fatura> l, String NIFc){
        return l.stream().anyMatch(f -> NIFc.equals(f.getNIFc()));
    }
    
    /** 
     * Testa se existe um utilizador com determinado NIF 
     * @param NIF 
     * @return 
     */
    public boolean existeUtilizadorNIF(String NIF){ 
        return this.utilizadores.values().stream().anyMatch(a -> NIF.equals(a.getNIF()));
    }
    
    /** 
     * Obter o lucro total de uma empresa 
     * @return 
     */
    public double getLucroEmp(){
        Empresa emp = (Empresa) this.getUtilizador();
        
        return emp.getLucro();
    }
    
    /** 
     * Obter a lista dos top 10 utilizadores com maior gasto no sistema 
     * @return 
     */
    public List<Individual> getTop10Util(){
        List<Individual> total = new ArrayList<Individual>();
        for(Utilizador u : this.utilizadores.values()){
            if(this.utilizador.getClass().getSimpleName().equals("Individual")) total.add((Individual) u);
        }
        
        List<Individual> res = total.stream().sorted(Comparator.comparing(Individual::getValDesTotal).reversed()).collect(Collectors.toList());
        
        if(res.size()<=10) return res;
        else{
            List<Individual> resu = new ArrayList<Individual>();
            for(int i = 0; i < 10; i++){
                resu.add(res.get(i));
            }
            
            return resu;
        }
    }
    
    /** 
     * Calcular a dedução total do agregado (individual) 
     * @return 
     */
    public double getDeducaoAgregado(){
        int idf = ((Individual)this.utilizador).getIDfam();
        double coefFam = this.familias.get(idf).getCoefFamilia();
        double res = 0.0;
        List<String> f = this.familias.get(idf).getNIFS();
        
        for(String s : f){
            Individual i = (Individual) this.utilizadores.get(s);
            res += i.getDeducaoTotal(coefFam);
        }
        
        return res;
    }
    
    /** 
     * Obter a lista de faturas de uma familia 
     * @return 
     */
    public List<Fatura> getFaturasFamilia(){
        int idf = ((Individual)this.utilizador).getIDfam();
        List<String> f = this.familias.get(idf).getNIFS();
        List<Fatura> res = new ArrayList<Fatura>();
        for(String s : f){
            res.addAll(this.getFaturasIndividual(s));
        }
        
        return res;
    }
    
    /** 
     * Obter a lista de faturas num dado intervalo de tempo 
     * @param NIFe 
     * @param NIFc 
     * @param begin
     * @param end  
     * @return
     */
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
    
    /** 
     * Obter a lista de faturas de um contribuinte ordenadas por valor 7
     * @param NIFe 
     * @param NIFc 
     * @return
     */
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
   
    /**
     * Gravar o estado da aplicação num determinado ficheiro.
     * @param fich
     */
    public void gravaObj(String fich) throws IOException, FileNotFoundException { 
        FileOutputStream fos = new FileOutputStream(fich);
        ObjectOutputStream oos = new ObjectOutputStream(fos); 
        oos.writeObject(this); 
        
        oos.flush(); 
        oos.close();
    }

    /**
     * Iniciar a aplicação com o estado guardado num determinado ficheiro.
     * @param fich
     * @return
     */
    public static JavaFatura leObj(String fich) throws IOException , ClassNotFoundException, FileNotFoundException{ 
        FileInputStream fos = new FileInputStream(fich);
        ObjectInputStream ois = new ObjectInputStream(fos); 
        
        JavaFatura fe = (JavaFatura) ois.readObject(); 
        
        ois.close(); 
        return fe;   
    }

    /** 
     * Devolve os parâmetros do JavaFatura numa String 
     * @return 
     */
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