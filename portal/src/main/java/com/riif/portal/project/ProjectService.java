package com.riif.portal.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(ProjectDTO dto){
        Project project = new Project();
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setDomain(dto.getDomain());
        project.setOwnerId(dto.getOwnerId());
        project.setPptUrl(dto.getPptUrl());
        project.setDocUrls(dto.getDocUrls());
        project.setStatus(ProjectStatus.pending);
        project.setCreatedAt(OffsetDateTime.now());
        project.setUpdatedAt(OffsetDateTime.now());

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(UUID id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No data found"+id));
    }

    public List<Project> getProjectByStatus(ProjectStatus status){
        return projectRepository.findByStatus(status);
    }

    public List<Project> getProjectByOwner(UUID id){
        return projectRepository.findByOwnerId(id);
    }

    public Project updateProject(UUID id , ProjectDTO dto){
        Project project = getProjectById(id);

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setDomain(dto.getDomain());
        project.setPptUrl(dto.getPptUrl());
        project.setDocUrls(dto.getDocUrls());
        project.setUpdatedAt(OffsetDateTime.now());

        return projectRepository.save(project);
    }

    public void deleteProject(UUID id){
        projectRepository.deleteById(id);
    }
}
