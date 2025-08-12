package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    ModuleModel save(ModuleModel moduleModel);

    List<ModuleModel> findAllByCourse(UUID courseId);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    void delete(ModuleModel moduleModel);

    Optional<ModuleModel> findById(UUID moduleId);
}
