package com.lucasbarros.imageliteapi.application.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

	private String username;
	private String email;
	private String password;

}
