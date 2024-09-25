package com.gdyd.gdydsupport.ai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${ai.filter.name}", url = "${ai.filter.endpoint}")
public interface AIFilteringClient {
    @PostMapping
    AbuseFilteringResponse sendAbuseFilteringRequest(@RequestBody AbuseFilteringRequest request);
}
