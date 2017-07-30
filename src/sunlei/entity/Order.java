package sunlei.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Order implements Serializable{
	private Integer id;
	private String no;
	private Double price;
	private Date createTime;
	private User user;
	private Set<Item> items=new HashSet<Item>();
	public Order() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Item> getItems() {
		return items;
	}
	public void setItems(Set<Item> items) {
		this.items = items;
	}
}
