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


	public MemberDTO register(Connection connection, MemberDTO regMemberDTO) {// 회원가입 처리
		MemberDTO newMemberDTO = new MemberDTO(); // 정보처리용 빈객체

		try {
			String sql = "insert into member (mno, mid, mpw, mdate) values (member_seq.nextval,?,?,sysdate)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, regMemberDTO.getMid());
			preparedStatement.setString(2, regMemberDTO.getMpw());
			int result = preparedStatement.executeUpdate();// 쿼리실행결과를 정수값으로 받음

			if (result > 0) { // 입력이 성공하면 입력값을 다시 불러와 객체에 넣는다.(전달용)
				System.out.println("test:" + result + "행의 입력이 성공했습니다.");
				/*
				 * sql = "select mno, mid, mpw from member where mid = ?"; preparedStatement =
				 * connection.prepareStatement(sql); preparedStatement.setString(1,
				 * regMemberDTO.getMid()); // 가입된 정보를 다시 불러와서 resultSet에 저장 ResultSet resultSet
				 * = preparedStatement.executeQuery(); while (resultSet.next()) {// resutSet 표에
				 * 있는 정보를 MemberDTO 객체에 넣음 newMemberDTO.setMno(resultSet.getInt("mno"));
				 * newMemberDTO.setMid(resultSet.getString("mid"));
				 * newMemberDTO.setMpw(resultSet.getString("mpw")); } // --while()
				 */				
				//resultSet.close();
				//preparedStatement.close();
				newMemberDTO.setUsable(true);
				newMemberDTO=regMemberDTO;
			} else {
				newMemberDTO.setUsable(false);
				//newMemberDTO.setMno(0);
				throw new ExistCheckException();
				// 입력이 실패하면 예외처리(unique검수)
			}
			preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("DB : 사용중인 id입니다.");
			// e.printStackTrace();
		} catch (ExistCheckException e) { // 동일id 존재시 예외
			System.out.println("사용중인 id입니다.");
		}

		return newMemberDTO;

	}// --register()

	public MemberDTO login(Connection connection, MemberDTO loginMemberDTO) {// 로그인 처리
		// db에 있는 로그인 값을 찾아 옴
		// connection ->main에서 넘어온 jdbc 1,2단계
		// loginMemberDTO ->로그인시 키보드로 입력받은 id, pw 값이 들어있다.
		MemberDTO loginDTO = new MemberDTO(); // 리턴용 빈객체
		try {
			String sql = "select mno, mid, mpw, mdate from member where mid = ? and mpw = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, loginMemberDTO.getMid());
			// service에서 받은 id가 첫번째 ?에 적용
			preparedStatement.setString(2, loginMemberDTO.getMpw());
			// service에서 받은 id가 두번째 ?에 적용
			System.out.println("test : 쿼리문실행");

			ResultSet resultSet = preparedStatement.executeQuery();
			//System.out.println("test:"+resultSet.getCursorName());
			// 위에서 만든 쿼리문을 실행하고 결과를 resultSet 표로 받는다.
			/*
			 * resultSet.afterLast();//불러온 resultSet의 커서를 마지막 행으로 이동 int count =
			 * resultSet.getRow(); //커서가 있는 행의 번호를 count에 삽입
			 * System.out.println("test : 현재행번호"+count); if (count < 1) { throw new
			 * SQLException(); }
			 */
			while (resultSet.next()) {
				loginDTO.setMno(resultSet.getInt("mno"));
				loginDTO.setMid(resultSet.getString("mid"));
				loginDTO.setMpw(resultSet.getString("mpw"));
				loginDTO.setMdate(resultSet.getDate("mdate"));
				// resutSet 표에 있는 정보를 MemberDTO 객체에 넣음
			} // --while()

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

	public void update() {// 회원정보 수정

	}// --update()

	public void delete() {// 회원탈퇴

	}// --delete()

}
