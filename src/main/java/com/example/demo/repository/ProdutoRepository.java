package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Produto;
import com.example.demo.projections.ProdutoProjection;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    @Query(nativeQuery = true,
        value = """
            select * from
            (
                SELECT DISTINCT p.id, p.name, p.image_url, p.price
                    FROM tb_product p
                    INNER JOIN tb_product_category pc ON pc.product_id = p.id
                    WHERE (:categoriesID IS NULL OR pc.category_id in :categoriesID) 
                            and LOWER(p.name) like LOWER(CONCAT('%',:name,'%'))
            ) as tb_result
        """,
        countQuery = """
            select count(*) from
            (
                SELECT DISTINCT p.id, p.name, p.image_url, p.price
                    FROM tb_product p
                    INNER JOIN tb_product_category pc ON pc.product_id = p.id
                    WHERE (:categoriesID IS NULL OR pc.category_id in :categoriesID) 
                            and LOWER(p.name) like LOWER(CONCAT('%',:name,'%'))
            ) as tb_result    
        """)
    public Page<ProdutoProjection> searchProducts(List<Long> categoriesID, String name, Pageable pageable);



    @Query(nativeQuery = true,
        value = """
            SELECT * FROM (
                SELECT DISTINCT p.id, p.name, p.image_url, p.price
                FROM tb_product p
                INNER JOIN tb_product_category pc ON pc.product_id = p.id
                WHERE pc.category_id IN (:categoriesID)
                AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ) AS tb_result
            """,
        countQuery = """
            SELECT COUNT(*) FROM (
                SELECT DISTINCT p.id
                FROM tb_product p
                INNER JOIN tb_product_category pc ON pc.product_id = p.id
                WHERE pc.category_id IN (:categoriesID)
                AND LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ) AS tb_count
            """)
    Page<ProdutoProjection> searchProductsWithCategories(@Param("categoriesID") List<Long> categoriesID,
                                                        @Param("name") String name,
                                                        Pageable pageable);



    @Query(nativeQuery = true,
        value = """
            SELECT * FROM (
                SELECT DISTINCT p.id, p.name, p.image_url, p.price
                FROM tb_product p
                WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ) AS tb_result
            """,
        countQuery = """
            SELECT COUNT(*) FROM (
                SELECT DISTINCT p.id
                FROM tb_product p
                WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
            ) AS tb_count
            """)
    Page<ProdutoProjection> searchProductsWithoutCategories(@Param("name") String name,
                                                            Pageable pageable);

} 
