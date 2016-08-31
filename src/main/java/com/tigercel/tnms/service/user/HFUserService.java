package com.tigercel.tnms.service.user;

import com.tigercel.tnms.dto.PageBean;
import com.tigercel.tnms.model.HFUser;
import com.tigercel.tnms.repository.user.HFUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by somebody on 2016/8/30.
 */
@Service
public class HFUserService {

    @Autowired
    private HFUserRepository hfUserRepository;

    public Page<HFUser> findAllByName(PageBean pb, String name) {

        return hfUserRepository.findAllByName(name,
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                ));
    }


    public Page<HFUser> findAll(PageBean pb) {
        return hfUserRepository.findAll(
                new PageRequest(pb.getPage() - 1,
                        pb.getSize(),
                        Sort.Direction.valueOf(pb.getSortOrder().toUpperCase()),
                        pb.getSortName()
                )
        );
    }


    public HFUser findOneByName(String name) {
        return hfUserRepository.findByName(name);
    }

    public HFUser findOne(Long id) {
        return hfUserRepository.findOne(id);
    }
}
