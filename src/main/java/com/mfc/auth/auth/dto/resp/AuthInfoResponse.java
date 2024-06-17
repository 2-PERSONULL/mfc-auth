package com.mfc.auth.auth.dto.resp;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthInfoResponse {
	private Short userGender;
	private LocalDate userBirth;
}
