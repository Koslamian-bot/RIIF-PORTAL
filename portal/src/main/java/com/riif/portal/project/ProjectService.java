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
        List<Project> projects = getProjectByOwner(dto.getOwnerId());

        boolean hasActive = projects.stream().anyMatch(
                proj ->
                        proj.getStatus() == ProjectStatus.approved ||
                        proj.getStatus() == ProjectStatus.meeting_scheduled
        );

        if(hasActive){
            throw new RuntimeException("Users can have only one Active Project");
        }

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

    public Project approveProject(UUID id , String reviewNotes){
        Project project = getProjectById(id);
        if(project.getStatus() != ProjectStatus.pending && project.getStatus() != ProjectStatus.meeting_scheduled){
            throw new RuntimeException("Only Pending Projects or Projects that are scheduled for meet can be accepted/rejected");
        }

        int currentYear = java.time.Year.now().getValue();
        long approvedCount = projectRepository.countApprovedByYear(currentYear);
        String token = String.format("RIIF-%d-%s",currentYear,project.getOwnerId());

        project.setStatus(ProjectStatus.approved);
        project.setToken(token);
        project.setReviewNotes(reviewNotes);
        project.setUpdatedAt(OffsetDateTime.now());

        return projectRepository.save(project);
    }

    public Project rejectProject(UUID id , String reviewNotes){
        Project project = getProjectById(id);

        project.setStatus(ProjectStatus.rejected);
        project.setUpdatedAt(OffsetDateTime.now());
        project.setReviewNotes(reviewNotes);

        return projectRepository.save(project);
    }

    public Project scheduleMeeting (UUID id , java.time.OffsetDateTime meetingTime){
        Project project = getProjectById(id);

        if(project.getStatus() == ProjectStatus.rejected || project.getStatus() == ProjectStatus.approved){
            throw new RuntimeException("Meetings can be only scheduled to projects with pending status and not for rejected or approved status");
        }

        project.setStatus(ProjectStatus.meeting_scheduled);
        project.setUpdatedAt(OffsetDateTime.now());
        project.setMeetingTime(meetingTime);

        return projectRepository.save(project);
    }

}
