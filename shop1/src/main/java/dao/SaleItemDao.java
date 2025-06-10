package dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.SaleItemMapper;
import logic.Item;
import logic.SaleItem;

@Repository
public class SaleItemDao {
	@Autowired
	//DBConfig에서 설정되어있으므로 autowired를 이용해 사용을 알림
	private SqlSessionTemplate template;
	private final Class<SaleItemMapper> cls = SaleItemMapper.class;

	
	public void insert(SaleItem saleItem) {
		template.getMapper(cls).insert(saleItem);
		
	}


	public List<SaleItem> list(int saleid) {
		return template.getMapper(cls).list(saleid);
	}


	
	
}
