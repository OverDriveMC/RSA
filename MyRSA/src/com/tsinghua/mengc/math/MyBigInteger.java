package com.tsinghua.mengc.math;


/**
 * 只考虑非负数
 * @author new
 *
 */
public class MyBigInteger {
	public int[] nums;

	public MyBigInteger(String s) {
		int pos=0;
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)!='0'){
				pos=i;
				break;
			}
		}
		
		nums = new int[s.length()-pos];
		for (int i =0; i < nums.length; i++) {
			nums[i] = s.charAt(s.length() - i - 1) - '0';
		}
	}

	public MyBigInteger(int[] nums) {
		// 去掉前导0
		int pos = nums.length - 1;
		for (int i = nums.length - 1; i >= 0; i--) {
			if (nums[i] != 0) {
				pos = i;
				break;
			}
		}
		this.nums = new int[pos + 1];
		for (int i = pos; i >= 0; i--) {
			this.nums[i] = nums[i];
		}
	}

	public MyBigInteger(Integer[] nums) {
		// 去掉前导0
		int pos = nums.length - 1;
		for (int i = nums.length - 1; i >= 0; i--) {
			if (nums[i] != 0) {
				pos = i;
				break;
			}
		}
		this.nums = new int[pos + 1];
		for (int i = pos; i >= 0; i--) {
			this.nums[i] = nums[i];
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public MyBigInteger add(MyBigInteger big2){
		int[]res=new int[Math.max(nums.length,big2.nums.length)+1];
//		System.out.println(this+"   "+big2);
//		System.out.println("length:"+res.length);
		int i=0,j=0,cnt=0;
		int carry=0;
		while(i<nums.length || j<big2.nums.length || carry!=0){
			if(i<nums.length){
				res[cnt]+=nums[i++];
			}
			if(j<big2.nums.length){
				res[cnt]+=big2.nums[j++];
			}
			if(carry!=0){
				res[cnt]+=carry;
			}
			carry=res[cnt]/10;
			res[cnt]=res[cnt]%10;
//			System.out.println("cnt:"+cnt+"  carry:"+carry+"   res[cnt]:"+res[cnt] );
			cnt++;
		}
		return new MyBigInteger(res);
	}
	
	/**
	 * 大数乘法
	 * @param big2
	 * @return
	 */
	public MyBigInteger multiply(MyBigInteger big2) {
		int length = big2.nums.length + nums.length;
		int[] res = new int[length];
		/**
		 * 第i位与第j位的乘积需要加到结果的i+j位
		 */
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < big2.nums.length; j++) {
				res[i + j] += nums[i] * big2.nums[j];
			}
		}
		int cnt = 0;
		int temp = 0;
		/**
		 * 进位
		 */
		for (int i = 0; i < length; i++) {
			temp = res[i] + cnt;
			if (temp >= 10) {
				cnt = temp / 10;
				res[i] = temp % 10;
			} else {
				cnt = 0;
				res[i] =temp;
			}
		}

		return new MyBigInteger(res);
	}
	/**
	 * 大数减法  保证big2一定小于当前数
	 */
	public MyBigInteger subtract(MyBigInteger big2){
		int[]res=new int[nums.length];
		int cnt=0;
		for(int i=0;i<big2.nums.length;i++){
			if((nums[i]-cnt)<big2.nums[i]){
				res[i]=nums[i]-cnt-big2.nums[i]+10;
				cnt=1;
			}else{
				res[i]=nums[i]-cnt-big2.nums[i];
				cnt=0;
			}
		}
		for(int i=big2.nums.length;i<nums.length;i++){
			if(nums[i]<cnt){
				res[i]=nums[i]-cnt+10;
				cnt=1;
			}else{
				res[i]=nums[i]-cnt;
				cnt=0;
			}
		}
		
		return new MyBigInteger(res);
	}
	/**
	 * 比较大小
	 */
	public int compareTo(MyBigInteger big2){
		int res=1;
		if(nums.length<big2.nums.length){
			res=-1;
		}else if(nums.length==big2.nums.length){
			boolean flag=false;
			for(int i=nums.length-1;i>=0;i--){
				if(nums[i]<big2.nums[i]){
					res=-1;
					flag=true;
					break;
				}else if(nums[i]>big2.nums[i]){
					flag=true;
					break;
				}
			}
			if(!flag){
				res=0;
			}
		}
			
		return res;
	}
	
	/**
	 * 取模
	 * @return
	 */
	public MyBigInteger remainder(final MyBigInteger big2){
		int relation=compareTo(big2);
		//小于等于的情况
		if(relation==-1){
			return new MyBigInteger(nums);
		}else if(relation==0){
			return new MyBigInteger("0");
		}
		int ntimes=nums.length-big2.nums.length;
		MyBigInteger big1=new MyBigInteger(nums);
		while(ntimes>=0){
			int nlength=big2.nums.length+ntimes;
			int []temp=new int[nlength];
			for(int i=0;i<nlength;i++){
				if(i<ntimes){
					temp[i]=0;
				}else{
					temp[i]=big2.nums[i-ntimes];
				}
			}
			MyBigInteger bigTemp=new MyBigInteger(temp);
			while(big1.compareTo(bigTemp)>=0){
				big1=big1.subtract(bigTemp);
			}
			//System.out.println("ntimes:"+ntimes+"  "+big1+"  bigTemp:"+bigTemp);
			ntimes--;
		}
		return big1;
	}
	
	/**
	 * 快速幂
	 */
	public static MyBigInteger quickPow(MyBigInteger base,int exp){
		MyBigInteger res=new MyBigInteger("1");
		while(exp>0){
			//偶数
			if((exp&1) !=0){
				res=res.multiply(base);
			}
			base=base.multiply(base);
			exp>>=1;
		}
		
		return res;
	}
	/**
	 * 快速幂取模
	 * @param base
	 * @param exp
	 * @param mod
	 * @return
	 */
	public static MyBigInteger quickpowmod(MyBigInteger base,int exp,MyBigInteger mod){
		MyBigInteger res=new MyBigInteger("1");
		while(exp>0){
			if((exp&1)!=0){
				res=res.multiply(base).remainder(mod);
			}
			base=base.multiply(base);
			exp>>=1;
		}
		return res;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(nums.length);
		for (int i = nums.length - 1; i >= 0; i--) {
			sb.append(nums[i]);
		}
		return sb.toString();
	}

}
