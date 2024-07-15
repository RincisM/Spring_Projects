package com.training.database.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequest {
    private String userName;
    private String postContent;
}
