package com.specification.demo.service;

import com.specification.demo.model.User;
import com.specification.demo.payload.search.SearchRequest;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<User> getUserList(SearchRequest request);
}
