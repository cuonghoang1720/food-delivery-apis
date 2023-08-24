package com.hmcuong.fooddelivery.service;

import com.hmcuong.fooddelivery.entity.DoiTac;
import com.hmcuong.fooddelivery.entity.DonDatHang;
import com.hmcuong.fooddelivery.entity.MonAn;
import com.hmcuong.fooddelivery.entity.ThucDon;
import com.hmcuong.fooddelivery.enums.TinhTrangDonHangEnum;
import com.hmcuong.fooddelivery.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoiTacService {

    private final DoiTacRepository doiTacRepository;
    private final CuaHangRepository cuaHangRepository;
    private final ThucDonRepository thucDonRepository;
    private final MonAnRepository monAnRepository;
    private final DonDatHangRepository donDatHangRepository;

    // todo Check DoiTac Exists in database
    public boolean checkIdDoiTacValid(String idDoiTac) {
        return doiTacRepository.findByIdDoiTac(idDoiTac).isEmpty();
    }

    public void updateTenThuDon(String idDoiTac, String idCuaHang, String idThucDon, String tenThucDonNew) {
        Optional<ThucDon> thucDonOptional = thucDonRepository.find_ThucDon_By_DoiTac_CuaHang(idDoiTac,idCuaHang,idThucDon);
        if(thucDonOptional.isPresent()){
            ThucDon thucDon = thucDonOptional.get();
            thucDon.setTenThucDon(tenThucDonNew);
            thucDonRepository.save(thucDon);
        }
    }

    public void update_MoTa_ThucDon(String idDoiTac, String idCuaHang, String idThucDon, String moTaNew) {
        Optional<ThucDon> thucDonOptional = thucDonRepository.find_ThucDon_By_DoiTac_CuaHang(idDoiTac,idCuaHang,idThucDon);
        if(thucDonOptional.isPresent()){
            ThucDon thucDon = thucDonOptional.get();
            thucDon.setMoTa(moTaNew);
            thucDonRepository.save(thucDon);
        }
    }

    // todo Update DonGia của MonAn
    public void update_DonGia_MonAn(String idDoiTac, String idCuaHang, String idThucDon, String idMonAn, String donGiaNew) {
        Optional<MonAn> monAnOptional = monAnRepository.find_MonAn_By_DoiTac_CuaHang_ThucDon(idDoiTac,idCuaHang,idThucDon,idMonAn);
        if(monAnOptional.isPresent()){
            MonAn monAn = monAnOptional.get();
            monAn.setDonGia(Double.parseDouble(donGiaNew));
            monAnRepository.save(monAn);
        }
    }

    // todo Update 'TenMonAn' của 'MonAn'
    public void update_TenMonAn_MonAn(String idDoiTac, String idCuaHang, String idThucDon, String idMonAn, String tenMonAnNew) {
        Optional<MonAn> monAnOptional = monAnRepository.find_MonAn_By_DoiTac_CuaHang_ThucDon(idDoiTac,idCuaHang,idThucDon,idMonAn);
        if(monAnOptional.isPresent()){
            MonAn monAn = monAnOptional.get();
            monAn.setTenMon(tenMonAnNew);
            monAnRepository.save(monAn);
        }
    }

    public void update_TinhTrangMon_MonAn(String idDoiTac, String idCuaHang, String idThucDon, String idMonAn, String tinhTrangMonNew) {
        Optional<MonAn> monAnOptional = monAnRepository.find_MonAn_By_DoiTac_CuaHang_ThucDon(idDoiTac,idCuaHang,idThucDon,idMonAn);
        if(monAnOptional.isPresent()){
            MonAn monAn = monAnOptional.get();
            monAn.setTinhTrangMon(tinhTrangMonNew);
            monAnRepository.save(monAn);
        }
    }

    public List<DoiTac> getAllDoiTac() {
        return doiTacRepository.findAll();
    }

    // Todo lấy đơn hàng của cửa hàng thuộc về đối tác đang trong tình trạng processing
    public List<DonDatHang> get_DonDatHang_Of_Cuahang(String idCuaHang) {
        return donDatHangRepository.findByMonAns_ThucDon_CuaHang_IdCuaHang(idCuaHang);
    }

    public boolean receiveOrders(String idDonDatHang, TinhTrangDonHangEnum tinhTrangDonHang) {
        Optional<DonDatHang> donDatHangOptional = donDatHangRepository.findById(idDonDatHang);
        if(donDatHangOptional.isPresent()) {
            DonDatHang donDatHang = donDatHangOptional.get();
            donDatHang.setTinhTrangDonHang(tinhTrangDonHang.toString());
            donDatHangRepository.save(donDatHang);
            return true;
        }
        return false;
    }
}
