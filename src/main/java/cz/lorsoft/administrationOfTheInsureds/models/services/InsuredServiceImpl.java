package cz.lorsoft.administrationOfTheInsureds.models.services;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuredEntity;
import cz.lorsoft.administrationOfTheInsureds.data.repositories.InsuredRepository;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredDTO;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuredServiceImpl implements InsuredService{
    @Autowired
    private InsuredRepository insuredRepository;
    @Autowired
    private InsuredMapper insuredMapper;
    @Override
    public void create(InsuredDTO insuredDTO) {
        InsuredEntity newInsured = insuredMapper.toEntity(insuredDTO);
        insuredRepository.save(newInsured);
    }
}
