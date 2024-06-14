package com.mfc.auth.auth.dto.kafka;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthInfoResponse {
	private String userId;
	private Short userGender;
	private LocalDate userBirth;
}
