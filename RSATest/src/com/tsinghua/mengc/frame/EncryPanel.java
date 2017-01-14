package com.tsinghua.mengc.frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tsinghua.mengc.math.MyBigInteger;
import com.tsinghua.mengc.util.PrimeUtil;

public class EncryPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JPanel ltPanel=new JPanel();
	private JPanel rtPanel=new JPanel();
	private JPanel lbPanel=new JPanel();
	private JPanel rbPanel=new JPanel();
	
	private JComboBox<String>rsaLevel=new JComboBox<>(new String[]{"RSA-512(not safe)","RSA-1024(recommend)","RSA-2048"});
	private JTextArea valueN=new JTextArea();
	private JTextArea valueB=new JTextArea();
	private JTextArea valueA=new JTextArea();
	private JTextArea valueP=new JTextArea();
	private JTextArea valueQ=new JTextArea();
	private JTextArea message=new JTextArea();
	private JTextArea encryptedMessage=new JTextArea();
	MyBigInteger p=null;
	MyBigInteger q=null;
	MyBigInteger n=null;
	MyBigInteger b=null;
	MyBigInteger a=null;
	
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
		//button添加点击事件
		generate.addActionListener(new GenerateKeyListener());
		
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
		valueN.setLineWrap(true);
		valueN.setEditable(false);
		JScrollPane scrolN=new JScrollPane(valueN);
		scrolN.setBounds(13,30,450,100);
		scrolN.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolN.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPanel.add(scrolN);
		JLabel valueBL=new JLabel("Value B:");
		valueBL.setBounds(10,150,100,15);
		inputPanel.add(valueBL);
		valueB.setLineWrap(true);
		valueB.setEditable(false);
		JScrollPane scrolB=new JScrollPane(valueB);
		scrolB.setBounds(13,170,450,100);
		scrolB.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolB.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		message.setLineWrap(true);
		JScrollPane scrol=new JScrollPane(message);
		scrol.setBounds(13,10,450,300);
		scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPanel.add(scrol);
		JButton loadMessage=new JButton("Load Message");
		loadMessage.addActionListener(new LoadMessageListener());
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
		valueA.setLineWrap(true);
		valueA.setEditable(false);
		JScrollPane scrolA=new JScrollPane(valueA);
		scrolA.setBounds(13,30,450,70);
		scrolA.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolA.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPanel.add(scrolA);
		
		JLabel valuePL=new JLabel("Value P:");
		valuePL.setBounds(10,120,100,15);
		inputPanel.add(valuePL);
		valueP.setLineWrap(true);
		valueP.setEditable(false);
		JScrollPane scrolP=new JScrollPane(valueP);
		scrolP.setBounds(13,140,450,70);
		scrolP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPanel.add(scrolP);
		
		JLabel valueQL=new JLabel("Value Q:");
		valueQL.setBounds(10,230,100,15);
		inputPanel.add(valueQL);
		valueQ.setLineWrap(true);
		valueQ.setEditable(false);
		JScrollPane scrolQ=new JScrollPane(valueQ);
		scrolQ.setBounds(13,250,450,70);
		scrolQ.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrolQ.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		encryptedMessageB.addActionListener(new EncryptMessageListener());
		encryptedMessageB.setBounds(15,10,150,25);
		inputPanel.add(encryptedMessageB);
		
		JButton saveMessageB=new JButton("Save Message");
		saveMessageB.setBounds(330,10,130,25);
		inputPanel.add(saveMessageB);
		encryptedMessage.setLineWrap(true);
		JScrollPane scrol=new JScrollPane(encryptedMessage);
		scrol.setBounds(13,45,450,300);
		scrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrol.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputPanel.add(scrol);
		inputPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		rbPanel.add(inputPanel);
	}
	
	
	class GenerateKeyListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String selectLevel=(String) rsaLevel.getSelectedItem();
			String slevel=selectLevel.substring(selectLevel.indexOf("-")+1,
					selectLevel.indexOf("(")==-1 ?selectLevel.length() :selectLevel.indexOf("(") );
			int level=Integer.parseInt(slevel)/2;
			System.out.println(level);
			
			p=PrimeUtil.generateBigPrime(level);
			q=null;
			do{
				q=PrimeUtil.generateBigPrime(level);
			}while(p.equals(q));
			n=p.multiply(q);
			valueN.setText(n.toString());
			valueP.setText(p.toString());
			valueQ.setText(q.toString());
			//计算n的欧拉函数
			MyBigInteger phi=n.subtract(p).subtract(q).add(MyBigInteger.ONE);
			
			for(int i=0;i<MyBigInteger.PRIMES.length;i++){
				if(PrimeUtil.gcd(b=MyBigInteger.PRIMES[i],phi).equals(MyBigInteger.ONE)){
					break;
				}
			}
			a=PrimeUtil.inverse(b, phi);
			valueA.setText(a.toString());
			valueB.setText(b.toString());
		}
		
	}
	
	
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
	class EncryptMessageListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(n==null){
				JOptionPane.showConfirmDialog(encryptedMessage,"还没有生成");
				return ;
			}
			String orimessage=message.getText();
			String encrypt=PrimeUtil.encryptString(orimessage,n, b);
			encryptedMessage.setText(encrypt);
		}
		
	}
}
