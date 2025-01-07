
package electricity_bills_system;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
    RoundedButton volver,crear;
    Choice empresa_nombre;
    JTextField cajon_usu,cajon_nombre,cajon_empresa;
    JLabel tipousu;
    String NIF;
    JPasswordField cajon_passwd,cajon_codigo;
    Signup(){
        super("Crear Cuenta");
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/icono_app.png")).getImage());
        setContentPane(new BackgroundPanel("images/login2.jpg"));
        setLayout(new BorderLayout());
        Font fuente1=new Font("Roboto", Font.PLAIN, 20);
        Font fuente2=new Font("Roboto", Font.PLAIN, 16);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 10, 25, 15);
        gbc.ipadx = 40;
        Color gris=new Color(230,230,230);
    


        JLabel head = new JLabel("CREAR CUENTA");
        head.setForeground(gris);
        head.setFont(fuente1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);

        JLabel tipo_usuario = new JLabel("Registrarse como");
        gbc.anchor = GridBagConstraints.WEST;
        tipo_usuario.setForeground(gris);
        tipo_usuario.setFont(fuente1);
        gbc.gridy = 1;
        
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(tipo_usuario, gbc);

        tipousu = new JLabel("       Usuario");       
        tipousu.setFont(fuente1);
        tipousu.setForeground(gris);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        panel.add(tipousu, gbc);

        JLabel nombreusu = new JLabel("Nombre de Usuario");
        gbc.anchor = GridBagConstraints.WEST;
        nombreusu.setForeground(gris);
        nombreusu.setFont(fuente1);
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(nombreusu, gbc);

        cajon_usu = new JTextField();
        cajon_usu.setFont(fuente2);
        cajon_usu.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_usu, gbc);
        
        JLabel nombrecompleto = new JLabel("Nombre y Apellido");
        nombrecompleto.setForeground(gris);
        nombrecompleto.setFont(fuente1);
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(nombrecompleto, gbc);

        cajon_nombre = new JTextField();
        cajon_nombre.setFont(fuente2);
        cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_nombre, gbc);
        
        JLabel contraseña = new JLabel("Contraseña");
        contraseña.setForeground(gris);
        contraseña.setFont(fuente1);
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(contraseña, gbc);

        cajon_passwd = new JPasswordField();
        cajon_passwd.setFont(fuente2);
        cajon_passwd.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_passwd, gbc);
        
        JLabel empresa = new JLabel("Nombre de Empresa");
        empresa.setForeground(gris);
        empresa.setFont(fuente1);
        gbc.gridy = 5;
        gbc.gridx = 0;
        panel.add(empresa, gbc);

        JTextField empresa_nombre = new JTextField();    
        empresa_nombre.setHorizontalAlignment(JTextField.CENTER);
        empresa_nombre.setFont(fuente2);
        gbc.gridx = 1;
        panel.add(empresa_nombre, gbc);
        
        
        JLabel empresacod = new JLabel("Codigo de Empresa");
        empresacod.setForeground(gris);
        empresacod.setFont(fuente1);
        gbc.gridy = 6;
        gbc.gridx = 0;
        panel.add(empresacod, gbc);

        cajon_codigo = new JPasswordField();
        cajon_codigo.setFont(fuente2);
        cajon_codigo.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_codigo, gbc);        
        
        JLabel inf = new JLabel("Para registrarse como usuario se debe obtener el codigo porporcionado por su empresa");
        gbc.anchor = GridBagConstraints.CENTER;
        inf.setForeground(gris);
        inf.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        panel.add(inf, gbc);
        
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 8; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(panelBotones, gbc);
        
        
        
        crear  = new RoundedButton("       Crear       ");
        crear.setFont(new Font("Roboto", Font.PLAIN, 16)); 
        crear.addActionListener(this);
        panelBotones.add(crear);
        panelBotones.add(crear, BorderLayout.WEST);

        volver = new RoundedButton("        Volver       ");
        volver.setFont(new Font("Roboto", Font.PLAIN, 16)); 
        volver.addActionListener(this);
        panelBotones.add(volver);
        panelBotones.add(volver, BorderLayout.EAST);
        
        

        add(panel, BorderLayout.PAGE_START);
        
        setSize(1150, 850);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == crear){
            String tipo_usuario = tipousu.getText();
            String nombre_usuario = cajon_usu.getText();
            nombre_usuario=nombre_usuario.toLowerCase().trim();
            int nombre_usuarioleght=nombre_usuario.length();
            String nombre = cajon_nombre.getText();
            nombre=nombre.toLowerCase().trim();
            int nombrelenght=nombre.length();
            String password = cajon_passwd.getText().trim();
            String CODE=cajon_codigo.getText().trim();            
            CODE=contraseña.encryptPassword(CODE);
            int passlenght=password.length();
            int ID=0;
            boolean codigo=true;
            if (!nombre_usuario.isEmpty() && !nombre.isEmpty() && !password.isEmpty()) {
                ID =IDgenerador.generadorId();}
            
            if(passlenght<=7){
            JOptionPane.showMessageDialog(null,"la contraseña debe ser de 8 carcateres mínimo");
            }
            if(nombrelenght<=2){
            JOptionPane.showMessageDialog(null,"el nombre es muy corto");
            }
            if(nombre_usuarioleght<=4){
            JOptionPane.showMessageDialog(null,"el nombre de usuario debe tener 5 letras mínimo");
            }
            if(ID<=0){
            JOptionPane.showMessageDialog(null,"se deben rellenar todos los datos");
            }            
            
            String rs3;
            int nombre_exsistente=0;
            try{ Connect c = new Connect();
            String query="select USUARIO from login;";                
              ResultSet rs2=c.s.executeQuery(query);   
               while(rs2.next()){
                   rs3=rs2.getString("USUARIO");
                   if(rs3.equals(nombre_usuario)){
                       nombre_exsistente=1;
                       JOptionPane.showMessageDialog(null,"nombre de usuario ya existe, usa otro");

                       break;
                   }
                   
               }
                rs2.close();
                c.s.close();
               
            }catch (Exception e){
                e.printStackTrace();
            }
            
            String encrypted_password = contraseña.encryptPassword(password);
            
            try {
                Connect c = new Connect ();
                ResultSet rs=c.s.executeQuery("SELECT NIF FROM company WHERE CODE='"+CODE+"'");
                if(rs.next()){
                    NIF=rs.getString("NIF");                    
                    }else{
                    codigo=false;
                }         
                
            } catch(Exception e){
                e.printStackTrace();
            }
                    
             
            if(ID>0 && passlenght>7 && nombrelenght>2 && nombre_usuarioleght>4 && nombre_exsistente==0 && codigo){
            try{ Connect c = new Connect();
                 String query = "insert into login values('"+ID+"', '"+nombre_usuario.trim()+"','"+nombre.toLowerCase().trim()+"','"+encrypted_password+"','"+tipo_usuario.trim()+"','"+NIF+"')";
                 
                 c.s.executeUpdate(query);
                 
                 JOptionPane.showMessageDialog(null,"cuenta creada con exito");
                 setVisible(false);
                 new Login();
            }catch (Exception e){
                e.printStackTrace();
            }
            
            }
            if(!codigo){
            JOptionPane.showMessageDialog(null,"Codigo de empresa incorrecto");
            }
        
        }else if(ae.getSource() == volver){
                setVisible(false);
                new Login();
        }
        
    }
    
     public static void main(String[] args){
    new Signup ();
        
    }

}
