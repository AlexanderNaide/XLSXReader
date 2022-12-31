package NoSource;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "sub_category_2")
public class SubCategory2{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @OneToMany (mappedBy = "subCategory2")
    private List<Product> productList;
}
