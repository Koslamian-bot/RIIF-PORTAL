package com.riif.portal.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findByOwnerId(UUID ownerId);
    List<Project> findByStatus(ProjectStatus status);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status='approved' AND YEAR(p.createdAt)= :year")
    long countApprovedByYear(@Param("year") int year);
}
