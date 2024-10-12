package com.windshop.phone.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_saleorder")
@Data
public class SaleOrder extends BaseEntity {
    @Column(name = "code")
    private String code;

    @Column(name = "total", precision = 20, scale = 2, nullable = false)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "seo")
    private String seo;

    @Column(name = "status_order_code")
    private Integer statusOrder;

    @Column(name = "status_order_name")
    private String statusOrderName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saleOrder", fetch = FetchType.EAGER)
    private List<SaleOrderProduct> saleOrderProducts = new ArrayList<>();

    public void addSaleOrderProducts(SaleOrderProduct _saleOrderProduct) {
        _saleOrderProduct.setSaleOrder(this);
        saleOrderProducts.add(_saleOrderProduct);
    }

}
