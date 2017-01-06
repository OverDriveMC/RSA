package com.tsinghua.mengc.util;

import java.math.BigInteger;
import java.util.Random;

import com.tsinghua.mengc.math.MyBigInteger;

public class PrimeUtil {
	private static final int MAX_LIMIT=2048;
	private static MyBigInteger[]pow2=new MyBigInteger[MAX_LIMIT+1];
	static{
		pow2[0]=new MyBigInteger("1");
		MyBigInteger two=new MyBigInteger("2");
		for(int i=1;i<=MAX_LIMIT;i++){
			pow2[i]=pow2[i-1].multiply(two);
		}
	}
	/**
	 * 生成一个n位的奇数
	 * @param nbits
	 * @return
	 */
	public static MyBigInteger generateBigInteger(int nbits){
		if(nbits>MAX_LIMIT){
			nbits=MAX_LIMIT;
		}
		MyBigInteger res=new MyBigInteger("1");
		Random random=new Random();
		for(int i=1;i<nbits-1;i++){
			boolean flag=random.nextBoolean();
			if(flag){
				res=res.add(pow2[i]);
			}
		}
		res=res.add(pow2[nbits-1]);
		return res;
	}
	public static boolean isPrime(MyBigInteger num){
		boolean flag=true;
		return flag;
	}
	
	public static void main(String[] args) {
		BigInteger b=new BigInteger("1");
		System.out.println(b.bitLength());
	}
}
