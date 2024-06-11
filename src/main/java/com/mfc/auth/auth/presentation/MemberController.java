package com.mfc.auth.auth.presentation;

import static com.mfc.auth.common.response.BaseResponseStatus.*;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.auth.auth.application.MemberService;
import com.mfc.auth.common.exception.BaseException;
import com.mfc.auth.common.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "members", description = "회원 서비스 컨트롤러 (토큰 필요)")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/resign")
	@Operation(summary = "회원 탈퇴 API", description = "유저/파트너 한 번에 탈퇴 (삭제)")
	public BaseResponse<Void> resign(@RequestHeader(value = "UUID", defaultValue = "") String uuid) {
		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}

		memberService.resign(uuid);
		return new BaseResponse<>();
	}
}
