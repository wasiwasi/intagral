package com.a304.intagral.db.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FollowingHashtagGetDto implements FollowListBase{

    String nickname;
    Integer followerCnt;
    @JsonProperty("isFollow")
    boolean isFollow;
}
