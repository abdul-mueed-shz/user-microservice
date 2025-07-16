package com.abdul.admin.domain.google.datastore;

import com.abdul.admin.domain.google.model.GoogleCredentialInfo;
import com.abdul.admin.domain.google.port.out.repository.GoogleCredentialRepository;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class JPADataStore extends AbstractDataStore<StoredCredential> {

    private final GoogleCredentialRepository googleCredentialRepository;
    private final DataStoreFactory dataStoreFactory;

    /**
     * @param dataStoreFactory data store factory
     * @param id               data store ID
     */
    public JPADataStore(DataStoreFactory dataStoreFactory, String id,
            GoogleCredentialRepository googleCredentialRepository) {
        super(dataStoreFactory, id);
        this.googleCredentialRepository = googleCredentialRepository;
        this.dataStoreFactory = dataStoreFactory;
    }

    @Override
    public DataStoreFactory getDataStoreFactory() {
        return dataStoreFactory;
    }

    @Override
    public int size() {
        return googleCredentialRepository.countAll();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(String key) {
        return googleCredentialRepository.existsByCredentialsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return googleCredentialRepository.findAllKeys();
    }

    @Override
    public Collection<StoredCredential> values() {
        return googleCredentialRepository.findAll().stream().map(googleCredentialInfo -> {
            StoredCredential credential = new StoredCredential();
            credential.setAccessToken(googleCredentialInfo.getAccessToken());
            credential.setRefreshToken(googleCredentialInfo.getRefreshToken());
            credential.setExpirationTimeMilliseconds(googleCredentialInfo.getExpirationTimeMilliseconds());
            return credential;
        }).toList();
    }

    @Override
    public StoredCredential get(String key) {
        GoogleCredentialInfo googleCredentialInfo = googleCredentialRepository.findByKey(key);
        if (Objects.nonNull(googleCredentialInfo)) {
            return new StoredCredential()
                    .setAccessToken(googleCredentialInfo.getAccessToken())
                    .setRefreshToken(googleCredentialInfo.getRefreshToken())
                    .setExpirationTimeMilliseconds(googleCredentialInfo.getExpirationTimeMilliseconds());
        }
        return null;
    }

    @Override
    public DataStore<StoredCredential> set(String key, StoredCredential storedCredential) throws IOException {
        GoogleCredentialInfo googleCredentialInfo = googleCredentialRepository.findByKey(key);
        if (Objects.nonNull(googleCredentialInfo)) {
            googleCredentialInfo.setAccessToken(storedCredential.getAccessToken());
            googleCredentialInfo.setRefreshToken(storedCredential.getRefreshToken());
            googleCredentialInfo.setExpirationTimeMilliseconds(storedCredential.getExpirationTimeMilliseconds());
            googleCredentialRepository.save(googleCredentialInfo);
        } else {
            googleCredentialRepository.save(
                    GoogleCredentialInfo.builder()
                            .credentialsKey(key)
                            .accessToken(storedCredential.getAccessToken())
                            .expirationTimeMilliseconds(storedCredential.getExpirationTimeMilliseconds())
                            .refreshToken(storedCredential.getRefreshToken()).build()
            );
        }
        return this;
    }

    @Override
    public DataStore<StoredCredential> clear() {
        googleCredentialRepository.deleteAll();
        return this;
    }

    @Override
    public DataStore<StoredCredential> delete(String key) {
        googleCredentialRepository.deleteByCredentialsKey(key);
        return this;
    }
}