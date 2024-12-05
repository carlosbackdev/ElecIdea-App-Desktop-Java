
package electricity_bills_system;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
    RoundedButton volver,crear;
    Choice tipousu;
    JTextField cajon_usu,cajon_nombre,cajon_passwd;
    Signup(){
        super("Crear Cuenta");
        setContentPane(new BackgroundPanel("images/login.jpg"));
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(45, 15, 25, 15);

        JLabel head = new JLabel("CREAR CUENTA");
        head.setForeground(Color.WHITE);
        head.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);

        JLabel tipo_usuario = new JLabel("Registrarse como:");
        tipo_usuario.setForeground(Color.WHITE);
        tipo_usuario.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        panel.add(tipo_usuario, gbc);

        tipousu = new Choice();
        tipousu.add("cliente");
        tipousu.add("administrador");
        tipousu.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridx = 1;
        panel.add(tipousu, gbc);

        JLabel nombreusu = new JLabel("Usuario");
        gbc.anchor = GridBagConstraints.WEST;
        nombreusu.setForeground(Color.WHITE);
        nombreusu.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(nombreusu, gbc);

        cajon_usu = new JTextField();
        cajon_usu.setFont(new Font("Roboto", Font.PLAIN, 18));
        cajon_usu.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_usu, gbc);
        
        JLabel nombrecompleto = new JLabel("Nombre y Apellido");
        nombrecompleto.setForeground(Color.WHITE);
        nombrecompleto.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(nombrecompleto, gbc);

        cajon_nombre = new JTextField();
        cajon_nombre.setFont(new Font("Roboto", Font.PLAIN, 18));
        cajon_nombre.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_nombre, gbc);
        
        JLabel contraseña = new JLabel("Contraseña");
        contraseña.setForeground(Color.WHITE);
        contraseña.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(contraseña, gbc);

        cajon_passwd = new JTextField();
        cajon_passwd.setFont(new Font("Roboto", Font.PLAIN, 18));
        cajon_passwd.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_passwd, gbc);
        
        crear = new RoundedButton("Crear Cuenta");
        crear.setBackground(new Color(222, 239, 255));
        crear.setForeground(Color.BLACK);
        crear.setFont(new Font("Roboto", Font.PLAIN, 18)); 
        gbc.gridy = 5;
        gbc.gridx = 0;
        crear.addActionListener(this);
        panel.add(crear, gbc);

        volver = new RoundedButton("Volver");
        volver.setBackground(new Color(222, 239, 255));
        volver.setForeground(Color.BLACK);
        volver.setFont(new Font("Roboto", Font.PLAIN, 18)); 
        gbc.gridx = 1;
        volver.addActionListener(this);
        panel.add(volver, gbc);

        add(panel, BorderLayout.PAGE_START);
        
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == crear){
            String tipo_usuario = tipousu.getSelectedItem();
            String nombre_usuario = cajon_usu.getText();
            nombre_usuario=nombre_usuario.toLowerCase();
            int nombre_usuarioleght=nombre_usuario.length();
            String nombre = cajon_nombre.getText();
            nombre=nombre.toLowerCase();
            int nombrelenght=nombre.length();
            String password = cajon_passwd.getText();
            int passlenght=password.length();
            int ID=0;
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
             
            if(ID>0 && passlenght>7 && nombrelenght>2 && nombre_usuarioleght>4 && nombre_exsistente==0){
            try{ Connect c = new Connect();
                 String query = "insert into login values("+ID+", '"+nombre_usuario+"','"+nombre+"','"+password+"','"+tipo_usuario+"')";
                 
                 c.s.executeUpdate(query);
                 
                 JOptionPane.showMessageDialog(null,"cuenta creada con exito");
                 setVisible(false);
                 new Login();
            }catch (Exception e){
                e.printStackTrace();
            }
            
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
