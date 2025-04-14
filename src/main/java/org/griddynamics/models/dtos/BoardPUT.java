package org.griddynamics.models.dtos;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;

    @Data
    @Builder
    public class BoardPUT {
        private @NonNull String name;
        private @NonNull String desc;

}
