package com.riif.portal.project;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class ReviewDTO {
    private String reviewNotes;
    private OffsetDateTime meetingTime;
}
