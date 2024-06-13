package com.sds.animalapp.model.volunteer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sds.animalapp.domain.VolunteerApplication;

@Service
public class VolunteerApplicationServiceImpl implements VolunteerApplicationService {

    @Autowired
    private VolunteerApplicationDAO volunteerApplicationDAO;

    @Override
    public void apply(VolunteerApplication application) {
        volunteerApplicationDAO.insert(application);
    }

    @Override
    public void cancel(int id) {
        volunteerApplicationDAO.delete(id);
    }

    @Override
    public List<VolunteerApplication> getAllApplications() {
        return volunteerApplicationDAO.findAll();
    }

    @Override
    public List<VolunteerApplication> getApplicationsByMemberIdx(int memberIdx) {
        return volunteerApplicationDAO.findByMemberIdx(memberIdx);
    }

    @Override
    public VolunteerApplication findById(int id) {
        return volunteerApplicationDAO.findApplicationById(id);
    }
}
