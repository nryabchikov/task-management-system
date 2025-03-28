package ru.effectivemobile.task_management_system.mapper;

import org.mapstruct.Mapper;
import ru.effectivemobile.task_management_system.dto.TaskRequest;
import ru.effectivemobile.task_management_system.dto.TaskResponse;
import ru.effectivemobile.task_management_system.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTask(TaskRequest taskRequest);

    TaskResponse toTaskResponse(Task task);
}
