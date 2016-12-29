package com.tsinghua.mengc;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DecryPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel topPanel=new JPanel();
	private JPanel bottomPanel=new JPanel();
	private JPanel lbPanel=new JPanel();
	private JPanel rbPanel=new JPanel();
	
	private JTextArea valueA=new JTextArea();
	private JTextArea valueN=new JTextArea();
	private JTextArea decryptedMessage=new JTextArea();
	private JTextArea message=new JTextArea();
	
	public DecryPanel(){
		this.setLayout(new GridLayout(2, 1));
		this.add(topPanel);
		this.add(bottomPanel);
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.add(lbPanel);
		bottomPanel.add(rbPanel);
		initTopPanel();
		initlbPanel();
		initlrPanel();
	}
	/*
	 * 初始化右下部Panel
	 */
	private void initlrPanel() {
		rbPanel.setLayout(null);
		JLabel messageL=new JLabel("Decrypted Text");
		messageL.setBounds(5,5,100,25);
		rbPanel.add(messageL);
		JPanel inputPanel=new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBounds(5,35,480,365);
		JButton decryptMessageB=new JButton("Decrypt Message");
		decryptMessageB.setBounds(15,10,130,25);
		inputPanel.add(decryptMessageB);
		
		JButton saveDecryptedTextB=new JButton("Save Decrypted Text");
		saveDecryptedTextB.setBounds(330,10,130,25);
		inputPanel.add(saveDecryptedTextB);
		
		JScrollPane scrol=new JScrollPane(decryptedMessage);
		scrol.setBounds(13,45,450,300);
		scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputPanel.add(scrol);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		rbPanel.add(inputPanel);
		
	}
	/**
	 * 初始化左下部Panel
	 */
	private void initlbPanel() {
		lbPanel.setLayout(null);
		JLabel messageL=new JLabel("Encrypted Text");
		messageL.setBounds(5,5,100,25);
		lbPanel.add(messageL);
		JPanel inputPanel=new JPanel();
		inputPanel.setLayout(null);
		inputPanel.setBounds(5,35,480,365);
		JButton loadEncryptedTextB=new JButton("Load Encrypted Text");
		loadEncryptedTextB.setBounds(15,10,130,25);
		inputPanel.add(loadEncryptedTextB);
		JScrollPane scrol=new JScrollPane(message);
		scrol.setBounds(13,45,450,300);
		scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputPanel.add(scrol);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		lbPanel.add(inputPanel);
	}
	/**
	 * 初始化上部Panel
	 */
	public void initTopPanel(){
		topPanel.setLayout(null);
		JLabel levelL=new JLabel("Private Key");
		levelL.setBounds(5,5,100,25);
		topPanel.add(levelL);
		JPanel inputPanel=new JPanel();
		inputPanel.setBounds(5, 30, 960, 360);
		inputPanel.setLayout(null);
		JLabel valueAL=new JLabel("Value A:");
		valueAL.setBounds(10,15,100,15);
		inputPanel.add(valueAL);
		JButton loadPrK=new JButton("Load Private Key");
		loadPrK.setBounds(800,10,130,25);
		inputPanel.add(loadPrK);
		JScrollPane scrolA=new JScrollPane(valueA);
		scrolA.setBounds(13,50,930,120);
		scrolA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolA);
		
		JLabel valueNL=new JLabel("Value N:");
		valueNL.setBounds(10,180,800,15);
		inputPanel.add(valueNL);
		JButton loadPK=new JButton("Load Public Key");
		loadPK.setBounds(800,175,130,25);
		inputPanel.add(loadPK);
		JScrollPane scrolN=new JScrollPane(valueN);
		scrolN.setBounds(13,215,930,120);
		scrolN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolN);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		topPanel.add(inputPanel);
	}
}
