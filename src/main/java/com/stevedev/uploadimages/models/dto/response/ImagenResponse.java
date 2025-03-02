package com.stevedev.uploadimages.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagenResponse {
    private Long id;
    private String url;
}
