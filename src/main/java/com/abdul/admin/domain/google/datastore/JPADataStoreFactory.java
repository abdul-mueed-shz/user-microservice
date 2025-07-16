package com.abdul.admin.domain.google.datastore;

import com.abdul.admin.domain.google.port.out.repository.GoogleCredentialRepository;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStoreFactory;
import com.google.api.client.util.store.DataStore;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JPADataStoreFactory extends AbstractDataStoreFactory {

    private final GoogleCredentialRepository googleCredentialRepository;

    @Override
    protected DataStore<StoredCredential> createDataStore(String id) {
        return new JPADataStore(this, id, googleCredentialRepository);
    }
}