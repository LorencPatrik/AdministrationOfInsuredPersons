package cz.lorsoft.administrationOfTheInsureds.models.dto;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuranceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InsuranceMapper {
    InsuranceEntity toEntity(InsuranceDTO source);
    InsuranceDTO toDTO(InsuranceEntity source);
    void updateInsuranceDTO(InsuranceDTO source, @MappingTarget InsuranceDTO target);
    void updateInsuranceEntity(InsuranceDTO source, @MappingTarget InsuranceEntity target);
}
