package translator;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.darkprograms.speech.translator.GoogleTranslate;


public class ChatServerGUI extends javax.swing.JFrame {

    /**
     * Creates new form ChatServerGUI
     */
    public ChatServerGUI() {
        initComponents();
    }

  
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAllChat = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButtonSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaSendMessage = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server (Client is not connected)");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(15, 15));

        jTextAreaAllChat.setColumns(20);
        jTextAreaAllChat.setRows(5);
        jScrollPane1.setViewportView(jTextAreaAllChat);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        }); 
        jPanel1.add(jButtonSend, java.awt.BorderLayout.LINE_END); 

        jTextAreaSendMessage.setColumns(20);
        jTextAreaSendMessage.setRows(5);
        jScrollPane2.setViewportView(jTextAreaSendMessage);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 369, 398);
    }

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String msg = jTextAreaSendMessage.getText();
        jTextAreaAllChat.append("Server : "+msg+ "\n");
     
		writer.println(msg);		
        
        jTextAreaSendMessage.setText("");
    
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        // TODO add your handling code here:
        try {
            serverSocket = new ServerSocket(5678);
            socket = serverSocket.accept();
            setTitle("Connected to "+socket.getInetAddress().getHostAddress()+":"+socket.getPort());
            writer = new PrintWriter(socket.getOutputStream(),true);
            scanner = new Scanner(socket.getInputStream());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        String input = scanner.nextLine();
                        jTextAreaSendMessage.setText("");
                        try {
							jTextAreaAllChat.append("Client : "+GoogleTranslate.translate("en", input)+"\n");
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
    public static void main(String args[]) {
       
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatServerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatServerGUI().setVisible(true);
            }
        });
    }

 
    private javax.swing.JButton jButtonSend;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaAllChat;
    private javax.swing.JTextArea jTextAreaSendMessage;
    
    private ServerSocket serverSocket;
    private Socket socket;
    private PrintWriter writer;
    private Scanner scanner;
}
