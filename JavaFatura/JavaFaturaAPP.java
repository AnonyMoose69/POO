import java.io.IOException; 
import java.util.Scanner; 
import java.util.ArrayList; 
import java.util.Map; 
import java.util.List; 
import java.util.InputMismatchException; 
import java.util.Set; 
import java.util.HashMap; 
import java.util.TreeMap; 
import java.util.HashSet;

public class JavaFaturaAPP
{
    private static JavaFatura jafat; 
    private static Menu menu_principal,menu_registo,menu_individual, 
                        menu_empresa, menu_individual_registado, 
                        menu_empresa_registado, menu_cria_fatura, 
                        menu_acede_fatura, 
                        menu_logado;
    
    private JavaFaturaAPP() {} 
    
    public static void main(String[] args) {  
        String file_name = "fat_estado.txt"; 
        carregarMenus(); 
        initApp(file_name); 
        apresentarMenu(); 
        try { 
            jafat.gravaObj(file_name); 
        }
        catch (IOException e) {  
            System.out.println("Não consegui gravar os dados!");
        }
        System.out.println("Volte Sempre!");
    }

    private static void apresentarMenu(){  
        int running = 1;         
        do { 
            if(jafat.getUtilizador() != null) {  
                menu_logado.executa(); 
                switch(menu_logado.getOpcao()) {  
                    case 1: menu(); 
                            break; 
                    case 2: fecharSessao(); 
                            break; 
                    case 0: running = 0;
                }
            }
            else {  
                  menu_principal.executa(); 
                  switch (menu_principal.getOpcao()) {
                  case 1: registo(); 
                          break; 
                  case 2: iniciarSessao(); 
                          break;  
                  case 0: running = 0;
            }
        } 
    }    while(running!=0);
    } 
    
    private static void menu() {  
    
    if(jafat.getUtilizador() == null) 
       running_menu_individual(); 
    else { 
        Utilizador util = jafat.getUtilizador(); 
        if(util.getClass().getSimpleName().equals("Empresa")) 
            running_menu_empresa();
        else running_menu_individual();
        }
    } 


    private static void carregarMenus() {  
        String[] menu0 = {"Menu", 
                         "Fechar sessão"}; 
        String[] menu1 = {"Registar Utilizador", 
                         "Iniciar sessão", 
                         };  
        String[] menu2 = {"Individual",  
                         "Empresa"}; 
        String[] menu3 = {"Aceder às faturas", 
                         "Classificar fatura por setor"};
        String[] menu4 = {"Associar fatura"}; 
        String[] menu5 = {"Fatura Nova"};
                         
        
        menu_logado = new Menu(menu0);  
        menu_principal = new Menu(menu1); 
        menu_registo = new Menu(menu2);  
        menu_individual = new Menu(menu3); 
        menu_empresa = new Menu(menu4);  
        menu_cria_fatura = new Menu(menu5);
    }
  
