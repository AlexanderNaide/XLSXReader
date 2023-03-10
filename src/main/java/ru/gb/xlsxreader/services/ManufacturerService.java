package ru.gb.xlsxreader.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.Manufacturer;
import ru.gb.xlsxreader.repository.ManufacturerRepository;

import java.util.Optional;

@Service
public class ManufacturerService {
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public void setCategoryRepository(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    public Optional<Manufacturer> findManByName(String title){
        return manufacturerRepository.findByTitle(title);
    }

    public Manufacturer addMan(Manufacturer manufacturer){
        return manufacturerRepository.saveAndFlush(manufacturer);
    }

    public void flush(){
        manufacturerRepository.flush();
    }
}
