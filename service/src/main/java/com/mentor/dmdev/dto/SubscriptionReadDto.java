package com.mentor.dmdev.dto;

import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionReadDto {

    private Long id;

    private SubscriptionTypes type;

    private SubscriptionStatus status;
}