package ru.gb.xlsxreader.controllers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gb.xlsxreader.model.*;
import ru.gb.xlsxreader.services.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

@Component
public class ReaderController {
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;
    private final ProductService productService;
    private final SubCategory1Service subCategory1Service;
    private final SubCategory2Service subCategory2Service;
    private final SubCategory3Service subCategory3Service;

    @Autowired
    public ReaderController(CategoryService categoryService,
                            ManufacturerService manufacturerService,
                            ProductService productService,
                            SubCategory1Service subCategory1Service,
                            SubCategory2Service subCategory2Service,
                            SubCategory3Service subCategory3Service) {
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.productService = productService;
        this.subCategory1Service = subCategory1Service;
        this.subCategory2Service = subCategory2Service;
        this.subCategory3Service = subCategory3Service;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void generateDataOnStartup() throws IOException {

        FileInputStream fis = new FileInputStream(new File("C:\\pr\\Инструмент.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        Row row = rowIterator.next();
//        readRow(row);

        while (rowIterator.hasNext()){
            writeRow(row);
            row = rowIterator.next();
        }
    }


    public void writeRow(Row row){
        Product product = new Product();

        Cell cat = row.getCell(0);
        String title = cat.getStringCellValue();
        Optional<Category> optionalCategory = categoryService.findCatByName(title);
        Category category;
        if(optionalCategory.isEmpty()){
            category = new Category();
            category.setTitle(title);
            categoryService.addCat(category);
        } else {
            category = optionalCategory.get();
        }
        product.setCategory(category);


        Cell cat2 = row.getCell(1);
        String titleS1 = cat2.getStringCellValue();
        Optional<SubCategory1> optionalSubCategory1 = subCategory1Service.findCatByName(titleS1);
        SubCategory1 subCategory1;
        if(optionalSubCategory1.isEmpty()){
            subCategory1 = new SubCategory1();
            subCategory1.setTitle(titleS1);
            subCategory1Service.addCat(subCategory1);
        } else {
            subCategory1 = optionalSubCategory1.get();
        }
        product.setSubCategory1(subCategory1);


        Cell cat3 = row.getCell(2);
        String titleS2 = cat3.getStringCellValue();
        Optional<SubCategory2> optionalSubCategory2 = subCategory2Service.findCatByName(titleS2);
        SubCategory2 subCategory2;
        if(optionalSubCategory2.isEmpty()){
            subCategory2 = new SubCategory2();
            subCategory2.setTitle(titleS2);
            subCategory2Service.addCat(subCategory2);
        } else {
            subCategory2 = optionalSubCategory2.get();
        }
        product.setSubCategory2(subCategory2);

        Cell cat4 = row.getCell(3);
        String titleS3 = cat4.getStringCellValue();
        Optional<SubCategory3> optionalSubCategory3 = subCategory3Service.findCatByName(titleS3);
        SubCategory3 subCategory3;
        if(optionalSubCategory3.isEmpty()){
            subCategory3 = new SubCategory3();
            subCategory3.setTitle(titleS3);
            subCategory3Service.addCat(subCategory3);
        } else {
            subCategory3 = optionalSubCategory3.get();
        }
        product.setSubCategory3(subCategory3);

        Cell art = row.getCell(4);
        String a = art.getStringCellValue();
        if (!Objects.equals(a, "")){
            product.setArticle(Long.parseLong(a));
        }

        Cell mod = row.getCell(5);
        product.setModification(mod.getStringCellValue());

        Cell tit = row.getCell(6);
        product.setTitle(tit.getStringCellValue());

        Cell path = row.getCell(7);
        product.setPath(path.getStringCellValue());

        Cell price = row.getCell(8);
        String p = price.getStringCellValue();
        if (!Objects.equals(p, "")){
            product.setPrice(Double.parseDouble(p.replace(",", ".")));
        }

        Cell oPrice = row.getCell(9);
        String op = oPrice.getStringCellValue();
        if (!Objects.equals(op, "")){
            product.setOldPrice(Double.parseDouble(op.replace(",", ".")));
        }

        Cell pPrice = row.getCell(10);
        String pp = pPrice.getStringCellValue();
        if (!Objects.equals(pp, "")){
            product.setPurchasePrice(Double.parseDouble(pp.replace(",", ".")));
        }

        Cell count = row.getCell(11);
        product.setCount(Integer.parseInt(count.getStringCellValue()));

        Cell desc = row.getCell(12);
        product.setDescription(desc.getStringCellValue());

        Cell sdesc = row.getCell(13);
        product.setShortDescription(sdesc.getStringCellValue());

        Cell man = row.getCell(14);
        String manTitle = man.getStringCellValue();
        Optional<Manufacturer> optionalManufacturer = manufacturerService.findManByName(manTitle);
        Manufacturer manufacturer;
        if(optionalManufacturer.isEmpty()){
            manufacturer = new Manufacturer();
            manufacturer.setTitle(manTitle);
            manufacturerService.addMan(manufacturer);
        } else {
            manufacturer = optionalManufacturer.get();
        }
        product.setManufacturer(manufacturer);

        Cell weight = row.getCell(15);
        String w = weight.getStringCellValue();
        if (!Objects.equals(w, "")){
            product.setWeight(Double.parseDouble(w.replace(",", ".")));
        }

        Cell img = row.getCell(19);
        product.setImages(img.getStringCellValue());

        Cell imgl = row.getCell(20);
        product.setImagesLinc(imgl.getStringCellValue());

        Cell spec = row.getCell(21);
        product.setSpecifications(spec.getStringCellValue());

        productService.addProd(product);
    }

    public void readRow(Row row){
        Cell cat = row.getCell(0);
        System.out.println("0 - " + cat.getStringCellValue());

        Cell cat2 = row.getCell(1);
        System.out.println("1 - " + cat2.getStringCellValue());

        Cell cat3 = row.getCell(2);
        System.out.println("2 - " + cat3.getStringCellValue());

        Cell cat4 = row.getCell(3);
        System.out.println("3 - " + cat4.getStringCellValue());

        Cell art = row.getCell(4);
        String a = art.getStringCellValue();
        System.out.print("4 - ");
        if (!Objects.equals(a, "")){
            System.out.println(Long.parseLong(a));
        }

        Cell mod = row.getCell(5);
        System.out.println("5 - " + mod.getStringCellValue());

        Cell tit = row.getCell(6);
        System.out.println("6 - " + tit.getStringCellValue());

        Cell path = row.getCell(7);
        System.out.println("7 - " + path.getStringCellValue());

        Cell price = row.getCell(8);
        String p = price.getStringCellValue();
        System.out.print("8 - ");
        if (!Objects.equals(p, "")){
            System.out.println(Double.parseDouble(p.replace(",", ".")));
        }


        Cell oPrice = row.getCell(9);
        String op = oPrice.getStringCellValue();
        System.out.print("9 - ");
        if (!Objects.equals(op, "")){
            System.out.println(Double.parseDouble(op.replace(",", ".")));
        }

        Cell pPrice = row.getCell(10);
        String pp = pPrice.getStringCellValue();
        System.out.print("10 - ");
        if (!Objects.equals(pp, "")){
            System.out.println(Double.parseDouble(pp.replace(",", ".")));
        }

        Cell count = row.getCell(11);
        System.out.println("11 - " + Integer.parseInt(count.getStringCellValue()));

        Cell desc = row.getCell(12);
        System.out.println("12 - " + desc.getStringCellValue());

        Cell sdesc = row.getCell(13);
        System.out.println("13 - " + sdesc.getStringCellValue());

        Cell man = row.getCell(14);
        System.out.println("14 - " + man.getStringCellValue());

        Cell weight = row.getCell(15);
        String w = weight.getStringCellValue();
        System.out.print("15 - ");
        if (!Objects.equals(w, "")){
            System.out.println(Double.parseDouble(w.replace(",", ".")));
        }

        Cell img = row.getCell(19);
        System.out.println("19 - " + img.getStringCellValue());

        Cell imgl = row.getCell(20);
        System.out.println("20 - " + imgl.getStringCellValue());

        Cell spec = row.getCell(21);
        System.out.println("21 - " + spec.getStringCellValue());
    }
}
