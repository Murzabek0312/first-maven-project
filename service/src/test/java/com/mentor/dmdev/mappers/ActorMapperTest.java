package com.mentor.dmdev.mappers;

import com.mentor.dmdev.dto.ActorReadDto;
import com.mentor.dmdev.entity.Actor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ActorMapperTest {

    private final ActorMapper mapper = new ActorMapperImpl();

    @Test
    void shouldMapFromActorToActorReadDto() {
        // Given:
        var id = 123L;
        var firstName = "firstName";
        var secondName = "secondName";
        var birthDate = LocalDate.of(2023, 11, 3);
        var biography = "biography";

        Actor actor = Actor.builder()
                .id(id)
                .firstname(firstName)
                .secondname(secondName)
                .birthDate(birthDate)
                .biography(biography)
                .build();

        // When:
        ActorReadDto actualResult = mapper.map(actor);

        // Then:
        assertEquals(id, actualResult.getId());
        assertEquals(firstName, actualResult.getFirstname());
        assertEquals(secondName, actualResult.getSecondname());
        assertEquals(birthDate, actualResult.getBirthDate());
        assertEquals(biography, actualResult.getBiography());
    }

    @Test
    void shouldMapFromListActorToListActorReadDto() {
        // Given-When:
        List<ActorReadDto> actualResult = mapper.map(List.of(mock(Actor.class), mock(Actor.class)));

        // Then:
        assertEquals(2, actualResult.size());
    }
}