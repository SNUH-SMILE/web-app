package kr.co.hconnect.vo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 혈압 구성정보
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class BatchVO implements Serializable {

    private String loginId;

    private String archiveId;

    private String archiveUrl;


    private String filePath;

    private String outFilePath;

    private int count;

    private String cDate;

}
