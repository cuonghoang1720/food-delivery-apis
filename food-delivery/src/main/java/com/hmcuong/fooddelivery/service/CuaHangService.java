package com.hmcuong.fooddelivery.service;

import com.hmcuong.fooddelivery.entity.CuaHang;
import com.hmcuong.fooddelivery.entity.DonDatHang;
import com.hmcuong.fooddelivery.entity.MonAn;
import com.hmcuong.fooddelivery.entity.ThucDon;
import com.hmcuong.fooddelivery.enums.HoatDongEnum;
import com.hmcuong.fooddelivery.enums.TinhTrangCuaHangEnum;
import com.hmcuong.fooddelivery.repository.CuaHangRepository;
import com.hmcuong.fooddelivery.repository.DonDatHangRepository;
import com.hmcuong.fooddelivery.repository.MonAnRepository;
import com.hmcuong.fooddelivery.repository.ThucDonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CuaHangService {
    private final CuaHangRepository cuaHangRepository;
    private final ThucDonRepository thucDonRepository;
    private final MonAnRepository monAnRepository;
    private final DonDatHangRepository donDatHangRepository;

    // todo Check CuaHang exists In Database
    public boolean checkIdCuaHangValid(String idCuaHang) {
        return cuaHangRepository.findById(idCuaHang).isEmpty();
    }

    public void updateTenCuaHang(String tenCuaHangNew,String idCuaHang) {
        Optional<CuaHang> cuaHangOptional = cuaHangRepository.findCuaHangById(idCuaHang);
        if(cuaHangOptional.isPresent()){
            CuaHang cuaHang = cuaHangOptional.get();
            cuaHang.setTenCuaHang(tenCuaHangNew);
            cuaHangRepository.save(cuaHang);
        }
    }

    public List<CuaHang> findCuaHangOfDoiTac(String idDoiTac) {
        return this.cuaHangRepository.findCuaHangOfDoiTac(idDoiTac).orElseThrow();
    }

    public Optional<CuaHang> findCuaHangById(String idCuaHang) {
        return this.cuaHangRepository.findById(idCuaHang);
    }

    public void updateHoatDong(String idCuaHang, HoatDongEnum hoatDong) {
        Optional<CuaHang> cuaHangOptional = cuaHangRepository.findCuaHangById(idCuaHang);
        if(cuaHangOptional.isPresent()){
            CuaHang cuaHang = cuaHangOptional.get();
            cuaHang.setHoatDong(hoatDong);
            cuaHangRepository.save(cuaHang);
        }
    }

    public void updateTinhTrang(String idCuaHang, TinhTrangCuaHangEnum tinhTrang) {
        Optional<CuaHang> cuaHangOptional = cuaHangRepository.findCuaHangById(idCuaHang);
        if(cuaHangOptional.isPresent()){
            CuaHang cuaHang = cuaHangOptional.get();
            cuaHang.setTinhTrang(tinhTrang);
            cuaHangRepository.save(cuaHang);
        }
    }

    public boolean checkIdThucDonValid(String idThucDon) {
        return this.thucDonRepository.findByIdThucDon(idThucDon).isEmpty();
    }

    public Optional<List<ThucDon>> find_ThucDon_Of_DoiTac(String idDoiTac) {
        return this.thucDonRepository.find_ThucDon_Of_DoiTac(idDoiTac);
    }

    public Optional<ThucDon> find_ThucDon_Of_DoiTac_And_CuaHang(String idDoiTac,String idCuaHang) {
        return this.thucDonRepository.find_ThucDon_Of_DoiTac_And_CuaHang(idDoiTac,idCuaHang);
    }

    public List<MonAn> get_MonAn_Of_CuaHang(String idThucDon) {
        return this.monAnRepository.findByThucDon(idThucDon);
    }

    public boolean checkCuaHangPossessThucDon(String idCuaHang, String idThucDon) {
        return thucDonRepository.find_ThuDon_By_CuaHang(idCuaHang,idThucDon).isEmpty();
    }

    public boolean checkIdMonAnValid(String idMonAn) {
        return monAnRepository.findById(idMonAn).isEmpty();
    }

    public boolean checkThucDonPossessMonAn(String idThucDon, String idMonAn) {
        return monAnRepository.find_MonAn_By_ThucDon(idThucDon,idMonAn).isEmpty();
    }

    public List<CuaHang> getAllCuaHang() {
        return cuaHangRepository.findAll();
    }

    public Optional<ThucDon> find_ThucDon_Of_CuaHang(String idCuaHang) {
        return thucDonRepository.findByIdCuaHang(idCuaHang);
    }

    public boolean checkDonDatHangValid(String idDonDatHang) {
        return donDatHangRepository.findById(idDonDatHang).isEmpty();
    }

    public boolean checkCuaHangPossessDonDatHang(String idCuaHang, String idDonDatHang) {
        List<DonDatHang> listDonDatHang = donDatHangRepository.findByMonAns_ThucDon_CuaHang_IdCuaHang(idCuaHang);
        for(DonDatHang ddh : listDonDatHang) {
            if(ddh.getIdDonDatHang().equals(idDonDatHang))  return true;
        }
        return false;
    }
}
