package kr.gdu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.gdu.service.BoardService;
/*
 * @Controller : @Component + Controller 기능
 * 		Mapping메서드의 리턴타입 : modelAndView : view이름 + 데이터
 * 		Mapping메서드의 리턴타입 : String : view이름 
 * 
 * @RestController :  @Component + Controller + 클라이언트로 데이터 직접전송
 * 		Mapping메서드의 리턴타입 : String : 클라이언트로 전달되는 문자열값
 * 		Mapping메서드의 리턴타입 : Object : 클라이언트로 전달되는 문자열값 ,JSON형식처리
 * spring 4.0이후에 추가됨
 * spring 4.0이전에는 @ResponseBody 기능을사용
 */
@RestController
@RequestMapping("ajax")
public class AjaxController {	
	@Autowired
	BoardService service;	
	
	//produces="text/plain; charset=UTF-8" : 전송될 데이터의 형식
	@PostMapping(value="uploadImage",produces="text/plain; charset=UTF-8")
	public String summernoteImageUpload(@RequestParam("image") MultipartFile multipartFile) {
		String imgUrl = service.summernoteImageUpload(multipartFile);
		return imgUrl;		
	}
}
