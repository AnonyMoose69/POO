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
    private Map<Integer, Familia> familias;
    private Utilizador utilizador; 
    private int id;
    private int idfam;
    private Admin adminstrador; 

    public JavaFatura(){ 
        this.faturas = new TreeMap<Integer,Fatura>(); 
        this.utilizadores = new TreeMap<String,Utilizador>(); 
        this.familias = new TreeMap<Integer,Familia>();
        this.utilizador = null; 
        this.idfam = 0;
        this.id = 0; 
        this.adminstrador = new Admin("Grupo34","admin@javafatura.com","Adminstrador","Uminho","poo2018");
    }

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
    
    public void registarEmpresa(Utilizador utilizador) throws UtilizadorExistenteException{
        if(this.utilizadores.containsKey(utilizador.getNIF())){ 
            throw new UtilizadorExistenteException ("Já existe este utilizador");
        }
        else { 
            this.utilizadores.put(utilizador.getNIF(),utilizador);
        }
    }
    
    
    public void registarUtilizador (Utilizador utilizador, int idfam) throws UtilizadorExistenteException, FamiliaInexistenteException{ 
        if(utilizador.getClass().getSimpleName().equals("Individual")) registarIndividual(utilizador, idfam);
        else registarEmpresa(utilizador);
        
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
    
    public Utilizador getContribuinte(String NIFc) throws UtilizadorExistenteException{
        if(this.utilizadores.containsKey(NIFc)){
            return this.utilizadores.get(NIFc);
        }
        else throw new UtilizadorExistenteException("Não existe o contribuinte");
    }
        
    //regista fatura a um dado utilizador mudar para Individual 
    //lista das faturas do individual esta vazia porque vai tudo para empresa (MALLL!!)
    //mudar NIFe para NIFc talvez?
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
    
    public Familia getFamilia(int id){
        return this.familias.get(id);
    }

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
    
    public boolean existeUtilizadorNIF(String NIF){ 
        return this.utilizadores.values().stream().anyMatch(a -> NIF.equals(a.getNIF()));
    }
    public double getLucroEmp(){
        Empresa emp = (Empresa) this.getUtilizador();
        
        return emp.getLucro();
    }
    
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
    
    public List<Fatura> getFaturasFamilia(){
        int idf = ((Individual)this.utilizador).getIDfam();
        List<String> f = this.familias.get(idf).getNIFS();
        List<Fatura> res = new ArrayList<Fatura>();
        for(String s : f){
            res.addAll(this.getFaturasIndividual(s));
        }
        
        return res;
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