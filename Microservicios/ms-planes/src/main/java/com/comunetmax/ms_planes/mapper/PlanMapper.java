package com.comunetmax.ms_planes.mapper;

import com.comunetmax.ms_planes.dto.PlanDTO;
import com.comunetmax.ms_planes.model.PlanInternet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlanMapper {

    PlanDTO toDto(PlanInternet plan);

    PlanInternet toEntity(PlanDTO planDto);
}