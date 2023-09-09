package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.ActorReadDto;
import com.mentor.dmdev.mappers.ActorMapper;
import com.mentor.dmdev.repository.ActorRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @NonNull
    public List<ActorReadDto> findAllByIds(@NonNull List<Long> ids) {
        return actorMapper.map(actorRepository.findAllByIds(ids));
    }
}