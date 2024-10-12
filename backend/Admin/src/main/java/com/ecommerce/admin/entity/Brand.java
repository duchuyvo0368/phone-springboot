package com.windshop.phone.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_brand")
@Data
public class Brand extends BaseEntity {

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "image")
	private String image;

	@Column(name = "seo", nullable = false)
	private String seo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "brand", fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	public void addProduct(Product product) {
		products.add(product);
		product.setBrand(this);
	}
	
	public void removeProduct(Product product) {
		products.remove(product);
		product.setBrand(null);
	}
}
