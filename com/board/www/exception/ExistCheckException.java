package com.board.www.exception;

public class ExistCheckException extends Exception {
	// 사용자정의 예외 - 동일한 id, 닉네임존재 시 ExistCheckException 발생
	// message : "사용할 수 없는 ID(닉네임)입니다."

	// 생성자1:기본생성자
	public ExistCheckException() {
	}

	// 생성자2:메시지 전달용 생성자
	public ExistCheckException(String message) {
		super(message);
	}
}
