package kr.co.hconnect.vo;

import kr.co.hconnect.common.VoValidationGroups;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
/**
 * 몸운동상태 조회
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HealthSearchVO implements Serializable {

    private String loginId;

    private String requestDate;
}
