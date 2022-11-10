package com.board.board.service.profile;

import com.board.board.config.auth.SessionUser;
import com.board.board.domain.User;
import com.board.board.service.aws.AwsS3Service;
import com.board.board.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final HttpSession httpSession;
    private final UserService userService;
    private final AwsS3Service awsS3Service;

    public Long CheckProfileAndChange(SessionUser sessionUser, String name, MultipartFile multipartFile, boolean isImgDelete) {
        /* 프로필 사진만 바꾼 경우 */
        if(sessionUser.getName().equals(name)) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                User user = userService.profileUpdateInSetting(sessionUser.getId(),awsS3Service.uploadImage(multipartFile));
                httpSession.setAttribute("user",new SessionUser(user));
            }
            /* 기본 사진으로 바꾼 경우 */
            if(isImgDelete) {
                User user = userService.profileUpdateInSetting(sessionUser.getId(),"/img/userIcon.png");
                httpSession.setAttribute("user",new SessionUser(user));
            }
            return 200L;
        }
        /* 별명이 바뀐경우 */
        else {
            /* 이름중복검사 */
            if(userService.checkUsernameDuplication(name)){
                return 400L;
            }
            /* 프로필도 바뀌었는지 */
            if( multipartFile != null && !multipartFile.isEmpty() ){
                userService.profileUpdateInSetting(sessionUser.getId(),awsS3Service.uploadImage(multipartFile));
            }
            if(isImgDelete) {
                userService.profileUpdateInSetting(sessionUser.getId(),"/img/userIcon.png");
            }
            User user = userService.nameUpdateInSetting(sessionUser.getId(), name);
            httpSession.setAttribute("user", new SessionUser(user));
        }
        return 200L;
    }

}
