package ru.gb.xlsxreader.controllers;

import lombok.RequiredArgsConstructor;
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
import java.util.*;

@Component
@RequiredArgsConstructor
public class ReaderController {
    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

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

        System.out.println("F I N I S H !!!");
    }


    public void writeRow(Row row){
        Product product = new Product();
        Categories categories0 = null;
        Categories categories1 = null;
        Categories categories2 = null;
        Categories categories3 = null;
        Manufacturer manufacturer;

        Cell cat0 = row.getCell(0);
        String title0 = cat0.getStringCellValue();
        Optional<Categories> optionalCategory0 = categoryService.findCatByName(title0);
        if(optionalCategory0.isEmpty()){
            categories0 = new Categories();
            categories0.setTitle(title0);
            categories0 = categoryService.addCat(categories0);
        } else {
            categories0 = optionalCategory0.get();
        }


        Cell cat1 = row.getCell(1);
        String title1 = cat1.getStringCellValue();
        Optional<Categories> optionalSubCategory1 = categoryService.findCatByName(title1);
        if(optionalSubCategory1.isEmpty()){
            categories1 = new Categories();
            categories1.setTitle(title1);
            categories1.setCategories(categories0);
            categories1 = categoryService.addCat(categories1);

            if (categories0.getCategoriesList() == null){
                categories0.setCategoriesList(new ArrayList<>());
            }
            categories0.getCategoriesList().add(categories1);
        } else {
            Categories test = optionalSubCategory1.get();
            if (!test.getCategories().equals(categories0)){
                categories1 = new Categories();
                categories1.setTitle(title1);
                categories1.setCategories(categories0);
                categories1 = categoryService.addCat(categories1);
                if (categories0.getCategoriesList() == null){
                    categories0.setCategoriesList(new ArrayList<>());
                }
                categories0.getCategoriesList().add(categories1);
            } else {
                categories1 = test;
            }
        }


        Cell cat2 = row.getCell(2);
        String title2 = cat2.getStringCellValue();
        if (Objects.equals(title2, "")){
            product.setCategories(categories1);
            if (categories1.getProductList() == null){
                categories1.setProductList(new ArrayList<>());
            }
            categories1.getProductList().add(product);
        } else {
            Optional<Categories> optionalSubCategory2 = categoryService.findCatByName(title2);
            if(optionalSubCategory2.isEmpty()){
                categories2 = new Categories();
                categories2.setTitle(title2);
                categories2.setCategories(categories1);
                categories2 = categoryService.addCat(categories2);
                if (categories1.getCategoriesList() == null){
                    categories1.setCategoriesList(new ArrayList<>());
                }
                categories1.getCategoriesList().add(categories2);
            } else {
                Categories test = optionalSubCategory2.get();
                if (!test.getCategories().equals(categories1)){
                    categories2 = new Categories();
                    categories2.setTitle(title2);
                    categories2.setCategories(categories1);
                    categories2 = categoryService.addCat(categories2);
                    if (categories1.getCategoriesList() == null){
                        categories1.setCategoriesList(new ArrayList<>());
                    }
                    categories1.getCategoriesList().add(categories2);
                } else {
                    categories2 = test;
                }
            }
        }


        Cell cat4 = row.getCell(3);
        String title3 = cat4.getStringCellValue();
        if (categories2 != null){
            if (Objects.equals(title3, "")){
                product.setCategories(categories2);
                if (categories2.getProductList() == null){
                    categories2.setProductList(new ArrayList<>());
                }
                categories2.getProductList().add(product);
            } else {
                Optional<Categories> optionalSubCategory3 = categoryService.findCatByName(title3);
                if(optionalSubCategory3.isEmpty()){
                    categories3 = new Categories();
                    categories3.setTitle(title3);
                    categories3.setCategories(categories2);
                    categories3 = categoryService.addCat(categories3);
                    if (categories2.getCategoriesList() == null){
                        categories2.setCategoriesList(new ArrayList<>());
                    }
                    categories2.getCategoriesList().add(categories3);
                } else {
                    Categories test = optionalSubCategory3.get();
                    if (!test.getCategories().equals(categories2)){
                        categories3 = new Categories();
                        categories3.setTitle(title3);
                        categories3.setCategories(categories2);
                        categories3 = categoryService.addCat(categories3);
                        if (categories2.getCategoriesList() == null){
                            categories2.setCategoriesList(new ArrayList<>());
                        }
                        categories2.getCategoriesList().add(categories3);
                    } else {
                        categories3 = test;
                    }
                }
            }
        }

        if (categories3 != null){
            product.setCategories(categories3);
            if (categories3.getProductList() == null){
                categories3.setProductList(new ArrayList<>());
            }
            categories3.getProductList().add(product);
        }


        Cell art = row.getCell(4);
        String a = art.getStringCellValue();
        if (!Objects.equals(a, "")){
            product.setArticle(a);
        }

        Cell mod = row.getCell(5);
        product.setModification(mod.getStringCellValue());

        Cell tit = row.getCell(6);
        product.setTitle(tit.getStringCellValue());


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

        Cell man = row.getCell(14);
        String titleMan = man.getStringCellValue();
        Optional<Manufacturer> optionalManufacturer = manufacturerService.findManByName(titleMan);
        if(optionalManufacturer.isEmpty()){
            manufacturer = new Manufacturer();
            manufacturer.setTitle(titleMan);
            manufacturer = manufacturerService.addMan(manufacturer);
        } else {
            manufacturer = optionalManufacturer.get();
        }
        if (manufacturer.getProductList() == null){
            manufacturer.setProductList(new ArrayList<>());
        }
        manufacturer.getProductList().add(product);
        product.setManufacturer(manufacturer);

        Cell img = row.getCell(19);
        String i = img.getStringCellValue();
        if (!Objects.equals(i, "")){
            product.setImagesTitle(i);
        }

        Cell imgl = row.getCell(20);
        String il = imgl.getStringCellValue();
        if (!Objects.equals(il, "")){
            product.setImagesLinc(il);
        }

        Cell spec = row.getCell(21);
        String sp = spec.getStringCellValue();
        if (!Objects.equals(sp, "")){
            product.setSpecifications(sp);
        }

//        categoryService.addCat(categories0);
//        categoryService.addCat(categories1);
//        if(categories2 != null) {
//            categoryService.addCat(categories2);
//        }
//        if(categories3 != null) {
//            categoryService.addCat(categories3);
//        }
        productService.addProd(product);
        categoryService.flush();
        manufacturerService.flush();
        productService.flush();
//        manufacturerService.addMan(manufacturer);
    }

/*    public void writeRowOld(Row row){
        Product product = new Product();
        ProductData data = new ProductData();

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

        product = productService.addProd(product);

        Cell path = row.getCell(7);
//        product.setPath(path.getStringCellValue());
        data.setPath(path.getStringCellValue());

        Cell desc = row.getCell(12);
//        product.setDescription(desc.getStringCellValue());
        data.setDescription(desc.getStringCellValue());

        Cell sdesc = row.getCell(13);
//        product.setShortDescription(sdesc.getStringCellValue());
        data.setShortDescription(sdesc.getStringCellValue());

        Cell img = row.getCell(19);
//        product.setImages(img.getStringCellValue());
        data.setImages(img.getStringCellValue());

        Cell imgl = row.getCell(20);
//        product.setImagesLinc(imgl.getStringCellValue());
        data.setImagesLinc(imgl.getStringCellValue());

        Cell spec = row.getCell(21);
//        product.setSpecifications(spec.getStringCellValue());
        data.setSpecifications(spec.getStringCellValue());

        data.setProduct(product);
        data = productDataService.addData(data);

        product.setData(data);

        productService.addProd(product);


    }*/

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
