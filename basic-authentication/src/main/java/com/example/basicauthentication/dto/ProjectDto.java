package com.example.basicauthentication.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectDto {
    private String name;
    private String intro;
    private Long owner;
    private String status;
    private String startDate;
    private String endDate;
    private List<Long> membersList;


}
