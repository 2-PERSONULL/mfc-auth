package com.mfc.auth.auth.application;

import com.mfc.auth.auth.dto.req.SignInReqDto;
import com.mfc.auth.auth.dto.req.SignUpReqDto;
import com.mfc.auth.auth.dto.req.SmsReqDto;
import com.mfc.auth.auth.dto.resp.SignInRespDto;

public interface AuthService {
	void sendSms(SmsReqDto dto); // 문자 전송
	void verifyCode(SmsReqDto dto); // 문자 검증
	String signUp(SignUpReqDto dto); // 회원가입
	boolean verifyEmail(String email); // 이메일 중복 검증
	SignInRespDto signIn(SignInReqDto dto); // 로그인
}
