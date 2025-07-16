package com.abdul.admin.adapter.out.persistence.repository;

import com.abdul.admin.adapter.out.persistence.entity.LinkedinUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkedinUserJpaRepository extends JpaRepository<LinkedinUser, Long> {

    LinkedinUser findByState(String state);
}
