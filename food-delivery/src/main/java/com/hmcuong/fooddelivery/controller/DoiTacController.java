package com.hmcuong.fooddelivery.controller;

import com.hmcuong.fooddelivery.DTO.HopDong.HopDongDTO;
import com.hmcuong.fooddelivery.DTO.MonAn.MonAnDTO;
import com.hmcuong.fooddelivery.entity.*;
import com.hmcuong.fooddelivery.enums.HoatDongEnum;
import com.hmcuong.fooddelivery.enums.TinhTrangCuaHangEnum;
import com.hmcuong.fooddelivery.enums.TinhTrangDonHangEnum;
import com.hmcuong.fooddelivery.error.CuaHang.CuaHangAlreadyExists;
import com.hmcuong.fooddelivery.error.CuaHang.CuaHangNotFound;
import com.hmcuong.fooddelivery.error.CuaHang.CuaHangPossessDonDatHang;
import com.hmcuong.fooddelivery.error.CuaHang.CuaHangPossessThucDon;
import com.hmcuong.fooddelivery.error.DoiTac.DoiTacPossessShop;
import com.hmcuong.fooddelivery.error.DoiTac.EmailDoiTacInvalid;
import com.hmcuong.fooddelivery.error.DoiTac.DoiTacNotFound;
import com.hmcuong.fooddelivery.error.DonDatHang.DonDatHangNotFound;
import com.hmcuong.fooddelivery.error.ErrorMsg;
import com.hmcuong.fooddelivery.error.HopDong.MaSoThueNotUnique;
import com.hmcuong.fooddelivery.error.MonAn.MonAnNotFound;
import com.hmcuong.fooddelivery.error.ThucDon.ThucDonNotFound;
import com.hmcuong.fooddelivery.error.ThucDon.ThucDonPossessMonAn;
import com.hmcuong.fooddelivery.service.CuaHangService;
import com.hmcuong.fooddelivery.service.DoiTacService;
import com.hmcuong.fooddelivery.service.LapHopDongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doitac")
@RequiredArgsConstructor
public class DoiTacController {

    private final LapHopDongService lapHopDongService;
    private final DoiTacService doiTacService;
    private final CuaHangService cuaHangService;

    // todo DoiTac đăng ký thông tin
    @PostMapping("/dangkythongtin")
    public ResponseEntity<Object> insertPartner(@RequestBody DoiTac doiTac){
        System.out.println("Thong Tin DoiTac: " + doiTac);
        if(doiTac.hasNullDataBeforeRegister(doiTac)){
            throw new NullPointerException("Please fill in all the information");
        }
        if(lapHopDongService.checkDoiTacExistsByEmail(doiTac.getEmail())){
            throw new EmailDoiTacInvalid("DoiTac with email: " + doiTac.getEmail() + " has already exists");
        }
        lapHopDongService.savePartner(doiTac);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Successfully"),HttpStatus.OK);
    }

    // todo DoiTac thêm thông tin cửa hàng
    @PostMapping("/{idDoiTac}/themcuahang")
    public ResponseEntity<Object> insertShop(@RequestBody CuaHang cuaHang,@PathVariable String idDoiTac){
        if(cuaHang.hasNullDataBeforeRegister(cuaHang)){
            throw new NullPointerException("Please fill in all the information");
        }
        DoiTac dt = lapHopDongService.findDoiTacById(idDoiTac);
        if(dt == null) {
            throw new DoiTacNotFound("DoiTac with idDoiTac: "+ idDoiTac + " doesn't");
        }
        if(!lapHopDongService.checkCuaHangExists(cuaHang.getIdCuaHang())){
            throw new CuaHangAlreadyExists("CuaHang with idCuaHang: "+ cuaHang.getIdCuaHang() + " doesn't exists");
        }
        lapHopDongService.saveShop(
                CuaHang
                        .builder()
                        .idCuaHang(cuaHang.getIdCuaHang())
                        .tenCuaHang(cuaHang.getTenCuaHang())
                        .hoatDong(cuaHang.getHoatDong())
                        .diaChi(cuaHang.getDiaChi())
                        .doiTac(dt)
                        .tinhTrang(cuaHang.getTinhTrang())
                        .build()
                );
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Insert Shop Successfully"),HttpStatus.OK);
    }

