package oth.ics.wtp.taDa_backend.dtos;

import java.util.List;

public record AppUserDto(String name, String fullName, String email, String biography, List<PostBriefDto> posts,
                         List<AppUserBriefDto> followers, List<AppUserBriefDto> followings) {
}
