package com.ncc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CronjobRequest {
    private String cronExpression;

    private String jobCommand;

    private Map<String, String> jobParameters;
}
