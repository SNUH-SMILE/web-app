package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class NotBlankSampleVO {

    @NotBlank(message = "{validation.null.centerId}")
    private String code;

    @NotBlank(message = "{validation.null.centerNm}")
    private String name;

}
