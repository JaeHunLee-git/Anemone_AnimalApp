package com.sds.animalapp.model.volunteer;

import java.util.List;

import com.sds.animalapp.domain.VolunteerApplication;
import com.sds.animalapp.domain.VolunteerNotice;

public interface VolunteerApplicationService {
    void apply(VolunteerApplication application);
    void cancel(int noticeId, int member_idx);
    int getRecordNum(int noticeId, int member_idx);
    List<VolunteerApplication> getAllApplications();
    List<VolunteerApplication> getApplicationsByMemberIdx(int memberIdx);
    VolunteerApplication findById(int id); // 추가된 메서드
}