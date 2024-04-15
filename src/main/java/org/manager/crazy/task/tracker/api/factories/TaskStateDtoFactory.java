package org.manager.crazy.task.tracker.api.factories;

import org.manager.crazy.task.tracker.api.dto.ProjectDto;
import org.manager.crazy.task.tracker.api.dto.TaskStateDto;
import org.manager.crazy.task.tracker.store.entities.ProjectEntity;
import org.manager.crazy.task.tracker.store.entities.TaskEntity;
import org.manager.crazy.task.tracker.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDtoFactory {
    public TaskStateDto makeTaskStateDto(TaskStateEntity entity) {

        return TaskStateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .ordinal(String.valueOf(entity.getOrdinal()))
                .createdAt(entity.getCreatedAt())
                .build();

    }
}
