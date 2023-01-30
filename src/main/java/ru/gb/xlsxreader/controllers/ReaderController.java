package ru.gb.xlsxreader.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.gb.xlsxreader.model.*;
import ru.gb.xlsxreader.services.*;

import java.io.File;
import java.io.FileInputStream;
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
//        FileInputStream fis = new FileInputStream(new File("C:\\pr\\Инструмент_test.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

//        extracted();

        Row row = rowIterator.next();
//        readRow(row);

        do {
            writeRow(row);
            row = rowIterator.next();
        } while (rowIterator.hasNext());
        writeRow(row);

        System.out.println("F I N I S H !!!");
    }

    private void extracted() {
        Optional<Product> optionalProduct = productService.findProdByName("Пила Zubr ZPT-255-1800PL");
        Product product = null;
        if (optionalProduct.isPresent()){
            product = optionalProduct.get();
            System.out.println("Продукт: " + product.getTitle());
            Category category = product.getCategory();
//            Categories categories = categoryService.addCat(product.getCategories());
//            Categories categories = categoryService.findById(product.getCategories().getId());
            System.out.println("Категория: " + category.getTitle());
            Category parentCategory = category.getParentCategory();
//            Categories parentCategories = categoryService.addCat(categories.getCategories());
//            Categories parentCategories = categoryService.findById(categories.getCategories().getId());
            System.out.println("Родительская категория: " + parentCategory.getTitle());
            Category firstCategory = parentCategory.getParentCategory();
//            Categories firstCategories = categoryService.findById(parentCategories.getCategories().getId());
            System.out.println("Начальная категория: " + firstCategory.getTitle());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("В категорию также включены:");
            for (Category category1 : categoryService.getCatList(category.getId())) {
                System.out.println(category1.getTitle());
                System.out.println("У которой родительская категория: " + category1.getParentCategory().getTitle());
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("В родительскую категорию также включены:");
            for (Category category1 : categoryService.getCatList(parentCategory.getId())) {
                System.out.println(category1.getTitle());
                System.out.println("У которой родительская категория: " + category1.getParentCategory().getTitle());
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("В изначальную категорию также включены:");
            for (Category category1 : categoryService.getCatList(firstCategory.getId())) {
                System.out.println(category1.getTitle());
                System.out.println("У которой родительская категория: " + category1.getParentCategory().getTitle());
            }

        }
    }


    public void writeRow(Row row){
        Product product = new Product();
        Category category0 = null;
        Category category1 = null;
        Category category2 = null;
        Category category3 = null;
        Manufacturer manufacturer;

        Cell cat0 = row.getCell(0);
        String title0 = cat0.getStringCellValue();
        List<Category> list0 = categoryService.findAllByTitle(title0);
        if (list0.size() == 0){
            category0 = new Category();
            category0.setTitle(title0);
        } else {
            for (Category c : list0) {
                if (category0 == null){
                    category0 = c;
                }
            }
        }
        categoryService.addCat(category0);


        Cell cat1 = row.getCell(1);
        String title1 = cat1.getStringCellValue();

        List<Category> list1 = categoryService.findAllByTitle(title1);
        if (list1.size() == 0){
            category1 = new Category();
            category1.setTitle(title1);
            category1.setParentCategory(category0);
        } else {
            for (Category c : list1) {
                if (c.getParentCategory().getId() == category0.getId()) {
                    category1 = c;
                    break;
                }
            }
            if(category1 == null){
                category1 = new Category();
                category1.setTitle(title1);
                category1.setParentCategory(category0);
            }
        }
        categoryService.addCat(category1);



        Cell cat2 = row.getCell(2);
        String title2 = cat2.getStringCellValue();
        if (Objects.equals(title2, "")){
            product.setCategory(category1);
        } else {
            List<Category> list2 = categoryService.findAllByTitle(title2);
            if (list2.size() == 0){
                category2 = new Category();
                category2.setTitle(title2);
                category2.setParentCategory(category1);
            } else {
                for (Category c : list2) {
                    if (c.getParentCategory().getId() == category1.getId()){
                        category2 = c;
                        break;
                    }
                }
                if(category2 == null){
                    category2 = new Category();
                    category2.setTitle(title2);
                    category2.setParentCategory(category1);
                }
            }
        }
        if (category2 != null){
            categoryService.addCat(category2);
        }


        Cell cat4 = row.getCell(3);
        String title3 = cat4.getStringCellValue();
        if (category2 != null){
            if (Objects.equals(title3, "")){
                product.setCategory(category2);
            } else {
                List<Category> list3 = categoryService.findAllByTitle(title3);
                if (list3.size() == 0){
                    category3 = new Category();
                    category3.setTitle(title3);
                    category3.setParentCategory(category2);
                } else {
                    for (Category c : list3) {
                        if (c.getParentCategory().getId() == category2.getId()){
                            category3 = c;
                            break;
                        }
                    }
                    if(category3 == null){
                        category3 = new Category();
                        category3.setTitle(title3);
                        category3.setParentCategory(category2);
                    }
                }
            }
            if (category3 != null){
                categoryService.addCat(category3);
            }
        }

        if (category3 != null){
            product.setCategory(category3);
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
        } else {
            manufacturer = optionalManufacturer.get();
        }
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

        manufacturerService.addMan(manufacturer);
        productService.addProd(product);
    }

    public void writeRowOld(Row row){
        Product product = new Product();
        Category category0 = null;
        Category category1 = null;
        Category category2 = null;
        Category category3 = null;
        Manufacturer manufacturer;

        Cell cat0 = row.getCell(0);
        String title0 = cat0.getStringCellValue();
        List<Category> list0 = categoryService.findAllByTitle(title0);
        if (list0.size() == 0){
            category0 = new Category();
            category0.setTitle(title0);
            category0.setCategoriesList(new ArrayList<>());
            category0.setProductsList(new ArrayList<>());
//            categories0 = categoryService.addCat(categories0);
        } else {
            for (Category c : list0) {
                if (category0 == null){
                    category0 = c;
                }
            }
        }

//
//        categories0 = categoryService.findByTitle(title0);
//        if(categories0 == null){
//            categories0 = new Categories();
//            categories0.setTitle(title0);
//            categories0 = categoryService.addCat(categories0);
//        }


        Cell cat1 = row.getCell(1);
        String title1 = cat1.getStringCellValue();

        List<Category> list1 = categoryService.findAllByTitle(title1);
        if (list1.size() == 0){
            category1 = new Category();
            category1.setTitle(title1);
            category1.setCategoriesList(new ArrayList<>());
            category1.setProductsList(new ArrayList<>());
            category1.setParentCategory(category0);
            category0.getCategoriesList().add(category1);
//            categories1 = categoryService.addCat(categories1);
        } else {
            for (Category c : list1) {
                if (category1 == null && Objects.equals(c.getParentCategory().getId(), Objects.requireNonNull(category0).getId())){
                    category1 = c;
                } else {
                    category1 = new Category();
                    category1.setTitle(title1);
                    category1.setCategoriesList(new ArrayList<>());
                    category1.setProductsList(new ArrayList<>());
                    category1.setParentCategory(category0);
                    category0.getCategoriesList().add(category1);
//                    categories1 = categoryService.addCat(categories1);
                }
            }
        }


//        categories1 = categoryService.findByTitle(title1);
//        if(categories1 == null || !Objects.equals(categories1.getCategories().getId(), categories0.getId())){
//            categories1 = new Categories();
//            categories1.setTitle(title1);
//            categories1.setCategories(categories0);
//            categories1 = categoryService.addCat(categories1);
//        }


        Cell cat2 = row.getCell(2);
        String title2 = cat2.getStringCellValue();
        if (Objects.equals(title2, "")){
            product.setCategory(category1);
            category1.getProductsList().add(product);
        } else {
            List<Category> list2 = categoryService.findAllByTitle(title2);
            if (list2.size() == 0){
                category2 = new Category();
                category2.setTitle(title2);

                category2.setCategoriesList(new ArrayList<>());
                category2.setProductsList(new ArrayList<>());
                category2.setParentCategory(category1);
                category1.getCategoriesList().add(category2);

//                categories2 = categoryService.addCat(categories2);
            } else {
                for (Category c : list2) {
//                    if (categories2 == null && Objects.equals(c.getCategories().getId(), Objects.requireNonNull(categories1).getId())){
                    if (category2 == null && (c.getParentCategory().getId() == category1.getId())){
                        category2 = c;
                    } else {
                        category2 = new Category();
                        category2.setTitle(title2);
                        category2.setCategoriesList(new ArrayList<>());
                        category2.setProductsList(new ArrayList<>());
                        category2.setParentCategory(category1);
                        category1.getCategoriesList().add(category2);
//                        categories2 = categoryService.addCat(categories2);
                    }
                }
            }


//            categories2 = categoryService.findByTitle(title2);
//            if(categories2 == null || !Objects.equals(categories2.getCategories().getId(), categories1.getId())){
//                categories2 = new Categories();
//                categories2.setTitle(title2);
//                categories2.setCategories(categories1);
//                categories2 = categoryService.addCat(categories2);
//            }
        }


        Cell cat4 = row.getCell(3);
        String title3 = cat4.getStringCellValue();
        if (category2 != null){
            if (Objects.equals(title3, "")){
                product.setCategory(category2);
                category2.getProductsList().add(product);
            } else {

                List<Category> list3 = categoryService.findAllByTitle(title3);
                if (list3.size() == 0){
                    category3 = new Category();
                    category3.setTitle(title3);
                    category3.setCategoriesList(new ArrayList<>());
                    category3.setProductsList(new ArrayList<>());
                    category3.setParentCategory(category2);
                    category2.getCategoriesList().add(category3);
//                    categories3 = categoryService.addCat(categories3);
                } else {
                    for (Category c : list3) {
                        if (category3 == null && Objects.equals(c.getParentCategory().getId(), Objects.requireNonNull(category2).getId())){
                            category3 = c;
                        } else {
                            category3 = new Category();
                            category3.setTitle(title3);
                            category3.setCategoriesList(new ArrayList<>());
                            category3.setProductsList(new ArrayList<>());
                            category3.setParentCategory(category2);
                            category2.getCategoriesList().add(category3);
//                            categories3 = categoryService.addCat(categories3);
                        }
                    }
                }


//                categories3 = categoryService.findByTitle(title3);
//                if(categories3 == null || !Objects.equals(categories3.getCategories().getId(), categories2.getId())) {
//                    categories3 = new Categories();
//                    categories3.setTitle(title3);
//                    categories3.setCategories(categories2);
//                    categories3 = categoryService.addCat(categories3);
//                }
            }
        }

        if (category3 != null){
            product.setCategory(category3);
            category3.getProductsList().add(product);
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
            manufacturer.setProductList(new ArrayList<>());
//            manufacturer = manufacturerService.addMan(manufacturer);
        } else {
            manufacturer = optionalManufacturer.get();
        }
        product.setManufacturer(manufacturer);
        manufacturer.getProductList().add(product);

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

//        productService.addProd(product);
//        categoryService.flush();
//        manufacturerService.flush();
//        productService.flush();
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
