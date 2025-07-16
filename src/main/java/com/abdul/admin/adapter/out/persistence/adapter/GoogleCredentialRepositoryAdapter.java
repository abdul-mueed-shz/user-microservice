package com.abdul.admin.adapter.out.persistence.adapter;

import com.abdul.admin.adapter.out.persistence.entity.GoogleCredential;
import com.abdul.admin.adapter.out.persistence.mapper.GoogleCredentialMapper;
import com.abdul.admin.adapter.out.persistence.repository.GoogleCredentialJpaRepository;
import com.abdul.admin.domain.google.model.GoogleCredentialInfo;
import com.abdul.admin.domain.google.port.out.repository.GoogleCredentialRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GoogleCredentialRepositoryAdapter implements GoogleCredentialRepository {

    private final GoogleCredentialMapper googleCredentialMapper;

    private final GoogleCredentialJpaRepository googleCredentialJpaRepository;

    @Override
    public List<GoogleCredentialInfo> findAll() {
        return googleCredentialMapper.totoGoogleCredentialInfoList(googleCredentialJpaRepository.findAll());
    }

    @Override
    public GoogleCredentialInfo findByKey(String key) {
        Optional<GoogleCredential> googleCredentialOptional =
                googleCredentialJpaRepository.findByKey(key);
        return googleCredentialOptional.map(googleCredentialMapper::toGoogleCredentialInfo).orElse(null);
    }

    @Override
    public void save(GoogleCredentialInfo googleCredentialInfo) {
        googleCredentialJpaRepository.save(googleCredentialMapper.toGoogleCredential(googleCredentialInfo));
    }

    @Override
    public Boolean existsByCredentialsKey(String key) {
        return googleCredentialJpaRepository.existsByCredentialsKey(key);
    }

    @Override
    public Set<String> findAllKeys() {
        return googleCredentialJpaRepository.findAllKeys();
    }

    @Override
    public void deleteByCredentialsKey(String key) {
        googleCredentialJpaRepository.deleteByCredentialsKey(key);
    }

    @Override
    public Integer countAll() {
        return googleCredentialJpaRepository.countAll();
    }

    @Override
    public void deleteAll() {
        googleCredentialJpaRepository.deleteAll();
    }
}
