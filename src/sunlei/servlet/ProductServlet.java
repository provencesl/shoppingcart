package sunlei.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.spi.Producer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sunlei.entity.Product;
import sunlei.util.JDBCUtil;



public class ProductServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String path=request.getServletPath();
		
		if("/findAll.product".equals(path)){
			findAll(request, response);
		}
	}
	
	protected void findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//查询所有的商品
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		List<Product> products=new ArrayList<Product>();
		try {
			con=JDBCUtil.getConnection();
			con.setAutoCommit(false);
			
			String sql=new StringBuffer()
						.append("select * from t_product ")
						.toString();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			while (rs.next()) {
				Product product=new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
				products.add(product);
			}
			
			con.commit();
			
			request.setAttribute("products", products);
			//转发到list.jsp
			request.getRequestDispatcher("/jsp/list.jsp").forward(request, response);
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
}
