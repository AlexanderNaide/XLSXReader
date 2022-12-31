package NoSource;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class ProductOld {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "sub_category_1_id", nullable = false)
    private SubCategory1 subCategory1;

    @ManyToOne
    @JoinColumn(name = "sub_category_2_id")
    private SubCategory2 subCategory2;

    @ManyToOne
    @JoinColumn(name = "sub_category_3_id")
    private SubCategory3 subCategory3;

    @Column(name = "art", nullable = false)
    private Long article;

    @Column(name = "modification")
    private String modification;

    @Column(name = "title")
    private String title;

//    @Column(name = "path")
//    private String path;

    @Column(name = "price")
    private Double price;

    @Column(name = "old_price")
    private Double oldPrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "count")
    private Integer count;

//    @Column(name = "description", length = 1200)
//    private String description;

//    @Column(name = "short_description")
//    private String shortDescription;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "weight")
    private Double weight;

    @OneToOne(mappedBy = "product")
    @Column(name = "data_id")
    private ProductData data;

//    @Column(name = "images", length = 2000)
//    private String images;

//    @Column(name = "images_linc", length = 2000)
//    private String imagesLinc;

//    @Column(name = "specifications", length = 3000)
//    private String specifications;

}
