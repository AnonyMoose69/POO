import java.io.IOException; 
import java.util.Scanner; 
import java.util.ArrayList; 
import java.util.Collection;
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
                        menu_acede_fatura, menu_admin,  
                        menu_logado; 
    private Utilizador utilizador;
    
    private JavaFaturaAPP() {} 
    
    /**
     * Função que faz executar toda a aplicação JavaFaturaAPP
     */
    public static void main(String[] args) {  
        Utilizador user;
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
    
    /**
     * Apresenta o menu principal.
     */
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
                  case 3: iniciarSessaoAd(); 
                          break;
                  case 0: running = 0;
            }
        } 
    }    while(running!=0);
    } 
    
    /**
     * Apresenta o Menu consoante o tipo de utilizador com sessão iniciada.
     */
    private static void menu() {  
    
        if(jafat.getUtilizador() == null) 
        running_menu_individual(); 
        else { 
            Utilizador util = jafat.getUtilizador(); 
            if(util.getClass().getSimpleName().equals("Empresa")) 
                running_menu_empresa();
                else {
                    if(util.getClass().getSimpleName().equals("Individual")) running_menu_individual();
                    else running_menu_admin();
                }
        }
    } 

    /**
     * Carrega todos os menus para apresentar.
     */
    private static void carregarMenus() {  
        String[] menu0 = {"Menu", 
                         "Fechar sessão"}; 
        String[] menu1 = {"Registar Utilizador", 
                         "Iniciar sessão",  
                         "Iniciar sessão como adminstrador"
                         };  
        String[] menu2 = {"Individual",  
                         "Empresa"}; 
        String[] menu3 = {"Aceder às faturas totais", 
                         "Classificar fatura por setor", 
                         "Aceder a uma fatura",
                         "Visualizar a família",
                         "Dedução total do individuo",
                         "Dedução total do agregado familiar",
                         "Aceder às faturas totais do agregado familiar"
                        };
        String[] menu4 = {"Associar fatura", 
                         "Consultar faturas ordenada por valor", 
                         "Consultar faturas ordenada por data",
                         "Consultar faturas de um contribuinte no intervalo de tempo pretendido",
                         "Consultar faturas de um contribuinte ordenadas por valor decrescente",
                         "Lucro da Empresa num intervalo de tempo"
                         };
        String[] menu5 = {"Fatura Nova"}; 
        String[] menu6 = {"Top 10 contribuidores que mais gastam no sistema",
                          "Top X empresas com mais faturas associadas no sistema (com valores da deduções)"
                         };
        
        menu_logado = new Menu(menu0);  
        menu_principal = new Menu(menu1); 
        menu_registo = new Menu(menu2);  
        menu_individual = new Menu(menu3); 
        menu_empresa = new Menu(menu4);  
        menu_cria_fatura = new Menu(menu5); 
        menu_admin = new Menu(menu6);
    }
  
    /**
     * Carrega o estado da aplicação da última vez que esta foi fechada.
     * @param fich
     */
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
    
    /**
     * Registo na ImobiliáriaApp e todas as consequências deste mesmo
     */
    private static void registo() {   
        Utilizador user; 
        Scanner is = new Scanner(System.in);  
        
        
        
        menu_registo.executa(); 
        if(menu_registo.getOpcao() != 0){ 
                   /** referências em comum */
                   String  NIF,email,nome,morada,password;
                   /** referente ao individual */
                   double COEFiscal = -1.0;
                   int atividade, dependInt;
                   boolean depend;
                   /** referente as empresas */
                   String INfat;
                   int agregado, INativ, nativ;
                   nativ = 0;
                   /** referente as famílias */
                   int familia = 0;

                   
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
                case 1:

                         while(COEFiscal < 0 || COEFiscal > 1){
                                System.out.print("Coeficiente fiscal (entre 0 e 1): "); 
                                COEFiscal = is.nextDouble(); 
                            }
                         System.out.print("É dependente? 1-Sim 2-Não \n"); 
                         dependInt = is.nextInt(); 
                         if(dependInt == 1) depend=true; 
                         else depend=false;
                         System.out.print("Atividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Não especificado;\n");
                         atividade = is.nextInt(); 
                         List<Integer> atividadesInd = new ArrayList<Integer>();
                         if (atividade >= 1 && atividade <= 6) atividadesInd.add(atividade);
                         while(atividadesInd.size() > 0 && atividadesInd.size() <= 6){
                             System.out.print("Deseja acrescentar mais alguma atividade económica ao seu perfil? 1-Sim 2-Não \n");
                             atividade = is.nextInt();
                             if(atividade == 1){
                                 System.out.print("Atividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Outro;\n");
                                 atividade = is.nextInt();
                                 if (atividade >= 1 && atividade <= 6) atividadesInd.add(atividade);
                                }
                             else break;
                            }
                         System.out.print("Pretende-se associar a uma familia existente? 1-Sim 2-Não");
                         familia = is.nextInt();
                         if (familia == 1){
                             System.out.print("Id da família: ");
                             familia = is.nextInt();
                            }
                         else {
                             System.out.print("Irá ser criada uma nova família (inicialmente vazia)!");
                             familia = -1;
                            }
                         
                         user = new Individual(NIF,nome,email,morada,password,atividadesInd,COEFiscal,null, depend, familia);
                         break;
                case 2:  System.out.print("Informações fator: "); 
                         INfat = is.nextLine(); 
                         System.out.print("Atividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Não especificado;\n"); 
                         INativ = is.nextInt();
                         List<String> atividades = new ArrayList<>();
                         Atividade a = Atividade.fromInt(INativ);                     
                         if (!a.getAtiv().equals("")) {nativ = 1; atividades.add(a.getAtiv());}
                         while(INativ<=6 && INativ >= 1 && nativ != 0 && nativ <= 6){
                             System.out.print("Deseja acrescentar mais alguma atividade económica à sua empresa? 1-Sim 2-Não \n");
                             INativ = is.nextInt();
                             if(INativ == 1){
                                 System.out.print("Atividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Outro;\n");
                                    INativ = is.nextInt();
                                    a = Atividade.fromInt(INativ);
                                    if (!a.getAtiv().equals("")) {nativ++; atividades.add(a.getAtiv());}                           
                                }
                             else break;
                            }
                         
                         user = new Empresa(NIF,nome,email,morada,password,atividades,INfat,null);
                         break; 
                default: user = new Empresa();
            }
            try{  
                jafat.registarUtilizador(user, familia);
            }
            catch(UtilizadorExistenteException | FamiliaInexistenteException e) {  
                System.out.println(e.getMessage());
            }
        }
        else System.out.println("Registo cancelado!"); 
        is.close();
    }
        
    /**
     * Inicio de sessão na JavaFaturaAPP
     */
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
        
    /**
     * Inicio de sessão como adminstrador na JavaFaturaAPP
     */
    private static void iniciarSessaoAd() { 
        Scanner is = new Scanner(System.in); 
        String NIF,password; 
        System.out.print("NIF: "); 
        NIF = is.nextLine(); 
        System.out.print("Password: "); 
        password = is.nextLine(); 
        
        try{  
            jafat.iniciaSessaoAdmin(NIF,password);
        }
        catch(SemAutorizacaoException e){  
            System.out.println(e.getMessage());
        }
        is.close();
    }
    
    /**
     * Fechar sessão na JavaFaturaAPP
     */
    private static void fecharSessao(){ 
        jafat.fechaSessao();
    }
    
    /**
     * Executar o menu para individuais registados na JavaFaturaAPP
     */
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
                case 4: verFamilia(); 
                        break;
                case 5: deducaoTotal();
                        break;
                case 6: deducaoTotalAgregado();
                        break;
                case 7: visualizarFaturasFam();
                        break;
            }        
        }while(menu_individual.getOpcao() != 0);
    
    }
    
    /**
     * Executar o menu para empresas registadas na JavaFaturaAPP
     */
    private static void running_menu_empresa() {  
        do{ 
            menu_empresa.executa(); 
            switch(menu_empresa.getOpcao()){  
                case 1: adicionaFatura(); 
                        break;   
                case 2: faturasEmpresaOrdValor(); 
                        break; 
                case 3: faturasEmpresaOrdData();
                        break;
                case 4: faturasEmpresaContIntervalo();
                        break;
                case 5: faturasEmpresaContOrdVal(); 
                        break;
                case 6: empresaLucro();
                        break;
            }
        }while(menu_empresa.getOpcao() != 0);    
    }
    
    /**
     * Executar o menu para adminstradores no JavaFaturaAPP
     */
    private static void running_menu_admin(){ 
       do{ 
           menu_admin.executa(); 
           switch(menu_admin.getOpcao()){ 
                       case 1: getTop10();
                               break;
                       case 2: getTopXEmp();
                               break;
            }
        }while(menu_admin.getOpcao() != 0);
    }
    
    /** 
     * Obter o top 10 utilizadores que mais gastam no sistema 
     */
    private static void getTop10(){
       Collection<Individual> res = jafat.getTop10Util();
       int i = 1;
       for(Individual u : res){
           System.out.print("\n NIF - " + u.getNIF() + "   Valor gasto: ");
           System.out.print(u.getValDesTotal());
        }
    }
    
    /**
     * Obter o top X empresas com mais faturas e a deducão fiscal do seu conjunto de faturas
     */
    private static void getTopXEmp(){
        Scanner is = new Scanner(System.in);
        int x;
        System.out.print("\n X (nº de empresas que deseja ver no top):  ");
        x = is.nextInt();
        
        Collection<Empresa> topX =  jafat.getTopXEmpMaisFat(x);
        Collection<Double> ded = jafat.getTopXEmpresasDeducao(topX);
        Double[] dedu = new Double[x];
        ded.toArray(dedu);
        int i = 1;
        
        for(Empresa e : topX){
            System.out.print("\n " + i + " - " + "NIF da empresa " + e.getNIF() + " Valor de dedução: " + dedu[i-1]);
            i++;
        }
        
        is.close();
    }
    
    /** 
     * Print de uma determinada familia
     */
    private static void verFamilia(){ 
       int id = ((Individual)jafat.getUtilizador()).getIDfam();
       Familia f = jafat.getFamilia(id);
       
       System.out.println(f);
    }
    
    /** 
     * Calcular o lucro de uma empresa 
     */
    private static void empresaLucro(){
       Scanner is = new Scanner(System.in);
       int ano_inicio, ano_fim, mes_inicio, mes_fim, dia_inicio, dia_fim, hora_inicio, hora_fim, minuto_fim, minuto_inicio;

       System.out.print("\n Indique o ano do inicio: (4 digitos)  ");
       ano_inicio = is.nextInt();
       System.out.print("\n Indique o mês do inicio: (entre 1 e 12)  ");
       mes_inicio = is.nextInt();
       System.out.print("\n Indique o dia do inicio: (apenas entre o nº de dias do mês)  ");
       dia_inicio = is.nextInt();
       System.out.print("\n Indique a hora do inicio: (entre 0 e 24)   ");
       hora_inicio = is.nextInt();
       System.out.print("\n Indique o minuto do inicio: (entre 0 e 60)   ");
       minuto_inicio = is.nextInt();
       System.out.print("\n Indique o ano do fim: (4 digitos)  ");
       ano_fim = is.nextInt();
       System.out.print("\n Indique o mês do fim: (entre 1 e 12)  ");
       mes_fim = is.nextInt();
       System.out.print("\n Indique o dia do fim: (apenas entre o nº de dias do mês)  ");
       dia_fim = is.nextInt(); 
       System.out.print("\n Indique a hora do fim: (entre 0 e 24)   ");
       hora_fim = is.nextInt();
       System.out.print("\n Indique o minuto do fim: (entre 0 e 60)   ");
       minuto_fim = is.nextInt();
       
       LocalDateTime begin = LocalDateTime.of(ano_inicio, mes_inicio, dia_inicio, hora_inicio, minuto_inicio);
       LocalDateTime end = LocalDateTime.of(ano_fim, mes_fim, dia_fim, hora_fim, minuto_fim);
       
       if(!(begin.isBefore(end))) {System.out.print("As datas são inválidas!"); return;}
       
       double res = jafat.getLucroEmp(begin, end);
       System.out.print("\n O lucro da empresa é: " + res);
    }
   
    /** 
     * Fazer print de uma determinada fatura dado um id desta mesma 
     */
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
   
    /** 
     * Print de faturas ordenadas por valor 
     */
    private static void faturasEmpresaOrdValor(){

            List<Fatura> faturasEmpresa = jafat.getFaturasPorValor(jafat.getUtilizador().getNIF());  
        
            for(Fatura f : faturasEmpresa){ 
            
                System.out.println("\n****************** Faturas *******************\n"); 
                System.out.println(f);
                System.out.println("************************************************\n");
        
        }    
    }
   
    /** 
     * Print das faturas ordenadas por data 
     */   
    private static void faturasEmpresaOrdData(){ 
    
       List<Fatura> faturasEmpresa = jafat.getFaturasPorData(jafat.getUtilizador().getNIF()); 
       
       for(Fatura f : faturasEmpresa){ 
           System.out.println("\n****************** Faturas *******************\n"); 
           System.out.println(f);
           System.out.println("************************************************\n");
       }
    }
   
    /** 
     * Calcular a dedução total tendo em contra o individual e agregado 
     */
    private static void deducaoTotal(){
       
       System.out.print("\n A sua dedução total é: " + jafat.deducaoTotalInd());
     
    }
   
    /** 
     * Print das faturas da familia (agregado)
     */
    public static void visualizarFaturasFam(){
       List<Fatura> l = jafat.getFaturasFamilia();
       
       for(Fatura f : l){ 
           System.out.println("\n****************** Faturas *******************\n"); 
           System.out.println(f);
           System.out.println("************************************************\n");
       }
    }
    
    /** 
     * Calculo da dedução total do agregado
     */
    private static void deducaoTotalAgregado(){
       System.out.print("\n A dedução do agregado familiar é: " + jafat.getDeducaoAgregado());
    }
    
    /** 
     * Obter e fazer print da lista de faturas num determinado intervalo de tempo 
     */
    private static void faturasEmpresaContIntervalo(){
       Scanner is = new Scanner(System.in);
       int ano_inicio, ano_fim, mes_inicio, mes_fim, dia_inicio, dia_fim, hora_inicio, minuto_inicio, hora_fim, minuto_fim;
       String NIFco;
       
       System.out.print("\n Indique o NIF do contribuinte que pretende:  ");
       NIFco = is.nextLine();
       System.out.print("\n Indique o ano do inicio: (4 digitos)  ");
       ano_inicio = is.nextInt();
       System.out.print("\n Indique o mês do inicio: (entre 1 e 12)  ");
       mes_inicio = is.nextInt();
       System.out.print("\n Indique o dia do inicio: (apenas entre o nº de dias do mês)  ");
       dia_inicio = is.nextInt();
       System.out.print("\n Indique a hora do inicio: (entre 0 e 24)   ");
       hora_inicio = is.nextInt();
       System.out.print("\n Indique o minuto do inicio: (entre 0 e 60)   ");
       minuto_inicio = is.nextInt();
       System.out.print("\n Indique o ano do fim: (4 digitos)  ");
       ano_fim = is.nextInt();
       System.out.print("\n Indique o mês do fim: (entre 1 e 12)  ");
       mes_fim = is.nextInt();
       System.out.print("\n Indique o dia do fim: (apenas entre o nº de dias do mês)  ");
       dia_fim = is.nextInt(); 
       System.out.print("\n Indique a hora do fim: (entre 0 e 24)   ");
       hora_fim = is.nextInt();
       System.out.print("\n Indique o minuto do fim: (entre 0 e 60)   ");
       minuto_fim = is.nextInt();
       
       LocalDateTime begin = LocalDateTime.of(ano_inicio, mes_inicio, dia_inicio, hora_inicio, minuto_inicio);
       LocalDateTime end = LocalDateTime.of(ano_fim, mes_fim, dia_fim, hora_fim, minuto_fim);
       
       if(!(begin.isBefore(end))) {System.out.print("As datas são inválidas!"); return;}
       
       try{
           List<Fatura> lista = jafat.getFaturasPorContribuinte(jafat.getUtilizador().getNIF(), NIFco, begin, end);
       
           
               for(Fatura f : lista){
                   System.out.println("\n****************** Faturas *******************\n"); 
                   System.out.println(f);
                   System.out.println("************************************************\n");
                }
            }
        
       catch(UtilizadorExistenteException | SemAutorizacaoException e){
           System.out.println(e.getMessage());
        }
       is.close();
    }
    
    /** 
     * Obter e fazer print das listas de faturas ordenadas por valor 
     */
    private static void faturasEmpresaContOrdVal(){
       Scanner is = new Scanner(System.in);
       String NIFco;
       
       System.out.print("\n Indique o NIF do contribuinte que pretende:  ");
       NIFco = is.nextLine();
       
       try{
           List<Fatura> lista = jafat.getFaturasPorContribuinteOrdV(jafat.getUtilizador().getNIF(), NIFco);
           
           for(Fatura f : lista){
                   System.out.println("\n****************** Faturas *******************\n"); 
                   System.out.println(f);
                   System.out.println("************************************************\n");
                }
        }
        
       catch(UtilizadorExistenteException | SemAutorizacaoException e){
           System.out.println(e.getMessage());
        }
       is.close();
    }
      
    /** 
     * Adiciona uma fatura a JavaFatura 
     */
    private static void adicionaFatura(){  
        Fatura fat = criaFatura(); 
        if(fat!=null){  
            try{  
                jafat.registaFatura(fat);   
            }                     
            catch(FaturaExisteException | SemAutorizacaoException | UtilizadorExistenteException e) {  
                System.out.println(e.getMessage());
            }
        }               
    }
   
    /** 
     * Cria uma fatura para ser adicionada a JavaFatura 
     * @return 
     */
    private static Fatura criaFatura(){  
       Fatura fatura = null; 
       Atividade ativ;
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
           List<String> natDes = emp.getAtiv();
           if(natDes.size() != 1) {
               System.out.print("\nTerá que definir a atividade económica da fatura (Mais que um setor de atividade ou nenhum)!\n");
               ativ = new Atividade();
            }
           else{ativ = Atividade.fromString(natDes.get(0));}
           
           System.out.print("\nValor da despesa:  "); 
           try{ 
               preco = is.nextDouble();
           } catch(InputMismatchException e) { 
               System.out.println("Valor da despesa inválido!");                
           }
           System.out.print("\n************************************************************************************************************************\n");
           fatura = new Fatura(jafat.getUtilizador().getNIF(),desig,data,NIFc,desc,ativ,preco,-1, (natDes.size() == 1));
       }    
       is.close(); 
       return fatura;
    
    }
    
    /** 
     * Altera o estado de uma fatura 
     */
    private static void alteraFatura(){ 
        int id, natDes; 
        Atividade r;
        Scanner is = new Scanner(System.in); 
        System.out.print("ID da fatura: "); 
        id = is.nextInt(); 
        is.nextLine();
        System.out.print("Natureza da despesa nova: "); 
        System.out.print("\nAtividade Economica: \n 1-Saúde; 2-Educação; 3-Restauração; 4-Transportes; \n 5-Reparação de veículos; 6-Eletricidade e água; 7- Não especificado;\n");
        natDes = is.nextInt();
        r = Atividade.fromInt(natDes);
        try {
            Fatura f = jafat.getFatura(id);
           
            jafat.setFatura(id, r);
        }
         catch(FaturaInexistenteException | SemAutorizacaoException | 
              EstadoInvalidoException e) {  
                  System.out.println(e.getMessage());
                }
                
        is.close();
    }
    
    /** 
     * Consultar a lista de faturas do utilizador 
     */
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
