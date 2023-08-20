package com.mentor.dmdev.dto;

import com.mentor.dmdev.enums.SubscriptionStatus;
import com.mentor.dmdev.enums.SubscriptionTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionCreateEditDto {

    @NotNull
    private SubscriptionTypes type;

    @NotNull
    private SubscriptionStatus status;
}