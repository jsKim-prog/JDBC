package com.board.www.service;

import java.sql.Connection;
import java.util.Scanner;

import com.board.www.dao.BoardDAO;
import com.board.www.dto.BoardDTO;
import com.board.www.dto.MemberDTO;

public class BoardService {
	// board의 부메뉴(C,R,U,D, L)
	public void menu(Scanner scanner, Connection connection, MemberDTO loginMember, Scanner sclong) {// 게시판 메뉴
		MemberService memberService = new MemberService();
		boolean menurun = true;
		while (menurun) {
			list(connection);
			System.out.println("----------------------------------------");
			System.out.println("1.상세보기 | 2.글쓰기 | 3.내글보기 | 4.닫기");
			int select = scanner.nextInt();
			switch (select) {
			case 1:
				view(scanner, connection);
				break;
			case 2:
				if (loginMember.isLoginCheck()) {
					write(scanner, sclong, connection, loginMember);
				} else {
					System.out.println("회원 전용 서비스입니다. \n로그인 화면으로 이동합니다.");
					loginMember = memberService.login(scanner, loginMember, connection);
				}
				break;
			case 3:
				if (loginMember.isLoginCheck()) {
					myMenu(scanner, connection, loginMember, sclong);
				} else {
					System.out.println("회원 전용 서비스입니다. \n로그인 화면으로 이동합니다.");
					loginMember = memberService.login(scanner, loginMember, connection);
				}
				break;
			case 4:
				menurun = false;
				break;
			default:
				System.out.println("1~4번만 입력하세요.");
			}// --switch()
		} // --while()

	}

	public void myMenu(Scanner scanner, Connection connection, MemberDTO loginMember, Scanner sclong) {// 내글보기 시메뉴
		boolean menurun = true;
		while (menurun) {
			myList(connection, loginMember); // 내 아이디로 검색된 결과 자동 보이기
			System.out.println("----------------------------------------");
			System.out.println("1.수정하기 | 2.삭제하기 | 3.닫기");
			int select = scanner.nextInt();
			switch (select) {
			case 1:
				update(scanner, connection, sclong);
				break;
			case 2:
				delete(scanner, connection);
				break;
			case 3:
				menurun = false;
				break;
			default:
				System.out.println("1~3번만 입력하세요.");
			}// --switch()
		}

	}

	public void list(Connection connection) {// 게시물 목록 보기
		BoardDAO boardDAO = new BoardDAO();
		System.out.println("=============== 대나무숲 게시판 =============");
		System.out.println("[게시물목록]");
		System.out.println("----------------------------------------");
		System.out.println("no.     Title        Writer       Date");
		System.out.println("----------------------------------------");

		boardDAO.list(connection);
	}

	public void myList(Connection connection, MemberDTO loginMember) {// 내 게시물 보기
		BoardDAO boardDAO = new BoardDAO();
		boardDAO.search(connection, loginMember);
	}// --myList()

	public void write(Scanner scanner, Scanner sclong, Connection connection, MemberDTO loginMember) {// 글쓰기
		System.out.println("작성자 : "+loginMember.getMid());
		System.out.println("제목 : ");
		System.out.print(">>>");
		String newtitle = sclong.nextLine();
		System.out.println("내용 : ");
		System.out.print(">>>");
		String newCon = sclong.nextLine();
		System.out.println("저장 : 1 >>>");
		int save = scanner.nextInt();
		BoardDAO boardDAO = new BoardDAO();
		if(save==1) {
		BoardDTO newBoard = new BoardDTO();//담을 새객체
		newBoard.setBtitle(newtitle);
		newBoard.setBcontent(newCon);
		newBoard.setBwriter(loginMember.getMid());
		boardDAO.write(connection, newBoard);
		}
		return;
	}// --write()

	public void view(Scanner scanner, Connection connection) {// 게시물 상세보기
		// boolean viewrun = true;
		BoardDAO boardDAO = new BoardDAO();

		System.out.println("상세보기 할 게시물의 번호를 입력하세요.");
		System.out.print(">>>");
		int findNo = scanner.nextInt();
		System.out.println("-----------------------------------");
		boardDAO.view(connection, findNo);
		System.out.println("-----------------------------------");
		System.out.println("닫기 : 1 >>>");
		int closeno = scanner.nextInt();
		if (closeno == 1) {
			return;
		}
	}// --view()

	public void update(Scanner scanner, Connection connection, Scanner sclong) {// 게시물 수정하기
		BoardDAO boardDAO = new BoardDAO();
		System.out.println("수정할 게시물 번호를 입력하세요.");
		System.out.print(">>>");
		int bno = scanner.nextInt();
		System.out.println("-------------------------");
		boardDAO.view(connection, bno); //검색한 게시물 보이기
		System.out.println("-------------------------");
		System.out.println();
		System.out.println("변경할 제목을 입력하세요. >>>");
		String title = sclong.nextLine();
		System.out.println("변경할 내용을 입력하세요. >>>");
		String content = sclong.nextLine();
		
		System.out.println("저장 : 1>>>");
		int choice = scanner.nextInt();
		if (choice == 1) {
			BoardDTO modboarBoardDTO = new BoardDTO();
			modboarBoardDTO.setBno(bno);
			modboarBoardDTO.setBtitle(title);
			modboarBoardDTO.setBcontent(content);
			boardDAO.update(connection, modboarBoardDTO);						
		} 
		return;

	}// --update()

	public void delete(Scanner scanner, Connection connection) {// 게시물 삭제하기
		BoardDAO boardDAO = new BoardDAO();
		System.out.println("삭제할 게시물 번호를 입력하세요.");
		System.out.print(">>>");
		int bno = scanner.nextInt();
		
		boardDAO.delete(connection, bno);
		
		return;
	}// --delete()

}
