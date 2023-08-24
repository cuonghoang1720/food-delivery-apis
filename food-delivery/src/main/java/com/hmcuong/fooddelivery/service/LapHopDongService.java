package com.hmcuong.fooddelivery.service;

import com.hmcuong.fooddelivery.entity.*;
import com.hmcuong.fooddelivery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LapHopDongService {

    private final DoiTacRepository doiTacRepository;
    private final HopDongRepository hopDongRepository;
    private final CuaHangRepository cuaHangRepository;
    private final ThucDonRepository thucDonRepository;
    private final MonAnRepository monAnRepository;

    public DoiTac savePartner(DoiTac doiTac) { return doiTacRepository.save(doiTac); }
    public HopDong saveContract(HopDong hopDong) {
        return hopDongRepository.save(hopDong);
    }
    public CuaHang saveShop(CuaHang cuaHang) {
        return cuaHangRepository.save(cuaHang);
    }
    public void saveMenu(ThucDon thucDon) { this.thucDonRepository.save(thucDon); }
    public MonAn saveFood(MonAn monAn) {
        return this.monAnRepository.save(monAn);
    }
    public boolean checkDoiTacExistsByEmail(String emailDoiTac) { return doiTacRepository.findByEmail(emailDoiTac) == null; }
    public List<DoiTac> getDoiTac() {
        return doiTacRepository.findAll();
    }
    public List<CuaHang> getCuaHang() {
        return this.cuaHangRepository.findAll();
    }
    public DoiTac findDoiTacById(String idDoiTac) { return this.doiTacRepository.findByIdDoiTac(idDoiTac).orElseThrow(); }
    public ThucDon findThucDonById(String idThucDon) { return this.thucDonRepository.findByIdThucDon(idThucDon).orElseThrow(); }

    public List<HopDong> getHopDong() {
        return this.hopDongRepository.findAll();
    }

    public Optional<CuaHang> findCuaHangById(String idCuaHang) {
        return this.cuaHangRepository.findCuaHangById(idCuaHang);
    }

    public List<ThucDon> getThucDon() {
        return this.thucDonRepository.findAll();
    }

    public List<MonAn> getMonAn() {
        return this.monAnRepository.findAll();
    }

    public boolean checkCuaHangExists(String idCuaHang) {
        return cuaHangRepository.findById(idCuaHang).isEmpty();
    }

    public boolean checkMaSoThueUnique(String maSoThue) {
        return hopDongRepository.findByMaSoThue(maSoThue).isEmpty();
    }

    public boolean checkDoiTacPossessShop(String idDoiTac, String idCuaHang) {
        return this.cuaHangRepository.findCuaHangByIdDoiTac(idCuaHang,idDoiTac).isEmpty();
    }
}
