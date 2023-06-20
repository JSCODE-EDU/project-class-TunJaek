package com.example.jscode.dto;

import com.example.jscode.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
public class MemberRequestDTO {

    @NotBlank(message = "비밀번호에는 공백이 포함될 수 없습니다.")
    @Size(min = 8,max = 15,message = "비밀번호는 8자이상 15장 이하로 작성해야 합니다.")
    private String password;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 공백을 포함할 수 없습니다.")
    private String email;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
    private LocalDateTime joinTime;

    @Builder
    public MemberRequestDTO(String password, String email, LocalDateTime joinTime){
        this.password=password;
        this.email=email;
        this.joinTime=joinTime;
    }
    public Member toEntity(){
        return Member.builder().password(password).email(email).joinTime(joinTime).build();
    }
}
