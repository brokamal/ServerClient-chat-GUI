package translator;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.darkprograms.speech.translator.GoogleTranslate;


public class ChatClientGUI extends javax.swing.JFrame {

    /**
     * Creates new form ChatClientGUI
     */
    public ChatClientGUI() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAllChat = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSendMessage = new javax.swing.JTextArea();
        jButtonSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client (Server is down)");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jTextAreaAllChat.setColumns(20);
        jTextAreaAllChat.setRows(5);
        jScrollPane1.setViewportView(jTextAreaAllChat);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jTextAreaSendMessage.setColumns(20);
        jTextAreaSendMessage.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSendMessage);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSend, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 369, 398);
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
        try {
            socket = new Socket("localhost", 5678);
            writer = new PrintWriter(socket.getOutputStream(), true);
            scanner = new Scanner(socket.getInputStream());
            setTitle("Client (Connected to the server)");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String input = scanner.nextLine();
                        try {
							jTextAreaAllChat.append("Server : "+GoogleTranslate.translate("id", input)+"\n");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                    }
                }
            });
            t.start();
        } catch (Exception e) {
        }
    }

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String msg = jTextAreaSendMessage.getText();
    	writer.println(msg);
        jTextAreaSendMessage.setText("");
        jTextAreaAllChat.append("Client : "+msg+ "\n");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClientGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClientGUI().setVisible(true);
            }
        });
    }

    
    private javax.swing.JButton jButtonSend;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaAllChat;
    private javax.swing.JTextArea jTextAreaSendMessage;
    
    private Socket socket;
    private PrintWriter writer;
    private Scanner scanner;

}
