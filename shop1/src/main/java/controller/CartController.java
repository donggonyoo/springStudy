package controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import logic.Cart;
import logic.Item;
import logic.ItemSet;
import service.ShopService;

@Controller
@RequestMapping("cart")
public class CartController {

	@Autowired
	private ShopService service;

	
	@RequestMapping("cartAdd")
	public ModelAndView add(Integer id , Integer quantity,HttpSession session) {
		//new ModelAndView(뷰명) : /WEB-INF/view/cart/cart.jsp
		ModelAndView mav = new ModelAndView("cart/cart");
		Item item = service.getItem(id);
		Cart cart = (Cart)session.getAttribute("CART");
		if(cart==null) {
			cart = new Cart();
			session.setAttribute("CART", cart);
		}
		
		List<ItemSet> list = cart.getItemSetList();
		for (int i = 0; i < list.size(); i++) {
			//같은 아이템이존재한다면 갯수만늘려주는 작업
			if(list.get(i).getItem().equals(item)) {
				quantity += list.get(i).getQuantity();
				list.remove(i);
			}
		}
		cart.push(new ItemSet(item, quantity));
		
		mav.addObject("message",item.getName()+":"+quantity+"개 장바구니 추가");
		mav.addObject("cart",cart);
		System.out.println(cart);
		return mav;
	}
}
