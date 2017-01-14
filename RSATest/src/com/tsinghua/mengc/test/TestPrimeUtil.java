package com.tsinghua.mengc.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.tsinghua.mengc.math.MyBigInteger;
import com.tsinghua.mengc.util.PrimeUtil;

public class TestPrimeUtil {
	Random random=new Random();
	static final int BASE=1<<30;
	static final int BASE2=16;
	@Test
	public void isPrimeTest(){
		Random random=new Random();
		BigInteger b=BigInteger.probablePrime(512,random);
		System.out.println(b.toString(16));
		MyBigInteger mb=new MyBigInteger(b.toString(16));
		long time1=System.currentTimeMillis();
		System.out.println(PrimeUtil.isPrime(mb, 512));
		long time2=System.currentTimeMillis();
		System.out.println(time2-time1);
		
		String s="a92859aa5c6dbb2b3a33323184c12e6a218d09b3eec1d2255605bb25978eb31b26809be9ace580f954010619ce2c8f31fba7056606321eae3fcfb9b64c75ba8bb8f68efbaa15219e19a0af08e93ff94527b6078d6892776f535384475bd9d4086824bd43fd6f360ed1253b064dfc22cc62e7f74bdd7479f1a9aad06f6b804d0b";
		BigInteger bi=new BigInteger(s,16);
		System.out.println(bi.isProbablePrime(100));
	}
	
	@Test
	public void gcdTest(){
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < 2000; j++) {
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		List<String> strs2 = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < 1500; j++) {
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			MyBigInteger bi11=new MyBigInteger(strs2.get(i));
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			BigInteger bi22 = new BigInteger(strs2.get(i),BASE2);
			long time1=System.currentTimeMillis();
			String res1=PrimeUtil.gcd(bi1, bi11).toString();
			long time2=System.currentTimeMillis();
			String res2=bi2.gcd(bi22).toString(BASE2);
			long time3=System.currentTimeMillis();
			System.out.println("my:"+(time2-time1)+"  system:"+(time3-time2));
			if (!res1.equals(res2)) {
				System.out.println("i:" + i + "-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
				System.out.println("res1:"+res1);
				System.out.println("res2:"+res2);
			}
		}
	}
	@Test
	public void extgcdTest(){
		MyBigInteger t1=new MyBigInteger("13");
		MyBigInteger t2=new MyBigInteger("15");
		System.out.println(PrimeUtil.inverse(t1, t2));
		
		
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < 2000; j++) {
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		List<String> strs2 = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < 1500; j++) {
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			MyBigInteger bi2=new MyBigInteger(strs2.get(i));
			long time1=System.currentTimeMillis();
			BigInteger b1=new BigInteger(bi1.toString(),16);
			BigInteger b2=new BigInteger(bi2.toString(),16);
			BigInteger gcd=b1.gcd(b2);
			if(!gcd.equals(BigInteger.ONE)){
				continue;
			}
			MyBigInteger inverse=PrimeUtil.inverse(bi1,bi2 );
			long time2=System.currentTimeMillis();
			System.out.println("time:"+(time2-time1));
			BigInteger b3=new BigInteger(inverse.toString(),16);
			String res=bi1.multiply(inverse).subtract(MyBigInteger.ONE).mod(bi2).toString();
			if (!res.equals("0") ) {
				System.out.println("i:" + i + "-----------------------------------");
				System.out.println("system:"+b1.multiply(b3).subtract(BigInteger.ONE).remainder(b2).toString(16));
				System.out.println("my:"+res);
			}
		}
	}
	@Test
	public void encryptDecryptTest(){
		MyBigInteger N=new MyBigInteger("64d8fa6c40a42e1fbb5372857931f654cead21173fca6986285bb3313881a1a015f7b7770911e9f4d3d2ec1c7066f09f1d6d9eed45af99ad60ac7bf11dc9d4b9");
		MyBigInteger e=new MyBigInteger("10001");
		String message="我是孟骋 abbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
		StringBuilder ss=new StringBuilder();
		for(int i=0;i<300;i++){
			ss.append('b');
		}
		message+=ss.toString();
		String encrypt=PrimeUtil.encryptString(message, N, e);
		System.out.println("encrypt message:"+encrypt);
		MyBigInteger d=new MyBigInteger("3abd16bf4eb752163169dd5e58c4fd1150800eb217f9b194ee94dabe64873cb7e1518019749c1ac02c86bb96548f8a6fbf09d9a8f6978fed349fa4b51e8f74b1");
		String decrypt=PrimeUtil.decryptString(encrypt, N, d);
		System.out.println("decrypt message:"+decrypt);
	}
	@Test
	public void encryptDecryptNumTest(){
		MyBigInteger N=new MyBigInteger("64d8fa6c40a42e1fbb5372857931f654cead21173fca6986285bb3313881a1a015f7b7770911e9f4d3d2ec1c7066f09f1d6d9eed45af99ad60ac7bf11dc9d4b9");
		//公钥
		MyBigInteger e=new MyBigInteger("10001");
		//私钥
		MyBigInteger d=new MyBigInteger("3abd16bf4eb752163169dd5e58c4fd1150800eb217f9b194ee94dabe64873cb7e1518019749c1ac02c86bb96548f8a6fbf09d9a8f6978fed349fa4b51e8f74b1");
		MyBigInteger m=new MyBigInteger("a2342421ccccccccccccccccccccccccccccccc22222222222222222222222222222222222222bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb222224");
		System.out.println(m.compareTo(N));
		MyBigInteger res=m.quickpowmodb(e, N);
		System.out.println(res);
		System.out.println(res.quickpowmodb(d,N));
		
		System.out.println(m.quickpowmod(e, N));
	}
}
