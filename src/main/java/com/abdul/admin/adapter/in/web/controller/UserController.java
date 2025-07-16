package com.abdul.admin.adapter.in.web.controller;

import com.abdul.admin.adapter.in.web.mapper.UserDtoMapper;
import com.abdul.admin.domain.user.port.in.GetUserDetailUseCase;
import com.abdul.admin.domain.user.port.in.GetUserUseCase;
import com.abdul.admin.dto.UserDetailResponse;
import com.abdul.admin.dto.UserResponse;
import com.abdul.toolkit.utils.user.port.in.GetUserDetailServiceUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserDtoMapper userDtoMapper;
    private final GetUserUseCase getUserUseCase;
    private final GetUserDetailUseCase getUserDetailUseCase;

    private final GetUserDetailServiceUseCase getUserDetailServiceUseCase1;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userDtoMapper.map(getUserUseCase.findAll()));
    }

    @GetMapping("/users/{searchTerm}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable String searchTerm) {
        getUserDetailServiceUseCase1.loadUserByUsername(searchTerm);
        return ResponseEntity.ok(
                userDtoMapper.map(getUserDetailUseCase.get(searchTerm))
        );
    }

    @GetMapping("/internal/users/{searchTerm}")
    public ResponseEntity<UserDetailResponse> getUserDetailsInternal(@PathVariable String searchTerm) {
        return ResponseEntity.ok(
                userDtoMapper.mapToUserDetailResponse(getUserDetailUseCase.get(searchTerm))
        );
    }
}
