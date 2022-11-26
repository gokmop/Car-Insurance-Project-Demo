package com.safetycar.repositories;

import com.safetycar.models.Image;
import com.safetycar.repositories.base.SafetyCarRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends SafetyCarRepository<Image,Integer> {
}
