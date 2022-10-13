package com.board.board.controller;


import com.board.board.service.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    /**
     * Amazon S3에 이미지 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @PostMapping("/image")
    public ResponseEntity uploadImage(@RequestParam(value = "image", required = true) MultipartFile multipartFile) {
        return ResponseEntity.ok().body(awsS3Service.uploadImage(multipartFile));
    }

    /**
     * Amazon S3에 이미지 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */
    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteImage(@RequestBody(required = true) String fileName) {
        awsS3Service.deleteImage(fileName);
        return ResponseEntity.ok().build();
    }
}
