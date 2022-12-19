package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.hconnect.common.BaseDefaultVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 환자 복약 알림
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DrugTimeListVO implements Serializable {

    private static final long serialVersionUID = -2180618354311714578L;

    private List<DrugTimeVO> drugTimeList;


}
