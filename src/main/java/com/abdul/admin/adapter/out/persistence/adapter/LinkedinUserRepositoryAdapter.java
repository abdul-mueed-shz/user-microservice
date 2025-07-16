package com.abdul.admin.adapter.out.persistence.adapter;

import com.abdul.admin.adapter.out.persistence.mapper.LinkedinUserMapper;
import com.abdul.admin.adapter.out.persistence.repository.LinkedinUserJpaRepository;
import com.abdul.admin.domain.linkedin.port.out.repository.LinkedinUserRepository;
import com.abdul.toolkit.utils.linkedin.model.LinkedinUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LinkedinUserRepositoryAdapter implements LinkedinUserRepository {

    private final LinkedinUserJpaRepository linkedinUserJpaRepository;
    private final LinkedinUserMapper linkedinUserMapper;

    @Override
    public LinkedinUserInfo findByState(String state) {
        return linkedinUserMapper.map(linkedinUserJpaRepository.findByState(state));
    }
}
