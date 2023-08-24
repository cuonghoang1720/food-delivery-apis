package com.hmcuong.fooddelivery.service;

import com.hmcuong.fooddelivery.DTO.DonDatHang.CartRequestDTO;
import com.hmcuong.fooddelivery.DTO.DonDatHang.OrderRequestDTO;
import com.hmcuong.fooddelivery.DTO.DonDatHang.OrderResponseDTO;
import com.hmcuong.fooddelivery.entity.CuaHang;
import com.hmcuong.fooddelivery.entity.DonDatHang;
import com.hmcuong.fooddelivery.entity.KhachHang;
import com.hmcuong.fooddelivery.entity.MonAn;
import com.hmcuong.fooddelivery.enums.TinhTrangDonHangEnum;
import com.hmcuong.fooddelivery.repository.CuaHangRepository;
import com.hmcuong.fooddelivery.repository.DonDatHangRepository;
import com.hmcuong.fooddelivery.repository.KhachHangRepository;
import com.hmcuong.fooddelivery.repository.MonAnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KhachHangService {

    private final KhachHangRepository khachHangRepository;
    private final CuaHangRepository cuaHangRepository;
    private final MonAnRepository monAnRepository;
    private final DonDatHangRepository donDatHangRepository;

    // todo insert KhachHang to Database
    public boolean saveKhachHang(KhachHang khachHang) {
        KhachHang khachHang1 = khachHangRepository.save(khachHang);
        return (khachHang1 != null);
    }

    public List<KhachHang> getAllKhachHang() {
        return this.khachHangRepository.findAll();
    }

    public Optional<KhachHang> findKhachHangByIdKhachHang(String idKhachHang) {
        return this.khachHangRepository.findById(idKhachHang);
    }

    public Optional<List<MonAn>> get_MonAn_By_TenMonAn(String tenMonAn) {
        return monAnRepository.find_MonAn_By_TenMonAn(tenMonAn);
    }

    public Optional<List<CuaHang>> get_CuaHang_By_LoaiAmThuc(String loaiAmThuc) {
        return cuaHangRepository.find_CuaHang_By_LoaiAmThuc(loaiAmThuc);
    }

    public OrderResponseDTO create_DonDatHang(String idKhachHang, OrderRequestDTO orderRequestDTO) {

        String idDonDatHang = java.util.UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String tinhTrangDonHang = "Waiting_For_Driver";
        LocalDateTime ngayLap = LocalDateTime.now();
        double tongTien = 0;
        double phivanchuyen = 0;

        List<MonAn> listMonAn = new ArrayList<>();
        // todo extract CartRequestDTO to list MonAn
        for(CartRequestDTO cart : orderRequestDTO.getCartRequestDTOS()){
            if(cart.hasNullDataBeforeInsert()){
                return OrderResponseDTO
                        .builder()
                        .idDonDatHang(idDonDatHang)
                        .idKhachHang(idKhachHang)
                        .taiXeDTO(null)
                        .ngayLap(ngayLap)
                        .tinhTrangDonHang(tinhTrangDonHang)
                        .tongTien(tongTien)
                        .listMonAn(orderRequestDTO.getCartRequestDTOS())
                        .message("You need to fill whole information required")
                        .diaChiGiaoHang(orderRequestDTO.getDiaChiGiaoHang())
                        .build();
            }
            Optional<MonAn> monAnOptional = monAnRepository.findById(cart.getIdMonAn());
            if(monAnOptional.isPresent()){
                MonAn monAn = monAnOptional.get();
                monAn.setSoLuong(Integer.parseInt(cart.getSoLuong()));
                listMonAn.add(monAn);
            }
        }

        // todo tính tổng tiền đơn hàng
        for (MonAn ma : listMonAn) tongTien += (ma.getDonGia() * ma.getSoLuong());
        System.out.println("Tong tien: " + tongTien);

        try{
            DonDatHang donDatHang = donDatHangRepository.save(
                    DonDatHang
                            .builder()
                            .idDonDatHang(idDonDatHang)
                            .khachHang(khachHangRepository.findById(idKhachHang).get())
                            .tinhTrangDonHang(tinhTrangDonHang)
                            .diaChiGiaoHang(orderRequestDTO.getDiaChiGiaoHang())
                            .phiVanChuyen(phivanchuyen)
                            .tongTien(tongTien)
                            .ngayLap(LocalDateTime.now())
                            .monAns(listMonAn)
                            .build()
            );
        } catch(DataAccessException ex) {
            ex.printStackTrace();
        }

        // TODO Create DonDatHang thành công
        return OrderResponseDTO
                .builder()
                .idDonDatHang(idDonDatHang)
                .idKhachHang(idKhachHang)
                .taiXeDTO(null)
                .ngayLap(ngayLap)
                .tinhTrangDonHang(tinhTrangDonHang)
                .tongTien(tongTien)
                .listMonAn(orderRequestDTO.getCartRequestDTOS())
                .message("Create Order successfully!")
                .diaChiGiaoHang(orderRequestDTO.getDiaChiGiaoHang())
                .build();
    }

    // Todo Khách Hàng hủy đơn hàng
    public boolean updateTinhTrangDonDatHang(String idDonDatHang) {
        Optional<DonDatHang> donDatHangOptional = donDatHangRepository.findById(idDonDatHang);
        System.out.println("DonDatHangOptional: " + donDatHangOptional );
        if(donDatHangOptional.isPresent()){
            DonDatHang ddh = donDatHangOptional.get();
            ddh.setTinhTrangDonHang("Cancelled_Order");
            donDatHangRepository.save(ddh);
            return true;
        }
        return false;
    }
}
