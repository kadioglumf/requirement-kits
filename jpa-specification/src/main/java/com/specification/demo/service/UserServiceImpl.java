package com.specification.demo.service;

import com.specification.demo.model.User;
import com.specification.demo.payload.search.SearchRequest;
import com.specification.demo.repository.UserRepository;
import com.specification.demo.service.search.SearchExpressionType;
import com.specification.demo.service.search.SearchRoleType;
import com.specification.demo.service.search.SearchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<User> getUserList(SearchRequest request) {
        SearchSpecification<User> specification = new SearchSpecification<>(request, SearchExpressionType.YOUR_CATEGORY_NAME, SearchRoleType.PUBLIC);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return userRepository.findAll(specification, pageable);
    }
}
