package com.tsinghua.mengc.util;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.tsinghua.mengc.math.MyBigInteger;

public class PrimeUtil {
	private static final int MAX_LIMIT=2048;
	private static final Random random=new Random();
	/**
	 * 生成一个n位奇数
	 */
	public static MyBigInteger generateBigInteger(int nbits){
		if(nbits>MAX_LIMIT){
			nbits=MAX_LIMIT;
		}
		BitSet bitset=new BitSet(nbits);
		bitset.set(0);
		bitset.set(nbits-1);
		for(int i=1;i<nbits-2;i++){
			boolean flag=random.nextBoolean();
			if(flag){
				bitset.set(i);
			}
		}
		return new MyBigInteger(bitset);
	}
	/**
	 * 判断是否素数，计算出要尝试的次数，采用Miller-Robin方法测试
	 * @param num
	 * @param bitlength
	 * @return
	 */
	public static boolean isPrime(MyBigInteger num,int bitlength){
		int trytime=0;
		if(bitlength<256){
			trytime=27;
		}else if(bitlength<512){
			trytime=15;
		}else if(bitlength<768){
			trytime=8;
		}else if(bitlength<1024){
			trytime=4;
		}else{
			trytime=2;
		}
		
		return testPrime(num, trytime);
	}
	private static boolean testPrime(MyBigInteger num,int tryTime){
		for(int i=0;i<tryTime;i++){
			if(!millerRabin(num)){
				return false;
			}
		}
		return true;
	}
	/**
	 * Miller-Robin方法测试是否素数
	 * @param num
	 * @return
	 */
	private static boolean millerRabin(MyBigInteger num){
		if(!num.isOdd()){
			return false;
		}
		Random random=new Random();
		MyBigInteger maxINT=new MyBigInteger(Integer.toHexString(Integer.MAX_VALUE));
		int base;
		if(num.compareTo(maxINT)>=0){
			base=random.nextInt(Integer.MAX_VALUE-1)+1;
		}else{
			base=random.nextInt(Integer.parseInt(num.toString()));
		}
		MyBigInteger thisMinusOne=num.subtract(MyBigInteger.ONE);
		MyBigInteger exp=thisMinusOne;
		MyBigInteger one=MyBigInteger.ONE;
		MyBigInteger judgeBase=new MyBigInteger(Integer.toHexString(base));
		while(!exp.isOdd()){
			//long start=System.currentTimeMillis();
			//MyBigInteger modres=judgeBase.quickpowmod(exp, num);
			MyBigInteger modres=judgeBase.quickpowmodb(exp, num);
//			System.out.println("judgeBase:"+judgeBase.getNums().length+"  "+exp.getNums().length+"  "+num.getNums().length);
			//long end=System.currentTimeMillis();
			//System.out.println("one quickmod:"+(end-start));
			if(modres.equals(thisMinusOne) ){
				break;
			}else if(modres.equals(one)){
				
			}else{
				return false;
			}
			exp=exp.rightShift(1);
		}
		return true;
	}
	/**
	 * 生成N bit质数
	 */
	public static MyBigInteger generateBigPrime(int bitlength){
		MyBigInteger num=generateBigInteger(bitlength);
		Set<String>vis=new HashSet<>();
		long start=System.currentTimeMillis();
		while(!isPrime(num, bitlength)){
			while(vis.contains(num.toString())){
				num=generateBigInteger(bitlength);
			}
			vis.add(num.toString());
		}
		long end=System.currentTimeMillis();
		System.out.println("generate prime time:"+(end-start));
		System.out.println(num);
		return num;
	}
	/**
	 * 计算x,y最大公约数
	 * @param x
	 * @param y
	 * @return
	 */
	public static MyBigInteger gcd(MyBigInteger x,MyBigInteger y){
		if(y.compareTo(MyBigInteger.ZERO)==0){
			return x;
		}
		return gcd(y,x.mod(y));
	}
	//扩展gcd
	public static MyBigInteger[] extgcd(MyBigInteger x,MyBigInteger y){
		MyBigInteger res[]=null;
		if(y.compareTo(MyBigInteger.ZERO)==0){
			res=new MyBigInteger[4];
			res[0]=MyBigInteger.ONE;
			res[1]=MyBigInteger.ZERO;
			res[2]=x;
			res[3]=y;
		}else{
			res=extgcd(y, x.mod(y));
			res[1]=res[0].add(x.divide(y).multiply(res[0]=res[1]));
			res[3]=MyBigInteger.ONE.subtract(res[3]);
		}
		return res;
	}
	/**
	 * 计算x关于modulus逆元
	 * @param x
	 * @param modulus
	 * @return
	 */
	public static MyBigInteger inverse(MyBigInteger x,MyBigInteger modulus){
		MyBigInteger res[]=extgcd(x, modulus);
		if(!res[2].equals(MyBigInteger.ONE)){
			System.err.println("no 逆元");
			//throw new RuntimeException("不存在逆元");
		}
		return res[3].equals(MyBigInteger.ONE)?modulus.subtract(res[0]):res[0];
	}
	/**
	 * 对字符串加密
	 * 传入字符串，公钥，N
	 */
	public static String encryptString(String message,MyBigInteger N,MyBigInteger e){
		StringBuilder sb=new StringBuilder();
		BitSet bits=BitSet.valueOf(message.getBytes());
		int encryptLength=256;
		for(int i=0;i<message.getBytes().length*Byte.SIZE;i+=encryptLength){
			BitSet b=bits.get(i,i+encryptLength);
//			System.out.println("i:"+i+"  b:"+b);
			MyBigInteger enmessage=new MyBigInteger(b);
//			System.out.println("num:"+enmessage);
			MyBigInteger cipher=enmessage.quickpowmod(e,N);
//			System.out.println("cipher:"+cipher);
			sb.append(cipher+"#");
		}
		return sb.toString();
	}
	public static String decryptString(String message,MyBigInteger N,MyBigInteger d){
		String[]strarr=message.split("#");
		int encryptLength=256;
		BitSet resbs=new BitSet(encryptLength*strarr.length);
		for(int i=0;i<strarr.length;i++){
			//System.out.println("strarr:"+strarr[i]);
			MyBigInteger m=new MyBigInteger(strarr[i]).quickpowmod(d, N);
			BitSet bs=m.getBits();
//			System.out.println("i:"+i+"    bits:"+bs);
//			System.out.println(bs.size());
//			System.out.println("m:"+m);
			for(int j=encryptLength;j>=0;j--){
				if(bs.get(j)){
					resbs.set(i*encryptLength+j);
				}
			}
		}
//		System.out.println("after decrypt:");
//		System.out.println(resbs);
		return new String(resbs.toByteArray());
	}
}
