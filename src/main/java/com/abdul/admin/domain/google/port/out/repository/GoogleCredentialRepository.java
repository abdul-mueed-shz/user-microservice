package com.abdul.admin.domain.google.port.out.repository;

import com.abdul.admin.domain.google.model.GoogleCredentialInfo;
import java.util.List;
import java.util.Set;

public interface GoogleCredentialRepository {

    GoogleCredentialInfo findByKey(String key);

    Boolean existsByCredentialsKey(String key);

    Set<String> findAllKeys();

    void deleteByCredentialsKey(String key);

    Integer countAll();

    List<GoogleCredentialInfo> findAll();

    void save(GoogleCredentialInfo credential);

    void deleteAll();
}
