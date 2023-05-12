package com.plutoz.demo.euromacc.elasticsearch.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserSearchResponse {
    private Long total;
    private List<UserResponse> userList;
}
