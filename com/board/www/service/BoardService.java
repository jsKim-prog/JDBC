package com.board.www.service;

import java.sql.Connection;

import com.board.www.dao.BoardDAO;

public class BoardService {
	//board의 부메뉴(C,R,U,D, L)
	public void list(Connection connection) {//게시물 목록 보기
		BoardDAO boardDAO = new BoardDAO();
		System.out.println("========================================");
		System.out.println("=============== 대나무숲 게시판 =============");
		System.out.println("[게시물목록]");
		System.out.println("----------------------------------------");
		System.out.println("no.   Title          Writer       Date");
		System.out.println("----------------------------------------");
		
		boardDAO.list(connection);
		
		
		
		
		
	}

}
