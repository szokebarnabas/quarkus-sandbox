import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

record FollowersResponse(@JsonAlias("num_of_followers") int followers) { }
record FollowingResponse(@JsonAlias("num_of_following") int following) {}
record Tweet(String user, @JsonAlias("is_retweet") boolean isRetweet, String message) {}
record MyTweetsResponse(List<Tweet> tweets) {}
public record ProfileResponse(int followers, int following, List<Tweet> tweets) {}
