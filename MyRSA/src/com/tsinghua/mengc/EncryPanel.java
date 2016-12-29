package com.tsinghua.mengc;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class EncryPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JPanel ltPanel=new JPanel();
	private JPanel rtPanel=new JPanel();
	private JPanel lbPanel=new JPanel();
	private JPanel rbPanel=new JPanel();
	
	private JComboBox<String>rsaLevel=new JComboBox<>(new String[]{"RSA-512(not safe)","RSA-1024(recommend)"});
	private JTextArea valueN=new JTextArea();
	private JTextArea valueB=new JTextArea();
	private JTextArea valueA=new JTextArea();
	private JTextArea valueP=new JTextArea();
	private JTextArea valueQ=new JTextArea();
	private JTextArea message=new JTextArea();
	private JTextArea encryptedMessage=new JTextArea();
	
	public EncryPanel(){
		this.setLayout(new GridLayout(2, 2));
		this.add(ltPanel);
		this.add(rtPanel);
		this.add(lbPanel);
		this.add(rbPanel);
		initltPanel();
		initrtPanel();
		initlbPanel();
		initrbPanel();
	}
	/**
	 * 初始化左上的Panel 
	 */
	public void initltPanel(){
		ltPanel.setLayout(null);
		JLabel levelL=new JLabel("Encryption Level:");
		levelL.setBounds(5,5,100,25);
		ltPanel.add(levelL);
		rsaLevel.setBounds(105,5,150,25);
		ltPanel.add(rsaLevel);
		JButton generate=new JButton("Generate Key");
		generate.setBounds(300,5,150,25);
		ltPanel.add(generate);
		JLabel pklabel=new JLabel("Public Key");
		pklabel.setBounds(5, 35, 100, 15);
		ltPanel.add(pklabel);
		
		JPanel inputPanel=new JPanel();
		inputPanel.setBounds(0, 50, 480, 350);
		inputPanel.setLayout(null);
		JLabel valueNL=new JLabel("Value N:");
		valueNL.setBounds(10,10,100,15);
		inputPanel.add(valueNL);
		JScrollPane scrolN=new JScrollPane(valueN);
		scrolN.setBounds(13,30,450,100);
		scrolN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolN);
		JLabel valueBL=new JLabel("Value B:");
		valueBL.setBounds(10,150,100,15);
		inputPanel.add(valueBL);
		JScrollPane scrolB=new JScrollPane(valueB);
		scrolB.setBounds(13,170,450,100);
		scrolB.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolB);
		JButton savePK=new JButton("Save Public Key");
		savePK.setBounds(330,300,130,25);
		inputPanel.add(savePK);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		ltPanel.add(inputPanel);
	}
	/**
	 * 初始化右上方的Panel
	 */
	public void initrtPanel(){
		rtPanel.setLayout(null);
		JLabel messageL=new JLabel("Message");
		messageL.setBounds(5,5,100,25);
		rtPanel.add(messageL);
		JPanel inputPanel=new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBounds(5,35,480,365);
		JScrollPane scrol=new JScrollPane(message);
		scrol.setBounds(13,10,450,300);
		scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputPanel.add(scrol);
		JButton loadMessage=new JButton("Load Message");
		loadMessage.setBounds(330,320,130,25);
		inputPanel.add(loadMessage);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		rtPanel.add(inputPanel);
	}
	/**
	 * 初始化左下方的Panel
	 */
	public void initlbPanel(){
		lbPanel.setLayout(null);
		JLabel prklabel=new JLabel("Private Key");
		prklabel.setBounds(5, 5, 100, 15);
		lbPanel.add(prklabel);
		
		JPanel inputPanel=new JPanel();
		inputPanel.setBounds(0, 30, 480, 380);
		inputPanel.setLayout(null);
		JLabel valueAL=new JLabel("Value A:");
		valueAL.setBounds(10,10,100,15);
		inputPanel.add(valueAL);
		JScrollPane scrolA=new JScrollPane(valueA);
		scrolA.setBounds(13,30,450,70);
		scrolA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolA);
		
		JLabel valuePL=new JLabel("Value P:");
		valuePL.setBounds(10,120,100,15);
		inputPanel.add(valuePL);
		JScrollPane scrolP=new JScrollPane(valueP);
		scrolP.setBounds(13,140,450,70);
		scrolP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolP);
		
		JLabel valueQL=new JLabel("Value Q:");
		valueQL.setBounds(10,230,100,15);
		inputPanel.add(valueQL);
		JScrollPane scrolQ=new JScrollPane(valueQ);
		scrolQ.setBounds(13,250,450,70);
		scrolQ.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolQ);
		
		JButton savePK=new JButton("Save Private Key");
		savePK.setBounds(330,345,130,25);
		inputPanel.add(savePK);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		lbPanel.add(inputPanel);
	}
	/**
	 * 初始化右下方的Panel
	 */
	public void initrbPanel(){
		rbPanel.setLayout(null);
		JLabel messageL=new JLabel("Encrypted Message");
		messageL.setBounds(5,5,100,25);
		rbPanel.add(messageL);
		JPanel inputPanel=new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBounds(5,35,480,365);
		JButton encryptedMessageB=new JButton("Encrypt Message");
		encryptedMessageB.setBounds(15,10,130,25);
		inputPanel.add(encryptedMessageB);
		
		JButton saveMessageB=new JButton("Save Message");
		saveMessageB.setBounds(330,10,130,25);
		inputPanel.add(saveMessageB);
		
		JScrollPane scrol=new JScrollPane(encryptedMessage);
		scrol.setBounds(13,45,450,300);
		scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputPanel.add(scrol);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		rbPanel.add(inputPanel);
	}
}
