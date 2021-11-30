package kr.co.hconnect.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;

/**
 * 격리상태
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QantnStatus implements Serializable {

    private static final long serialVersionUID = 4709837972628367928L;

    /**
     * 격리상태순번
     */
    private long qantnStatusSeq;
    /**
     * 격리/입소내역ID
     */
    private String admissionId;
    /**
     * 격리상태구분 - CD006
     */
    private String qantnStatusDiv;
    /**
     * 격리상태명
     */
    private String qantnStatusNm;

}
