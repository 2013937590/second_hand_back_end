package com.fyy.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    @DecimalMax(value = "999999.99", message = "价格不能超过999999.99")
    private BigDecimal price;

    @NotNull(message = "新旧程度不能为空")
    @Min(value = 1, message = "新旧程度最小为1")
    @Max(value = 10, message = "新旧程度最大为10")
    private Integer conditionScore;

    private List<String> images;
} 