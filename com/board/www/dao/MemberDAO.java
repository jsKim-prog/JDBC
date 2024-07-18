package com.board.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.board.www.dto.MemberDTO;
import com.board.www.exception.ExistCheckException;

public class MemberDAO {
	// 회원 DB에 대한 C(회원가입) R(로그인) U(회원정보수정) D(회원탈퇴)

	public MemberDAO() {

	}// 기본생성자

	public MemberDAO(Connection connection) {
		// Connection conn = connection; //받은 connection 넣을 conn 객체 생성
	}// 커스텀 생성자

	public MemberDTO checkID(Connection connection, MemberDTO regMemberDTO) {// 동일 아이디 사용불가 처리
		try {
			String sql = "select * from member where mid=? ";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, regMemberDTO.getMid());
			ResultSet resultSet = preparedStatement.executeQuery();
			//int result =0;//count 결과 값 담을 변수
			if(resultSet.next()) {//자료가 있으면
				regMemberDTO.setUsable(false);
				throw new ExistCheckException("사용중인 id입니다.");
			}else {//자료가 없으면
				regMemberDTO.setUsable(true);
			}

			
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("검색오류.sql문 확인");
			e.printStackTrace();
		} catch (ExistCheckException e) { // 동일id 존재시 예외
			System.out.println(e.getMessage());
		}
		return regMemberDTO;
	}// --checkID()

	public MemberDTO register(Connection connection, MemberDTO regMemberDTO) {// 회원가입 처리
		MemberDTO newMemberDTO = new MemberDTO(); // 정보처리용 빈객체

		try {
			String sql = "insert into member (mno, mname,mid, mpw, mdate) values (member_seq.nextval,?,?,?,sysdate)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, regMemberDTO.getMname());
			preparedStatement.setString(2, regMemberDTO.getMid());
			preparedStatement.setString(3, regMemberDTO.getMpw());
			int result = preparedStatement.executeUpdate();// 쿼리실행결과를 정수값으로 받음

			if (result > 0) {
				System.out.println("test:" + result + "행의 입력이 성공했습니다.");
				newMemberDTO = regMemberDTO;

			} else {
				throw new SQLException();
				// 입력이 실패하면 예외처리(unique검수)
			}
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("DB : 입력실패");
			// e.printStackTrace();
		}

		return newMemberDTO;

	}// --register()

	public MemberDTO login(Connection connection, MemberDTO loginMemberDTO) {// 로그인 처리
		// db에 있는 로그인 값을 찾아 옴
		// connection ->main에서 넘어온 jdbc 1,2단계
		// loginMemberDTO ->로그인시 키보드로 입력받은 id, pw 값이 들어있다.
		// **resultSet의 data를 먼저 불러오고 행판단 등을 해야 오류 안남. 바로 getRow() 등 사용하면 0으로 인식
		MemberDTO loginDTO = new MemberDTO(); // 리턴용 빈객체
		loginDTO.setLoginCheck(false);
		try {
			String sql = "select mno, mname, mid, mpw, mdate from member where mid = ? and mpw = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginMemberDTO.getMid());
			preparedStatement.setString(2, loginMemberDTO.getMpw());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				loginDTO.setMno(resultSet.getInt("mno"));
				loginDTO.setMname(resultSet.getString("mname"));
				loginDTO.setMid(resultSet.getString("mid"));
				loginDTO.setMpw(resultSet.getString("mpw"));
				loginDTO.setMdate(resultSet.getDate("mdate"));				
				// resutSet 표에 있는 정보를 MemberDTO 객체에 넣음(**resulSet의 값을 먼저 뽑아야 한다!)
			} // --while()
			
			if(loginDTO.getMname()==null) {//검색된 내용이 없어 이름(pk)에 null이 들어갔다면
				loginDTO.setLoginCheck(false);//로그인 체크 안됨표시	
				throw new SQLException();
			}else {//이름에 검색내용이 들어갔다면
				loginDTO.setLoginCheck(true);
			}

			resultSet.close();
			preparedStatement.close();

		} catch (SQLException e) {
			System.out.println("해당하는 id와 pw가 없습니다.");
			System.out.println("관리자 : sql문을 확인하세요.");
			System.out.println("회원 :id와 pw를 확인하세요.");
			// e.printStackTrace();
		}
		return loginDTO; // 로그인 완성용 객체
	}// --login()

	public MemberDTO update(Connection connection, MemberDTO modMemberDTO) {// 회원정보 수정	
	try {
		String sql="update member set mpw=? where mid =?";
		PreparedStatement pps = connection.prepareStatement(sql);
		pps.setString(1, modMemberDTO.getMpw());
		pps.setString(2, modMemberDTO.getMid());
		
		int result = pps.executeUpdate();//결과값을 정수를 받는다.
		
		if(result>0) {
			//modMemberDTO.setUsable(true);
			System.out.println(modMemberDTO.getMid()+"님의 패스워드 변경 완료!");
		}else {
			//modMemberDTO.setUsable(false);
			throw new SQLException();
		}
		pps.close();
		
	} catch (SQLException e) {
		System.out.println("변경실패. 관리자에게 문의하세요.");
		e.printStackTrace();
	}
	return modMemberDTO;
	}// --update()

	public MemberDTO delete(Connection connection, MemberDTO delMemberDTO) {// 회원탈퇴
		try {
			String sql="delete from member where mid=? and mpw=?";
			PreparedStatement pps = connection.prepareStatement(sql);
			pps.setString(1, delMemberDTO.getMid());
			pps.setString(2, delMemberDTO.getMpw());
			
			int result = pps.executeUpdate();
			if(result>0) {
				delMemberDTO.setUsable(false);//객체 사용불가로 체크				
			}else {//결과가 안 돌아올 때
				throw new SQLException();
			}
			pps.close();
		} catch (SQLException e) {
			System.out.println("삭제오류.관리자에게 문의하세요.");
			//e.printStackTrace();
		}
		
		return delMemberDTO;

	}// --delete()

}
