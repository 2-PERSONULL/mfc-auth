package com.mfc.auth.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SmsReqDto {
	private String phone;
	private String code;
}
