package AnnualPaidLeave;

import java.util.ArrayList;

public class AnnualPService {

	private static ArrayList<appFormVO> list = new ArrayList<appFormVO>();

	public static void Select() {

		// 얻어온 글을 출력하는 메서드
		System.out.println("[ Service클래스의 Select()실행 ]");

		//list = AnnualPDAO.select();

		for (appFormVO memo : list) {
			System.out.println();
		}

	}
	
	public static void Insert() {

		System.out.println("[ Service클래스의 Insert()실행 ]");

		appFormVO vo = new appFormVO();

		if (AnnualPDAO.insert(vo)) {
			System.out.println("성공적으로 추가되었습니다.");
		} else {
			System.out.println("추가 실패");
		}
	}
	
//	public static void Update() {
//
//		System.out.println("[ Service클래스의 Search()실행 ]");
//
//		appFormVO vo = AnnualPDAO.Search();
//
//				vo.setName(name);
//				vo.setMemo(memo);
//				vo.setPassword(pw);
//
//				if (AnnualPDAO.Update(vo) > 0) {
//					System.out.println("정상적으로 수정되었습니다.");
//				} else {
//					System.out.println("제대로 수정되지 않았습니다.");
//					System.out.println("내용을 확인해주세요!");
//					return;
//				}
//			} 
//
//	public static appFormVO Search() {
//		
//		
//
//		// 실제 데이터베이스에서 검색하기
//		appFormVO vo = AnnualPDAO.Search(name);
//		System.out.println("확인: " + vo);
//
//		return vo;
//	}

	public static void Delete(int index) {
//		appFormVO vo = AnnualPDAO.Search();
		
		if(index != -1) {
			AnnualPDAO.delete(index);
		} else {
			System.out.println("찾는 값이 없습니다.");
		}

	}

}
