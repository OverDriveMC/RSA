package com.tsinghua.mengc.math;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.BitSet;

/**
 * 只考虑非负数
 * @author new
 *
 */
public class MyBigInteger {
	private static final int EXP=30;
	private static final int BASE=1<<30;
	//存储2^31进制的数
	private long[]nums;
	
	//比特数
	private int nbits;
	//存储bit
	private BitSet bits=null;
	public static final MyBigInteger ZERO=new MyBigInteger("0x0");
	public static final MyBigInteger ONE=new MyBigInteger("0x1");
	
	public static MyBigInteger[] PRIMES = new MyBigInteger[] {
			new MyBigInteger("0x10001"),
			new MyBigInteger("0xc5"), new MyBigInteger("0xc7"),
			new MyBigInteger("0xd3"), new MyBigInteger("0xdf"),
			new MyBigInteger("0xe3"), new MyBigInteger("0xe5") };
	
	public MyBigInteger(BitSet bits){
		this.bits=bits.get(0, this.nbits=bits.length());
		int numslength=nbits%EXP==0 ? nbits/EXP : nbits/EXP+1;
		nums=new long[numslength];
		int cnt=0;
		for(int i=0;i<nbits;i+=EXP){
			int temp=0;
			for(int j=EXP-1;j>=0;j--){
				temp<<=1;
				temp|=bits.get(i+j) ?1 :0;
			}
			nums[cnt++]=temp;
		}
	}
	
	public MyBigInteger(long[]nums){
		int pos=0;
		for(int i=nums.length-1;i>=0;i--){
			if(nums[i]!=0){
				pos=i;
				break;
			}
		}
		this.nums=new long[pos+1];
		for(int i=0;i<this.nums.length;i++){
			this.nums[i]=nums[i];
		}
		
	}


	
	private void calcBit() {	
		nbits=(this.nums.length-1)*EXP+Integer.SIZE-
				Integer.numberOfLeadingZeros((int)nums[this.nums.length-1]);
//		System.out.println("nbits:"+nbits);
		bits=new BitSet(nbits);
		int cnt=0;
		for(int i=0;i<nbits;i+=EXP){
			int temp=1;
			for(int j=0;j<EXP && (i+j)<nbits;j++){
				if((nums[cnt] & temp)!=0){
					bits.set(i+j);
				}
				temp<<=1;
			}
			cnt++;
		}
	}
	
	
	public MyBigInteger(String hex){
		//去掉前导符号与0
		hex=hex.replaceFirst("^(0x)?0*", "");
		if(hex.length()==0){
			hex="0";
		}
		//去掉第一位数字上的前导0
		nbits=(hex.length()-1)*4+Integer.SIZE-
				Integer.numberOfLeadingZeros(Character.digit(
						hex.charAt(0),16 ));
		nbits=nbits==0?1:nbits;
		bits=new BitSet(nbits);
		//根据16进制设置1的位置
		for(int i=hex.length()-1,j=0;i>=0;i--,j+=4){
			//将相应为按16进制转化成数字
			int h=Character.digit(hex.charAt(i), 16);
			//16进制，4位
			for(int k=0;k<4;k++){
				bits.set(j+k,(h&1)==1 );
				h>>=1;
			}
		}
		/**
		 * 转换成2^30进制存储
		 */
		int numslength=nbits%EXP==0 ? nbits/EXP : nbits/EXP+1;
		nums=new long[numslength];
		int cnt=0;
		for(int i=0;i<nbits;i+=EXP){
			int temp=0;
			for(int j=EXP-1;j>=0;j--){
				temp<<=1;
				temp|=bits.get(i+j) ?1 :0;
			}
			nums[cnt++]=temp;
		}
	}
	
	/**
	 * 大数加法
	 * @param big2
	 * @return
	 */
	public MyBigInteger addbit(MyBigInteger big2){
		MyBigInteger sum=new MyBigInteger(bits);
		sum.nbits=Math.max(this.nbits, big2.nbits);
		boolean carry=false;
		for(int i=0;i<sum.nbits;i++){
			//不存在则为false
			boolean a=bits.get(i),b=big2.bits.get(i);
			sum.bits.set(i,a^b^carry);
			carry=(carry &&(a||b)) || (a&&b);
		}
		if(carry){
			sum.bits.set(sum.nbits++);
		}
		return sum;
	}
	
