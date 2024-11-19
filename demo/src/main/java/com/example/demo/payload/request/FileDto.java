package com.example.demo.payload.request;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String fileName;
    private String fileUrl;

}
