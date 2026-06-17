package com.riif.portal.project;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;
    private String domain;

    @Column(name = "owner_id")
    private UUID ownerId;

    @Column(name = "ppt_url")
    private String pptUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="doc_urls",columnDefinition = "jsonb")
    private List<String> docUrls;


    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "project_status")
    private ProjectStatus status = ProjectStatus.pending;

    private String token;

    @Column(name = "reviewer_id")
    private UUID reviewerId;

    @Column(name = "review_notes")
    private String reviewNotes;

    @Column(name = "meeting_time")
    private OffsetDateTime meetingTime;

    @Column(name = "created_at" , updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;


}
