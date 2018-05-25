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
import java.time.LocalDateTime;

public class JavaFaturaAPP
{
    private static JavaFatura jafat; 
    private static Menu menu_principal,menu_registo,menu_individual, 
                        menu_empresa, menu_individual_registado, 
                        menu_empresa_registado, menu_cria_fatura, 
                        menu_acede_fatura,  
                        menu_logado; 
    private Utilizador utilizador;
    
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
        String[] menu3 = {"Aceder às faturas totais", 
                         "Classificar fatura por setor", 
                         "Aceder a uma fatura"};
        String[] menu4 = {"Associar fatura", 
                         "Consultar faturas ordenada por valor", 
                         "Consultar faturas ordenada por data"};
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
        try {            jafat = JavaFatura.leObj(fich); 
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
                   INfat;
                   int COEFiscal, agregado, INativ, nativ;
                   nativ = 0;
                   
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
                case 2:  System.out.print("Informações fator: "); 
                         INfat = is.nextLine(); 
                         System.out.print("Atividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Não especificado;\n"); 
                         INativ = is.nextInt();
                         Atividade a = new Atividade();
                         a.setBoolInfo(INativ);
                         if (INativ<=6 && INativ >= 1) nativ = 1;
                         while(INativ<=6 && INativ >= 1 && nativ != 0 && nativ <= 6){
                             System.out.print("Deseja acrescentar mais alguma atividade económica à sua empresa? 1-Sim 2-Não \n");
                             INativ = is.nextInt();
                             if(INativ == 1){
                                 System.out.print("Atividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Outro;\n");
                                    INativ = is.nextInt();
                                    if (INativ<=6 && INativ >= 1) nativ++;
                                    a.setBoolInfo(INativ);
                                }
                             else break;
                            }
                         
                         user = new Empresa(NIF,nome,email,morada,password,a,INfat,null);
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
                case 3: faturaIndividual(); 
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
                case 2: faturasEmpresaOrdValor(); 
                        break; 
                case 3: faturasEmpresaOrdData();
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
       Scanner is = new Scanner(System.in);
      
       System.out.print("Id da fatura desejada: ");
       int id = is.nextInt();
       
       try{
           Fatura f = jafat.getFatura(id);
           System.out.println(f);
        }
        catch(SemAutorizacaoException | FaturaInexistenteException e){
            System.out.println(e.getMessage());
        }
       is.close();
    }
   // devolve todas as faturas EMPRESA dado um NIF 
   private static void faturasEmpresaOrdValor(){

            List<Fatura> faturasEmpresa = jafat.getFaturasPorValor(jafat.getUtilizador().getNIF());  
        
            for(Fatura f : faturasEmpresa){ 
            
                System.out.println("\n****************** Faturas *******************\n"); 
                System.out.println(f);
                System.out.println("************************************************\n");
        
        }    
    }
    
   private static void faturasEmpresaOrdData(){ 
    
       List<Fatura> faturasEmpresa = jafat.getFaturasPorData(jafat.getUtilizador().getNIF()); 
       
       for(Fatura f : faturasEmpresa){ 
           System.out.println("\n****************** Faturas *******************\n"); 
           System.out.println(f);
           System.out.println("************************************************\n");
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
    
    /*adiciona fatura a JavaFatura*/
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
           String desig,NIFc,desc;
           double preco = 0; 
           LocalDateTime data;
           
           System.out.print("\n************************************************************************************************************************\n"); 
           System.out.print("\nNIF contribuinte:  "); 
           NIFc = is.nextLine(); 
           System.out.print("\nDesignação do emitente:  "); 
           desig = is.nextLine(); 
           System.out.print("\nDescrição da despesa:  "); 
           desc = is.nextLine(); 
           data = LocalDateTime.now();
           
           Empresa emp = (Empresa) jafat.getUtilizador();
           Atividade natDes = emp.getAtiv();
           if(natDes.temAtividadeOuMaisQueUma()) {
               System.out.print("\nTerá que validar a fatura futuramente (Mais que um setor de atividade ou nenhum)!\n");
               natDes = new Atividade();
            }
           
           
           System.out.print("\nValor da despesa:  "); 
           try{ 
               preco = is.nextDouble();
           } catch(InputMismatchException e) { 
               System.out.println("Valor da despesa inválido!");                
           }
           System.out.print("\n************************************************************************************************************************\n");
           fatura = new Fatura(jafat.getUtilizador().getNIF(),desig,data,NIFc,desc,natDes,preco,null,-1,0);
       }    
       is.close(); 
       return fatura;
   }

   private static void alteraFatura(){ 
        int id, natDes; 
        Scanner is = new Scanner(System.in); 
        System.out.print("ID da fatura: "); 
        id = is.nextInt(); 
        is.nextLine();
        System.out.print("Natureza da despesa nova: "); 
        System.out.print("\nAtividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Não especificado;\n");
        natDes = is.nextInt(); 
        System.out.print("\n!!! Atenção, Se escolher uma área de atividade que não pertence a empresa a fatura não irá ser alterada !!!\n");
        Atividade r = new Atividade();
        try {
            Fatura f = jafat.getFatura(id);
            Empresa emp = (Empresa) jafat.getUtilizador(f.getNIFe());
            Atividade a = emp.getAtiv();
            if (natDes == 1 && (a.getSaude() || a.getNEspecificado())) r.setSaude(true);
            if (natDes == 2 && (a.getEducacao() || a.getNEspecificado())) r.setEducacao(true);
            if (natDes == 3 && (a.getRestauracao() || a.getNEspecificado())) r.setRestauracao(true);
            if (natDes == 4 && (a.getTransportes() || a.getNEspecificado())) r.setTransportes(true);
            if (natDes == 5 && (a.getVeiculos() || a.getNEspecificado())) r.setVeiculos(true);
            if (natDes == 6 && (a.getElet() || a.getNEspecificado())) r.setElet(true);
            jafat.setFatura(id, r,LocalDateTime.now());
        }
         catch(FaturaInexistenteException | SemAutorizacaoException | 
              EstadoInvalidoException e) {  
                  System.out.println(e.getMessage());
                }
                
        is.close();
   }
      
   //consulta faturas do contribuinte com determinado NIF --- passar fatura f para dentro do alteraFatura e depois faz set dos correspondentes valores no setFatura
   private static void consultaFaturas(){ 
        Scanner is = new Scanner(System.in); 
        List<Fatura> lista = new ArrayList<Fatura>(); 
        String in; 
        
        
        lista = jafat.getFaturasIndividual(jafat.getUtilizador().getNIF()); 
        if(lista.isEmpty()) System.out.print("\nNão tem faturas associadas ao contribuinte!");
        for(Fatura f : lista) 
            System.out.println(f); 
        System.out.print("\nFatura possui dados correctos? S/N: "); 
        in = is.nextLine(); 
        if(in.equals("N")){ 
                alteraFatura();
        } else if (in.equals("S")) { System.out.print("Obrigado e volte sempre!"); }
        is.close();
    }
}
