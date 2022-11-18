FROM wiremock/wiremock:2.35.0

COPY --chown=185 mappings/* /home/wiremock/mappings/

EXPOSE 8090
USER 185

ENTRYPOINT ["/docker-entrypoint.sh", "--port=8090",  "--verbose"]