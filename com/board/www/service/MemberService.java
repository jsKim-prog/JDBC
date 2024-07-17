package com.board.www.service;

import java.sql.Connection;
import java.util.Scanner;

import com.board.www.dao.MemberDAO;
import com.board.www.dto.MemberDTO;

public class MemberService {
	// 회원에 대한 처리(C(회원가입), R(로그인), U(회원정보수정), D(회원탈퇴))
	public MemberDTO memberMenu(Scanner scanner, MemberDTO loginMember, Connection connection) {// while문으로 부메뉴 반복 처리
		System.out.println("회원관리용 서비스로 진입");
		boolean memberrun = true;
		while (memberrun) {
			System.out.println("1.회원가입 | 2.로그인 | 3.회원정보수정 | 4.회원탈퇴 | 5.종료");
			System.out.print(">>>");
			int memberSelect = scanner.nextInt();
			switch (memberSelect) {
			case 1:
				join(scanner, connection);
				break;
			case 2:
				loginMember = login(scanner, loginMember, connection);
				break;
			case 3:
				modify();
				break;
			case 4:
				delete();
				break;
			case 5:
				System.out.println("회원관리 메뉴를 종료합니다.");
				memberrun = false;
				break;
			default:
				System.out.println("1~5값만 입력하세요.");
			}// --switch()
		} // --while()
		return loginMember;
	}// --memberMenu()

	public void join(Scanner scanner, Connection connection) {// 회원가입용 메서드
		//boolean regcheck = false; //회원가입 성공여부 체크용
		boolean runcheck = true;
		MemberDAO memberDAO = new MemberDAO(); // connection정보를 가진 객체 생성->실패
		MemberDTO regMemberDTO = new MemberDTO();//입력받은 값 넣을 객체
		MemberDTO newMemberDTO = new MemberDTO();//dao 실행결과 받을 객체

		while (runcheck) {
			System.out.println("아이디를 입력하세요.");
			System.out.print(">>>");
			regMemberDTO.setMid(scanner.next());

			System.out.println("패스워드를 입력하세요.");
			System.out.print(">>>");
			regMemberDTO.setMpw(scanner.next());// 입력받은 값을 객체에 세팅

			newMemberDTO = memberDAO.register(connection, regMemberDTO); // 입력받은 객체를 dao에 전달하여 검증 후 그 값을 다시 reg객체에 담기
			if (newMemberDTO.isUsable()) {// id가 사용불가면 while문 반복(객체 재셋팅)-usable=false
				runcheck = true;
			} else {//회원가입 성공시
				System.out.println(newMemberDTO.getMid()+"님 환영합니다.");
				System.out.println("가입하신 계정으로 로그인 해주세요.");
				//regcheck = true;
				runcheck=false;
			}

		}//--while()
		//return regcheck;
	}// --join()

	public MemberDTO login(Scanner scanner, MemberDTO loginMember, Connection connection) {// 회원로그인용 메서드
		System.out.println("로그인 메서드로 진입");
		System.out.print("ID : ");
		String loginID = scanner.next();
		System.out.print("PW : ");
		String loginPW = scanner.next();
		MemberDTO loginMemberDTO = new MemberDTO(loginID, loginPW); // **setter 안쓰는 방법
		// 키보드로 입력받은 값을 객체로 생성

		// MemberDAO memberDAO = new MemberDAO(connection); //객체생성되면서 connection 정보 갖고
		// 들어감
		MemberDAO memberDAO = new MemberDAO();
		return memberDAO.login(connection, loginMemberDTO); // 기본객체 생성하고 메서드 실행하면서 connection 정보 전달
		// DB에서 넘어온 객체를 리턴
	}// --login()

	public void modify() {// 회원로그인용 메서드
		System.out.println("회원정보 수정 메서드로 진입");
	}// --modify()

	public void delete() {// 회원로그인용 메서드
		System.out.println("회원탈퇴 메서드로 진입");
	}// --delete()

}
