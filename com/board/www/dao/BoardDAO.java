package com.board.www.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.board.www.dto.BoardDTO;
import com.board.www.dto.MemberDTO;

public class BoardDAO {
	// 데이터베이스 처리용 C(글쓰기)R(상세읽기)U(게시물수정)D(게시물삭제)
	public void list(Connection connection) {// 전체리스트보기
		// BoardDTO boardDTO = null;
		try {
			String sql = "select bno, btitle, bcontent, bwriter,bdate from board order by bno desc";
			// board 테이블에 있는 데이터를 가져옴
			PreparedStatement preparedStatement = connection.prepareStatement(sql); // 3단계
			ResultSet resultSet = preparedStatement.executeQuery(); // 4단계
			// boardDTO = new BoardDTO();
			while (resultSet.next()) {// 표형식으로 return된 값 유무 판단
				System.out.printf("%02d\t", resultSet.getInt("bno"));
				System.out.printf("%-10s\t", resultSet.getString("btitle"));
				// System.out.print(resultSet.getString("bcontent")+"\t");
				System.out.printf("%-6s\t", resultSet.getString("bwriter"));
				System.out.printf("%10s\n", resultSet.getDate("bdate"));

				/*
				 * boardDTO.setBno(resultSet.getInt("bno"));
				 * boardDTO.setBtitle(resultSet.getString("btitle"));
				 * boardDTO.setBcontent(resultSet.getString("bcontent"));
				 * boardDTO.setBwriter(resultSet.getString("bwriter"));
				 * boardDTO.setBdate(resultSet.getDate("bdate"));
				 */
			}
			// 5단계
			resultSet.close();
			preparedStatement.close(); // **connection은 계속 연결(main 담
		} catch (SQLException e) {
			System.out.println("BoardDAO.list() sql문 오류");
			e.printStackTrace();
		}

	}// --list()

	public void write(Connection connection, BoardDTO newBoard) {// 글쓰기
		try {
			String sql = "insert into board (bno, btitle, bcontent, bwriter, bdate) values (board_seq.nextval, ?, ?, ?, sysdate)";
			PreparedStatement pps = connection.prepareStatement(sql);
			pps.setString(1, newBoard.getBtitle());
			pps.setString(2, newBoard.getBcontent());
			pps.setString(3, newBoard.getBwriter());
			
			int result = pps.executeUpdate();
			
			if (result > 0) {
				System.out.println(result + "건의 입력이 완료되었습니다.");
			} else {
				throw new SQLException();
			}
			pps.close();
		} catch (SQLException e) {
			System.out.println("입력실패. 관리자에게 문의하세요.");
			e.printStackTrace();
		}
		

	}//--write()

	public void view(Connection connection, int findNo) {// 게시물 상세보기
		try {
			String sql = "select * from board where bno = ?";
			PreparedStatement pps = connection.prepareStatement(sql);
			pps.setInt(1, findNo);

			ResultSet rst = pps.executeQuery();

			while (rst.next()) {
				System.out.printf("작성자 : %s\t 작성일 : %s\n", rst.getString("bwriter"), rst.getDate("bdate"));
				System.out.println("제목 : " + rst.getString("btitle"));
				System.out.println("제목 : " + rst.getString("bcontent"));
			}
		} catch (SQLException e) {
			System.out.println("BoardDAO.view() sql문 오류");
			e.printStackTrace();
		}

	}

	public void update(Connection connection, BoardDTO modboarBoardDTO) {// 게시물 수정하기
		try {
			String sql = "update board set btitle = ?,bcontent = ? where bno = ?";
			PreparedStatement pps = connection.prepareStatement(sql);
			pps.setString(1, modboarBoardDTO.getBtitle());
			pps.setString(2, modboarBoardDTO.getBcontent());
			pps.setInt(3, modboarBoardDTO.getBno());

			int result = pps.executeUpdate();

			if (result > 0) {
				System.out.println(result + "건의 변경이 완료되었습니다.");
			} else {
				throw new SQLException();
			}
			pps.close();
		} catch (SQLException e) {
			System.out.println("변경실패. 관리자에게 문의하세요.");
			e.printStackTrace();
		}

	}

	public void delete(Connection connection, int bno) {// 게시물 삭제하기
		try {
			String sql = "delete from board where bno = ?";
			PreparedStatement pps = connection.prepareStatement(sql);
			pps.setInt(1, bno);

			int result = pps.executeUpdate();
			if (result > 0) {
				System.out.println(result + "건의 삭제가 완료되었습니다.");
			} else {
				throw new SQLException();
			}
			pps.close();

		} catch (SQLException e) {
			System.out.println("삭제실패. 관리자에게 문의하세요.");
			e.printStackTrace();
		}

	}

	public void search(Connection connection, MemberDTO loginMember) {// 내게시물 검색
		// 로그인 계정에 해당하는 게시물만 검색
		// BoardDTO myboardDTO = new BoardDTO(); 검색결과 넣을 새객체 생성
		try {
			String sql = "select * from board where bwriter = ? ";
			PreparedStatement pps = connection.prepareStatement(sql);
			pps.setString(1, loginMember.getMid());

			ResultSet rst = pps.executeQuery();

			while (rst.next()) {
				System.out.printf("%02d\t", rst.getInt("bno"));
				System.out.printf("%-10s\t", rst.getString("btitle"));
				System.out.printf("%-6s\t", rst.getString("bwriter"));
				System.out.printf("%10s\n", rst.getDate("bdate"));
			} // --while()

			rst.close();
			pps.close();
		} catch (SQLException e) {
			System.out.println("sql오류. 관리자에게 문의하세요.");
			e.printStackTrace();
		}

	}// --search()

}
