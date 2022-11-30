package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 신규 알린 저장 정보 VO
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RecordSaveVO implements Serializable {


    /**
     * 격리/입소내역ID
     */
    @NotBlank(message = "{validation.null.admissionId}")
    private String admissionId;
    /**
     * 내용
     */

    private String record;
    /**
     * 진료기록자
     */
    private String medicalRecorder;
    /**
     * 진료기록일
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date medicalDate;





}
