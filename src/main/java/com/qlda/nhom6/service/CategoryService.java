package com.qlda.nhom6.service;

import com.NgocHieu.Buoi22.model.Category;
import com.NgocHieu.Buoi22.model.LoaiThuoc;
import com.NgocHieu.Buoi22.repository.CategoryRepository;
import com.NgocHieu.Buoi22.repository.IloaiThuoc;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE,
        rollbackFor = {Exception.class, Throwable.class})
public class CategoryService {

    private final CategoryRepository iloaiThuoc;

    private final  IloaiThuoc iloaiThuoc2;

    public List<Category> getAllLoaiThuoc() {
        return iloaiThuoc.findAll();
    }
    public Optional<Category> getLoaiThuoc(Long id) {
        return iloaiThuoc.findById(id);
    }
    public void addLoaithuoc(Category category){

        iloaiThuoc.save(category);

    }
    public void updateLoaithuoc(@NotNull Category category){
        Category existingLoaiThuoc= iloaiThuoc
                .findById(category.getId())
                .orElse(null);
        Objects.requireNonNull(existingLoaiThuoc).setTen(category.getTen());

        iloaiThuoc.save(existingLoaiThuoc);
    }
    public List<LoaiThuoc> getSubcategoriesByCategoryId(Long categoryId) {
        return iloaiThuoc2.findByCategoryId(categoryId);
    }
    public void deleteLoaithuoc(Long id){
        iloaiThuoc.deleteById(id);
    }
}
