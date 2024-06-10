package com.mfc.auth.auth.dto.kafka;

import java.util.List;

import com.mfc.auth.auth.dto.req.SignUpReqDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileDto {
	private String uuid;
	private String nickname;
	private List<Long> favoriteStyles;
	private String role;

	public static ProfileDto toBuild(SignUpReqDto dto, String uuid) {
		return ProfileDto.builder()
				.uuid(uuid)
				.nickname(dto.getNickname())
				.favoriteStyles(dto.getFavoriteStyles())
				.role(dto.getRole())
				.build();
	}
}
