package com.mfc.auth.auth.presentation;

import static com.mfc.auth.common.response.BaseResponseStatus.*;

import org.modelmapper.ModelMapper;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.auth.auth.application.MemberService;
import com.mfc.auth.auth.dto.req.ModifyPasswordReqDto;
import com.mfc.auth.auth.vo.req.ModifyPasswordReqVo;
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
	private final ModelMapper modelMapper;

	@PostMapping("/resign")
	@Operation(summary = "회원 탈퇴 API", description = "유저/파트너 한 번에 탈퇴 (삭제)")
	public BaseResponse<Void> resign(@RequestHeader(value = "UUID", defaultValue = "") String uuid) {
		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}

		memberService.resign(uuid);
		return new BaseResponse<>();
	}

	@PutMapping("/password")
	@Operation(summary = "비밀번호 수정 API", description = "유저/파트너 공통 적용")
	public BaseResponse<Void> modifyPassword(
			@RequestHeader(name = "UUID", defaultValue = "") String uuid,
			@RequestBody @Validated ModifyPasswordReqVo vo) {

		if(!StringUtils.hasText(uuid)) {
			throw new BaseException(NO_REQUIRED_HEADER);
		}

		memberService.modifyPassword(uuid, modelMapper.map(vo, ModifyPasswordReqDto.class));
		return new BaseResponse<>();
	}
}
