package com.mfc.auth.auth.vo.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SmsReqVo {
	@NotBlank
	private String phone;
	private String code;
}
