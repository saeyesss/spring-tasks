package com.saeyesss.Tasks.service;

import com.saeyesss.Tasks.dto.Response;
import com.saeyesss.Tasks.dto.UserRequest;
import com.saeyesss.Tasks.entity.User;

public interface UserService {
    Response<?> signUp(UserRequest userRequest);

    Response<?> login(UserRequest userRequest);

    User getCurrentLoggedInUser();

}
