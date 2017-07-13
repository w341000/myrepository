package junit.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;

import org.junit.BeforeClass;
import org.junit.Test;

import bean.BuyCart;
import bean.BuyItem;
import bean.product.ProductInfo;
import bean.product.ProductStyle;

public class BuyCartTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		BuyCart items=new BuyCart();
		
		ProductStyle style=new ProductStyle();
		style.setId(1);
		ProductInfo product=new ProductInfo();
		product.setId(1);
		product.addProductStyle(style);
		
		ProductStyle style1=new ProductStyle();
		style1.setId(1);
		ProductInfo product1=new ProductInfo();
		product1.setId(1);
		product1.addProductStyle(style1);
		
		
		BuyItem item=new BuyItem();
		item.setAmount(5);
		item.setProduct(product);
		
		BuyItem item1=new BuyItem();
		item1.setAmount(5);
		item1.setProduct(product1);
	
		items.addItem(item);
		items.addItem(item1);
		System.out.println(item.getAmount());
		
	}

}
