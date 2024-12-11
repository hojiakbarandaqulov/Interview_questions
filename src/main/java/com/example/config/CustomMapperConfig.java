package com.example.config;

import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import org.mapstruct.MapperConfig;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
@MapperConfig
public class CustomMapperConfig {
    public static ModelMapper customModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<ProfileEntity, ProfileDTO>() {
            @Override
            protected void configure() {
                map().setEmail(source.getEmail());  // Maxsus moslashtirish logikasi
            }
        });
        return modelMapper;
    }
}
