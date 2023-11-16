package com.aarhankhan.redditapi.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRedditRequest {
    private String name;
    private String type;
}
