package com.windshop.phone.repository;

import com.windshop.phone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.windshop.phone.model.ProductSellerDto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Page<Product> findAllByStatus(Integer status, Pageable pageable);
    Page<Product> findAllByBrandIdAndStatus(Integer brand_id, Integer status, Pageable pageable);
age<Product> findAllByCategoryIdAndStatus(Integer category_id, Integer status, Pageable pageable);

    @Query(value = "SELECT new com.windshop.phone.model.ProductSellerDto(p, sum(sop.quantity), sop.price, sop.month) FROM Product p" +
            " inner join SaleOrderProduct sop  on sop.product.id = p.id" +
            " inner join SaleOrder so on so.id = sop.saleOrder.id" +
            " where so.statusOrder = 2 " +
            " group by sop.product.id, sop.price, sop.month" +
            " order by sum(sop.quantity) desc")
    List<ProductSellerDto> topSeller();

    @Query(value = "SELECT new com.windshop.phone.model.ProductSellerDto(p, sum(sop.quantity), sop.price, sop.year) FROM Product p" +
            " inner join SaleOrderProduct sop  on sop.product.id = p.id" +
            " inner join SaleOrder so on so.id = sop.saleOrder.id" +
            " where so.statusOrder = 2 " +
            " group by sop.product.id, sop.price, sop.year" +
            " order by sum(sop.quantity) desc")
    List<ProductSellerDto> topSellerYear();
}
