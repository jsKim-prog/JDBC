package com.board.www.service;

import java.sql.Connection;
import java.util.Scanner;

import com.board.www.dao.MemberDAO;
import com.board.www.dto.Author;
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
				loginMember = join(scanner, connection);
				break;
			case 2:
				loginMember = login(scanner, loginMember, connection);				
				break;
			case 3:
				if (loginMember.isLoginCheck()) {// 로그인된 회원만 변경서비스로
					loginMember = modify(scanner, loginMember, connection);
				} else {
					System.out.println("회원 전용 서비스입니다. \n로그인 화면으로 이동합니다.");
					loginMember = login(scanner, loginMember, connection);
				}

				break;
			case 4:
				if (loginMember.isLoginCheck()) {// 로그인된 회원만 탈퇴서비스로
					loginMember = delete(scanner, loginMember, connection);
				} else {
					System.out.println("회원 전용 서비스입니다. \n로그인 화면으로 이동합니다.");
					loginMember = login(scanner, loginMember, connection);
				} 
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

	public MemberDTO join(Scanner scanner, Connection connection) {// 회원가입용 메서드
		MemberDAO memberDAO = new MemberDAO(); // connection정보를 가진 객체 생성->실패
		MemberDTO regMemberDTO = new MemberDTO();// 입력받은 값 넣을 객체
		MemberDTO newMemberDTO = new MemberDTO();// dao 실행결과 받을 객체

		System.out.println("이름을 입력하세요.");
		System.out.print(">>>");
		regMemberDTO.setMname(scanner.next());
		boolean idcheck = true;
		while (idcheck) {
			System.out.println("아이디를 입력하세요.");
			System.out.print(">>>");
			regMemberDTO.setMid(scanner.next());
			regMemberDTO = memberDAO.checkID(connection, regMemberDTO);
			if (regMemberDTO.isUsable()) {// 동일id 없으면
				System.out.println(regMemberDTO.getMid() + "는 사용가능한 id입니다.");
				idcheck = false;
				break;
			} else {// id가 사용불가면 while문 반복(객체 재셋팅)-usable=false
				System.out.println(regMemberDTO.getMid() + "는 사용불가능한 id입니다. 다른 id를 입력해주세요.");
				continue;
			}
		} // --while()
		System.out.println("패스워드를 입력하세요.");
		System.out.print(">>>");
		regMemberDTO.setMpw(scanner.next());// 입력받은 값을 객체에 세팅

		newMemberDTO = memberDAO.register(connection, regMemberDTO);
		System.out.println(newMemberDTO.getMid() + "님 환영합니다.");
		System.out.println("가입하신 계정으로 로그인 해주세요.");
		return newMemberDTO;
	}// --join()
	

	public MemberDTO login(Scanner scanner, MemberDTO loginMember, Connection connection) {// 회원로그인용 메서드
		System.out.println("로그인 메서드로 진입");
		boolean loginrun = true;
		MemberDTO loginMemberDTO = new MemberDTO(); // while문 사용하면서 기본생성자료 변경
		// 키보드로 입력받은 값을 넣을 객체 생성
		// MemberDAO memberDAO = new MemberDAO(connection); //객체생성되면서 connection 정보 갖고
		// 들어감
		MemberDAO memberDAO = new MemberDAO(); // MemberDAO 사용위한 객체

		while (loginrun) {
			System.out.print("ID : ");
			loginMemberDTO.setMid(scanner.next());
			System.out.print("PW : ");
			loginMemberDTO.setMpw(scanner.next());
			loginMember = memberDAO.login(connection, loginMemberDTO); // 기본객체 생성하고 메서드 실행하면서 connection 정보 전달
			if (loginMember.isLoginCheck()) {// logincheck==true
				loginrun = false;
				System.out.println(loginMember.getMname() + "님 환영합니다.");
				break;
			} else {
				loginrun = true;
			}
		} // --while()
		return loginMember;
		// DB에서 넘어온 객체를 리턴
	}// --login()

	public MemberDTO modify(Scanner scanner, MemberDTO loginMember, Connection connection) {// 회원 정보수정용 메서드
		MemberDTO modMemberDTO = new MemberDTO();// 변경정보를 담을 빈객체 생성
		MemberDAO memberDAO = new MemberDAO();
		modMemberDTO = loginMember; // 일단 로그인 정보를 빈객체에 복사
		String oldPW=null;
		String modPW=null;
		System.out.println("----- 패스워드 변경 ------");
		boolean modrun=true;
		while (modrun) {	
			//System.out.println("test : 로그인계정pw"+loginMember.getMpw());
			//System.out.println("test : 변경계정pw"+modMemberDTO.getMpw());
			System.out.print("이전 패스워드 : ");
			oldPW = scanner.next();
			System.out.print("신규 패스워드 : ");
			modPW = scanner.next();
			if(!modMemberDTO.getMpw().equals(oldPW)) {//입력정보가 로그인 정보와 다르면
				System.out.println("계정을 확인할 수 없습니다.");				
			}else if (oldPW.equals(modPW)) {// 변경 전후가 같으면
				System.out.println("변경전과 동일한 패스워드입니다.\n새로운 패스워드를 입력해 주세요.");
			} else {// 다르면 dao로 보낸다.
				modrun=false;
				break;
			} // --if()
		}//--while()
		modMemberDTO.setMpw(modPW);
		loginMember = memberDAO.update(connection, modMemberDTO);// db업데이트 처리한 값을 loginMember에 업데이트
		return loginMember;

	}// --modify()

	public MemberDTO delete(Scanner scanner, MemberDTO loginMember, Connection connection) {// 회원탈퇴용 메서드
		boolean delrun = true;
		while (delrun) {
			System.out.println("회원을 탈퇴하시겠습니까? \n모든 서비스에 대한 권리를 상실하실 수 있습니다.");
			System.out.print("No:1 / Yes:2 >>>");
			int selInt = scanner.nextInt();
			switch (selInt) {
			case 1:
				System.out.println("감사합니다. 서비스를 계속 즐겨보세요.");
				delrun = false;
				break;
			case 2: // 회원정보 재확인 후 탈퇴 진행
				// MemberDTO delMember = new MemberDTO(); //삭제비교용 객체 생성
				// 회원정보 재확인
				System.out.println("회원정보를 재확인합니다.");
				System.out.println("아이디를 입력하세요.");
				System.out.print(">>>");
				String delid = scanner.next();
				System.out.println("패스워드를 입력하세요.");
				System.out.print(">>>");
				String delpw = scanner.next();

				if (loginMember.getMid().equals(delid)) {// 로그인 정보와 입력 아이디 비교
					if (loginMember.getMpw().equals(delpw)) {// 로그인 정보와 입력 패스워드 비교
						MemberDAO memberDAO = new MemberDAO();
						MemberDTO delMemberDTO = new MemberDTO();// 삭제회원용 객체 생성
						delMemberDTO = memberDAO.delete(connection, loginMember);
						delMemberDTO.setAuthor(Author.GUEST);
						delMemberDTO.setLoginCheck(false);
						loginMember = delMemberDTO;// 탈퇴계정을 게스트용 계정으로 바꾸어 loginMember에 업데이트
						System.out.println(delMemberDTO.getMname() + "님의 회원탈퇴가 완료되었습니다. 안녕히 가세요.");
						
						delrun = false;
						break;
					} else {// 패스워드 틀린 경우
						System.out.println("error : 회원정보가 확인되지 않습니다.");
						continue;
					}

				} else {// id 틀린 경우
					System.out.println("회원정보가 확인되지 않습니다.");
					continue;
				}
			default:
				System.out.println("1~2값만 입력하세요.");
			}// --switch()

		} // --while()
		return loginMember;
	}// --delete()

}
