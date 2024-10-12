package com.windshop.phone.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_product")
@Getter
@Setter
public class Product extends BaseEntity {

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @Column(name = "price", precision = 13, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(name = "price_sale", precision = 13, scale = 0)
    private BigDecimal priceSale;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "short_description", length = 3000, nullable = true)
    private String shortDes;

    @Lob
    @Column(name = "detail_description", nullable = true, columnDefinition = "LONGTEXT")
    private String shortDetails;

    @Column(name = "seo", nullable = true)
    private String seo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductImage> productImages = new ArrayList<>();

    public void addProductImages(ProductImage _productImages) {
        _productImages.setProduct(this);
        productImages.add(_productImages);
    }

    public void removeProductImages(ProductImage _productImages) {
        _productImages.setProduct(null);
        productImages.remove(_productImages);
    }

    public void removeProductImages() {
        for (ProductImage productImage : productImages) {
            removeProductImages(productImage);
        }
    }
}
