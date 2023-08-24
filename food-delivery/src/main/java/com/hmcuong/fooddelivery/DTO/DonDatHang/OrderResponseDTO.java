package com.hmcuong.fooddelivery.DTO.DonDatHang;

import com.hmcuong.fooddelivery.DTO.TaiXe.TaiXeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String idDonDatHang;
    private String idKhachHang;
    private TaiXeDTO taiXeDTO;
    private LocalDateTime ngayLap;
    private double tongTien;
    private List<CartRequestDTO> listMonAn;
    private String diaChiGiaoHang;
    private String tinhTrangDonHang;
    private String message;
}
