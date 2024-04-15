package org.manager.crazy.task.tracker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String ordinal;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;
}
