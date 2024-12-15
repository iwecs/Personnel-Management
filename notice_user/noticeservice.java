package notice_user;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Scanner;

import notice_user.noticeDAO;
import notice_user.noticeVO;

public class noticeservice {
	public static void select() {
	
		System.out.println("noticeservice클래스의 select()");

		ArrayList<noticeVO> list = noticeDAO.select();

		for (noticeVO notice : list) {
			System.out.println(notice);
		}
	}

	public static void insert() {
		System.out.println("noticeservice클래스 insert()");
		Scanner sc = new Scanner(System.in);

		System.out.print("title: ");
		String title = sc.nextLine();

		System.out.println("content: ");
		String content = sc.nextLine();

		System.out.println("writer_id: ");
		int writer_id = sc.nextInt();

		noticeVO temp = new noticeVO(title, content, writer_id, null);

		noticeDAO.insert(temp);
	}

	public static void update() {

		Scanner sc = new Scanner(System.in);
		System.out.println("noticeservice의 update()");
		noticeVO vo = search();

		if (vo != null) {

			System.out.print("수정하는 제목 update title:");
			String title = sc.nextLine();

			System.out.print("수정하는 내용 update content:");
			String content = sc.nextLine();

			System.out.print("수정하는 작성자 update writer_id:");
			int writer_id = sc.nextInt();

			vo.setTitle(title);
			vo.setContent(content);
			vo.setWriter_id(writer_id);

			if (noticeDAO.update(vo) > 0) {
				System.out.println("수정 되었습니다.");
			} else {
				System.out.println("수정되지 않았습니다.");
				System.out.println("다시 확인하시기 바랍니다.");
			}
		}
		return;
	}

	public static void delete() {
		noticeVO vo = search();
		if (vo != null) {
			noticeDAO.delete(vo);
			System.out.println("삭제 되었습니다.");
		} else {
			System.out.println("찾으시는 제목이 없습니다.");
		}
	}

	public static noticeVO search() {
		Scanner sc = new Scanner(System.in);
		System.out.print("제목 검색:");
		String title = sc.nextLine();

		noticeVO vo = noticeDAO.search(title);
		System.out.println("제목 검색 확인: " + vo);
		return vo;
	}
}