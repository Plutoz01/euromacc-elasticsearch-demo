package com.plutoz.demo.euromacc.elasticsearch.dto.response;

import java.util.List;

public record ErrorResponse(List<String> errors) {
}
