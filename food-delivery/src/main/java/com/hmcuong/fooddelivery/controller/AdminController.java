package com.hmcuong.fooddelivery.controller;

import com.hmcuong.fooddelivery.entity.DoiTac;
import com.hmcuong.fooddelivery.entity.HopDong;
import com.hmcuong.fooddelivery.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // todo lấy danh sách tất cả các đối tác
    @GetMapping("/doitac")
    public List<DoiTac> getAllDoiTac(){
        return adminService.getAllDoiTac();
    }

    // todo lấy dánh sách tất cả các hợp đồng
    @GetMapping("/hopdong")
    public List<HopDong> getAllHopDong(){
        return adminService.getAllHopDong();
    }

    // todo lấy danh sách hợp đồng của đối tác
    @GetMapping("/{idDoiTac}/hopdong")
    public List<HopDong> getHopDongByIdDoiTac(@PathVariable String idDoiTac){
        return adminService.getAllHopDongByIdDoiTac(idDoiTac);
    }

}
