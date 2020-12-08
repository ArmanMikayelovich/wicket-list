package com.mikayelovich.service.impl;

import com.mikayelovich.dao.IssueDao;
import com.mikayelovich.model.IssueDto;
import com.mikayelovich.model.IssueEntity;
import com.mikayelovich.service.IssueService;
import com.mikayelovich.util.enums.Mapper;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.ManagedBean;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service("issueService")
@Transactional(readOnly = true)
@ManagedBean
public class IssueServiceImpl implements IssueService {
    private final IssueDao issueDao;

    public IssueServiceImpl(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    @Override
    @Transactional
    public void save(IssueDto dto) {
        IssueEntity issueEntity = Mapper.dtoToEntity(dto);
        issueEntity.setCreatedAt(LocalDateTime.now());
        issueDao.save(issueEntity);
    }

    @Override
    public List<IssueDto> getAll() {
        return issueDao.getAll().stream()
                .map(Mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(IssueDto dto) {
        IssueEntity issueEntity = issueDao.findById(dto.getId());
        issueEntity.setStatus(dto.getStatus());
        issueEntity.setName(dto.getName());
        issueEntity.setSortPlace(dto.getSortPlace());
        issueDao.update(issueEntity);
    }

    @Override
    @Transactional
    public void SyncWithDatabase(List<IssueDto> dtoList) {
        dtoList.forEach(issueDTO -> {

            if (issueDTO.getId() == null) {
                save(issueDTO);

            } else if (issueDTO.isDeleted()) {
                issueDao.delete(issueDTO.getId());
            } else {
                update(issueDTO);
            }
        });
    }

    @Override
    public void delete(Long id) {
        issueDao.delete(id);
    }
}
