package com.hmcuong.fooddelivery.service;

import com.hmcuong.fooddelivery.entity.DonDatHang;
import com.hmcuong.fooddelivery.entity.TaiXe;
import com.hmcuong.fooddelivery.error.ErrorMsg;
import com.hmcuong.fooddelivery.repository.DonDatHangRepository;
import com.hmcuong.fooddelivery.repository.TaiXeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaiXeService {

    private final TaiXeRepository taiXeRepository;
    private final DonDatHangRepository donDatHangRepository;

    public ResponseEntity<Object> getProfileById(String idTaiXe) {
        Optional<TaiXe> taiXeOptional = taiXeRepository.findById(idTaiXe);
        if(taiXeOptional.isPresent()){
            TaiXe taiXe = taiXeOptional.get();
            return new ResponseEntity<>(taiXe, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.NOT_FOUND,"TaiXe not found with idTaiXe: "+idTaiXe), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> getDonDatHang() {
        Optional<List<DonDatHang>> donDatHangList =  this.donDatHangRepository.findByTinhTrangDonHang("Waiting_For_Driver");
        if(donDatHangList.isPresent()){
            List<DonDatHang> donDatHangs = donDatHangList.get();
            return new ResponseEntity<>(donDatHangs, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.NOT_FOUND,"DonDatHang not found"), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> receiveOrder(String idTaiXe,String idDonDatHang) {
        // todo update DonDatHang
        Optional<TaiXe> taiXeOptional = taiXeRepository.findById(idTaiXe);
        Optional<DonDatHang> donDatHangOptional = donDatHangRepository.findById(idDonDatHang);
        if(donDatHangOptional.isPresent() && taiXeOptional.isPresent()){
            DonDatHang donDatHang = donDatHangOptional.get();
            donDatHang.setTaiXe(taiXeOptional.get());
            donDatHang.setTinhTrangDonHang("Preparing_For_Order");
            donDatHangRepository.save(donDatHang);
            return new ResponseEntity<>(donDatHang,HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorMsg(HttpStatus.BAD_REQUEST,"Something went wrong"), HttpStatus.BAD_REQUEST);
    }
}
