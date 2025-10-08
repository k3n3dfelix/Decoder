package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    ModuleModel save(ModuleModel moduleModel);

    Page<ModuleModel> findAllByCourse(Specification<ModuleModel> spec, Pageable pageable);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    void delete(ModuleModel moduleModel);

    Optional<ModuleModel> findById(UUID moduleId);
}
