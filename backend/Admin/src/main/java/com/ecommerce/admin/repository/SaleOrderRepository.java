package com.windshop.phone.repository;

import com.windshop.phone.entity.SaleOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
mport org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {

   Page<SaleOrder> findAllByUserIdAndStatusOrderByCreatedDateAsc(Integer userId, Integer status, Pageable pageable);

    @Query(value = "SELECT SUM(total) FROM tbl_saleorder WHERE MONTH(created_date) =month(now()) AND status_order_code = 2", nativeQuery = true)
    BigDecimal sumTotalInMonth();

    @Query(value = "SELECT COUNT(*) FROM tbl_saleorder WHERE status_order_code = 2 " +
            "AND MONTH(created_date) =month(now())", nativeQuery = true)
    Integer quantityOrder();

    @Query(value = "SELECT sum(sop.quantity) FROM tbl_saleorder so\n" +
            "INNER JOIN tbl_saleorder_products sop on so.id = sop.saleorder_id\n" +
            " WHERE so.status_order_code = 2 AND MONTH(so.created_date) =month(now())", nativeQuery = true)
    Integer quantityProduct();
