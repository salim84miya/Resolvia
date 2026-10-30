package com.salim.actora.auth.mapper;

import com.salim.actora.auth.dto.UserDto;
import com.salim.actora.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "password" ,ignore = true)
    UserDto mapUserToUserResponse(User user);

}
