package com.qlda.nhom6.repository;

import com.qlda.nhom6.model.Thuoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IThuocRepository extends PagingAndSortingRepository<Thuoc, Long>, JpaRepository<Thuoc, Long> {
    default List<Thuoc> findAllBooks(Integer pageNo,
                                    Integer pageSize,
                                    String sortBy) {
        return findAll(PageRequest.of(pageNo,
                pageSize,
                Sort.by(sortBy)))
                .getContent();
    }
    @Query("""
        SELECT b FROM Thuoc b
        WHERE b.Tenthuoc LIKE %?1%
        OR b.nhaSanXuat.TenNSX LIKE %?1%
        OR b.loaithuoc.tenLoai LIKE %?1%
        """)
    Page<Thuoc> searchBook(String keyword, Pageable pageable);

    @Query("""
        SELECT COUNT(b) FROM Thuoc b
        WHERE b.Tenthuoc LIKE %?1%
        OR b.nhaSanXuat.TenNSX LIKE %?1%
        OR b.loaithuoc.tenLoai LIKE %?1%
        """)
    long countSearchResults(String keyword);

    @Query("SELECT t FROM Thuoc t WHERE t.category.id = :categoryId")
    Page<Thuoc> findByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT b FROM Thuoc b WHERE b.loaithuoc.id = :subcategoryID")
    Page<Thuoc> findBySubcategory(@Param("subcategoryID") Long subcategoryID, Pageable pageable);


    default List<Thuoc> findBooksByCategory(Long categoryId, Integer pageNo, Integer pageSize, String sortBy) {
        return findByCategory(categoryId, PageRequest.of(pageNo, pageSize, Sort.by(sortBy))).getContent();
    }
    default List<Thuoc> findBooksBySubcategory(Long subcategoryID, Integer pageNo, Integer pageSize, String sortBy) {
        return findBySubcategory(subcategoryID, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)))
                .getContent();
    }



    @Query("SELECT COUNT(t) FROM Thuoc t WHERE t.category.id = :categoryId")
    long countByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT COUNT(t) FROM Thuoc t WHERE t.loaithuoc.id = :subcategoryId")
    long countBySubcategory(@Param("subcategoryId") Long subcategoryId);
}


