package ru.gb.xlsxreader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.xlsxreader.model.SubCategory2;
import ru.gb.xlsxreader.repository.SubCategory2Repository;

import java.util.Optional;

@Service
public class SubCategory2Service {
    private SubCategory2Repository subCategory2Repository;

    @Autowired
    public void setCategoryRepository(SubCategory2Repository subCategory2Repository) {
        this.subCategory2Repository = subCategory2Repository;
    }

    public Optional<SubCategory2> findCatByName(String title){
        return subCategory2Repository.findByTitle(title);
    }

    public void addCat(SubCategory2 subCategory2){
        subCategory2Repository.save(subCategory2);
    }
}
