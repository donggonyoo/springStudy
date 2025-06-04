package service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.ItemDao;
import logic.Item;

@Service
public class ShopService {

	@Autowired
	private ItemDao itemDao;

	public List<Item> itemList() {
		return itemDao.list();

	}

	public Item getItem(Integer id) {
		return itemDao.getItem(id);
	}

	public void itemCreate(Item item, HttpServletRequest request) {

		//업로드파일이 존재 시 
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			//프로젝트의 img폴더 하위에 저장
			String path =request.getServletContext().getRealPath("/")+"img/";
			uploadFileCreate(item.getPicture(),path);//폴더에업로드
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}

		int maxid = itemDao.maxId();
		item.setId(maxid+1); //현재 최대 id에 +1을 해준다.
		itemDao.insert(item);
	}


	private void uploadFileCreate(MultipartFile picture, String path) {
		String orgFile = picture.getOriginalFilename();//업로드된 파일명
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}		
		try {
			//transferTo 메서드를 호출하여 업로드된 파일을 지정된 경로에 저장
			picture.transferTo(new File(path+orgFile));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void itemUpdate(Item item, HttpServletRequest request) {
		//업로드파일이 존재 시 
		if(item.getPicture() != null && !item.getPicture().isEmpty()) {
			//프로젝트의 img폴더 하위에 저장
			String path =request.getServletContext().getRealPath("/")+"img/";
			uploadFileCreate(item.getPicture(),path);//폴더에업로드
			item.setPictureUrl(item.getPicture().getOriginalFilename());
		}
		
		itemDao.update(item);

	}

	public void deleteItem(Integer id) {
		itemDao.deleteItem(id);
		
	}

}
