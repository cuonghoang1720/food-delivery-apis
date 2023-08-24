package com.hmcuong.fooddelivery.service;

import com.hmcuong.fooddelivery.entity.DoiTac;
import com.hmcuong.fooddelivery.entity.HopDong;
import com.hmcuong.fooddelivery.repository.DoiTacRepository;
import com.hmcuong.fooddelivery.repository.HopDongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final DoiTacRepository doiTacRepository;
    private final HopDongRepository hopDongRepository;

    public List<DoiTac> getAllDoiTac() {
        return this.doiTacRepository.findAll();
    }

    public List<HopDong> getAllHopDong() {
        return hopDongRepository.findAll();
    }

    public List<HopDong> getAllHopDongByIdDoiTac(String idDoiTac) {
        return hopDongRepository.findByDoiTac_IdDoiTac(idDoiTac);
    }
}
