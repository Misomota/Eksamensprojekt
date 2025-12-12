package com.misomota.exam.service;

import com.misomota.exam.DRY.DatabaseOperationException;
import com.misomota.exam.DRY.NotFoundException;
import com.misomota.exam.model.Resource;
import com.misomota.exam.repository.ResourceRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource createResource(Resource resource) {
        try {
            return resourceRepository.createResource(resource);
        } catch (DataAccessException dataAccessException) {
            throw new DatabaseOperationException("failed to create resource", dataAccessException);
        }
    }

    public List<Resource> readResources(int taskId) {
       List<Resource> resourceList = resourceRepository.readResources(taskId);
       if (resourceList == null) {
           throw new NotFoundException("No resource found");
       }
        return resourceList;
    }

    public Resource findResourceByID(int id) {
        Resource resource = resourceRepository.findResourceByID(id);
        if (resource == null) {
            throw new NotFoundException("No resources found");
        }
        return resource;
    }

    public Resource updateResource(Resource resource, int id) {
        try {
            Resource existing = findResourceByID(id);
            existing.setResourceName(resource.getResourceName());
            existing.setTaskID(resource.getTaskID());

            int rows = resourceRepository.updateResources(existing);
            if (rows == 0) throw new NotFoundException(id);
            return existing;
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to update resource", e);
        }
    }

    public void deleteResource(int resourceId) {
        try {
            resourceRepository.deleteResource(resourceId);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Failed to delete resource", e);
        }
    }
}

