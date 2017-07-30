package sunlei.servlet;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sunlei.entity.User;
import sunlei.vo.Cart;



public class CartServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String path=request.getServletPath();
		
		if("/add.cart".equals(path)){
			add(request, response);
		}
		
		if("/find.cart".equals(path)){
			find(request, response);
		}
		
		if("/clear.cart".equals(path)){
			clear(request, response);
		}
		
		if("/check.cart".equals(path)){
			check(request, response);
		}
		
		if("/removeById.cart".equals(path)){
			removeById(request, response);
		}
		
		if("/removeByIds.cart".equals(path)){
			removeByIds(request, response);
		}
		
		if("/changeNum.cart".equals(path)){
			changeNum(request, response);
		}
	}
	
	protected void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//判断用户是否登陆
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			return;
		}
		//获取商品id
		Integer productId=Integer.parseInt(request.getParameter("productId"));
//		System.out.println(productId);
		
		//从session中取出当前登录用户的购物车
		Cart cart=(Cart)request.getSession().getAttribute("cart");	
		
		cart.add(productId);
		
		response.sendRedirect(request.getContextPath()+"/findAll.product");
	}
	
	protected void find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//判断用户是否登陆
		User user=(User)request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			return;
		}
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}
	
	protected void clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//从session中取出当前登录用户的购物车
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		cart.clear();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}
	
	protected void check(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		//判断当前用户的购物车中是否已经购买了商品
		if(cart.getItems().size()==0){
			request.setAttribute("message", "当前没有购买任何商品");
			request.getRequestDispatcher("/jsp/cart.jsp").forward(request, response);
			return;
		}
		response.sendRedirect(request.getContextPath()+"/jsp/order.jsp");
	}
	
	protected void removeById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer productId=Integer.parseInt(request.getParameter("productId"));
//		System.out.println(productId);
		
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		cart.removeByProductId(productId);
		
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
	}
	
	protected void removeByIds(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] productIds=request.getParameterValues("productId");
		
//		System.out.println(Arrays.toString(productIds));
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		cart.removeByProductIds(productIds);
		
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}
	
	protected void changeNum(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer productId=Integer.parseInt(request.getParameter("productId"));
		Integer num=Integer.parseInt(request.getParameter("num"));
//		System.out.println(productId+" "+num);
		
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		cart.modifyProductNum(productId, num);
		
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
	}
}
