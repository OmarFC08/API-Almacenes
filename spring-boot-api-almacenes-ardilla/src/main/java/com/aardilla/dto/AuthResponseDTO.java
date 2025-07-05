package com.aardilla.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {

	private String token;

	public AuthResponseDTO(String token) {
		super();
		this.token = token;
	}
}
