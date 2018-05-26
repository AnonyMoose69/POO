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
                        menu_acede_fatura, menu_admin,  
                        menu_logado; 
    private Utilizador utilizador;
    
    private JavaFaturaAPP() {} 
    
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
                         "Lucro da Empresa"
                         };
        String[] menu5 = {"Fatura Nova"}; 
        String[] menu6 = {"Top 10 contribuidores que mais gastam no sistema"};
        
        menu_logado = new Menu(menu0);  
        menu_principal = new Menu(menu1); 
        menu_registo = new Menu(menu2);  
        menu_individual = new Menu(menu3); 
        menu_empresa = new Menu(menu4);  
        menu_cria_fatura = new Menu(menu5); 
        menu_admin = new Menu(menu6);
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
       
   private static void running_menu_admin(){ 
       do{ 
           menu_admin.executa(); 
           switch(menu_admin.getOpcao()){ 
                       case 1: getTop10();
                               break;
            }
        }while(menu_admin.getOpcao() != 0);
   }
   
   private static void getTop10(){
       List<Individual> res = jafat.getTop10Util();
       
       for(Individual i : res){
           System.out.print(i.getNIF());
           System.out.print(i.getValDesTotal());
        }
        
       
    }
   private static void verFamilia(){ 
       int id = ((Individual)jafat.getUtilizador()).getIDfam();
       Familia f = jafat.getFamilia(id);
       
       System.out.println(f);
   }
   
   private static void empresaLucro(){
       double res = jafat.getLucroEmp();
       System.out.print("\n O lucro da empresa é: " + res);
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
   
   private static void deducaoTotal(){
       int idFam = ((Individual)jafat.getUtilizador()).getIDfam();
       
       double coefFam = jafat.getFamilia(idFam).getCoefFamilia();
       
       System.out.print("\n A sua dedução total é: " + (((Individual)jafat.getUtilizador()).getDeducaoTotal(coefFam)));
     
       
   }
   
   public static void visualizarFaturasFam(){
       List<Fatura> l = jafat.getFaturasFamilia();
       
       for(Fatura f : l){ 
           System.out.println("\n****************** Faturas *******************\n"); 
           System.out.println(f);
           System.out.println("************************************************\n");
       }
    }
    
   private static void deducaoTotalAgregado(){
       System.out.print("\n A dedução do agregado familiar é: " + jafat.getDeducaoAgregado());
    }
   private static void faturasEmpresaContIntervalo(){
       Scanner is = new Scanner(System.in);
       int ano_inicio, ano_fim, mes_inicio, mes_fim, dia_inicio, dia_fim;
       String NIFco;
       
       System.out.print("\n Indique o NIF do contribuinte que pretende:  ");
       NIFco = is.nextLine();
       System.out.print("\n Indique o ano do inicio: (4 digitos)  ");
       ano_inicio = is.nextInt();
       System.out.print("\n Indique o mês do inicio: (entre 1 e 12)  ");
       mes_inicio = is.nextInt();
       System.out.print("\nIndique o dia do inicio: (apenas entre o nº de dias do mês)  ");
       dia_inicio = is.nextInt();
       System.out.print("\nIndique o ano do fim: (4 digitos)  ");
       ano_fim = is.nextInt();
       System.out.print("\nIndique o mês do fim: (entre 1 e 12)  ");
       mes_fim = is.nextInt();
       System.out.print("\nIndique o dia do fim: (apenas entre o nº de dias do mês)  ");
       dia_fim = is.nextInt();
       
       LocalDateTime begin = LocalDateTime.of(ano_inicio, mes_inicio, dia_inicio, 0, 0);
       LocalDateTime end = LocalDateTime.of(ano_fim, mes_fim, dia_fim, 0, 0);
       
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
      
    /*adiciona fatura a JavaFatura*/
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
   
   
   /** cria fatura para ser adicionada a Javafatura **/
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
           fatura = new Fatura(jafat.getUtilizador().getNIF(),desig,data,NIFc,desc,ativ,preco,-1,false);
       }    
       is.close(); 
       return fatura;
    
    }

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
