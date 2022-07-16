import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class ChatClient1 extends JFrame implements Runnable
{

	
	private static final long serialVersionUID = 1L;
	Socket socket;
	JTextArea ta;
	JButton send,logout;
	JTextField tf;
	
	Thread thread;
	
	DataInputStream din;
	DataOutputStream dout;
	
	String LoginName;
	
	ChatClient1(String login) throws UnknownHostException, IOException
	{
		super(login);
		LoginName =login;
     		
		ta=new JTextArea(18,50);
		tf=new JTextField(50);
		
		send=new JButton("Send");
		
		logout=new JButton("Logout");
		
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					dout.writeUTF(LoginName + " " + "DATA " +tf.getText().toString());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});

		
		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					dout.writeUTF(LoginName + " " + "LOGOUT" );
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		
		
		socket= new Socket("localhost", 5217);
		
		din=new DataInputStream(socket.getInputStream());  //taking input from socket
		dout=new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(LoginName);
		dout.writeUTF(LoginName +" "+ "LOGIN");
		
		thread=new Thread(this);
		thread.start();
		setup();
	}
	private void setup() {
		// TODO Auto-generated method stub
		setSize(700,500);                    //for setting size
		JPanel panel=new JPanel();           // creation of new Panel
		panel.add(new JScrollPane(ta));      //Adding text area 
		panel.add(tf);
		panel.add(send);             //adding sending button
		panel.add(logout);          //adding logout button
		
		add(panel);                          //adding panel to the JFrame 
		
		setVisible(true);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				ta.append("\n" + din.readUTF());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
   public static void main(String args[]) throws UnknownHostException, IOException 
   {
	   ChatClient1 client = new ChatClient1("User2"); 
   }
}

