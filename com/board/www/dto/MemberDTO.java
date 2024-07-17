package com.board.www.dto;

import java.sql.Date;

public class MemberDTO {
	// 필드
	private int mno;
	private String mid;
	private String mpw;
	private Date mdate;
	private boolean usable;

	// 생성자
	public MemberDTO() {

	} // 기본생성자 -> new MemberDTO();

	public MemberDTO(String loginID, String loginPW) {
		this.mid = loginID;
		this.mpw = loginPW;
	}// 커스텀 생성자->id와 pw 처리용

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
	
	

}
