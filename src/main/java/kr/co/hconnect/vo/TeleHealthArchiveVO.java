package kr.co.hconnect.vo;

import com.opentok.Archive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 화상상담 저장
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class TeleHealthArchiveVO implements Serializable {

   private String archiveId;

   private Date createAt;

   private Integer partnerId;

   private String name;

   private String sessionId;

   private Long size;

   private String status;

   private String outputMode;

   private String reason;



}