	public MyBigInteger add(MyBigInteger big2){
		long res[]=new long[Math.max(nums.length,big2.nums.length)+1];
		long carry=0;
		int i=0,j=0;
		int cnt=0;
		while(i<nums.length || j<big2.nums.length|| carry !=0){
			if(i<nums.length){
				res[cnt]+=nums[i++];
			}
			if(j<big2.nums.length){
				res[cnt]+=big2.nums[j++];
			}
			if(carry!=0){
				res[cnt]+=carry;
			}
			carry=res[cnt]/BASE;
			res[cnt]=res[cnt]%BASE;
//			System.out.println("cnt:"+cnt+"  "+res[cnt]);
			cnt++;
			
		}
		return new MyBigInteger(res);
	}
	
	/**
	 * 保证减数一定小于被减数
	 * 大数减法
	 */
	public MyBigInteger subtract(MyBigInteger big2){
		if(compareTo(big2)<0){
			throw new RuntimeException("被减数小于减数");
		}
		long[]res=new long[nums.length];
		long carry=0;
		int i=0,j=0;
		while(i<res.length){
			res[i]-=carry;
			if(i<res.length){
				res[i]+=nums[i];
			}
			if(j<big2.nums.length){
				res[i]-=big2.nums[j++];
			}
			if(res[i]<0){
				carry=1;
				res[i]+=BASE;
			}else{
				carry=0;
			}
			i++;
		}
		return new MyBigInteger(res);
	}
	/**
	 * 大数乘法
	 */
	public MyBigInteger multiplybit(MyBigInteger big2){
		MyBigInteger res=MyBigInteger.ZERO;
		for(int i=0;i<nbits;i++){
			if(bits.get(i)){
				res=res.add(big2);
			}
			big2=big2.add(big2);
		}
		return res;
	}
	/**
	 * 大数乘法
	 * @param big2
	 * @return
	 */
	public MyBigInteger multiply(MyBigInteger big2){
		long[]res=new long[nums.length+big2.nums.length];
		//System.out.println(nums[0]+" *" +big2.nums[0]);
		for(int i=0;i<nums.length;i++){
			for(int j=0;j<big2.nums.length;j++){
				res[i+j]+=nums[i]*big2.nums[j];
				if(res[i+j]>=BASE){
					res[i+j+1]+=res[i+j]/BASE;
					res[i+j]=res[i+j]%BASE;
				}
			}
		}
		return new MyBigInteger(res);
	}
	public MyBigInteger multiply(long big2){
		MyBigInteger b=new MyBigInteger(Long.toHexString(big2));
		return multiply(b);
	}
	/**
	 * 大数除法
	 */
	public MyBigInteger divide(final MyBigInteger big2){
		if(big2.equals(MyBigInteger.ZERO)){
			throw new RuntimeException("can not divide zero");
		}
		if(compareTo(big2)<0){
			return MyBigInteger.ZERO;
		}
		MyBigInteger res=MyBigInteger.ZERO;
		int lendiff=nums.length-big2.nums.length;
		MyBigInteger dividend=new MyBigInteger(nums);
		while(dividend.compareTo(big2)>=0){
			MyBigInteger temp=big2.multiplyTen(lendiff);
			long l=0,r=BASE;
			while(r-l>1){
				long m=l+r>>1;
				MyBigInteger temp2=temp.multiply(m);
				if(temp2.compareTo(dividend)>0){
					r=m;
				}else{
					l=m;
				}
			}
			dividend=dividend.subtract(temp.multiply(l));
			res=res.add(new MyBigInteger(Long.toHexString(l)).multiplyTen(lendiff));
			lendiff--;
		}
		return res;
	}
	/**
	 * 大数取模
	 */
	public MyBigInteger mod(final MyBigInteger big2){
		if(big2.equals(MyBigInteger.ZERO)){
			throw new RuntimeException("can not divide zero");
		}
		if(compareTo(big2)<0){
			return this;
		}
		int lendiff=nums.length-big2.nums.length;
		MyBigInteger dividend=new MyBigInteger(nums);
		while(dividend.compareTo(big2)>=0){
			MyBigInteger temp=big2.multiplyTen(lendiff);
			long l=0,r=BASE;
			while(r-l>1){
				long m=l+r>>1;
				MyBigInteger temp2=temp.multiply(m);
				if(temp2.compareTo(dividend)>0){
					r=m;
				}else{
					l=m;
				}
			}
			dividend=dividend.subtract(temp.multiply(l));
			lendiff--;
		}
		return dividend;
	}
	
	
	/**
	 * 乘以基于进制的10
	 * @param len
	 * @return
	 */
	public MyBigInteger multiplyTen(int len){
		long[]newnums=new long[nums.length+len];
		for(int i=0;i<len;i++){
			newnums[i]=0;
		}
		for(int i=len;i<newnums.length;i++){
			newnums[i]=nums[i-len];
		}
		return new MyBigInteger(newnums);
	}
	
