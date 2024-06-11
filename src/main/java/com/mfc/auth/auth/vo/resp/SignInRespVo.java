package com.mfc.auth.auth.vo.resp;

import lombok.Getter;

@Getter
public class SignInRespVo {
	private String uuid;
	private String accessToken;
	private String refreshToken;
}
