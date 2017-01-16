package com.tsinghua.mengc.frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.codec.binary.Base64;

import com.tsinghua.mengc.math.MyBigInteger;
import com.tsinghua.mengc.util.PrimeUtil;

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
	
	MyBigInteger n=null;
	MyBigInteger b=null;
	MyBigInteger a=null;
	
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
		decryptMessageB.addActionListener(new DecryptListener());
		decryptMessageB.setBounds(15,10,150,25);
		inputPanel.add(decryptMessageB);
		
		JButton saveDecryptedTextB=new JButton("Save Decrypted Text");
		saveDecryptedTextB.addActionListener(new SaveMessageListener());
		saveDecryptedTextB.setBounds(300,10,160,25);
		inputPanel.add(saveDecryptedTextB);
		
		JScrollPane scrol=new JScrollPane(decryptedMessage);
		decryptedMessage.setLineWrap(true);
		decryptedMessage.setEditable(false);
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
		loadEncryptedTextB.addActionListener(new LoadMessageListener());
		loadEncryptedTextB.setBounds(15,10,150,25);
		inputPanel.add(loadEncryptedTextB);
		JScrollPane scrol=new JScrollPane(message);
		message.setLineWrap(true);
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
		loadPrK.addActionListener(new LoadPrivateKeyListener());
		loadPrK.setBounds(800,10,130,25);
		inputPanel.add(loadPrK);
		JScrollPane scrolA=new JScrollPane(valueA);
		valueA.setLineWrap(true);
		valueA.setEditable(false);
		scrolA.setBounds(13,50,930,120);
		scrolA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolA);
		
		JLabel valueNL=new JLabel("Value N:");
		valueNL.setBounds(10,180,800,15);
		inputPanel.add(valueNL);
		JButton loadPK=new JButton("Load Public Key");
		loadPK.addActionListener(new LoadPublicKeyListener());
		loadPK.setBounds(800,175,130,25);
		inputPanel.add(loadPK);
		JScrollPane scrolN=new JScrollPane(valueN);
		valueN.setLineWrap(true);
		valueN.setEditable(false);
		scrolN.setBounds(13,215,930,120);
		scrolN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		inputPanel.add(scrolN);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		topPanel.add(inputPanel);
	}
	
	class LoadPublicKeyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser=new JFileChooser();
			int result=chooser.showOpenDialog(message);
			if(result==JFileChooser.APPROVE_OPTION){
				File messageFile=chooser.getSelectedFile();
				try(BufferedReader br=new BufferedReader(new FileReader(messageFile))){
					StringBuilder sb=new StringBuilder();
					String line=null;
					while((line=br.readLine())!=null){
						sb.append(line);
					}
					valueN.setText(sb.toString());
					String str=new String(Base64.decodeBase64(sb.toString()));
					String tarr[]=str.split("#");
					if(tarr.length!=2){
						JOptionPane.showMessageDialog(valueN, "选择公钥不合法");
						return ;
					}
					n=new MyBigInteger(tarr[0]);
					b=new MyBigInteger(tarr[1]);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(chooser, "所选文件不合法!");
				}
				
			}
		}
		
	}
	
	class LoadPrivateKeyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser=new JFileChooser();
			int result=chooser.showOpenDialog(message);
			if(result==JFileChooser.APPROVE_OPTION){
				File messageFile=chooser.getSelectedFile();
				try(BufferedReader br=new BufferedReader(new FileReader(messageFile))){
					StringBuilder sb=new StringBuilder();
					String line=null;
					while((line=br.readLine())!=null){
						sb.append(line);
					}
					valueA.setText(sb.toString());
					String str=new String(Base64.decodeBase64(sb.toString()));
					String tarr[]=str.split("#");
					if(tarr.length!=2){
						JOptionPane.showMessageDialog(valueN, "选择私钥不合法");
						return ;
					}
					n=new MyBigInteger(tarr[0]);
					a=new MyBigInteger(tarr[1]);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(chooser, "所选文件不合法!");
				}
				
			}
		}
		
	}
	
	/**
	 * 加载编码后的信息
	 */
	class LoadMessageListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser=new JFileChooser();
			int result=chooser.showOpenDialog(message);
			if(result==JFileChooser.APPROVE_OPTION){
				File messageFile=chooser.getSelectedFile();
				try(BufferedReader br=new BufferedReader(new FileReader(messageFile))){
					StringBuilder sb=new StringBuilder();
					String line=null;
					while((line=br.readLine())!=null){
						sb.append(line);
					}
					message.setText(sb.toString());
				}catch(Exception ex){
					JOptionPane.showMessageDialog(chooser, "所选文件不合法!");
				}
				
			}
		}
		
	}
	
	class DecryptListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String publicKey=valueN.getText();
			String pun=null;
			if(publicKey!=null && !publicKey.trim().equals("")){
				pun=new String(Base64.decodeBase64(publicKey)).split("#")[0];
			}
			String privateKey=valueA.getText();
			String prn=new String(Base64.decodeBase64(privateKey)).split("#")[0];
			if(pun!=null && !pun.equals(prn)){
				System.out.println(pun);
				JOptionPane.showMessageDialog(valueN, "选择公钥私钥不配套!");
				return ;
			}
			String toDecryptMessage=message.getText();
			String decryptMessage=PrimeUtil.decryptString(toDecryptMessage, n, a);
			decryptedMessage.setText(decryptMessage);
		}
	}
	class SaveMessageListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser=new JFileChooser();
			int result=chooser.showSaveDialog(message);
			if(result==JFileChooser.APPROVE_OPTION){
				try(PrintWriter pw=new PrintWriter(chooser.getSelectedFile())){
					String decryptMessage=decryptedMessage.getText();
					pw.println(decryptMessage);
					JOptionPane.showMessageDialog(decryptedMessage, "保存成功!");
				}catch(Exception ex){
					JOptionPane.showMessageDialog(decryptedMessage, "保存失败!");
				}
			}
		}
		
	}
	
}
