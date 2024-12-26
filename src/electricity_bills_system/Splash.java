
package electricity_bills_system;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;


public class Splash extends JFrame  implements Runnable{    
    Thread t;
    Splash(){
    
        ImageIcon img1= new ImageIcon(ClassLoader.getSystemResource("images/portada2.jpg"));
        Image i1=img1.getImage().getScaledInstance(1200,800, Image.SCALE_DEFAULT);
        ImageIcon imagen1= new ImageIcon(i1);
        JLabel image=new JLabel(imagen1);
        add(image);
        
        int x=1;
        for(int i=2;i<820; i+=4,x+=1)
        setSize(i +x, i);
        setLocationRelativeTo(null);
        try{
            Thread.sleep(5);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        t=new Thread(this);
        t.start();
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
                
        setVisible(true);

    }
    
    public void run(){
        try{
            //tiempo de carga de frame
            Thread.sleep(4000);
            setVisible(false);
            //login
            new Login();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
    new Splash ();
    
    
    }
    
}
