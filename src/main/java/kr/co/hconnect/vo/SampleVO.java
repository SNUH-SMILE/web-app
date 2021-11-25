package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SampleVO {

    private String field1;
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate field2;
    @JsonFormat(pattern = "HHmmss")
    private LocalTime field3;

}
