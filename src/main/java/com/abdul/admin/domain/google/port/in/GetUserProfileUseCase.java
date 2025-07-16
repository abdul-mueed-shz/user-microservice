package com.abdul.admin.domain.google.port.in;

import com.abdul.admin.domain.google.model.GoogleUserResponse;
import java.io.IOException;

public interface GetUserProfileUseCase {

    GoogleUserResponse execute(String userId) throws IOException;
}
