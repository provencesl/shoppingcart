package sunlei.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sunlei.entity.User;
import sunlei.util.JDBCUtil;
import sunlei.vo.Cart;



public class UserServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String path=request.getServletPath();
		
		if("/regist.user".equals(path)){
			regist(request, response);
		}
		
		if("/login.user".equals(path)){
			login(request, response);
		}
		
		if("/logout.user".equals(path)){
			logout(request, response);
		}
	}
	
	protected void regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取jsp提交的信息
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		String textcode=request.getParameter("textcode");
		
		//校验验证码
		String code=request.getSession().getAttribute("code").toString();
		if(!textcode.equals(code)){
			request.setAttribute("message", "验证码不一致");
			request.getRequestDispatcher("/jsp/regist.jsp").forward(request, response);
			return;
		}
		
		//判断用户名是否已被注册
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con=JDBCUtil.getConnection();
			con.setAutoCommit(false);
			
			String sql=new StringBuffer()
						.append("select * from t_user ")
						.append("where username=? ")
						.toString();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			rs=ps.executeQuery();
			if(rs.next()){
				request.setAttribute("message", "用户名已存在");
				request.getRequestDispatcher("/jsp/regist.jsp").forward(request, response);
				con.commit();
				return;
			}
			//如果没有注册 将信息保存到数据库
			sql=new StringBuffer()
					.append("insert into t_user ")
					.append("       (username,password,phone,address) ")
					.append("values ")
					.append("       (?,?,?,?) ")
					.toString();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, phone);
			ps.setString(4, address);
			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JDBCUtil.close(con, ps, rs);
		}
		//跳转到登陆界面
		response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//根据页面传用户名密码判断数据库中是否已存在
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		User user=new User();
		try {
			con=JDBCUtil.getConnection();
			con.setAutoCommit(false);
			
			String sql=new StringBuffer()
						.append("select * from t_user ")
						.append("where username=? and password=? ")
						.toString();
			ps=con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs=ps.executeQuery();
			if(rs.next()){
				user.setId(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				
			}else{
				request.setAttribute("message", "用户名密码不正确");
				request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
				con.commit();
				return;
			}
			con.commit();
			//登陆成功将登陆的用户保存下来
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			//登陆成功创建出新的购物车
			session.setAttribute("cart", new Cart());
			response.sendRedirect(request.getContextPath()+"/findAll.product");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			JDBCUtil.close(con, ps, rs);
		}
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//销毁session
		HttpSession session=request.getSession();
		session.invalidate();
		//重定向到商品列表页面
		response.sendRedirect(request.getContextPath()+"/findAll.product");
	}
}
