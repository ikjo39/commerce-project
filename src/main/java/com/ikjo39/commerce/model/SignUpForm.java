package com.ikjo39.commerce.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpForm {

	@Email(message = "이메일 형식에 맞게 입력해 주세요.")
	@NotBlank(message = "아이디는 필수 항목 합니다.")
	private String email;
	@NotBlank(message = "이름은 필수 항목 합니다.")
	private String name;
	@NotBlank(message = "비밀번호는 필수 항목 합니다.")
	private String password;
	@NotBlank(message = "연락처는 필수 항목 합니다.")
	private String phoneNumber;
}
