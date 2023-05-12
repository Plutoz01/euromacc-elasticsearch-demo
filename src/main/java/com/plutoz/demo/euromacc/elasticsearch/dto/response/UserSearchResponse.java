package com.plutoz.demo.euromacc.elasticsearch.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
// TODO: check: make fields final and convert to record
public class UserSearchResponse {
    private Long total;
    private List<UserResponse> userList;
}
