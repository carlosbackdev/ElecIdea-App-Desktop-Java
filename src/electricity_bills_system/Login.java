
package electricity_bills_system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;



class RoundedButton extends JButton {

    public RoundedButton(String label) {
        super(label);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
        setFocusPainted(false); 
        setBackground(new Color(86, 94, 100)); 
        setForeground(new Color(220, 220, 220)); 
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(65, 122, 194)); 
                setBorderPainted(true);  
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(new Color(86, 94, 100));  
                setBorderPainted(false);  
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        if (!getModel().isPressed()) {
            g2.setColor(new Color(200, 200, 200, 100)); 
            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 30, 30);
        }

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 

        if (getModel().isPressed()) {
            g2.setColor(new Color(100, 100, 100, 50));
            g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 30, 30);
        }

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }
}



class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        backgroundImage = new ImageIcon(ClassLoader.getSystemResource(fileName)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class Login extends JFrame implements ActionListener{
    RoundedButton login,signup,signup_empresa;
    JTextField cajon_usuario;
    JComboBox tipousu;
    String NIF,ID_USER;
    JPasswordField cajon_contra;
    Login(){
    super("Inicio Sesion Usuario");
        setContentPane(new BackgroundPanel("images/inicio.jpg"));    
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("images/icono_app.png")).getImage());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(45, 5, 25, 5);
        gbc.ipadx = 40;
        Color gris=new Color(210,210,210);
        
        JLabel head = new JLabel("       INICIO DE SESIÓN");
        head.setForeground(gris);
        head.setFont(new Font("Roboto", Font.PLAIN, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(head, gbc);
        
        JLabel label_usuario = new JLabel("Usuario");
        label_usuario.setForeground(gris);
        label_usuario.setFont(new Font("Roboto", Font.PLAIN, 24));
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;

        panel.add(label_usuario, gbc);

        cajon_usuario = new JTextField();
        cajon_usuario.setFont(new Font("Roboto", Font.PLAIN, 14));
        cajon_usuario.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(cajon_usuario, gbc);

        JLabel label_contraseña = new JLabel("Contraseña");
        label_contraseña.setForeground(gris);
        label_contraseña.setFont(new Font("Roboto", Font.PLAIN, 24)); 
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(label_contraseña, gbc);

        cajon_contra = new JPasswordField();
        cajon_contra.setFont(new Font("Roboto", Font.PLAIN, 14));
        cajon_contra.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0; 
        panel.add(cajon_contra, gbc);

        JLabel tipo_usuario = new JLabel("Conectarse");
        tipo_usuario.setForeground(gris);
        tipo_usuario.setFont(new Font("Roboto", Font.PLAIN, 24)); 
        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(tipo_usuario, gbc);

        tipousu = new JComboBox();
        tipousu.addItem("usuario");
        tipousu.addItem("administrador");
        tipousu.addItem("cliente");
        tipousu.setFont(new Font("Roboto", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 0;
        panel.add(tipousu, gbc);
        
        JLabel margen = new JLabel();
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(margen, gbc);
        
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false); 
        gbc.gridx = 0;
        gbc.gridy = 5; 
        gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; 
        gbc.anchor = GridBagConstraints.CENTER; 
        panel.add(panelBotones, gbc);
                
        
        login  = new RoundedButton("     Entrar     ");
        login.setFont(new Font("Roboto", Font.PLAIN, 18)); 
        login.addActionListener(this);
        panelBotones.add(login);
        panelBotones.add(login, BorderLayout.WEST);

        signup = new RoundedButton("     Registro     ");
        signup.setFont(new Font("Roboto", Font.PLAIN, 18)); 
        signup.addActionListener(this);
        panelBotones.add(signup);
        panelBotones.add(signup, BorderLayout.EAST);
        
        signup_empresa = new RoundedButton("Registrar Empresa");
        signup_empresa.setFont(new Font("Roboto", Font.PLAIN, 18));
        gbc.gridy = 6;
        gbc.gridx = 0;
        signup_empresa.addActionListener(this);
        panel.add(signup_empresa, gbc);
        
        add(panel, BorderLayout.PAGE_START);
        
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setSize(900, 800);
        setLocationRelativeTo(null);
        setVisible(true);    
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == login){
            String username= cajon_usuario.getText().trim();
            username = username.toLowerCase().trim();
            String password= cajon_contra.getText().trim();
            String encrypted_password = contraseña.encryptPassword(password);      
            String user=(String) tipousu.getSelectedItem();
            
            try{
                Connect c=new Connect();
                String query="select * from login where USUARIO ='"+username+"'and PASSWORD='"+encrypted_password+"' and USER='"+user+"'";
                
               ResultSet rs = c.s.executeQuery(query);
               if(rs.next()){
                   NIF=rs.getString("NIF");
                   ID_USER=rs.getString("ID");
                   setVisible(false);
                   new Project(NIF,ID_USER);
               } else{
                   JOptionPane.showMessageDialog(null,"Datos incorrectos");
                   cajon_usuario.setText("");
                   cajon_contra.setText("");
               }
                
                
            }catch(Exception e){
                e.printStackTrace();
            }
            
        
        }else if(ae.getSource() == signup){
            setVisible(false);
            new Signup();
        }else if(ae.getSource() == signup_empresa){
            setVisible(false);
            new SignupCompany();
        }
        
    }
    
    public static void main(String[] args){
    new Login ();
        
    }
}
