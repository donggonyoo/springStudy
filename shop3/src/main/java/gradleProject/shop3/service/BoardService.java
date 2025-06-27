package gradleProject.shop3.service;

import gradleProject.shop3.domain.Board;
import gradleProject.shop3.domain.Comment;
import gradleProject.shop3.dto.board.BoardDto;
import gradleProject.shop3.dto.board.DeleteBoardDto;
import gradleProject.shop3.repository.BoardRepository;
import gradleProject.shop3.repository.CommRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;

//package gradleProject.shop3.service;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
@Service
public class BoardService {
	//appliaction.properties의 환경변수값 읽어오기
	//@Value("${summernote.imgupload}")
	//private String UPLOAD_IMAGE_DIR;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	CommRepository commRepository;

	public int boardcount(String boardid, String searchtype, String searchcontent) {
		/*
		root : 조회대상 객체(Board)
		query : 쿼리전체구조
		cri : CriteriaBuilder(조건문)
		 */
		Specification<Board> spec = ((root, query, cri) ->
				cri.equal(root.get("boardid"), boardid));
		if (searchtype != null) {
			if(searchtype.equals("title")) {
				spec = spec.and(((root, query, cri) ->
						//where title like '%searchcontent%'
						cri.like(root.get("title").as(String.class), "%" + searchcontent + "%")));
			}
			else if (searchtype.equals("writer")) {
				spec = spec.and(((root, query, cri) ->
						cri.like(root.get("writer").as(String.class), "%" + searchcontent + "%")));
			}
			else if (searchtype.equals("content")) {
				spec = spec.and(((root, query, cr) ->
						cr.like(root.get("content").as(String.class), "%" + searchcontent + "%")));
			}
		}
		//조건에맞는 건수 조회 select count(*) from board where xx like '%search%'
		int count = (int) boardRepository.count(spec);
		return count;
	}
	/*
	페이징을 위한 게시물 목록조회(Page 사용)
	 */
	public Page<Board> boardlist
	(Integer pageNum, int limit, String boardid, String searchtype, String searchcontent) {
		Specification<Board> spec = ((root, query, cri) ->
				cri.equal(root.get("boardid"), boardid));
		if (searchtype != null) {
			if(searchtype.equals("title")) {
				spec = spec.and(((root, query, cri) ->
						//where title like '%searchcontent%'
						cri.like(root.get("title").as(String.class), "%" + searchcontent + "%")));
			}
			else if (searchtype.equals("writer")) {
				spec = spec.and(((root, query, cri) ->
						cri.like(root.get("writer").as(String.class), "%" + searchcontent + "%")));
			}
			else if (searchtype.equals("content")) {
				spec = spec.and(((root, query, cr) ->
						cr.like(root.get("content").as(String.class), "%" + searchcontent + "%")));
			}
		}
		/*
		pageNUm -1 : 현재페이지 (0부터 시작)
		limit : 한페이지에 출력할 게시물 갯수 (한번에 조회 되는 레코드갯수)
		 */
		Pageable pageable = PageRequest.of(pageNum - 1, limit,
				Sort.by(Sort.Order.desc("grp"), Sort.Order.asc("grpstep"), Sort.Order.desc("regdate")));
		//grp : 게시물 번호  || grpstep : 게시물의 답글?0 :원글  1:답글 .....

		return boardRepository.findAll(spec, pageable);
	}
	public Board getBoard(int num) {
		return boardRepository.findById(num).orElse(null);

	}
	public void addReadcnt(int num) {
		boardRepository.addReadcnt(num);

	}
	public void boardWrite(BoardDto dto, HttpServletRequest request) {
		int maxNum = boardRepository.maxNum();
		dto.setNum(++maxNum);
		dto.setGrp(maxNum);
		if(dto.getFile1() != null && !dto.getFile1().isEmpty()) {
			String path =
					request.getServletContext().getRealPath("/")+"board/file/";
			this.uploadFileCreate(dto.getFile1(),path);
			dto.setFileurl(dto.getFile1().getOriginalFilename());
			dto.setRegdate(new Date());

		}
		Board board = new Board(dto);
		boardRepository.save(board);

	}
//
	private void uploadFileCreate(MultipartFile file, String path) {
		String orgFile = file.getOriginalFilename();
		File f = new File(path);
		if(!f.exists()) {
			f.mkdirs();
		}
		try {
			file.transferTo(new File(path+orgFile));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
//
	public void boardUpdate(BoardDto board, HttpServletRequest request) {
		if(board.getFile1()!=null && !board.getFile1().isEmpty()) {
			String path =
					request.getServletContext().getRealPath("/")+"board/file/";
			this.uploadFileCreate(board.getFile1(),path);
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		board.setRegdate(new Date());
		Board boardMain = new Board(board);
		boardRepository.save(boardMain);
	}


	public void boardDelete(DeleteBoardDto dto) {
		int num = dto.getNum();
		boardRepository.deleteById(num);
	}
//	public void boardReply( Board board) {
//		boardDao.grpStepAdd(board); //이미등록된 grpstep값 1씩 증가
//		int max = boardDao.maxNum();
//		board.setNum(++max);
//		board.setGrplevel(board.getGrplevel()+1);
//		board.setGrpstep(board.getGrpstep()+1);
//		boardDao.insert(board);
//	}
	public List<Comment> commentList(int num) {
		return null;

	}
//	public int comMaxSeq(int num) {
//		return commDao.comMaxSeq(num);
//	}
//	public void comInsert( Comment comm) {
//		commDao.comInsert(comm);
//
//	}
//	public Comment commSelectOne(int num, int seq) {
//		return commDao.commSelectOne(num,seq);
//	}
//	public void commDel(int num, int seq) {
//		commDao.commDel(num,seq);
//
//	}
//	public String summernoteImageUpload(MultipartFile multipartFile) {
//		File dir = new File(UPLOAD_IMAGE_DIR+"board/image");
//		if(!dir.exists()) {
//			dir.mkdirs();
//		}
//		//파일의 이름
//		String fileSystemName = multipartFile.getOriginalFilename();
//		File file = new File(dir,fileSystemName);
//		try {
//			multipartFile.transferTo(file);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "/board/image/"+fileSystemName;
//	}
//
//	public String sidoSelect1(String si, String gu) {
//		BufferedReader fr = null;
//		String path  = UPLOAD_IMAGE_DIR+"data/sido.txt";
//		try {
//			//sido.txt파일을 읽어 fr에저장
//			fr = new BufferedReader(new FileReader(path));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Set<String> set = new LinkedHashSet<>();
//		String data = null;
//		if(si==null&&gu==null) {
//			try {
//				while((data=fr.readLine())!=null) {
//					// '\\s+' : 1개이상의 공백
//					String[] arr = data.split("\\s+");
//					if(arr.length>=3) {
//						//시군구가 존재할 때 시만 set에 넣는다(중복방지)
//						set.add(arr[0].trim());
//					}
//				}
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		List<String> list = new ArrayList<>(set);
//		return list.toString();//[서울특별시,경기도,강원도,,..,,]
//	}
//
//
//
//	public List<String> sigunSelect2(String si, String gu) {
//		BufferedReader fr = null;
//		String path  = UPLOAD_IMAGE_DIR+"data/sido.txt";
//		try {
//			//sido.txt파일을 읽어 fr에저장
//			fr = new BufferedReader(new FileReader(path));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Set<String> set = new LinkedHashSet<>();
//		String data = null;
//		if(si==null&&gu==null) {
//			try {
//				while((data=fr.readLine())!=null) {
//					// '\\s+' : 1개이상의 공백
//					String[] arr = data.split("\\s+");
//					if(arr.length>=3) {
//						//시군구가 존재할 때 시만 set에 넣는다(중복방지)
//						set.add(arr[0].trim());
//					}
//				}
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		else if(gu==null) {
//			si = si.trim();
//			try {
//				while((data=fr.readLine())!=null) {
//					String[] arr = data.split("\\s+");
//					//배열의크기가3이상이면서 0번쨰요소가 si인
//					if(arr.length>=3 && arr[0].equals(si)
//							&& !arr[1].contains(arr[0])) {
//
//						set.add(arr[1].trim());
//
//					}
//				}
//			}
//			catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		else {
//			si = si.trim();
//			gu=gu.trim();
//			try {
//				while((data=fr.readLine())!=null) {
//					String[] arr = data.split("\\s+");
//					//배열의크기가3이상이면서 0번쨰요소가 si인
//					if(arr.length>=3 && arr[0].equals(si)
//							&& arr[1].equals(gu) && !arr[0].equals(arr[1])
//							&& !arr[2].contains(arr[1])) {
//
//						if(arr.length>3) {
//							if(arr[3].contains(arr[1])) {
//								continue;
//							}
//							arr[2] += " "+arr[3];
//						}
//						set.add(arr[2].trim());
//
//					}
//				}
//			}
//			catch(IOException e) {
//				e.printStackTrace();
//			}
//		}
//		ArrayList<String> list = new ArrayList<>(set);
//		System.out.println("list ::::: "+list);
//		return list;
//	}
//
//	public String exchange1() {
//		Document doc = null;
//		List<List<String>> trlist = new ArrayList<>();
//		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
//		String exdate = null;
//		try {
//			doc = Jsoup.connect(url).get();
//			Elements trs = doc.select("tr"); //tr태그를 다 가져와
//			exdate = doc.select("p.table-unit").html();
//			for(Element tr : trs) {
//				List<String> tdlist = new ArrayList<>();
//				Elements tds = tr.select("td");//tr태그내의 td를 모두가져와
//				for(Element td : tds) {
//					tdlist.add(td.html());//td태그내의 내용들 (innerHTML이라생각하면댐)
//					//[USD,미국달러,1325,..]
//				}
//				if(tdlist.size()>0) {
//					if(tdlist.get(0).equals("USD")|| tdlist.get(0).equals("CNH")
//							|| tdlist.get(0).equals("JPY(100)")|| tdlist.get(0).equals("EUR")) {
//						trlist.add(tdlist);
//					}
//				}
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		StringBuilder sb = new StringBuilder();
//		sb.append("<h4 class='text-center my-3'>수출입은행<br>"+exdate +"</h4>");
//	      sb.append("<table class='table table-bordered table-hover text-center'>");
//	      sb.append("<thead><tr><th>통화</th><th>기준율</th><th>받으실때</th><th>보내실때</th></tr></thead>");
//	      sb.append("<tbody>");
//	      for(List<String> tds : trlist) {
//	         sb.append("<tr><td>"+tds.get(0)+"<br>"+tds.get(1)+"</td><td>"+tds.get(4)+"</td>");
//	         sb.append("<td>"+tds.get(2)+"</td><td>"+tds.get(3)+"</td></tr>");
//	      }
//	      sb.append("</tbody>");
//	      sb.append("</table>");
//		return sb.toString();
//	}
//
//
//	//JSON
//	public Map<String, Object> exchange2() {
//		Document doc = null;
//		List<List<String>> trlist = new ArrayList<>();
//		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
//		String exdate = null;
//		try {
//			//DOM
//			doc = Jsoup.connect(url).get();
//			Elements trs = doc.select("tr"); //tr태그를 다 가져와
//			//exdate = p태그의 class = table-unit
//			exdate = doc.select("p.table-unit").html();
//			for(Element tr : trs) {
//				List<String> tdlist = new ArrayList<>();
//				Elements tds = tr.select("td");//tr태그내의 td를 모두가져와
//				for(Element td : tds) {
//					tdlist.add(td.html());//td태그내의 내용들 (innerHTML이라생각하면댐)
//					//[USD,미국달러,1325,..]
//				}
//				if(tdlist.size()>0) {
//					if(tdlist.get(0).equals("USD")|| tdlist.get(0).equals("CNH")
//							|| tdlist.get(0).equals("JPY(100)")|| tdlist.get(0).equals("EUR")) {
//						trlist.add(tdlist);
//					}
//				}
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		Map<String, Object> map = new HashMap<>();
//		map.put("exdate", exdate);
//		map.put("trlist", trlist);
//		return map;
//	}
//
//	public Map<String, Integer> graph1(String id) {
//		List<Map<String,Object>> list = boardDao.graph1(id);
//		Map<String,Integer> map = new HashMap<>();
//		for (Map<String, Object> m : list) {
//			String writer = (String)m.get("writer");
//			long cnt = (Long)m.get("cnt");
//			map.put(writer, (int)cnt);
//		}
//
//		return map;
//	}
//
//	public Map<String, Object> boardImg() {
//		Document doc = null;
//		String url = "https://gudi.kr";
//		String imgSrc = null;
//		try {
//			doc = Jsoup.connect(url).get();
//			Elements el = doc.select("img.scroll_logo");
//			System.out.println("el : "+el);
//			imgSrc = el.first().attr("src"); //el의  src 내용(최초 1개)만 뽑음
//			System.out.println("imgSrc :: "+imgSrc);
//
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//
//		HashMap<String, Object> map = new HashMap<>();
//		map.put("img", imgSrc);
//		return map;
//	}
//	public Map<String, Integer> graph2(String id) {
//		List<Map<Date,Object>> list = boardDao.graph2(id);
//		Map<String,Integer> map = new HashMap<>();
//		for (Map<Date, Object> m : list) {
//			Date date = (Date)m.get("reg");
//			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
//			String regdate = sdf.format(date);
//			long cnt = (Long)m.get("cnt");
//			map.put(regdate, (int)cnt);
//		}
//		System.out.println("map ::: "+map);
//
//		return map;
//	}
}