    /** carrega o estado da aplicação desde a últiva vez que foi fechada. */
    private static void initApp(String fich) { 
        try { 
            jafat = JavaFatura.leObj(fich); 
        } 
        catch (IOException e) {  
            jafat = new JavaFatura(); 
            System.out.println("Não foi possível ler os dados!\nErro de leitura.");
        }
        catch (ClassNotFoundException e) { 
            jafat = new JavaFatura(); 
            System.out.println("Não foi possível ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch(ClassCastException e) {  
            jafat = new JavaFatura(); 
            System.out.println("Não foi possível ler os dados!\nFicheiro com formato errado.");
        }
    } 

    private static void registo() {   
        Utilizador user; 
        Scanner is = new Scanner(System.in); 
        
        menu_registo.executa(); 
        if(menu_registo.getOpcao() != 0){ 
                   /** referências em comum */
                   String  NIF,email,nome,morada,password,
                   /** referente ao individual */
                   NIFagregado, ativEco, 
                   /** referente as empresas */
                   INativ, INfat;
                   int COEFiscal, agregado;
                   
            System.out.print("NIF: "); 
            NIF = is.nextLine(); 
            System.out.print("Nome: "); 
            nome = is.nextLine(); 
            System.out.print("Email: "); 
            email = is.nextLine(); 
            System.out.print("Morada: "); 
            morada = is.nextLine(); 
            System.out.print("Password: "); 
            password = is.nextLine(); 
            
            switch(menu_registo.getOpcao()){  
                case 1:  System.out.print("Agregado: "); 
                         agregado = is.nextInt(); 
                         System.out.print("Coeficiente fiscal: "); 
                         COEFiscal = is.nextInt(); 
                         System.out.print("NIF Agregado: "); 
                         is.nextLine();
                         NIFagregado = is.nextLine();                                                    
                         System.out.print("Atividade económica: "); 
                         ativEco = is.nextLine(); 
                         user = new Individual(NIF,nome,email,morada,password,NIFagregado,ativEco,agregado,COEFiscal,null);
                         break;
                case 2:  System.out.print("Informações atividade: "); 
                         INativ = is.nextLine(); 
                         System.out.print("Informações fator: "); 
                         INfat = is.nextLine(); 
                         user = new Empresa(NIF,nome,email,morada,password,INativ,INfat,null);
                         break; 
                default: user = new Empresa();
            }
            try{  
                jafat.registarUtilizador(user);
            }
            catch(UtilizadorExistenteException e) {  
                System.out.println("Este utilizador já existe!");
            }
        }
        else System.out.println("Registo cancelado!"); 
        is.close();
    }

    private static void iniciarSessao() { 
        Scanner is = new Scanner(System.in); 
        String NIF,password; 
        System.out.print("NIF: "); 
        NIF = is.nextLine(); 
        System.out.print("Password: "); 
        password = is.nextLine(); 
        
        try{  
            jafat.iniciaSessao(NIF,password);
        }
        catch(SemAutorizacaoException e){  
            System.out.println(e.getMessage());
        }
        is.close();
    }

    private static void fecharSessao(){ 
        jafat.fechaSessao();
    }

    private static void running_menu_individual(){  
        do{ 
            menu_individual.executa(); 
            switch(menu_individual.getOpcao()){  
                case 1: consultaFaturas(); 
                        break;   
                case 2: alteraFatura(); 
                        break;
            }        
        }while(menu_individual.getOpcao() != 0);
    
    }

    private static void running_menu_empresa() {  
        do{ 
            menu_empresa.executa(); 
            switch(menu_empresa.getOpcao()){  
                case 1: adicionaFatura(); 
                        break;             
            }
        }while(menu_empresa.getOpcao() != 0);    
    }
    
    private static void running_menu_acede_fatura() { 
        do{ 
            menu_acede_fatura.executa(); 
            switch(menu_acede_fatura.getOpcao()){ 
                case 1: consultarFaturas(); 
                        break;
            }
    
        }while(menu_acede_fatura.getOpcao() != 0); 
    }
    
    private static void faturaIndividual(){ 
        Map<Fatura,Individual> faturaIndividual = new TreeMap<Fatura,Individual>(); 
        faturaIndividual = jafat.getMapeamentoFaturas(); 
        for(Map.Entry<Fatura,Individual> entry : faturaIndividual.entrySet()){ 
            Fatura f = entry.getKey(); 
            Individual i = entry.getValue(); 
            System.out.println("\n****************** Faturas *******************"); 
            System.out.println(f); 
            System.out.println(i); 
            System.out.println("*************************************");
        
        }
    
    }
    /** Consulta faturas dado um determinado NIF */
    private static void consultarFaturas(){ 
        List<Consulta> lista = new ArrayList<Consulta>(); 
        try{  
            lista = jafat.getConsultas();
        }
        catch(SemAutorizacaoException e){ 
            System.out.println(e.getMessage());
        }
        for(Consulta c: lista){  
            String x = c.toString(); 
            System.out.println(x);
        }
    }
    
    /**func que retorna os 10 cont com mais despesas*/
    private static void topContribuidores(){   
        Set<String> lista = new HashSet<String>(); 
        lista = jafat.getTopFaturas(10); 
        for(String  i:lista){ 
            System.out.println(i);
        }
    }
    
    /**adiciona fatura a JavaFatura*/
    private static void adicionaFatura(){  
        Fatura fat = criaFatura(); 
        if(fat!=null){  
            try{  
                jafat.registaFatura(fat);
            }
            catch(FaturaExisteException | SemAutorizacaoException e) {  
                System.out.println(e.getMessage());
            }
        }               
    }
   
   /** cria fatura para ser adicionada a Javafatura **/
   private static Fatura criaFatura(){  
       Fatura fatura = null; 
       Scanner is = new Scanner(System.in); 
       
       menu_cria_fatura.executa();
       if(menu_cria_fatura.getOpcao() != 0){ 
           String NIFe,desig,data,NIFc,desc,natDes; 
           int preco = 0; 
           
           System.out.print("Nif emitente: "); 
           NIFe = is.nextLine();
           System.out.print("NIF contribuinte: "); 
           NIFc = is.nextLine(); 
           System.out.print("Designação do emitente: "); 
           desig = is.nextLine(); 
           System.out.print("Descrição da despesa: "); 
           desc = is.nextLine(); 
           System.out.print("Data da despesa: "); 
           data = is.nextLine(); 
           System.out.print("Natureza da despesa: "); 
           natDes = is.nextLine(); 
           System.out.print("Valor da despesa: "); 
           try{ 
               preco = is.nextInt();
           } catch(InputMismatchException e) { 
               System.out.println("Valor da despesa inválido!");                
           }
           fatura = new Fatura(NIFe,desig,data,NIFc,desc,natDes,preco,null);
        }    
       is.close(); 
       return fatura;
   }

    private static void alteraFatura(){ 
        String NIF, desc; 
        Scanner is = new Scanner(System.in); 
        System.out.print("NIF: "); 
        NIF = is.nextLine(); 
        System.out.print("Descrição nova: "); 
        desc = is.nextLine(); 
        try{ jafat.setFatura(NIF,desc); 
        }
        catch(FaturaInexistenteException | SemAutorizacaoException | 
              EstadoInvalidoException e) {  
                  System.out.println(e.getMessage());
                }
                is.close();
    }
    
    //consulta faturas do contribuinte com determinado NIF
    private static void consultaFaturas(){ 
        Scanner is = new Scanner(System.in); 
        List<Fatura> lista = new ArrayList<Fatura>(); 
        String NIF; 
        System.out.print("NIF do contribuinte: ");
        NIF = is.nextLine(); 
        
        lista = jafat.getFaturas(NIF); 
        if(lista.isEmpty()) System.out.print("\nNão tem faturas associadas!");
        for(Fatura f : lista) 
            System.out.println(f); 
        is.close();
    }
}
