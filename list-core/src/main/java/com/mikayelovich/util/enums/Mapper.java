package com.mikayelovich.util.enums;

import com.mikayelovich.model.IssueDto;
import com.mikayelovich.model.IssueEntity;

public class Mapper {

    public static IssueEntity dtoToEntity(IssueDto dto) {
        IssueEntity issueEntity = new IssueEntity();
        issueEntity.setName(dto.getName());
        issueEntity.setStatus(dto.getStatus());
        issueEntity.setSortPlace(dto.getSortPlace());
        issueEntity.setCreatedAt(dto.getCreatedAt());
        return issueEntity;
    }



    public static IssueDto entityToDto(IssueEntity entity) {
        IssueDto issueDto = new IssueDto();
        issueDto.setId(entity.getId());
        issueDto.setName(entity.getName());
        issueDto.setStatus(entity.getStatus());
        issueDto.setCreatedAt(entity.getCreatedAt());
        issueDto.setSortPlace(entity.getSortPlace());
        return issueDto;
    }
}
