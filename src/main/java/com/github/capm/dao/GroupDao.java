package com.github.capm.dao;

import com.github.capm.entity.Group;

import java.util.List;

public interface GroupDao {

    List<Group> getGroupByName(String groupName);

    Group getGroupById(int id);

}
