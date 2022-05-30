package kr.co.hconnect.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 저장 완료 정보
 * @param <T>
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class SaveCompletedVO<T, R> implements Serializable {

    private static final long serialVersionUID = 4579983023673705041L;

    private T data;

    private List<R> list;
}
