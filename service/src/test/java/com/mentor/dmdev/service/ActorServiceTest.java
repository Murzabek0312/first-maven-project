package com.mentor.dmdev.service;

import com.mentor.dmdev.dto.ActorReadDto;
import com.mentor.dmdev.entity.Actor;
import com.mentor.dmdev.mappers.ActorMapper;
import com.mentor.dmdev.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorService actorService;

    @Test
    void shouldFindAllByIds() {
        // Given:
        var ids = List.of(12L, 13L);
        var actor1 = mock(Actor.class);
        var actor2 = mock(Actor.class);
        var actorReadDto1 = mock(ActorReadDto.class);
        var actorReadDto2 = mock(ActorReadDto.class);
        var actorReadDtoList = List.of(actorReadDto1, actorReadDto2);
        var actors = List.of(actor1, actor2);

        doReturn(actors).when(actorRepository).findAllByIds(ids);
        doReturn(actorReadDtoList).when(actorMapper).map(actors);

        // When:
        var actualResult = actorService.findAllByIds(ids);

        // Then:
        assertEquals(actorReadDtoList, actualResult);
    }
}