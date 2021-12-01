package kr.co.hconnect.vo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SampleVO {

    @JsonProperty("f1")
    private String field1;

    @JsonProperty("f2")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate field2;

    @JsonProperty("f3")
    @JsonFormat(pattern = "HHmmss")
    private LocalTime field3;

}
