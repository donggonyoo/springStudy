package logic;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class Cart {
	private List<ItemSet> itemSetList = new ArrayList<ItemSet>();
	
	public List<ItemSet> getItemSetList(){
		return itemSetList;
	}
	
	public void push(ItemSet set) {
		itemSetList.add(set);
	}
	
	public int getTotal() {
		return itemSetList.stream()
				//getItem().getPrice : item객체의 price프로퍼티
				.mapToInt(s->s.getItem().getPrice() * s.getQuantity())
				.sum();
	}
	

}
