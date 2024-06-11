package com.mfc.auth.auth.dto.kafka;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteProfileDto {
	private String uuid;
}
