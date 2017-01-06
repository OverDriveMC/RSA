package com.tsinghua.mengc.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.tsinghua.mengc.math.MyBigInteger;

public class TestMyBigInteger {
	String str="12345678987654321";
	MyBigInteger num1=new MyBigInteger(str);
	MyBigInteger num2=new MyBigInteger(str);
	
	@Test
	public void multiply(){
		Random random=new Random();
		List<String>strs=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder(10);
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		for(int i=0;i<100;i++){
			
			MyBigInteger bi1=new MyBigInteger(strs.get(i));
			BigInteger bi2=new BigInteger(strs.get(i));
			System.out.println(bi1);
			if(!bi1.multiply(bi1).toString().equals(bi2.multiply(bi2).toString())){
				System.out.println("i:"+i+"-----------------------------------");
			}
		}
		
	}
	
	@Test
	public void subtractTest(){
		
		Random random=new Random();
		List<String>strs=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		List<String>strs2=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<15;j++){
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for(int i=0;i<100;i++){	
			MyBigInteger bi1=new MyBigInteger(strs.get(i));
			BigInteger bi2=new BigInteger(strs.get(i));
			
			
			if(!bi1.subtract(new MyBigInteger(strs2.get(i))).toString().equals(
					bi2.subtract(new BigInteger(strs2.get(i))).toString()
					)){
				System.out.println("i:"+i+"-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
			}
		}
	}
	@Test
	public void compareToTest(){
		Random random=new Random();
		List<String>strs=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		List<String>strs2=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for(int i=0;i<100;i++){	
			MyBigInteger bi1=new MyBigInteger(strs.get(i));
			BigInteger bi2=new BigInteger(strs.get(i));
			
			
			if(bi1.compareTo(new MyBigInteger(strs2.get(i)))!=
					bi2.compareTo(new BigInteger(strs2.get(i)))
					){
				System.out.println("i:"+i+"-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
			}
		}
		
	}
	@Test
	public void remainderTest(){
		Random random=new Random();
		List<String>strs=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		List<String>strs2=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for(int i=0;i<100;i++){	
			MyBigInteger bi1=new MyBigInteger(strs.get(i));
			BigInteger bi2=new BigInteger(strs.get(i));
			if(!bi1.remainder(new MyBigInteger(strs2.get(i))).toString().equals(
					bi2.remainder(new BigInteger(strs2.get(i))).toString()
					)){
				System.out.println("i:"+i+"-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
			}
		}
	}
	
	/**
	 * 测试大数快速幂
	 */
	@Test
	public void powTest(){
		for(int i=1;i<=100;i++){
			MyBigInteger big1=new MyBigInteger(i+"");
			BigInteger big2=new BigInteger(i+"");
			String res1=MyBigInteger.quickPow(big1,100).toString();
			String res2=big2.pow(100).toString();
			if(!res1.equals(res2)){
				System.out.println(i);
				System.out.println(res1);
				System.out.println(res2);
			}
		}
	}
	/**
	 * 测试大数快速幂取模
	 */
	@Test
	public void powModTest(){
		String mod="65537";
		for(int i=1;i<=100;i++){
			MyBigInteger big1=new MyBigInteger(i+"");
			BigInteger big2=new BigInteger(i+"");
			String res1=MyBigInteger.quickpowmod(big1,100,new MyBigInteger(mod)).toString();
			String res2=big2.pow(100).remainder(new BigInteger(mod)).toString();
			if(!res1.equals(res2)){
				System.out.println(i);
				System.out.println(res1);
				System.out.println(res2);
			}
		}
	}
	@Test
	public void addTest(){
		Random random=new Random();
		List<String>strs=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<20;j++){
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		List<String>strs2=new ArrayList<>();
		for(int i=0;i<100;i++){
			StringBuilder s=new StringBuilder();
			for(int j=0;j<15;j++){
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for(int i=0;i<100;i++){	
			MyBigInteger bi1=new MyBigInteger(strs.get(i));
			BigInteger bi2=new BigInteger(strs.get(i));
			
			
			if(!bi1.add(new MyBigInteger(strs2.get(i))).toString().equals(
					bi2.add(new BigInteger(strs2.get(i))).toString()
					)){
				System.out.println("i:"+i+"-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
			}
		}
	}
	
}
