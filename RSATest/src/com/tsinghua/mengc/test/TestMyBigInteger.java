package com.tsinghua.mengc.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.tsinghua.mengc.math.MyBigInteger;

public class TestMyBigInteger {
	Random random=new Random();
	static final int BASE=1<<30;
	static final int BASE2=16;
	@Test
	public void sample(){
		String num="3266f0ebb807b9bd418e368fcba7cc24171a8ab86094ee04dfa6dcea6475eb18b6fd2f5f18e1791d4cf2d399ca6a440337a722727495b56c199d82dfade61d02a945a721749ce542c7a3b614d51f3da03bd04b9a18ca2b6a7ba21ddb7a2d5645b056ac039d647dc736567f57cae9bab575d32fd44ae5af3628b3edca4e94f01897739df9028f6461d39220729f7a1b28034af3e66ec6cb77d92093a54730e1a1b8b4c07eaaa3066e5400c5ef44b851f41a1bf6f8359e15f8326799c0b32e9e7bddd96bcba8da8a6a71255b7974b8e10d633f0f180c54bfeef5feeb130e3c52365219783cb7cf911a2d5b2c64a51aab3f5a73fa63e375b209d077280c71a75930a45f4a89934408aa279f46896bce85a0d7850a4de8ff513a3fa566f03c9376d629c6796f34698be54fc9583fd294ceab50f399ad323682673a46c85b48dfd2ad3e7ac4c4b7b317185565a026f63fd056768c4c5dc76ddd6fcf29cb5597258a4ce4fa8f49f3b4a4b195fc32e55568225b61d2e078767a0f90c593bc9595f0f641a501059d268248a24165acb8af7318ec043daf3569715a1046acf55663a3a40ee81d09107fda5db2191f628474e72b5c870844c6739ebba1b7a1c8e9af887226ef48e659380ebcc1d9c4895d22fc555f414591e8253a9e5bc4be7bf4dc595402342d611a6da5e47498491284ef5cf96eff901000";
		BigInteger bi=new BigInteger(num,16);
		BigInteger res1=bi.multiply(bi);
		System.out.println("res1:"+res1);
		MyBigInteger bi2=new MyBigInteger(num);
		System.out.println("bi2:"+bi2);
		MyBigInteger res2=bi2.multiply(bi2);
		
		
		System.out.println("res2:"+res2);
		//e0e10f201000000

		System.out.println(res1.toString(16).equals(res2.toString()));
	}
	@Test                               
	public void sample2(){
		long []num=new long[2];
		num[0]=16777216;
		num[1]=943211464;
		MyBigInteger bi=new MyBigInteger(num);
		System.out.println(bi);
	}
	
	@Test
	public void construct1Test(){
		long nums[]=new long[20];
		System.out.println(BASE);
		for(int i=0;i<20;i++){
			nums[i]=random.nextInt(BASE);
			System.out.println(i+"  "+ nums[i]+"  "+Long.toBinaryString(nums[i])+"   "+Long.toBinaryString(nums[i]).length());
			
		}
		System.out.println();
		new MyBigInteger(nums);
		
	}
	
	
	@Test
	public void addTest(){
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
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			String res1=bi1.add(new MyBigInteger(strs2.get(i))).toString();
			String res2=bi2.add(new BigInteger(strs2.get(i),BASE2)).toString(BASE2);
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
	public void subtractTest(){
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
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			String res1=bi1.subtract(new MyBigInteger(strs2.get(i))).toString();
			String res2=bi2.subtract(new BigInteger(strs2.get(i),BASE2)).toString(BASE2);
//			System.out.println("res1:"+res1);
//			System.out.println("res2:"+res2);
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
	public void multiplyTest() {
		Random random = new Random();
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder(10);
			for (int j = 0; j < 1000; j++) {
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			String res1=bi1.multiply(bi1).toString();
			String res2=bi2.multiply(bi2).toString(BASE2);
//			System.out.println(res1);
//			System.out.println(res2);
			if (!res1.equals(res2)) {
				System.out.println("i:" + i + "-----------------------------------");
				System.out.println(bi1);
				System.out.println(bi2);
				System.out.println("-------------------------------------------------");
			}
		}

	}
	@Test
	public void divideTest() {
		Random random = new Random();
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
			for (int j = 0; j < 1000; j++) {
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			String res1=bi1.divide(new MyBigInteger(strs2.get(i))).toString();
			String res2=bi2.divide(new BigInteger(strs2.get(i),BASE2)).toString(BASE2);
			System.out.println(res1);
			System.out.println(res2);
			if (!res1.equals(res2)) {
				System.out.println("i:" + i + "-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
			}
		}
		
	}
	@Test
	public void modTest() {
		Random random = new Random();
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
			for (int j = 0; j < 1000; j++) {
				s.append(random.nextInt(10));
			}
			strs2.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			String res1=bi1.mod(new MyBigInteger(strs2.get(i))).toString();
			String res2=bi2.remainder(new BigInteger(strs2.get(i),BASE2)).toString(BASE2);
//			System.out.println(res1);
//			System.out.println(res2);
			if (!res1.equals(res2)) {
				System.out.println("i:" + i + "-----------------------------------");
				System.out.println(bi1);
				System.out.println(strs2.get(i));
			}
		}
	}
	@Test
	public void shiftRightTest(){
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < 2000; j++) {
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			for(int j=0;j<100;j++){
				bi1=bi1.rightShift(1);
				bi2=bi2.shiftRight(1);
				String res1=bi1.toString();
				String res2=bi2.toString(BASE2);
				System.out.println(res1);
				System.out.println(res2);
				if (!res1.equals(res2)) {
					System.out.println("i:" + i + "-----------------------------------"+j);
				}
			}
		}
	}
	
	/**
	 * 测试大数快速幂取模
	 */
	@Test
	public void powModTest() {
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < 1024; j++) {
				s.append(random.nextInt(10));
			}
			strs.add(s.toString());
		}
		for (int i = 0; i < 100; i++) {
			MyBigInteger bi1 = new MyBigInteger(strs.get(i));
			BigInteger bi2 = new BigInteger(strs.get(i),BASE2);
			long time1=System.currentTimeMillis();
			String res1=bi1.quickpowmod(bi1,bi1.subtract(MyBigInteger.ONE)).toString();
			long time2=System.currentTimeMillis();
			String res2=bi2.modPow(bi2, bi2.subtract(BigInteger.ONE)).toString(BASE2);
			long time3=System.currentTimeMillis();
			System.out.println("my:"+(time2-time1)+" system:"+(time3-time2));
			if(!res1.equals(res2)){
				System.out.println(i+"------------------------------");
				System.out.println("res1:"+res1);
				System.out.println("res2:"+res2);
			}
		}
	}
	
}
