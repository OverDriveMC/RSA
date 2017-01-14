package com.tsinghua.mengc.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel encryPanel=new EncryPanel();
	private JPanel decryPanel =new DecryPanel();
	
	public MainFrame() {
		this.setTitle("RSAHelper");
		this.setBounds(500, 100,1000,900);
		
		addComponent();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void addComponent(){
		JTabbedPane tabbedPane=new JTabbedPane(JTabbedPane.TOP);
		this.add(tabbedPane);
		//添加两个tab页
		tabbedPane.add("Encryption", encryPanel);
		tabbedPane.add("Decryption", decryPanel);
		
	}
}
