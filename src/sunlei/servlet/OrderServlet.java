package sunlei.servlet;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.Detail;

import sunlei.entity.Item;
import sunlei.entity.Order;
import sunlei.entity.User;
import sunlei.util.JDBCUtil;
import sunlei.util.NoUtil;
import sunlei.vo.Cart;


import com.mysql.jdbc.Statement;

public class OrderServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String path=request.getServletPath();
		
		if("/add.order".equals(path)){
			add(request, response);
		}
		
		if("/myOrders.order".equals(path)){
			myOrders(request, response);
		}
		
	
	}
	
	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Cart cart=(Cart)session.getAttribute("cart");
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		String no=NoUtil.getNo();
		
		try {
			con=JDBCUtil.getConnection();
			con.setAutoCommit(false);
			//生成订单
			String sql=new StringBuffer()
						.append("insert into t_order ")
						.append("       (no,price,user_id,createTime) ")
						.append("values ")
						.append("       (?,?,?,?) ")
						.toString();
			ps=con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, no);
			ps.setDouble(2, cart.getPrice());
			ps.setInt(3, user.getId());
			ps.setTimestamp(4, new Timestamp(new Date().getTime()));
			ps.executeUpdate();
			
			rs=ps.getGeneratedKeys();
			
			Integer order_id=null;
			if(rs.next()){
				order_id=rs.getInt(1);
			}
			
			//生成订单明细
			sql=new StringBuffer()
					.append("insert into t_item ")
					.append("       (order_id,num,price,product_id) ")
					.append("values ")
					.append("       (?,?,?,?) ")
					.toString();
			ps=con.prepareStatement(sql);
			
			for(Item item:cart.getItems()){
				ps.setInt(1, order_id);
				ps.setInt(2, item.getNum());
				ps.setDouble(3, item.getPrice());
				ps.setInt(4, item.getProduct().getId());
				
				ps.executeUpdate();
			}
			con.commit();
			
			request.getSession().setAttribute("cart",new Cart());
			request.setAttribute("no", no);
			request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);
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
	
	protected void myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user=(User)request.getSession().getAttribute("user");
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Order> orders=new ArrayList<Order>();
		try {
			con=JDBCUtil.getConnection();
			con.setAutoCommit(false);
			
			String sql=new StringBuffer()
						.append("select * from t_order ")
						.append("where user_id=? order by createTime desc")
						.toString();
			ps=con.prepareStatement(sql);
			ps.setInt(1, user.getId());
			rs=ps.executeQuery();
			while(rs.next()){
				Order order=new Order();
				order.setId(rs.getInt("id"));
				order.setNo(rs.getString("no"));
				order.setPrice(rs.getDouble("price"));
				order.setCreateTime(rs.getTimestamp("createTime"));
				orders.add(order);
			}
			con.commit();
			
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/jsp/myOrders.jsp").forward(request, response);
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
