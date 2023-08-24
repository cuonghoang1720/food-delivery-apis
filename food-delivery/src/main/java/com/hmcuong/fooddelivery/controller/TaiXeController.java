package com.hmcuong.fooddelivery.controller;

import com.hmcuong.fooddelivery.service.TaiXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/TaiXe")
@RequiredArgsConstructor
public class TaiXeController {

    private final TaiXeService taiXeService;

    // Todo lấy thông tin của tài xế
    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(@RequestParam String idTaiXe){
        return this.taiXeService.getProfileById(idTaiXe);
    }

    // Todo tài xế lấy các đơn đặt hàng
    @GetMapping("/{idTaiXe}/dondathang")
    public ResponseEntity<Object> getDonDatHang(@PathVariable String idTaiXe){
        return this.taiXeService.getDonDatHang();
    }

    // Todo TaiXe nhận đơn hàng
    @PutMapping("/{idTaiXe}/dondathang/{idDonDatHang}/nhandonhang")
    public ResponseEntity<Object> receiveOrders(@PathVariable String idTaiXe,@PathVariable String idDonDatHang){
        return this.taiXeService.receiveOrder(idTaiXe,idDonDatHang);
    }

}
