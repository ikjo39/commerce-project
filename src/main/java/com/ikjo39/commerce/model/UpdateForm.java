package com.ikjo39.commerce.model;

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
public class UpdateForm {

	private String phoneNumber;
	private String zipCode;
	private String address;
	private String addressDetail;


}
