

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test; 
import java.util.Set;

/**
 * The test class Testes.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class Testes
{
   private JavaFatura jav; 
   private Empresa e; 
   private Fatura f; 
   
   @Test 
   public void mainTest(){ 
       jav = new JavaFatura(); 
       try{ 
            jav.iniciaSessao("",null); 
                           fail();
       } catch(SemAutorizacaoException e) { 
           
       } catch(Exception e) { 
           fail(); 
       }
       
       try { 
           e = new Empresa(); 
           jav.registarUtilizador(e);
       } catch(Exception e) { 
           fail(); 
       }

       String NIF = e.getNIF(); 
       String password = e.getPassword();
   
       try { 
           jav.iniciaSessao(NIF,password); 
        } catch(Exception e) {  
            fail();  
        }
    
       f = new Fatura(); 
       try { 
           jav.registaFatura(f); 
       } catch (Exception e) {  
           fail(); 
       } 
       
       int s = jav.getFaturas("Fatura").size(); 
       assertTrue(s>0); 
       Set<String>fats = jav.getTopFaturas(0); 
       assertTrue(fats.contains(f.getNIFe())); 
       assertTrue(jav.getMapeamentoFaturas().keySet().contains(f)); 
       try { 
           assertTrue(jav.getConsultas().size()>0); 
       } catch(Exception e) { 
           fail(); 
       }
   
       jav.fechaSessao(); 
       Individual i = new Individual(); 
       try { 
           jav.registarUtilizador(i); 
       } catch(Exception e) { 
           fail();
       }
       NIF = i.getNIF(); 
       password = i.getPassword(); 
       try{ 
           jav.iniciaSessao(NIF,password); 
           jav.setFatura(f.getNIFc(),f.getDesc()); 
           assertTrue(jav.getFaturas("Fatura").contains(f)); 
       } catch(Exception e) { 
           e.printStackTrace(); 
           fail();
       }
       }
   }
