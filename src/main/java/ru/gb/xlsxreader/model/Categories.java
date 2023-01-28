package ru.gb.xlsxreader.model;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
@NoArgsConstructor
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Unsigned
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @Unsigned
    private Categories categories;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "categories")
    private List<Categories> categoriesList;

    @OneToMany(mappedBy = "categories")
    private List<Product> productList;

//    public List<Categories> getCategoriesList() {
//        if (categoriesList.isEmpty()){
//            categoriesList = new ArrayList<>();
//        }
//        return categoriesList;
//    }

//    public List<Product> getProductList() {
//        if (productList.isEmpty()){
//            productList = new ArrayList<>();
//        }
//        return productList;
//    }
}
