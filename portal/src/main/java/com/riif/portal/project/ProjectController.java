package com.riif.portal.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(dto));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll (@RequestParam (required = false) ProjectStatus status){
        if(status != null){
            return ResponseEntity.ok(projectService.getProjectByStatus(status));
        }
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getOne (@PathVariable UUID id){
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Project>> getByOwner (@PathVariable UUID ownerId){
        return ResponseEntity.ok(projectService.getProjectByOwner(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> update(@PathVariable UUID id , @RequestBody ProjectDTO dto){
        return ResponseEntity.ok(projectService.updateProject(id,dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
