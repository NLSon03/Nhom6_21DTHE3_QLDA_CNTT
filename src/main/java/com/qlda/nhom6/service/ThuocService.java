package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Thuoc;
import com.NgocHieu.Buoi22.repository.IThuocRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class ThuocService {

    final IThuocRepository thuocRepository;
    public Optional<Thuoc> getThuocById(Long id) {
        return thuocRepository.findById(id);
    }

    public List<Thuoc> getAllMedicines(Integer pageNo,
                                   Integer pageSize,
                                   String sortBy){
        return thuocRepository.findAllBooks(pageNo,pageSize,sortBy);
    }

    public Optional<Thuoc>getMedicineById(Long  id){
        return thuocRepository.findById(id);
    }

    public void addMedicine( Thuoc thuoc){
        thuocRepository.save(thuoc);
    }

    public void updateMedicine(@NotNull Thuoc thuoc){

        Thuoc existingThuoc = thuocRepository.findById(thuoc.getIdthuoc()).orElse(null);
            Objects.requireNonNull(existingThuoc).setCachdung(thuoc.getCachdung());

            existingThuoc.setGiaban(thuoc.getGiaban());
            existingThuoc.setBaoquan(thuoc.getBaoquan());
            existingThuoc.setChuY(thuoc.getChuY());
            existingThuoc.setCongdung(thuoc.getCongdung());

            existingThuoc.setHansudung(thuoc.getHansudung());
            existingThuoc.setTenthuoc(thuoc.getTenthuoc());

            existingThuoc.setPhantacdung(thuoc.getPhantacdung());
            existingThuoc.setThanhPhan(thuoc.getThanhPhan());
            existingThuoc.setDonViTinh(thuoc.getDonViTinh());
            existingThuoc.setLoaithuoc(thuoc.getLoaithuoc());
            existingThuoc.setNhaSanXuat(thuoc.getNhaSanXuat());

            thuocRepository.save(existingThuoc);




    }
    public List<Thuoc> getBooksByCategory(Long  category,Integer pageNo, Integer pageSize, String sortBy) {
        return thuocRepository.findBooksByCategory(category, pageNo, pageSize, sortBy);
    }
    public List<Thuoc> getBooksBySubcategory(Long subcategory, Integer pageNo, Integer pageSize, String sortBy) {
        return thuocRepository.findBooksBySubcategory(subcategory, pageNo, pageSize, sortBy);
    }
    public int getTotalPages(Long categoryId, Long subcategoryId, int pageSize) {
        long totalItems;

        if (categoryId != null) {
            if (subcategoryId != null) {
                totalItems = thuocRepository.countBySubcategory(subcategoryId);
            } else {
                totalItems = thuocRepository.countByCategory(categoryId);
            }
        } else {
            totalItems = thuocRepository.count();
        }

        return (int) Math.ceil((double) totalItems / pageSize);
    }
    public void deleteMedicineById(Long  id){
        thuocRepository.deleteById(id);
    }


    public List<Thuoc> searchBook(String keyword, int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Thuoc> pageResult = thuocRepository.searchBook(keyword, pageable);
        return pageResult.getContent();
    }

    public long countSearchResults(String keyword) {
        return thuocRepository.countSearchResults(keyword);
    }

    public List<Thuoc> getAllProducts() {
        return thuocRepository.findAll();
    }
    public List<Thuoc> getProductsByPage(int page, int pageSize) {
        // Tính toán vị trí bắt đầu của danh sách sản phẩm
        int startIndex = (page - 1) * pageSize;

        // Lấy danh sách sản phẩm từ vị trí bắt đầu và kích thước trang
        List<Thuoc> allProducts = getAllProducts();
        List<Thuoc> thuocs = new ArrayList<>();

        for (int i = startIndex; i < startIndex + pageSize && i < allProducts.size(); i++) {
            thuocs.add(allProducts.get(i));
        }

        return thuocs;
    }

    public int getTotalPages(int pageSize) {
        List<Thuoc> allProducts = getAllProducts();
        return (int) Math.ceil((double) allProducts.size() / pageSize);
    }
    public List<Thuoc> getBooksByCategory(Long categoryId, int page, int pageSize, String sortBy) {
        // Call the repository method to get the paginated and sorted list of books by category
        return thuocRepository.findBooksByCategory(categoryId, page - 1, pageSize, sortBy);
    }


}
