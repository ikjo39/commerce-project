package com.ikjo39.commerce.member.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberUpdatePassword {
	@NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
	private String password;
	@Size(min = 4, max = 20, message = "새 비밀번호는 4-20 길이로 입력해 주세요")
	@NotBlank(message = "새 비밀번호는 필수 항목입니다.")
	private String newPassword;
	@NotBlank(message = "새 확인 비밀번호는 필수 항목입니다.")
	private String reNewPassword;
}
