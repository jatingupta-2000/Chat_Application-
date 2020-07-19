package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Client extends JFrame implements ActionListener {

	JPanel p1;
	JTextField t1;
	JButton b1;
	static JTextArea a1;

	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;

	Client() {

		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(7, 94, 84));
		p1.setBounds(0, 0, 450, 70);
		add(p1);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("demo//icons//3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);

		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});

		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("demo//icons//2.png"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);

		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("demo//icons//attachment.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		JLabel l5 = new JLabel(i9);
		l5.setBounds(350, 20, 35, 30);
		p1.add(l5);
		l5.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				loadFile();
			}
		});


		ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("demo//icons//3icon.png"));
		Image i15 = i14.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
		ImageIcon i16 = new ImageIcon(i15);
		JLabel l7 = new JLabel(i16);
		l7.setBounds(410, 20, 13, 25);
		p1.add(l7);

		JLabel l3 = new JLabel("Ram");
		l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 15, 100, 18);
		p1.add(l3);

		JLabel l4 = new JLabel("Active Now");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 35, 100, 20);
		p1.add(l4);

		a1 = new JTextArea();
		a1.setBounds(5, 75, 440, 570);
		a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		a1.setEditable(false);
		a1.setLineWrap(true);
		a1.setWrapStyleWord(true);
	    JScrollPane scrollPane = new JScrollPane(a1);
	    scrollPane.setBounds(5, 75, 440, 570);
		add(scrollPane);



		t1 = new JTextField();
		t1.setBounds(5, 655, 310, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		add(t1);

		b1 = new JButton("Send");
		b1.setBounds(320, 655, 123, 40);
		b1.setBackground(new Color(7, 94, 84));
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		b1.addActionListener(this);
		add(b1);

		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setSize(450, 700);
		setLocation(1000, 50);
		setUndecorated(true);
		setVisible(true);
	}

	public void loadFile() {
		try {
			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				String filepath = f.getPath();
				try {
					BufferedReader br = new BufferedReader(new FileReader(filepath));
					String s1 = "", s2 = "";
					while ((s1 = br.readLine()) != null) {
						s2 += s1 + "\n";
					}
					sendFileContent(s2);
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println(e);

		}

	}

	public void sendFileContent(String s2) {
		try {
			a1.setText(a1.getText() + "\n\t\t\t" + s2);
			dout.writeUTF(s2);
			dout.flush();
			t1.setText("");
		} catch (Exception e) {
			System.out.println(e);

		}

	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			try {
				String out = t1.getText();
				a1.setText(a1.getText() + "\n\t\t\t" + out);
				dout.writeUTF(out);
				dout.flush();
				t1.setText("");
			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}

	public static void main(String[] args) {
		new Client().setVisible(true);

		try {
			s = new Socket("localhost", 3333);

			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());

			String msginput = "";

			while (true) {
				msginput = din.readUTF();
				a1.setText(a1.getText() + "\n" + msginput);
			}

		} catch (Exception e) {
			System.out.println(e);

		}
	}
}