package NoSource;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "products")
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    @Column(name = "path")
    private String path;

    @Column(name = "description", length = 1200)
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "images", length = 2000)
    private String images;

    @Column(name = "images_linc", length = 2000)
    private String imagesLinc;

    @Column(name = "specifications", length = 3000)
    private String specifications;


}
