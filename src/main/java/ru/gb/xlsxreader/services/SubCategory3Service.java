package ru.gb.xlsxreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.SubCategory3;
import ru.gb.xlsxreader.repository.SubCategory3Repository;

import java.util.Optional;

@Service
public class SubCategory3Service {
    private SubCategory3Repository subCategory3Repository;

    @Autowired
    public void setCategoryRepository(SubCategory3Repository subCategory3Repository) {
        this.subCategory3Repository = subCategory3Repository;
    }

    public Optional<SubCategory3> findCatByName(String title){
        return subCategory3Repository.findByTitle(title);
    }

    public void addCat(SubCategory3 subCategory3){
        subCategory3Repository.save(subCategory3);
    }
}
