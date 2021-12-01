package kr.co.hconnect.common;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Spring의 BeanUtils 클래스 기능 테스트
 */
public class SpringBeanUtilsTest {

    /**
     * BeanUtils.copyProperties(Object, Object) 메서드 테스트
     */
    @Test
    public void copyPropertiesTest1() {
        SamplePojo source = new SamplePojo();
        source.setField1("field1");
        source.setField2(10);
        source.setField3(LocalDate.now());
        source.setField4(LocalTime.now());
        source.setField5(LocalDateTime.now());

        SamplePojo target = new SamplePojo();
        BeanUtils.copyProperties(source, target);

        assertEquals(source.getField1(), target.getField1());
        assertEquals(source.getField2(), target.getField2());
        assertEquals(source.getField3(), target.getField3());
        assertEquals(source.getField4(), target.getField4());
        assertEquals(source.getField5(), target.getField5());
    }

    /**
     * BeanUtils.copyProperties(Object, Object, String...) 메서드 테스트
     */
    @Test
    public void copyPropertiesTest2() {
        SamplePojo source = new SamplePojo();
        source.setField1("field1");
        source.setField2(10);
        source.setField3(LocalDate.now());
        source.setField4(LocalTime.now());
        source.setField5(LocalDateTime.now());

        SamplePojo target = new SamplePojo();
        BeanUtils.copyProperties(source, target, "field3", "field4");

        assertEquals(source.getField1(), target.getField1());
        assertEquals(source.getField2(), target.getField2());
        assertNull(target.getField3());
        assertNull(target.getField4());
        assertEquals(source.getField5(), target.getField5());
    }

    /**
     * 샘플 POJO
     */
    private static class SamplePojo {
        private String field1;
        private int field2;
        private LocalDate field3;
        private LocalTime field4;
        private LocalDateTime field5;

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public int getField2() {
            return field2;
        }

        public void setField2(int field2) {
            this.field2 = field2;
        }

        public LocalDate getField3() {
            return field3;
        }

        public void setField3(LocalDate field3) {
            this.field3 = field3;
        }

        public LocalTime getField4() {
            return field4;
        }

        public void setField4(LocalTime field4) {
            this.field4 = field4;
        }

        public LocalDateTime getField5() {
            return field5;
        }

        public void setField5(LocalDateTime field5) {
            this.field5 = field5;
        }
    }

}
