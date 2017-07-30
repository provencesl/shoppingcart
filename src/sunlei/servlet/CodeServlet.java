package sunlei.servlet;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.misc.CEFormatException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CodeServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path=request.getServletPath();
		if("/show.code".equals(path)){
			show(request, response);
		}
		if("/check.code".equals(path)){
			check(request, response);
		}
	}
	
	protected void show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Random random=new Random();
		
		//获得画板
		BufferedImage image=new BufferedImage(120, 50, BufferedImage.TYPE_INT_RGB);
		
		//获得画笔
		Graphics graphics=image.getGraphics();
		
		//设置画笔的颜色
		graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
		
		//画背景色
		graphics.fillRect(0, 0, 120, 50);
		
		//设置画笔的颜色
		graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
		
		graphics.setFont(new Font("Consolas", Font.BOLD+Font.ITALIC, 30));
		
		String str="qazxswedcvfrtgbnhyujmkiolp1234567890";
		
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<5;i++){
			int index=random.nextInt(str.length());
			sb.append(str.charAt(index));
		}
		
		graphics.drawString(sb.toString(), 25, 30);
		
		//将验证码保存在session中
		HttpSession session=request.getSession();
		session.setAttribute("code",sb.toString());
		
		for(int i=0;i<10;i++){
			//设置画笔的颜色
			graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
			//画干扰线
			graphics.drawLine(random.nextInt(121), random.nextInt(51),random.nextInt(121), random.nextInt(51));
		}
		
		for(int i=0;i<5;i++){
			//设置画笔的颜色
			graphics.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
			//画点
			graphics.fillOval(random.nextInt(121), random.nextInt(51), 10, 10);
		}
		
		//图片压缩
		OutputStream out=response.getOutputStream();
		JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image);
		
	}
	
	protected void check(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("CodeServlet.check()");
		//获得前台文本框的值
		String codetext=request.getParameter("codetext");
		//获得保存在session中的验证码
		String code=(String)request.getSession().getAttribute("code");
		System.out.println(code.equals(codetext));
		if(code.equals(codetext)){
			request.setAttribute("message", "验证码正确");
		}else{
			request.setAttribute("message", "验证码错误");
		}
		
		request.getRequestDispatcher("/jsp/code.jsp").forward(request, response);
	}
	
/*	public static void main(String[] args) {
		Random r=new Random();
//		for (int i = 0; i < 1000; i++) {
//			System.out.println(r.nextInt(256));
//		}
		
		String str="qazxswedcvfrtgbnhyujmkiolp1234567890";
		
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<5;i++){
			int index=r.nextInt(str.length());
			sb.append(str.charAt(index));
			  
		}
		System.out.println(sb.toString());
	}*/
}









