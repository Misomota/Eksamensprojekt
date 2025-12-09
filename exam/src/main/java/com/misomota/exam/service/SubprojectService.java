package com.misomota.exam.service;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.model.Subproject;
import com.misomota.exam.repository.SubprojectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {
    private final SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    public List<Subproject> showSubproject(int projectID) {
        List<Subproject> subprojects = subprojectRepository.showSubproject(projectID);
        if (subprojects == null) {
            throw new NotFoundException("no subprojects found");
        }
        return subprojects;
    }

    public Subproject findSubprojectByID(int id) {
        Subproject subproject = subprojectRepository.findSubprojectByID(id);
        if (subproject == null) {
            throw new NotFoundException("no subproject found");
        }
        return subproject;
    }

    public Subproject addSubproject(Subproject subproject, int projectID) {
        try {
            return subprojectRepository.addSubproject(subproject, projectID);
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("Failed to create subproject: ", dataAccessException);
        }
    }

    public void deleteSubproject(int id) {
        try {
            int rows = subprojectRepository.deleteSubproject(id);
            if (rows == 0) throw new NotFoundException(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to delete subproject", e);
        }

        subprojectRepository.deleteSubproject(id);
    }

    public Subproject updateSubproject(Subproject subproject, int id) {
        try {
            Subproject existing = findSubprojectByID(id);
            existing.setSubprojectName(subproject.getSubprojectName());

            int rows = subprojectRepository.updateSubproject(existing);
            if (rows == 0) throw new NotFoundException(id);
            return existing;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to update subproject", e);
        }

    }
}