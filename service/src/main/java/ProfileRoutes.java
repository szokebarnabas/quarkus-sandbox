import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ApplicationScoped
public class ProfileRoutes {

    private final ProfileService profileService;

    @Inject
    public ProfileRoutes(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Route(path = "/api/profile/:profileId", methods = Route.HttpMethod.GET)
    public Uni<ProfileResponse> handle(RoutingContext rc) {
        return profileService.getProfile()
                .onFailure()
                .retry()
                .withBackOff(Duration.of(5, ChronoUnit.SECONDS))
                .atMost(3);
    }
}
