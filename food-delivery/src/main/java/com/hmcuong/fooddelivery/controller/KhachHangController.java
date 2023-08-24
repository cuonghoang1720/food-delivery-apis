package com.hmcuong.fooddelivery.controller;

import com.hmcuong.fooddelivery.DTO.DonDatHang.CartRequestDTO;
import com.hmcuong.fooddelivery.DTO.DonDatHang.OrderRequestDTO;
import com.hmcuong.fooddelivery.DTO.DonDatHang.OrderResponseDTO;
import com.hmcuong.fooddelivery.entity.*;
import com.hmcuong.fooddelivery.error.ErrorMsg;
import com.hmcuong.fooddelivery.service.CuaHangService;
import com.hmcuong.fooddelivery.service.DoiTacService;
import com.hmcuong.fooddelivery.service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/khachhang")
@RequiredArgsConstructor
public class KhachHangController {

    private final KhachHangService khachHangService;
    private final DoiTacService doiTacService;
    private final CuaHangService cuaHangService;

    @GetMapping("/")
    public List<KhachHang> getKhachHang(){
        return this.khachHangService.getAllKhachHang();
    }

    // TODO Đăng ký khách hàng mới
    @PostMapping("/dangkythanhvien")
    public ResponseEntity<Object> InsertCustomer(@RequestBody KhachHang khachHang){
        if(khachHangService.saveKhachHang(khachHang)){
            return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Insert New Customer: "+ khachHang +" Successfully!"), HttpStatus.OK);
        }
            throw new IllegalArgumentException("Email & Phone number can be duplicate !");
    }

    // TODO xem danh sách đối tác
    @GetMapping("/doitac")
    public List<DoiTac> getDoiTac(){
        return doiTacService.getAllDoiTac();
    }

    // TODO xem danh sách các cửa hàng
    @GetMapping("/cuahang")
    public List<CuaHang> getCuaHang(){
        return cuaHangService.getAllCuaHang();
    }

    // TODO xem danh sách các MonAn của CuaHang
    @GetMapping("/cuahang/{idCuaHang}/monan")
    public ResponseEntity<List<MonAn>> getMonAnOfCuaHang(@PathVariable String idCuaHang) {
        Optional<ThucDon> thucDonOptional = cuaHangService.find_ThucDon_Of_CuaHang(idCuaHang);
        if (thucDonOptional.isPresent()) {
            ThucDon thucDon = thucDonOptional.get();
            List<MonAn> monAnList = cuaHangService.get_MonAn_Of_CuaHang(thucDon.getIdThucDon());
            return ResponseEntity.ok(monAnList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // TODO tìm kiếm món ăn theo tên
    @GetMapping("/tenmonan")
    public ResponseEntity<List<MonAn>> get_MonAn_By_TenMonAn(@RequestParam String tenMonAn){
        Optional<List<MonAn>> monAnList = khachHangService.get_MonAn_By_TenMonAn(tenMonAn);
        if(monAnList.isPresent()){
            List<MonAn> monAnList1 = monAnList.get();
            return ResponseEntity.ok(monAnList1);
        }
        return ResponseEntity.notFound().build();
    }

    // TODO tìm kiếm MonAn theo loại ẩm thực
    @GetMapping("/loaiamthuc")
    public ResponseEntity<List<CuaHang>> get_CuaHang_By_LoaiThucPham(@RequestParam String loaiAmThuc) {
        System.out.println("Loai Am Thuc: "+ loaiAmThuc);
        Optional<List<CuaHang>> cuaHangList = khachHangService.get_CuaHang_By_LoaiAmThuc(loaiAmThuc);
        if(cuaHangList.isPresent()){
            List<CuaHang> cuaHangList1 = cuaHangList.get();
            return ResponseEntity.ok(cuaHangList1);
        }
        return ResponseEntity.notFound().build();
    }

    // TODO khách hàng đặt hàng
    @PostMapping("/{idKhachHang}/datHang")
    public ResponseEntity<Object> addItemsToCart(
            @RequestBody OrderRequestDTO orderRequestDTO,
            @PathVariable String idKhachHang
    ) {
        System.out.println("OrderRequestDTO: " + orderRequestDTO);
        if(orderRequestDTO.hasNullDataBeforeInsert()) {
            return new ResponseEntity<>(new ErrorMsg(HttpStatus.BAD_REQUEST,"Please fill full information"),HttpStatus.BAD_REQUEST);
        }

        // TODO tạo một đơn đặt hàng với idKhachHang, idDonDatHang (được tự động generate)
        OrderResponseDTO orderResponseDTO = khachHangService.create_DonDatHang(idKhachHang,orderRequestDTO);

        if( orderResponseDTO.getMessage().equals("You need to fill whole information required")){
            return new ResponseEntity<>(orderResponseDTO,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(orderResponseDTO,HttpStatus.OK);
    }

    // todo KhachHang cập nhật tình trạng DonDatHang (thông thường hủy "DonHang")
    @PutMapping("/{idKhachHang}/dondathang/huydonhang")
    public ResponseEntity<Object> updateTinhTrangDonDatHang(
            @PathVariable String idKhachHang,
            @RequestParam String idDonDatHang
    ) {
        // kiểm tra KhachHang hợp lệ
        if(khachHangService.findKhachHangByIdKhachHang(idKhachHang).isEmpty()){
            return new ResponseEntity<>(new ErrorMsg(HttpStatus.NOT_FOUND,"KhachHang with: " + idKhachHang + " doesn't exists"),HttpStatus.NOT_FOUND);
        }
        // update tinhtrangdonhang -> canceled
        if(khachHangService.updateTinhTrangDonDatHang(idDonDatHang)){
            return new ResponseEntity<>(new ErrorMsg(HttpStatus.OK,"Canceled Orders Successfully"),HttpStatus.OK);
        }

        return new ResponseEntity<>(new ErrorMsg(HttpStatus.BAD_REQUEST,"something went wrong, please try again!"),HttpStatus.BAD_REQUEST);
    }
}