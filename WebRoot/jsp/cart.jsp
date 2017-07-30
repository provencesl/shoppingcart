<%@page import="sunlei.vo.Cart"%>
<%@ page language="java" import="java.util.*,sunlei.entity.Item" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我的购物车</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  <script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.4.1.min.js"></script>
  <script type="text/javascript">
  		$(function(){
			$("#removeChecked").click(function(){
				var flag=false;
				$("input[name='productId']").each(function(){
// 					alert($(this).attr("checked"));
					if($(this).attr("checked")){
						flag=true;
					}
				});
				if(flag){
					$("form").submit();
				}else{
					alert("请选择要删除的商品");
				}
			});	
  			
  			$("th input").click(function(){
//   				alert($(this).attr("checked"));
				if($(this).attr("checked")){
					$("input[name='productId']").attr("checked",true);
				}else{
					$("input[name='productId']").attr("checked",false);
				}
  			});
  			
  			$("a[name='changeNum']").click(function(){
//   				alert($(this).parent().parent().html());
				var productId=$("input[type='checkbox']",$(this).parent().parent()).val();
				var num=$("input[type='text']",$(this).parent().parent()).val();
//   				alert(productId+" "+num);
				$(this).attr("href","<%=request.getContextPath()%>/changeNum.cart?productId="+productId+"&num="+num);
  			});
  		});
  </script>
  <body>
  		<%
  			Cart cart=(Cart)session.getAttribute("cart");
  		 %>
    	<h1>我的购物车</h1>
    	<hr/>
    	<form action="<%=request.getContextPath() %>/removeByIds.cart" method="post">
	    	<table border="1">
	    		<tr>
	    			<th>
	    				<input type="checkbox"/>
	    				全选
	    			</th>
	    			<th>序号</th>
	    			<th>商品</th>
	    			<th>数量</th>
	    			<th>价格</th>
	    			<th>操作</th>
	    		</tr>
	    		
	    		<%	
	    			int i=1;
	    			for(Item item:cart.getItems()){
	    		 %>
		    		<tr>
		    			<td>
		    				<input name="productId" type="checkbox" value="<%=item.getProduct().getId()%>"/>
		    			</td>
		    			<td><%=i++ %></td>
		    			<td><%=item.getProduct().getName() %></td>
		    			<td>
		    				<input type="text" value="<%=item.getNum() %>" style="border: 0;width: 40px;"/>
		    			</td>
		    			<td><%=item.getPrice() %></td>
		    			<td>
		    				<a href="<%=request.getContextPath()%>/removeById.cart?productId=<%=item.getProduct().getId()%>">删除</a>
		    				<a href="javascript:;" name="changeNum">修改</a>
		    			</td>
		    		</tr>
	    		<%
	    			}
	    		 %>
	    	</table>
    	</form>
    	<%
    		String msg=(String)request.getAttribute("message");
    		if(msg!=null){
    	 %>
    		<span style="color: red;"><%=msg %></span>
    	<%
    		}
    	 %>
    	<hr/>
    	总计：<%=cart.getPrice() %>元<br/>
    	<a id="removeChecked" href="javascript:;">删除选中项</a>
    	<a href="<%=request.getContextPath()%>/clear.cart">清空购物车</a>
    	<a href="<%=request.getContextPath()%>/findAll.product">继续购物</a>
    	<a href="<%=request.getContextPath()%>/check.cart">结算</a>
  </body>
</html>