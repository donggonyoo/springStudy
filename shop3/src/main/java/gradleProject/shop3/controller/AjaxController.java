package gradleProject.shop3.controller;


import gradleProject.shop3.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.Map.Entry;

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
	//@Autowired
	//ChatbotService chatService;
	
	//produces="text/plain; charset=UTF-8" : 전송될 데이터의 형식
	@PostMapping(value="uploadImage",produces="text/plain; charset=UTF-8")
	public String summernoteImageUpload(@RequestParam("image") MultipartFile multipartFile) {
		String imgUrl = service.summernoteImageUpload(multipartFile);
		return imgUrl;		
	}
	
	@RequestMapping(value="select1",produces = "text/plain; charset=UTF-8")
	public String sidoSelect1(String si,String gu) {

		System.out.println("sidoSelect1Controller 접근");
		return service.sidoSelect1(si,gu);
	}
	
	@RequestMapping(value="select2")
	public List<String> sigunSelect2(String si ,String gu) {
		return service.sigunSelect2(si,gu); //list객체를 클라이언트로 전달
	}
	
	@RequestMapping(value="exchange1",produces = "text/plain; charset=UTF-8")
	public String exchange() {
		//미국달러,중국,일본,유로 4개통화만		
		//return service.exchange1();
		return null;
	}
	
	@RequestMapping(value="exchange2")
	public Map<String, Object> exchange2() { //json 데이터로 전송
		return service.exchange2();
	}
	
	@RequestMapping(value="graph1")
	public List<Entry<String, Integer>> graph1(String id) {
		System.out.println("graph1접근");
		Map<String,Integer> map = service.graph1(id);
		System.out.println("map : "+map);
		List<Entry<String, Integer>> list = new ArrayList<>();
		for (Entry<String, Integer> m : map.entrySet()) {
			list.add(m);			
		}
		//value 크기별 정렬(내림차순)!!		
		Collections.sort(list,(m1,m2)->m2.getValue() - m1.getValue());
//		Collections.sort(list,new Comparator<Entry<String, Integer>>(){
//			@Override
//			public int compare(Entry<String, Integer> m1, Entry<String, Integer> m2) {
//				return m2.getValue().compareTo(m1.getValue());
//			}
//		});
		return list;
	}
	
	@RequestMapping(value="boardImg")
	public Map<String, Object> boardImg() { 
		return service.boardImg();
	}
	
	@RequestMapping(value="graph2")
	public List<Entry<String, Integer>> graph2(String id) {
		System.out.println("graph2접근");
		Map<String,Integer> map = service.graph2(id);
		System.out.println("map : "+map);
		List<Entry<String, Integer>> list = new ArrayList<>();
		for (Entry<String, Integer> m : map.entrySet()) {
			list.add(m);			
		}
		//value 크기별 정렬(내림차순)!!		
		//Collections.sort(list,(m1,m2)->m2.getValue() - m1.getValue());		
		Collections.sort(list,new Comparator<Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> m1, Entry<String, Integer> m2) {			
				return m2.getValue().compareTo(m1.getValue());
			}			
		});				
		return list;
	}
	
	@PostMapping("gptquestion")
	public String gptQuestion(String question) {
		String response = null;
		try {
			//response = chatService.getChatGPTResponse(question);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("response :: "+response);
		return response;
	}
}
