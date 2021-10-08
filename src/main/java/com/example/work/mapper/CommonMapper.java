package com.example.work.mapper;

import com.example.work.entity.CourseEntity;
import com.example.work.entity.UserEntity;
import com.example.work.entity.UserSettingsEntity;
import com.example.work.response.body.Course;
import com.example.work.response.body.LecturerRegisteredBy;
import com.example.work.response.body.UserSettingResponseBodyPart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonMapper {
    List<Course> map(List<CourseEntity> courseEntities);
    Course map(CourseEntity entity);
    UserSettingResponseBodyPart map(UserSettingsEntity entity);
    List<UserSettingResponseBodyPart> mapToUserSettingResponseBodyPartList(List<UserSettingsEntity> entity);
    LecturerRegisteredBy map(UserEntity userEntity);
}
