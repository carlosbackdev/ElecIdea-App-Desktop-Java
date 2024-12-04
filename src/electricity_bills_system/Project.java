
package electricity_bills_system;

import javax.swing.*;
import java.awt.*;

public class Project extends JFrame{
    
    Project(){
        super("Proyectos");
        setContentPane(new BackgroundPanel("images/login.jpg"));
        setLayout(new BorderLayout()); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        Font menufont =new Font("Roboto", Font.PLAIN, 22);
        
        JMenuBar navegador=new JMenuBar();
        navegador.setBackground(new Color(205, 205, 205));
        setJMenuBar(navegador);
        
        JMenu admin=new JMenu("Administración");
        admin.setFont(menufont);
        admin.setBackground(new Color(205, 205, 205));
        ImageIcon icon1= new ImageIcon(ClassLoader.getSystemResource("images/ADMIN.png"));
        Image image1 = icon1.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        admin.setIcon(new ImageIcon(image1));
        navegador.add(admin);
        
        JMenuItem cliente=new JMenuItem("Nuevo Cliente");
        cliente.setFont(menufont);
        admin.add(cliente);
        
        
        
        
        
        setLayout(new FlowLayout());
        
        
        setVisible(true);
        
    }
    
    public static void main(String[] args){
        new Project();
    }
    
    
    
}
