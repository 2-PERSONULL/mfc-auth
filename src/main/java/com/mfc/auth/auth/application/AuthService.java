package com.mfc.auth.auth.application;

import com.mfc.auth.auth.dto.req.SmsReqDto;

public interface AuthService {
	void sendSms(SmsReqDto dto); // 문자 전송
	void verifyCode(SmsReqDto dto); // 문자 검증
}
