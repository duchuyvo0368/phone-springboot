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
@Table(name = "tbl_cart")
@Getter
@Setter
public class Cart extends BaseEntity{

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "email_user")
    private String emailUser;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ProductCart> products = new ArrayList<>();
}
