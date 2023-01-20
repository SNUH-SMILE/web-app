package kr.co.hconnect.vo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 화상상담 영상 다운로드
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeleReqArchiveDownVO implements Serializable {

    private static final long serialVersionUID = -7720951042995744407L;

    /**
     * 입소아이디
     */
    private String admissionId;

    /**
     * 환자아이디
     */
    private String patientId;


    /**
     * 화상상담 일자 - 당일자
     */
    private LocalDate cDate;

}
