
package electricity_bills_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Project extends JFrame implements ActionListener{
    JMenuItem cliente,generar,material,factura_detalle;
    String NIF,ID_USER;
    
    Project(String NIF, String ID_USER){
        this.NIF=NIF;
        this.ID_USER=ID_USER;   
        
        setContentPane(new BackgroundPanel("images/fondo.png"));
        setLayout(new BorderLayout()); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(950, 650));
        Font menufont =new Font("Roboto", Font.PLAIN, 22);
        
        JMenuBar navegador=new JMenuBar();
        navegador.setBackground(new Color(205, 205, 205));
        setJMenuBar(navegador);
        
        JMenu mn=new JMenu(">");
        mn.setFont(menufont);
        mn.setBackground(new Color(205, 205, 205));
        ImageIcon icon3= new ImageIcon(ClassLoader.getSystemResource("images/menu.png"));
        Image image3 = icon3.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        mn.setIcon(new ImageIcon(image3));
        navegador.add(mn);
        
        JMenu admin=new JMenu("Administración   ");
        admin.setFont(menufont);
        admin.setBackground(new Color(205, 205, 205));
        ImageIcon icon1= new ImageIcon(ClassLoader.getSystemResource("images/ADMIN.png"));
        Image image1 = icon1.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        admin.setIcon(new ImageIcon(image1));
        navegador.add(admin);
        
        cliente=new JMenuItem("Nuevo Cliente ");
        cliente.setFont(menufont);
        cliente.setMnemonic('D');
        cliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        admin.add(cliente);
        cliente.addActionListener(this);
        
        JMenuItem cliente_detalles=new JMenuItem("Fichas Clientes ");
        cliente_detalles.setFont(menufont);
        cliente_detalles.setMnemonic('F');
        cliente_detalles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        admin.add(cliente_detalles);
        
        JMenuItem cliente_crear=new JMenuItem("Modificar Clientes ");
        cliente_crear.setFont(menufont);
        cliente_crear.setMnemonic('M');
        cliente_crear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        admin.add(cliente_crear);
        
        JMenuItem factura=new JMenuItem("Factura Clientes ");
        factura.setFont(menufont);
        factura.setMnemonic('B');
        factura.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        admin.add(factura);
        
        JMenu info=new JMenu("Datos   ");
        info.setFont(menufont);
        info.setBackground(new Color(205, 205, 205));
        ImageIcon icon2= new ImageIcon(ClassLoader.getSystemResource("images/datos2.png"));
        Image image2 = icon2.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        info.setIcon(new ImageIcon(image2));
        navegador.add(info);
        
        JMenuItem info_ver=new JMenuItem("Ver Información ");
        info_ver.setFont(menufont);
        info_ver.setMnemonic('I');
        info_ver.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        info.add(info_ver);
        
        JMenuItem info_actualizar=new JMenuItem("Actualizar Información ");
        info_actualizar.setFont(menufont);
        info_actualizar.setMnemonic('A');
        info_actualizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        info.add(info_actualizar);
        
        JMenu accion=new JMenu("Facturas   ");
        accion.setFont(menufont);
        accion.setBackground(new Color(205, 205, 205));
        ImageIcon icon4= new ImageIcon(ClassLoader.getSystemResource("images/facturas.png"));
        Image image4 = icon4.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        accion.setIcon(new ImageIcon(image4));
        navegador.add(accion);
        
        generar=new JMenuItem("Generar Factura ");
        generar.setFont(menufont);
        generar.setMnemonic('G');
        generar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        accion.add(generar);
        generar.addActionListener(this);
        
        JMenuItem pago=new JMenuItem("Pago Facturas ");
        pago.setFont(menufont);
        pago.setMnemonic('P');
        pago.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        accion.add(pago);
        
        factura_detalle=new JMenuItem("Detalles Facturas ");
        factura_detalle.setFont(menufont);
        factura_detalle.setMnemonic('O');
        factura_detalle.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        accion.add(factura_detalle);
        factura_detalle.addActionListener(this);
        
        JMenu add_accion=new JMenu("Añadir   ");
        add_accion.setFont(menufont);
        add_accion.setBackground(new Color(205, 205, 205));
        ImageIcon icon5= new ImageIcon(ClassLoader.getSystemResource("images/add.png"));
        Image image5 = icon5.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        add_accion.setIcon(new ImageIcon(image5));
        navegador.add(add_accion);        
        
        
        JMenuItem nota=new JMenuItem("Añadir notas ");
        nota.setFont(menufont);
        nota.setMnemonic('N');
        nota.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        add_accion.add(nota);
        
        JMenu proyectos=new JMenu("Proyecto   ");
        proyectos.setFont(menufont);
        proyectos.setBackground(new Color(205, 205, 205));
        ImageIcon icon6= new ImageIcon(ClassLoader.getSystemResource("images/proyecto.png"));
        Image image6 = icon6.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        proyectos.setIcon(new ImageIcon(image6));
        navegador.add(proyectos);
        
        material=new JMenuItem("Material");
        material.setFont(menufont);
        material.setMnemonic('C');
        material.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        proyectos.add(material);
        material.addActionListener(this);
        
        JMenuItem calculo=new JMenuItem("Calcular ");
        calculo.setFont(menufont);
        calculo.setMnemonic('C');
        calculo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        proyectos.add(calculo);
        
        JMenu salir=new JMenu("Salir   ");
        salir.setFont(menufont);
        salir.setBackground(new Color(205, 205, 205));
        ImageIcon icon7= new ImageIcon(ClassLoader.getSystemResource("images/salir.png"));
        Image image7 = icon7.getImage().getScaledInstance(22, 22, Image.SCALE_DEFAULT);
        salir.setIcon(new ImageIcon(image7));
        navegador.add(salir);
        
        
        setLayout(new FlowLayout());
        
        
        setVisible(true);
        
    }
    public void actionPerformed(ActionEvent ae){
     if(ae.getSource()==cliente){
     new newClient(NIF,ID_USER);
     }
     if(ae.getSource()==generar){
     new calculateBill("","",NIF,ID_USER);
     }
     if(ae.getSource()==material){
     new MaterialFrame("","",NIF,ID_USER);
     }
     if(ae.getSource()==factura_detalle){
     new BillSearch(NIF,ID_USER);
     }
    }
    
    public static void main(String[] args){
        new Project("","");
    }
    
    
    
}
