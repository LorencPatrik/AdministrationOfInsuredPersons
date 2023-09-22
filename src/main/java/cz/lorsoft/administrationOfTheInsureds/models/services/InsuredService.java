package cz.lorsoft.administrationOfTheInsureds.models.services;

import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredDTO;

import java.util.List;

public interface InsuredService {
    void create(InsuredDTO insuredDTO);
    List<InsuredDTO> getAll();
    InsuredDTO getById(long insuredId);
}
