package com.misomota.exam.service;

import com.misomota.exam.model.Subproject;
import com.misomota.exam.repository.SubprojectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubprojectService {
    private final SubprojectRepository subprojectRepository;

    public SubprojectService(SubprojectRepository subprojectRepository) {
        this.subprojectRepository = subprojectRepository;
    }

    public List<Subproject> showSubproject(int projectID) {
        return subprojectRepository.showSubproject(projectID);
    }

    public Subproject findSubprojectByID(int id) {
        return subprojectRepository.findSubprojectByID(id);
    }

    public Subproject addSubproject(Subproject subproject, int projectID) {
        return subprojectRepository.addSubproject(subproject, projectID);
    }

    public void deleteSubproject(int id) {
        subprojectRepository.deleteSubproject(id);
    }

    public void  updateSubproject(Subproject subproject, int projectID) {
        subprojectRepository.updateSubproject(subproject, projectID);
    }
}