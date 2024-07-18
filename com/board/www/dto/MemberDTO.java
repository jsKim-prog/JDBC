package com.board.www.dto;

import java.sql.Date;

public class MemberDTO {
	// 필드
	private int mno;
	private String mname;
	private String mid;
	private String mpw;
	private Date mdate;
	// 필드-검증용
	private boolean usable;
	private boolean loginCheck;
	private Author author;

	// 생성자
	public MemberDTO() {

	} // 기본생성자 -> new MemberDTO();

	public MemberDTO(String loginID, String loginPW) {
		this.mid = loginID;
		this.mpw = loginPW;
	}// 커스텀 생성자->id와 pw 처리용

	public MemberDTO(Author author) {
		this.author = Author.GUEST;
		this.loginCheck = false;
	} // 게스트용 생성자-> new MemberDTO(Author.GUEST);

	// 메서드 -> 게터/세터
	public int getMno() {
		return mno;
	}

	public String getMid() {
		return mid;
	}

	public String getMpw() {
		return mpw;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMno(int mno) {
		this.mno = mno;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public void setMpw(String mpw) {
		this.mpw = mpw;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public boolean isUsable() {
		return usable;
	}

	public void setUsable(boolean usable) {
		this.usable = usable;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public boolean isLoginCheck() {
		return loginCheck;
	}

	public void setLoginCheck(boolean loginCheck) {
		this.loginCheck = loginCheck;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
