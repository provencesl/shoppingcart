package sunlei.vo;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sunlei.entity.Item;
import sunlei.entity.Product;
import sunlei.util.JDBCUtil;



public class Cart implements Serializable{
	private List<Item> items=new ArrayList<Item>();
	private Double price=0.0;
	public Cart() {
		super();
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public void add(Integer productId){
		//根据商品id查询商品信息
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Product product=new Product();
		try {
			con=JDBCUtil.getConnection();
			con.setAutoCommit(false);
			
			String sql=new StringBuffer()
						.append("select * from t_product ")
						.append("where id=? ")
						.toString();
			ps=con.prepareStatement(sql);
			ps.setInt(1, productId);
			rs=ps.executeQuery();
			if(rs.next()){
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setPrice(rs.getDouble("price"));
			}
			
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
		
		price=price+product.getPrice();
		
		//判断新增的商品是否已经增加过
		for(Item item:items){
			if(productId==item.getProduct().getId()){
				item.setNum(item.getNum()+1);
				item.setPrice(item.getNum()*product.getPrice());
				return;
			}
		}
		Item item=new Item();
		item.setNum(1);//
		item.setPrice(product.getPrice());//当前商品购买的数量*当前商品的单价
		item.setProduct(product);
		items.add(item);
	}
	
	public void removeByProductId(Integer productId){
		for (int i=0;i<items.size();i++) {
			if(productId==items.get(i).getProduct().getId()){
				price=price-items.get(i).getPrice();
				items.remove(i);
			}
		}
	}
	
	public void modifyProductNum(Integer productId,Integer num){
		//修改对用商品的数量和价格 总价格
		for(Item item:items){
			if(productId==item.getProduct().getId()){
				price=price-item.getPrice();
				item.setNum(num);
				item.setPrice(item.getProduct().getPrice()*num);
				price=price+item.getPrice();
			}
		}
	}
	
	public void removeByProductIds(String[] productIds){
		for(int i=0;i<productIds.length;i++){
			removeByProductId(Integer.parseInt(productIds[i]));
		}
	}
	
	public void clear(){
		items=new ArrayList<Item>();
		price=0.0;
	}
}
