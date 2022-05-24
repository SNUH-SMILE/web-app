package kr.co.hconnect.rest;

import kr.co.hconnect.service.TreatmentCenterService;
import kr.co.hconnect.vo.TreatmentCenterVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/treatmentCenter")
public class TreatmentCenterRestController {

    /**
     * 생활치료센터 Service
     */
    private final TreatmentCenterService treatmentCenterService;

    /**
     * 생성자
     *
     * @param treatmentCenterService 생활치료센터 Service
     */
    public TreatmentCenterRestController(TreatmentCenterService treatmentCenterService) {
        this.treatmentCenterService = treatmentCenterService;
    }

    /**
     * 생활치료센터 리스트 조회
     *
     * @return List&lt;TreatmentCenterVO&gt;
     */
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<TreatmentCenterVO> selectTreatmentCenterList() {
        return treatmentCenterService.selectTreatmentCenterList();
    }
}
