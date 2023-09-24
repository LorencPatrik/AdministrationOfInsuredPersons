package cz.lorsoft.administrationOfTheInsureds.models.services;

import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuranceDTO;

import java.util.List;

public interface InsuranceService {
    void create(InsuranceDTO insuranceDTODTO);
    List<InsuranceDTO> getAll();
    InsuranceDTO getById(long insuranceId);
    void edit(InsuranceDTO insuranceDTO);
    void delete(long insuranceId);
}

