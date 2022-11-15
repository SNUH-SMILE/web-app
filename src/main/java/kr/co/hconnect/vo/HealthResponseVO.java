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
 * 몸운동상태 저장
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HealthResponseVO implements Serializable{

    private static final long serialVersionUID = 4853998108152210877L;

    private String status;
}
