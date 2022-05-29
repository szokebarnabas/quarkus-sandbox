import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.mutiny.ext.web.client.predicate.ResponsePredicate;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TwitterClient {

    private final ObjectMapper objectMapper;
    private WebClient webClient;

    @Inject
    public TwitterClient(Vertx vertx) {
        this.webClient = WebClient.create(vertx);
        this.objectMapper = new ObjectMapper();
    }

    public Uni<FollowersResponse> getFollowers() {
        return webClient.get(8090, "localhost", "/api/followers")
                .send()
                .log()
                .onItem()
                .transform(bufferHttpResponse -> bufferHttpResponse.bodyAsJson(FollowersResponse.class));
    }

    public Uni<FollowingResponse> getFollowing() {
        return webClient.get(8090, "localhost", "/api/following")
                .send()
                .log()
                .onItem()
                .transform(bufferHttpResponse -> bufferHttpResponse.bodyAsJson(FollowingResponse.class));
    }

    public Uni<List<Tweet>> getMyTweets() {

        return webClient.get(8090, "localhost", "/api/my-tweets")
                .expect(ResponsePredicate.JSON)
                .expect(ResponsePredicate.SC_OK)
                .send()
                .log()
                .onItem()
                .transform(Unchecked.function(bufferHttpResponse -> {
                    final String json = bufferHttpResponse.bodyAsString();
                    return objectMapper.readValue(json, new TypeReference<>() {});
                }));
    }
}
