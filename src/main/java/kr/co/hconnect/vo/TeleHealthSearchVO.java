package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
/**
 * 화상상담 연결
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeleHealthSearchVO implements Serializable{

    private String loginId;

}
