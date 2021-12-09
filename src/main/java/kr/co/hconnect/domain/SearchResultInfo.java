package kr.co.hconnect.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 측정결과 검색 조건
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchResultInfo implements Serializable {

    private static final long serialVersionUID = 5219098809479607333L;

    /**
     * 아이디
     */
    @NotNull(message = "{validation.null.loginId}")
    private String loginId;
    /**
     * 측정일자
     */
    @NotNull(message = "{validation.null.resultDate}")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate resultDate;
    /**
     * 입소자 Id
     */
    private String admissionId;
    /**
     * 측정항목 Id
     */
    private String itemId;

}
