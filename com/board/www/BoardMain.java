package com.board.www;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.board.www.dto.Author;
//import com.board.www.dao.BoardDAO;
import com.board.www.dto.MemberDTO;
import com.board.www.service.BoardService;
import com.board.www.service.MemberService;

public class BoardMain {
	// 필드
	public static Scanner scanner = new Scanner(System.in);
	public static Scanner sclong = new Scanner(System.in);
	// public static BoardDAO boardDAO = new BoardDAO(); //jdbc 담당
	public static Connection connection = null;
	public static MemberDTO loginMember = null; // 로그인 후의 객체
	// 생성자

	public BoardMain() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 1단계(드라이버명)
			connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.111.103:1521:orcl", "boardtest", "boardtest"); // 2단계(url,
																														// id,
																														// pw)
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버명 또는 ojdbc6.jar를 확인해 주세요.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("url, id, pw나 쿼리문이 잘못됨");
			e.printStackTrace();
			System.exit(0); // 프로세스 강제종료
		}
	}

	// 메서드
	public static void main(String[] args) {
		// JDBC를 활용하여 게시판 구현
		// 기본적인 설정 : 스캐너, jdbc연동, 주메뉴

		BoardMain boardMain = new BoardMain(); // 생성자 호출 -> 1단계, 2단계 실행
		MemberDTO guestMem = new MemberDTO(Author.GUEST);//처음 접속한 guest 멤버 객체 생성
		loginMember=guestMem; //로그인세션에 게스트 객체 연결

		System.out.println("MBC 아카데미 대나무숲 오신 것을 환영합니다.");
		boolean run = true;
		while (run) {
			System.out.println("1.회원 | 2.게시판 | 3.종료");
			System.out.print(">>>");
			int select = scanner.nextInt();

			switch (select) {
			case 1:
				System.out.println("회원용 서비스로 진입합니다.");
				MemberService memberService = new MemberService();
				loginMember = memberService.memberMenu(scanner, loginMember, connection);
				// 회원서비스에서 로그인 정보가 유지되어야 함
				break;
			case 2:
				System.out.println("게시판 서비스로 진입입합니다.");
				BoardService boardService = new BoardService();
				boardService.menu(scanner, connection, guestMem, sclong);
				break;
			case 3:
				System.out.println("대나무숲을 종료합니다.");
				run = false;
				break;
			default:
				System.out.println("1~3번만 입력하세요.");
			}// --switch()
		} // --while()

	}

}
