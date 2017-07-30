package sunlei.util;

import java.util.Date;
import java.util.Random;

public class NoUtil {
	public static synchronized String getNo(){
		Random random=new Random();
		int n=random.nextInt(100);
		String s="0"+n;
		s=s.substring(s.length()-2);
		return new Date().getTime()+s;
	}
	
	public static void main(String[] args) {
		String no=NoUtil.getNo();
		System.out.println(no);
	}
}
