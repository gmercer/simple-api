package com.github.capm.service;

import com.github.capm.entity.Group;
import com.github.capm.entity.User;

import java.util.List;

public interface GroupService {

    List<Group> getGroupByName(String groupName);

    Group getGroupById(int id);

    List<Group> findAllGroups();

}