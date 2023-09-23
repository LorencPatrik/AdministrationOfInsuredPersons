package cz.lorsoft.administrationOfTheInsureds.models.services;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuredEntity;
import cz.lorsoft.administrationOfTheInsureds.data.repositories.InsuredRepository;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredDTO;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuredMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<InsuredDTO> getAll() {
        List<InsuredDTO> insureds = new ArrayList<>();
        Iterable<InsuredEntity> fetchedInsureds = insuredRepository.findAll();
        for (InsuredEntity insuredEntity : fetchedInsureds){
            InsuredDTO mappedInsured = insuredMapper.toDTO(insuredEntity);
            insureds.add(mappedInsured);
        }
        return insureds;
    }

    @Override
    public InsuredDTO getById(long insuredId) {
        InsuredEntity fetchedInsured = getInsuredOrThrow(insuredId);
        return insuredMapper.toDTO(fetchedInsured);
    }

    @Override
    public void edit(InsuredDTO insuredDTO) {
        InsuredEntity fetchedInsured = getInsuredOrThrow(insuredDTO.getInsuredId());
        insuredMapper.updateInsuredEntity(insuredDTO, fetchedInsured);
        insuredRepository.save(fetchedInsured);

    }

    private InsuredEntity getInsuredOrThrow(long insuredId){
        return insuredRepository
                .findById(insuredId)
                .orElseThrow();
    }
}
