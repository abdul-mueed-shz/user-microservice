package com.abdul.admin.adapter.out.persistence.mapper;

import com.abdul.admin.adapter.out.persistence.entity.GoogleCredential;
import com.abdul.admin.domain.google.model.GoogleCredentialInfo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GoogleCredentialMapper {

    List<GoogleCredentialInfo> totoGoogleCredentialInfoList(List<GoogleCredential> credentials);

    GoogleCredential toGoogleCredential(GoogleCredentialInfo googleCredentialInfo);

    GoogleCredentialInfo toGoogleCredentialInfo(GoogleCredential googleCredential);
}
