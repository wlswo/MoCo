package com.board.board.controller;


import com.board.board.service.aws.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    /** @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환 */
    @Operation(summary = "이미지 업로드 요청", description = "게시글 작성, 프로필 사진 업로드시 요청 Api 입니다.")
    @PostMapping("/image")
    public ResponseEntity uploadImage(@Parameter(description = "이미지를 multipartFile 타입으로 받습니다.") @RequestParam(value = "image", required = true) MultipartFile multipartFile) {
        return ResponseEntity.ok().body(awsS3Service.uploadImage(multipartFile));
    }

    /** @return 성공 시 200 Success */
    @Operation(summary = "이미지 삭제 요청", description = "이미지 삭제시 요청 Api 입니다.")
    @DeleteMapping("/image")
    public ResponseEntity<Void> deleteImage(@Parameter(description = "이미지가 저장된 url 를 파라미터로 받습니다.") @RequestBody(required = true) String fileName) {
        awsS3Service.deleteImage(fileName);
        return ResponseEntity.ok().build();
    }
}
