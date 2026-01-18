package com.kesi.tracker.group.application.dto;

import com.kesi.tracker.group.domain.AccessType;
import lombok.Data;

import java.util.List;

@Data
public class GroupCreationRequest {
    private String name;
    private String introduction;
    private String description;
    private AccessType access;
    private List<Long> profileFileIds;
}
