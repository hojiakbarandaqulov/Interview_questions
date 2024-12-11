package com.example.mapper;

import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileEntity profileToProfileEntity(ProfileDTO profile);

    ProfileDTO profileEntityToProfileDTO(ProfileEntity profile);
}
