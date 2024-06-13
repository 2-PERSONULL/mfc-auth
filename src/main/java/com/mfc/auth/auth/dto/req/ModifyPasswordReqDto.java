package com.mfc.auth.auth.dto.req;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ModifyPasswordReqDto {
	private String password;
}
