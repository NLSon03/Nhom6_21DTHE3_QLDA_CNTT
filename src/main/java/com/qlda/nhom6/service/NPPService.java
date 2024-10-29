package com.qlda.nhom6.service;

import com.qlda.nhom6.model.NhaPhanPhoi;
import com.qlda.nhom6.repository.INhaPPRepository;
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
public class NPPService {
    private final INhaPPRepository nhaPPRepository;

    public List<NhaPhanPhoi> getAllNPP() {
        return nhaPPRepository.findAll();
    }
    public Optional<NhaPhanPhoi> getNPP(Long id) {
        return nhaPPRepository.findById(id);
    }
    public void addNpp(NhaPhanPhoi nhaPhanPhoi){

        nhaPPRepository.save(nhaPhanPhoi);

    }
    public void updateNPP(@NotNull NhaPhanPhoi nhaPhanPhoi){
        NhaPhanPhoi existingNPP= nhaPPRepository
                .findById(nhaPhanPhoi.getMaNPP())
                .orElse(null);
        Objects.requireNonNull(existingNPP).setTenNPP(nhaPhanPhoi.getTenNPP());
        existingNPP.setEmail(nhaPhanPhoi.getEmail());
        existingNPP.setDiaChi(nhaPhanPhoi.getDiaChi());
        existingNPP.setMaSoThue(nhaPhanPhoi.getMaSoThue());
        existingNPP.setGhiChu(nhaPhanPhoi.getGhiChu());
        nhaPPRepository.save(existingNPP);



    }
    public void deleteNpp(Long id){
        nhaPPRepository.deleteById(id);
    }
}
