package com.hmcuong.fooddelivery.error;

import com.hmcuong.fooddelivery.error.CuaHang.CuaHangNotFound;
import com.hmcuong.fooddelivery.error.CuaHang.CuaHangPossessDonDatHang;
import com.hmcuong.fooddelivery.error.CuaHang.CuaHangPossessThucDon;
import com.hmcuong.fooddelivery.error.DoiTac.DoiTacPossessShop;
import com.hmcuong.fooddelivery.error.DoiTac.EmailDoiTacInvalid;
import com.hmcuong.fooddelivery.error.DoiTac.DoiTacNotFound;
import com.hmcuong.fooddelivery.error.DonDatHang.DonDatHangNotFound;
import com.hmcuong.fooddelivery.error.HopDong.MaSoThueNotUnique;
import com.hmcuong.fooddelivery.error.MonAn.MonAnNotFound;
import com.hmcuong.fooddelivery.error.ThucDon.ThucDonNotFound;
import com.hmcuong.fooddelivery.error.ThucDon.ThucDonPossessMonAn;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class ExceptionController {
    private static String getSimpleName(Exception e){
        return e.getClass().getSimpleName();
    }

    // todo "DoiTac" đã tồn tại với email này
    @ExceptionHandler(EmailDoiTacInvalid.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMsg handleDoiTacInvalid(EmailDoiTacInvalid e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.BAD_REQUEST,
                getSimpleName(e)
        );
    }
    // todo Không tìm thấy "DoiTac" bằng "idDoiTac" trong database
    @ExceptionHandler(DoiTacNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleDoiTacNotFound(DoiTacNotFound e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }
    // todo Handle không tìm thấy "CuaHang" bằng "idCuaHang" trong database
    @ExceptionHandler(CuaHangNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleCuaHangNotFound(CuaHangNotFound e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle "MaSoThue" không phải là giá trị unique
    @ExceptionHandler(MaSoThueNotUnique.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorMsg handleMaSoThueNotUnique(CuaHangNotFound e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_ACCEPTABLE,
                getSimpleName(e)
        );
    }

    // Todo Handle "DoiTac" không sở hữu "Cuahang"
    @ExceptionHandler(DoiTacPossessShop.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleDoiTacPossessShop(DoiTacPossessShop e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle "ThucDon" không tồn tại trong database
    @ExceptionHandler(ThucDonNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleThucDonNotFound(ThucDonNotFound e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle "CuaHang" không sở hữu "ThucDon"
    @ExceptionHandler(CuaHangPossessThucDon.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleCuaHangPossessThucDon(CuaHangPossessThucDon e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle "MonAn" không tồn tại trong database
    @ExceptionHandler(MonAnNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleMonAnNotFound(MonAnNotFound e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle "ThucDon" không sở hữu "MonAn"
    @ExceptionHandler(ThucDonPossessMonAn.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleMonAnNotFound(ThucDonPossessMonAn e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle DonDatHang không tồn tại
    @ExceptionHandler(DonDatHangNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleDonDatHangNotFound(DonDatHangNotFound e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }

    // Todo Handle "CuaHang" không sở hữu "DonDatHang"
    @ExceptionHandler(CuaHangPossessDonDatHang.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMsg handleCuaHangPossessDonDatHang(CuaHangPossessDonDatHang e, WebRequest request) {
        return new ErrorMsg(
                HttpStatus.NOT_FOUND,
                getSimpleName(e)
        );
    }
}