import java.util.ArrayList; 
import java.util.List; 
import java.util.InputMismatchException; 
import java.util.Scanner; 

public class Menu
{
    private List<String> opcoes; 
    private int op; 
    
    public Menu(String[] opcoes){ 
        this.opcoes = new ArrayList<String>(); 
        for(String op: opcoes) 
            this.opcoes.add(op);
        this.op = 0;
    }

    public void executa(){
        do{ 
           showMenu(); 
           this.op = lerOpcao();
    }
    while(this.op == -1);
}

    private void showMenu() {
        System.out.println("\n*************** MENU ***************");
        for (int i = 0; i<this.opcoes.size();i++) {  
            System.out.print("  "+(i+1)); 
            System.out.print(" - "); 
            System.out.println(this.opcoes.get(i)); 
        }
        System.out.println("  0 - Sair"); 
        System.out.println("************************************");
    }

    private int lerOpcao() {  
        int op; 
        Scanner is = new Scanner(System.in);
    
        System.out.print("Opção: "); 
        try{  
            op = is.nextInt();
        }
        catch (InputMismatchException e) {  
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {  
            System.out.println("Opção inválida!!!"); 
            op = -1;
        }
        return op;
    }

    public int getOpcao() {  
        return this.op;
    }
}
