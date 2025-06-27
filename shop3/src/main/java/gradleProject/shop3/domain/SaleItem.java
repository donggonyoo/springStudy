package gradleProject.shop3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="saleitem")
//@IdClass : 키의 클래스. 다중키에서 사용됨
@IdClass(SaleItemId.class)  //기본키가 복수개인 경우 필요
@Setter
@Getter
@ToString
@NoArgsConstructor
public class SaleItem {// 주문상품

	@Id
	private int saleid;  //주문번호
	@Id
	private int seq;     //주문상품번호
	private int itemid;  //상품아이디
	private int quantity;//주문상품수량

	@ManyToOne
	@JoinColumn(name = "itemid", insertable = false, updatable = false)
	private Item item;   //상품아이디에 해당하는 상품정보

	@ManyToOne
	@JoinColumn(name = "saleid", insertable = false, updatable = false)
	private  Sale sale;

	public SaleItem(int saleid, int seq, ItemSet itemSet) {
		this.saleid = saleid;
		this.seq = seq;
		//this.item = itemSet.getItem();
		this.itemid = itemSet.getItem().getId(); //상품id
		this.quantity = itemSet.getQuantity();   //주문수량
	}
}
