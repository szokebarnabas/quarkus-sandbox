import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniAndGroup3;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;


@ApplicationScoped
public class ProfileService {

    private TwitterClient twitterClient;

    @Inject
    public ProfileService(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    public Uni<ProfileResponse> getProfile() {
        final Uni<FollowersResponse> followers = twitterClient.getFollowers()
                .onFailure()
                .recoverWithItem(() -> new FollowersResponse(0));

        final Uni<FollowingResponse> following = twitterClient.getFollowing()
                .onFailure()
                .recoverWithItem(() -> new FollowingResponse(0));

        final Uni<List<Tweet>> myTweets = twitterClient.getMyTweets();

        final UniAndGroup3<FollowersResponse, FollowingResponse, List<Tweet>> unis = Uni.combine()
                .all()
                .unis(followers, following, myTweets);

        return unis.combinedWith((follower, following1, tweets) ->
                new ProfileResponse(follower.followers(), following1.following(), tweets));

    }
}
