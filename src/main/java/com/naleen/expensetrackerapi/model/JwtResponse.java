package com.naleen.expensetrackerapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class JwtResponse {

	private final String jwtToken;
}
