package com.example.work.entity;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table("user_coordinates")
public class UserCoordinatesEntity implements Persistable<Integer> {
    @Id
    Integer userId;
    Double latitude;
    Double longitude;

    @Transient
    boolean isNew;

    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
