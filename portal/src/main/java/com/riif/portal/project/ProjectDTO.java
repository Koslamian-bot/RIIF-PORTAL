package com.riif.portal.project;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectDTO {
    private String title;
    private String description;
    private String domain;
    private UUID ownerId;
    private String pptUrl;
    private List<String> docUrls;

}
