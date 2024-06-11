package com.mfc.auth.auth.dto.kafka;

import java.util.List;

import com.mfc.auth.auth.dto.req.SignUpReqDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InsertProfileDto {
	private String uuid;
	private String nickname;
	private List<Long> favoriteStyles;
	private String role;

	public static InsertProfileDto toBuild(SignUpReqDto dto, String uuid) {
		return InsertProfileDto.builder()
				.uuid(uuid)
				.nickname(dto.getNickname())
				.favoriteStyles(dto.getFavoriteStyles())
				.role(dto.getRole())
				.build();
	}
}
