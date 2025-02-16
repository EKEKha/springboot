package com.example.jwtmember.controller.user;


import com.example.jwtmember.dto.JoinDTO;
import com.example.jwtmember.response.ApiResultDTO;
import com.example.jwtmember.response.ResultCode;
import com.example.jwtmember.service.UserJoinService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserJoinController {

    private final UserJoinService userJoinService;
    @PostMapping("/join")
    public ResponseEntity<ApiResultDTO> join(JoinDTO joinDTO){

        System.out.println(joinDTO.getLoginId());
        userJoinService.joinProcess(joinDTO);

        ApiResultDTO apiResultDTO= ApiResultDTO
                .builder()
                .message("회원 가입 성공")
                .status(ResultCode.SUCCESS)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(apiResultDTO);

    }

}
