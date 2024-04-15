package org.manager.crazy.task.tracker.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.manager.crazy.task.tracker.api.dto.AckDto;
import org.manager.crazy.task.tracker.api.dto.ProjectDto;
import org.manager.crazy.task.tracker.api.exceptions.BadRequestException;
import org.manager.crazy.task.tracker.api.exceptions.NotFoundException;
import org.manager.crazy.task.tracker.api.factories.ProjectDtoFactory;
import org.manager.crazy.task.tracker.store.entities.ProjectEntity;
import org.manager.crazy.task.tracker.store.repositories.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RestController
public class ProjectController {

    ProjectRepository projectRepository;

    ProjectDtoFactory projectDtoFactory;

    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects{project_id}";
    public static final String DELETE_PROJECT = "/api/projects{project_id}";


    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDto> fetchProjects(
            @RequestParam(value = "prefix_name", required = false)Optional<String> optionalPrefixName) {

        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAll);

        return projectStream
                .map(projectDtoFactory::makeProjectDto)
                .collect(Collectors.toList());
    }

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(
            @RequestParam String name) {
        if(name.trim().isEmpty()){
            throw new BadRequestException("Name can't be empty.");
        }


        projectRepository
                .findByName(name)
                .ifPresent(project -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });

        ProjectEntity project = projectRepository.saveAndFlush(
                ProjectEntity.builder()
                        .name(name)
                        .build()
        );

        return projectDtoFactory.makeProjectDto(project);
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(
            @PathVariable("project_id") Long projectId,
            @RequestParam String name) {
        ProjectEntity project = projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Project with \"%s\" doesn't exist.", projectId))
                );

        projectRepository
                .findByName(name)
                .filter(anotherProject -> !Objects.equals(anotherProject.getId(), projectId))
                .ifPresent(anotherProject -> {
                    throw new BadRequestException(String.format("Project \"%s\" already exists.", name));
                });

        project.setName(name);

        project = projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeProjectDto(project);
    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDto deleteProject(@PathVariable("project_id") Long projectId){

        projectRepository
                .findById(projectId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Project with \"%s\" doesn't exist.", projectId))
                );

        projectRepository.deleteById(projectId);

        return AckDto.makeDefault(true);
    }
}
