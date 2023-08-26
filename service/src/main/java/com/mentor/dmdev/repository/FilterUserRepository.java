package com.mentor.dmdev.repository;

import com.mentor.dmdev.dto.filters.UserFilter;
import com.mentor.dmdev.entity.User;
import lombok.NonNull;

import java.util.List;

public interface FilterUserRepository {

    @NonNull
    List<User> findAllByFilter(@NonNull UserFilter userFilter);
}