package com.github.capm.service;

import com.github.capm.dao.GroupDao;
import com.github.capm.entity.Group;

import javax.annotation.Resource;
import java.util.List;

public class GroupServiceImpl implements GroupService {

    @Resource
    GroupDao groupDao;

    @Override
    public List<Group> getGroupByName(String groupName) {
        return  groupDao.getGroupByName(groupName);
    }

    @Override
    public Group getGroupById(int id) {
        return null;
    }
}
