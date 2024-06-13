package com.mfc.auth.auth.vo.req;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ModifyPasswordReqVo {
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()])[A-Za-z\\d~!@#$%^&*()]{8,20}$",
		message = "비밀번호는 영대소문자, 숫자, 특수문자를 1개 이상 포함해야 하며, 8~20자여야 합니다.")
	private String password;
}
