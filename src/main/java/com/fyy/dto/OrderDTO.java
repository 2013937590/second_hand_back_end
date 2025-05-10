package com.fyy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class OrderDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotBlank(message = "收货地址不能为空")
    private String address;

    @NotBlank(message = "收货人不能为空")
    private String contact_name;

    @NotBlank(message = "联系电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String contact_phone;
} 