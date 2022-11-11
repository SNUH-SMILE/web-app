package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 공지사항 조회
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NoticeSupportSearchVO implements Serializable {

    private static final long serialVersionUID = -3905253603729726578L;

    private String LoginId;

    private String admisstionId;
}