	/**
	 * 左移
	 */
	public MyBigInteger leftShiftBit(int n){
		BitSet bits=new BitSet(nbits+n);
		for(int i=0;i<nbits;i++){
			bits.set(n+i,this.bits.get(i));
		}
		return new MyBigInteger(bits);
	}
	/**
	 * 右移
	 */
	public MyBigInteger rightShift(int n){
		long[]res=new long[nums.length-n/EXP];
		int offset=nums.length-res.length;
		//先舍弃掉被偏移清零的位
		for(int i=0;i+offset<nums.length;i++){
			res[i]=nums[i+offset];
		}
		offset=n-offset*EXP;
		for(int i=0;i<res.length;i++){
			//每一位左移
			res[i]=res[i]>>offset;
			//从前面补位
			if(i+1<res.length){
				res[i] |=(res[i+1] & ((1<<offset)-1))<<(EXP-offset);
			}
		}
		return new MyBigInteger(res);
	}
	
	
	/**
	 * 右移
	 */
	public MyBigInteger rightShiftBit(int n){
		if(n>=nbits){
			return MyBigInteger.ZERO;
		}
		return new MyBigInteger(bits.get(n,nbits));
	}
	
	/**
	 * 判断是否奇数
	 * @return
	 */
	public boolean isOdd(){
		return (nums[0]&1)==1;
	}
	/**
	 * 快速幂
	 * @param exp
	 * @param MODNUMBER
	 * @return
	 */
	public  MyBigInteger quickpowmod(MyBigInteger exp, MyBigInteger MODNUMBER) {
		MyBigInteger res=MyBigInteger.ONE;
		MyBigInteger judgeBase=this;
		while(exp.compareTo(MyBigInteger.ZERO)>0){
			//long start=System.currentTimeMillis();
			if((exp.nums[0]&1) !=0){
				res=res.multiply(judgeBase).mod(MODNUMBER);
			}
			judgeBase=judgeBase.multiply(judgeBase);
			//long end1=System.currentTimeMillis();
//			if(end1-start !=0){
//				System.out.println("one multiply process 1:"+(end1-start));
//			}
			judgeBase=judgeBase.mod(MODNUMBER);
			
			//long end2=System.currentTimeMillis();
//			if(end2-end1 !=0){
//				System.out.println("one mod process 2:"+(end2-end1));
//			}
			exp=exp.rightShift(1);
		}
		return res;
	}
	/**
	 * 使用bigInteger的快速幂
	 */
	public  MyBigInteger quickpowmodb(MyBigInteger exp, MyBigInteger MODNUMBER) {
		BigInteger judgeBase=new BigInteger(this.toString(),16);
		BigInteger judgeExp=new BigInteger(exp.toString(),16);
		BigInteger judgeMod=new BigInteger(MODNUMBER.toString(),16);
		BigInteger res=judgeBase.modPow(judgeExp, judgeMod);
		return new MyBigInteger(res.toString(16));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(nums);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyBigInteger other = (MyBigInteger) obj;
		if (!Arrays.equals(nums, other.nums))
			return false;
		return true;
	}

	/**
	 * 比较大小
	 * @param N
	 * @return
	 */
	public int compareTo(MyBigInteger N){
		int cmp=Integer.compare(nums.length, N.nums.length);
		if(cmp!=0){
			return cmp;
		}
		for(int i=nums.length-1;i>=0;i--){
			if(nums[i] >N.nums[i]){
				return 1;
			}else if(nums[i]<N.nums[i]){
				return -1;
			}
		}
		return 0;
	}
	
	public int compareToBit(MyBigInteger N){
		int cmp=Integer.compare(nbits, N.nbits);
		if(cmp!=0){
			return cmp;
		}
		for(int i=nbits-1;i>=0;i--){
			boolean a=bits.get(i),b=N.bits.get(i);
			if(!a && b){
				return -1;
			}else if(a&& !b){
				return 1;
			}
		}
		return 0;
	}
	
	
	@Override
	public String toString(){
		if(bits==null){
			calcBit();
		}
		if(equals(MyBigInteger.ZERO)){
			return "0";
		}
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<nbits;i+=4){
			int temp=0;
			for(int j=3;j>=0;j--){
				temp<<=1;
				temp|=bits.get(i+j) ?1 :0;
			}
			sb.append(Integer.toHexString(temp));
		}
		return sb.reverse().toString();
	}

	public long[] getNums() {
		return nums;
	}

	public void setNums(long[] nums) {
		this.nums = nums;
	}

	public BitSet getBits() {
		if(bits==null){
			calcBit();
		}
		return bits;
	}

	public void setBits(BitSet bits) {
		this.bits = bits;
	}

	public int getNbits() {
		return nbits;
	}

	public void setNbits(int nbits) {
		this.nbits = nbits;
	}

	
}
