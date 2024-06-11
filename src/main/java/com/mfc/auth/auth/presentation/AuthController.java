package com.mfc.auth.auth.presentation;

import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfc.auth.auth.application.AuthService;
import com.mfc.auth.auth.dto.req.SignInReqDto;
import com.mfc.auth.auth.dto.req.SignUpReqDto;
import com.mfc.auth.auth.dto.req.SmsReqDto;
import com.mfc.auth.auth.dto.resp.SignInRespDto;
import com.mfc.auth.auth.vo.req.SignInReqVo;
import com.mfc.auth.auth.vo.req.SignUpReqVo;
import com.mfc.auth.auth.vo.req.SmsReqVo;
import com.mfc.auth.auth.vo.resp.SignInRespVo;
import com.mfc.auth.common.response.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "auth", description = "인증 서비스 컨트롤러")
public class AuthController {
	private final AuthService authService;
	private final ModelMapper modelMapper;

	@PostMapping("/sms/send")
	@Operation(summary = "휴대폰 인증 문자 전송 API", description = "인증번호 전송 (번호에 - 제거)")
	public BaseResponse<Void> sendSms(@RequestBody @Validated SmsReqVo vo) {
		authService.sendSms(modelMapper.map(vo, SmsReqDto.class));
		return new BaseResponse<>();
	}

	@PostMapping("/sms/verify")
	@Operation(summary = "휴대폰 인증 문자 검증 API", description = "인증번호 검증 (6자리, 유효시간 5분)")
	public BaseResponse<Void> verifySms(@RequestBody SmsReqVo vo) {
		authService.verifyCode(modelMapper.map(vo, SmsReqDto.class));
		return new BaseResponse<>();
	}

	@PostMapping("/signup")
	@Operation(summary = "회원가입 API", description = "회원가입")
	public BaseResponse<String> signUp(
			@RequestBody @Validated SignUpReqVo vo) {
		return new BaseResponse<>(authService.signUp(modelMapper.map(vo, SignUpReqDto.class)));
	}

	@GetMapping("/email/{email}")
	@Operation(summary = "이메일 중복 확인 API", description = "이메일 중복 확인")
	public BaseResponse<Boolean> verifyEmail(@PathVariable String email) {
		return new BaseResponse<>(authService.verifyEmail(email));
	}

	@PostMapping("/signin")
	@Operation(summary = "로그인 API", description = "로그인 성공 시 토큰, uuid, partnerCode 반환")
	public BaseResponse<SignInRespVo> signIn(@RequestBody SignInReqVo vo, HttpServletResponse resp) {
		SignInRespDto dto = authService.signIn(modelMapper.map(vo, SignInReqDto.class));
		resp.addHeader("accessToken", dto.getAccessToken());
		resp.addHeader("refreshToken", dto.getRefreshToken());
		resp.addHeader("uuid", dto.getUuid());

		return new BaseResponse<>(
				modelMapper.map(dto, SignInRespVo.class)
		);
	}
}