    // todo DoiTac lưu thực đơn của cửa hàng
    @PostMapping("/{idDoiTac}/cuahang/{idCuaHang}/themthucdon")
    public ResponseEntity<Object> insertMenu(@RequestBody ThucDon thucDon,@PathVariable String idDoiTac,@PathVariable String idCuaHang){
        if(thucDon.hasNullDataBeforeRegister(thucDon)){
            throw new NullPointerException("Please fill in all the information");
        }

        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);

//        System.out.println("ThucDon: "+ thucDon);
        CuaHang cuaHang = lapHopDongService.findCuaHangById(idCuaHang).orElseThrow();
//        System.out.println("cuaHang : " + cuaHang);
        lapHopDongService.saveMenu(
                ThucDon
                        .builder()
                        .idThucDon(thucDon.getIdThucDon())
                        .TenThucDon(thucDon.getTenThucDon())
                        .MoTa(thucDon.getMoTa())
                        .cuaHang(cuaHang)
                        .build()
        );
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Create Menu Successfully"),HttpStatus.OK);
    }

    // todo Thêm món ăn vào thực đơn
    @PostMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/themmonan")
    public ResponseEntity<Object> insertFood(@RequestBody MonAnDTO monAnDTO, @PathVariable String idThucDon){
        System.out.println("MonAnDTO: " + monAnDTO);
        if(monAnDTO.hasNullDataBeforeRegister(monAnDTO)){
            throw new NullPointerException("Please fill in all the information");
        }
        lapHopDongService.saveFood(
                MonAn
                        .builder()
                        .idMonAn(monAnDTO.getIdMonAn())
                        .MieuTaMon(monAnDTO.getMieuTaMon())
                        .TenMon(monAnDTO.getTenMon())
                        .DonGia(monAnDTO.getDonGia())
                        .TinhTrangMon(monAnDTO.getTinhTrangMon())
                        .thucDon(lapHopDongService.findThucDonById(idThucDon))
                        .TuyChon(monAnDTO.getTuyChon())
                        .build()
        );
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Create Food Successfully"),HttpStatus.OK);
    }

    // todo DoiTac lập hợp đồng
    @PostMapping("/{idDoiTac}/laphopdong")
    public ResponseEntity<Object> insertContract(@RequestBody HopDongDTO hopDongDTO, @PathVariable String idDoiTac){
        System.out.println("HopDong: " + hopDongDTO);
        if(hopDongDTO.hasNullDataBeforeRegister(hopDongDTO)) {
            throw new NullPointerException("Please fill in all the information");
        }
        if(lapHopDongService.checkMaSoThueUnique(hopDongDTO.getMaSoThue())){
            throw new MaSoThueNotUnique("MaSoThue: " + hopDongDTO.getMaSoThue() + " is not Unique");
        }
        lapHopDongService.saveContract(
                HopDong
                        .builder()
                        .id_HopDong(hopDongDTO.getId_HopDong())
                        .doiTac(lapHopDongService.findDoiTacById(idDoiTac))
                        .ngayLap(hopDongDTO.getNgayLap())
                        .ngayKetThuc(hopDongDTO.getNgayKetThuc())
                        .maSoThue(hopDongDTO.getMaSoThue())
                        .nguoiDaiDien(hopDongDTO.getNguoiDaiDien())
                        .soChiNhanhDangKy(hopDongDTO.getSoChiNhanhDangKy())
                        .diaChiCacChiNhanh(hopDongDTO.getDiaChiCacChiNhanh())
                        .soTaiKhoan(hopDongDTO.getSoTaiKhoan())
                        .nganHang(hopDongDTO.getNganHang())
                        .chiNhanhNganHang(hopDongDTO.getChiNhanhNganHang())
                        .phiKichHoat(hopDongDTO.getPhiKichHoat())
                        .lanGiaHan(hopDongDTO.getLanGiaHan())
                        .MoTa(hopDongDTO.getMoTa())
                        .build()
        );
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Create Contract Successfully"),HttpStatus.OK);
    }

    // todo DoiTac cập nhật tên CuaHang
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/updateCuaHang")
    public ResponseEntity<Object> updateTenCuaHang(@RequestParam  String tenCuaHangNew, @PathVariable String idCuaHang,@PathVariable String idDoiTac){
        System.out.println("ten cua hang new: " + tenCuaHangNew);
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        cuaHangService.updateTenCuaHang(tenCuaHangNew,idCuaHang);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'TenCuaHang' Successfully"),HttpStatus.OK);
    }

    // todo Get CuaHang của DoiTac
    @GetMapping("/{idDoiTac}/cuahang")
    public List<CuaHang> findCuaHangOfDoiTac(@PathVariable String idDoiTac){
        // todo Check idDoiTac
        if(doiTacService.checkIdDoiTacValid(idDoiTac)){
            throw new DoiTacNotFound("DoiTac with: " + idDoiTac + "is not exists");
        }
        return cuaHangService.findCuaHangOfDoiTac(idDoiTac);
    }

    // todo DoiTac cập nhật hoạt động của CuaHang
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/updateHoatDong")
    public ResponseEntity<Object> update_hoatDong_CuaHang(@PathVariable String idDoiTac,@PathVariable String idCuaHang, @RequestParam HoatDongEnum hoatDong){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        cuaHangService.updateHoatDong(idCuaHang,hoatDong);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'HoatDong' Successfully"),HttpStatus.OK);
    }

    // todo DoiTac cập nhật tình trạng của CuaHang
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/updateTinhTrang")
    public ResponseEntity<Object> update_tinhTrang_CuaHang(@PathVariable String idDoiTac,@PathVariable String idCuaHang, @RequestParam TinhTrangCuaHangEnum tinhTrang){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        cuaHangService.updateTinhTrang(idCuaHang,tinhTrang);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'TinhTrang' Successfully"),HttpStatus.OK);
    }

    // TODO DoiTac xem danh sách thực đơn
    @GetMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucDon")
    public List<ThucDon> getThucDon(@PathVariable String idDoiTac,@PathVariable String idCuaHang) {
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        return cuaHangService.find_ThucDon_Of_DoiTac(idDoiTac).orElseThrow();
    }

    // TODO DoiTac xem danh sách món ăn của thực đơn
    @GetMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/monan")
    public List<MonAn> getMonAnOfThucDon(@PathVariable String idDoiTac,@PathVariable String idCuaHang,@PathVariable String idThucDon) {
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckCuaHangPossessThucDon(idCuaHang,idThucDon);
        ThucDon thucDon = cuaHangService.find_ThucDon_Of_DoiTac_And_CuaHang(idDoiTac,idCuaHang).orElseThrow();
        // get MonAn by ThucDon
        return cuaHangService.get_MonAn_Of_CuaHang(thucDon.getIdThucDon());
    }

    // TODO DoiTac cập nhật 'TenThucDon'
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/updateTenThucDon")
    public ResponseEntity<Object> update_TenThucDon(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang,
            @PathVariable String idThucDon,
            @RequestParam String tenThucDonNew
    ){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckCuaHangPossessThucDon(idCuaHang,idThucDon);
        doiTacService.updateTenThuDon(idDoiTac,idCuaHang,idThucDon,tenThucDonNew);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'TenThucDon' Successfully"),HttpStatus.OK);
    }

    // TODO DoiTac cập nhật 'MoTa' của ThucDon
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/updateMoTaThucDon")
    public ResponseEntity<Object> update_MoTa_ThucDon(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang,
            @PathVariable String idThucDon,
            @RequestParam String moTaNew
    ){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckCuaHangPossessThucDon(idCuaHang,idThucDon);
        doiTacService.update_MoTa_ThucDon(idDoiTac,idCuaHang,idThucDon,moTaNew);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'MoTa' of ThucDon Successfully"),HttpStatus.OK);
    }

    // TODO 'DoiTac' cập nhật 'DonGia' của 'MonAn'
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/monan/{idMonAn}/updateDonGia")
    public ResponseEntity<Object> update_MoTa_ThucDon(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang,
            @PathVariable String idThucDon,
            @PathVariable String idMonAn,
            @RequestParam String donGiaNew
    ){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckCuaHangPossessThucDon(idCuaHang,idThucDon);
        CheckThucDonPossessMonAn(idThucDon,idMonAn);
        doiTacService.update_DonGia_MonAn(idDoiTac,idCuaHang,idThucDon,idMonAn,donGiaNew);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'DonGia' of MonAn Successfully"),HttpStatus.OK);
    }

    // TODO 'DoiTac' cập nhật 'TenMonAn' của 'MonAn'
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/monan/{idMonAn}/updateTenMonAn")
    public ResponseEntity<Object> update_TenMonAn(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang,
            @PathVariable String idThucDon,
            @PathVariable String idMonAn,
            @RequestParam String tenMonAnNew
    ){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckCuaHangPossessThucDon(idCuaHang,idThucDon);
        CheckThucDonPossessMonAn(idThucDon,idMonAn);
        doiTacService.update_TenMonAn_MonAn(idDoiTac,idCuaHang,idThucDon,idMonAn,tenMonAnNew);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'TenMonAn' of MonAn Successfully"),HttpStatus.OK);
    }

    // TODO 'DoiTac' cập nhật 'TinhTrangMon' của 'MonAn'
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/thucdon/{idThucDon}/monan/{idMonAn}/updateTinhTrangMonAn")
    public ResponseEntity<Object> update_TinhTrangMon_MonAn(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang,
            @PathVariable String idThucDon,
            @PathVariable String idMonAn,
            @RequestParam String tinhTrangMonNew
    ){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckCuaHangPossessThucDon(idCuaHang,idThucDon);
        CheckThucDonPossessMonAn(idThucDon,idMonAn);
        doiTacService.update_TinhTrangMon_MonAn(idDoiTac,idCuaHang,idThucDon,idMonAn,tinhTrangMonNew);
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Update 'TenMonAn' of MonAn Successfully"),HttpStatus.OK);
    }

    // todo Đối tác xem các đơn đặt hàng đang ở tình trạng "preparing"
    @GetMapping("/{idDoiTac}/cuahang/{idCuaHang}/dondathang")
    public ResponseEntity<Object> get_DonDatHang_Of_CuaHang_Of_DoiTac(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang
    ){
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        List<DonDatHang> listDonDatHangOptional = doiTacService.get_DonDatHang_Of_Cuahang(idCuaHang);
        return new ResponseEntity<>(listDonDatHangOptional,HttpStatus.OK);
    }

    // todo Đối Tác tiếp nhận/không tiếp nhận đơn hàng
    @PutMapping("/{idDoiTac}/cuahang/{idCuaHang}/dondathang/{idDonDatHang}")
    public ResponseEntity<Object> partnerReceiveOrders(
            @PathVariable String idDoiTac,
            @PathVariable String idCuaHang,
            @PathVariable String idDonDatHang,
            @RequestParam TinhTrangDonHangEnum tinhTrangDonHang
    ){
        System.out.println("Tinh trang don hang: "+ tinhTrangDonHang);
        CheckDoiTacPossessCuaHang(idDoiTac,idCuaHang);
        CheckDonDatHangOfCuaHang(idCuaHang,idDonDatHang);
        if(doiTacService.receiveOrders(idDonDatHang,tinhTrangDonHang)){
            return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,tinhTrangDonHang +" Orders Successfully with idDonDatHang: " + idDonDatHang),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.BAD_REQUEST,tinhTrangDonHang + " Orders Failed !"),HttpStatus.BAD_REQUEST);
    }

    void CheckDoiTacPossessCuaHang(String idDoiTac,String idCuaHang){
        // todo Check idDoiTac
        if(doiTacService.checkIdDoiTacValid(idDoiTac)){
            throw new DoiTacNotFound("DoiTac with: " + idDoiTac + "is not exists");
        }
        // todo Check idCuaHang
        if(cuaHangService.checkIdCuaHangValid(idCuaHang)){
            throw new CuaHangNotFound("CuaHang with: " + idCuaHang + "is not exists");
        }
        // todo check DoiTac có CuaHang hay không
        if(lapHopDongService.checkDoiTacPossessShop(idDoiTac,idCuaHang)){
            throw new DoiTacPossessShop("DoiTac: " + idDoiTac + " don't possess CuaHang: " + idCuaHang);
        }
    }

    void CheckCuaHangPossessThucDon(String idCuaHang,String idThucDon){
        // todo Check idCuaHang
        if(cuaHangService.checkIdCuaHangValid(idCuaHang)){
            throw new CuaHangNotFound("CuaHang with: " + idCuaHang + "is not exists");
        }
        // todo Check idThucDon
        if(cuaHangService.checkIdThucDonValid(idThucDon)){
            throw new ThucDonNotFound("ThucDon with: " + idThucDon + "is not exists");
        }
        // todo check CuaHang có ThucDon hay không
        if(cuaHangService.checkCuaHangPossessThucDon(idCuaHang,idThucDon)){
            throw new CuaHangPossessThucDon("CuaHang: " + idCuaHang + " don't possess ThucDon: " + idThucDon);
        }
    }

    void CheckThucDonPossessMonAn(String idThucDon,String idMonAn) {
        // todo Check idThucDon Valid
        if(cuaHangService.checkIdThucDonValid(idThucDon)){
            throw new ThucDonNotFound("ThucDon with: " + idThucDon + "is not exists");
        }
        // todo Check idMonAn Valid
        if(cuaHangService.checkIdMonAnValid(idMonAn)){
            throw new MonAnNotFound("MonAn with: " + idMonAn + "is not exists");
        }
        if(cuaHangService.checkThucDonPossessMonAn(idThucDon,idMonAn)){
            throw new ThucDonPossessMonAn("ThucDon: " + idThucDon + " don't possess MonAn: " + idMonAn);
        }
    }

    void CheckDonDatHangOfCuaHang(String idCuaHang,String idDonDatHang) {
        // todo Check idCuaHang
        if(cuaHangService.checkIdCuaHangValid(idCuaHang)){
            throw new CuaHangNotFound("CuaHang with: " + idCuaHang + "is not exists");
        }
        // todo Check DonDatHang Valid
        if(cuaHangService.checkDonDatHangValid(idDonDatHang)){
            throw new DonDatHangNotFound("DonDatHang with idDonDatHang: "+ idDonDatHang + " is not exists");
        }
        if(!cuaHangService.checkCuaHangPossessDonDatHang(idCuaHang,idDonDatHang)){
            throw new CuaHangPossessDonDatHang("CuaHang: " + idCuaHang + " don't possess DonDatHang: " + idDonDatHang);
        }
    }

}
