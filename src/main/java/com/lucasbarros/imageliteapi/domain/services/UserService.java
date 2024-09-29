package com.lucasbarros.imageliteapi.domain.services;

import com.lucasbarros.imageliteapi.domain.AccessToken;
import com.lucasbarros.imageliteapi.domain.entities.User;

public interface UserService {

	User getByEmail(String email);

	User save(User user);

	AccessToken authenticate(String email, String password);

}
