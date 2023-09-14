package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PatientLocationInfoRequestVO {

    /**
     * 격리/입소내역ID
     */
    @NotNull(message = "{validation.null.admissionId}")
    private String admissionId;
    /**
     * 측정일자
     */
    @NotNull(message = "{validation.null.resultDate}")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate resultDate;

}
