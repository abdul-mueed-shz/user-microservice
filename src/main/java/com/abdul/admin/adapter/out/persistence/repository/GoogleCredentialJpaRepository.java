package com.abdul.admin.adapter.out.persistence.repository;

import com.abdul.admin.adapter.out.persistence.entity.GoogleCredential;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GoogleCredentialJpaRepository extends JpaRepository<GoogleCredential, String> {

    @Query("SELECT GC FROM GoogleCredential GC WHERE GC.credentialsKey = :key ")
    Optional<GoogleCredential> findByKey(String key);

    Boolean existsByCredentialsKey(String key);

    @Query("SELECT GC.credentialsKey FROM GoogleCredential GC")
    Set<String> findAllKeys();

    void deleteByCredentialsKey(String key);

    @Query("SELECT count(GC) FROM GoogleCredential GC")
    Integer countAll();
}