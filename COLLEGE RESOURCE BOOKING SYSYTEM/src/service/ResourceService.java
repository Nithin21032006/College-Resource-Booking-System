package service;

import model.Resource;
import java.util.ArrayList;
import java.util.List;

public class ResourceService {
    private List<Resource> resources;

    public ResourceService() { resources = new ArrayList<>(); }

    public void addResource(Resource resource) { resources.add(resource); }

    public boolean deleteResource(String resourceId) {
        return resources.removeIf(r -> r.getResourceId().equals(resourceId));
    }

    public Resource getResourceById(String resourceId) {
        for (Resource r : resources) {
            if (r.getResourceId().equals(resourceId)) return r;
        }
        return null;
    }

    public List<Resource> getAllResources() { return resources; }
}
