package cz.lorsoft.administrationOfTheInsureds.models.dto;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuredEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InsuredMapper {
    InsuredEntity toEntity(InsuredDTO source);
    InsuredDTO toDTO(InsuredEntity source);
}
