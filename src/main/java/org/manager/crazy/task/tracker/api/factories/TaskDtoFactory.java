package org.manager.crazy.task.tracker.api.factories;

import org.manager.crazy.task.tracker.api.dto.TaskDto;
import org.manager.crazy.task.tracker.api.dto.TaskStateDto;
import org.manager.crazy.task.tracker.store.entities.TaskEntity;
import org.manager.crazy.task.tracker.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoFactory {

    public TaskDto makeTaskDto(TaskEntity entity) {

        return TaskDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .build();

    }
}
