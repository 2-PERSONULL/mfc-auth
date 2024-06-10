package com.mfc.auth.auth.vo.req;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpReqVo {
	@Email
	private String email;
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()])[A-Za-z\\d~!@#$%^&*()]{8,20}$",
			message = "비밀번호는 영대소문자, 숫자, 특수문자를 1개 이상 포함해야 하며, 8~20자여야 합니다.")
	private String password;
	@NotBlank
	private String name;
	@NotBlank
	private String nickname;
	@NotBlank
	private String phone;
	private LocalDate birth;
	private Short gender;
	private List<Long> favoriteStyles = new ArrayList();
	@NotBlank
	private String role;
}
