package cz.lorsoft.administrationOfTheInsureds.models.services;

import cz.lorsoft.administrationOfTheInsureds.data.entities.InsuranceEntity;
import cz.lorsoft.administrationOfTheInsureds.data.repositories.InsuranceRepository;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuranceDTO;
import cz.lorsoft.administrationOfTheInsureds.models.dto.InsuranceMapper;
import cz.lorsoft.administrationOfTheInsureds.models.exceptions.InsuranceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsuranceServiceImpl implements InsuranceService{
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private InsuranceMapper insuranceMapper;
    @Override
    public void create(InsuranceDTO insuranceDTO) {
        InsuranceEntity newInsurance = insuranceMapper.toEntity(insuranceDTO);
        insuranceRepository.save(newInsurance);
    }

    @Override
    public List<InsuranceDTO> getAll() {
        List<InsuranceDTO> allInsurance = new ArrayList<>();
        Iterable<InsuranceEntity> fetchedAllInsurance = insuranceRepository.findAll();
        for (InsuranceEntity insuranceEntity : fetchedAllInsurance){
            InsuranceDTO mappedInsurance = insuranceMapper.toDTO(insuranceEntity);
            allInsurance.add(mappedInsurance);
        }
        return allInsurance;
    }

    @Override
    public InsuranceDTO getById(long insuranceId) {
        InsuranceEntity fetchedInsurance = getInsuranceOrThrow(insuranceId);
        return insuranceMapper.toDTO(fetchedInsurance);
    }

    @Override
    public void edit(InsuranceDTO insuranceDTO) {
        InsuranceEntity fetchedInsurance = getInsuranceOrThrow(insuranceDTO.getInsuranceId());
        insuranceMapper.updateInsuranceEntity(insuranceDTO, fetchedInsurance);
        insuranceRepository.save(fetchedInsurance);
    }

    @Override
    public void delete(long insuranceId) {
        InsuranceEntity fetchedEntity = getInsuranceOrThrow(insuranceId);
        insuranceRepository.delete(fetchedEntity);
    }

    private InsuranceEntity getInsuranceOrThrow(long insuranceId){
        return insuranceRepository
                .findById(insuranceId)
                .orElseThrow(InsuranceNotFoundException :: new);
    }

}
