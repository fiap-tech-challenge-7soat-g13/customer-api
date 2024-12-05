FROM maven:3-eclipse-temurin-21-alpine AS build
WORKDIR /app

ARG RDS_COMBINED_CA_BUNDLE_FILE=/tmp/rds-combined-ca-bundle.pem

# install ssl documentdb dependencies
#RUN apk add wget
#RUN wget https://s3.amazonaws.com/rds-downloads/rds-combined-ca-bundle.pem
RUN apk add openssl perl
RUN apk add curl openssl perl; \
  curl -sS "https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem" > $RDS_COMBINED_CA_BUNDLE_FILE; \
  awk 'split_after == 1 {n++;split_after=0} /-----END CERTIFICATE-----/ {split_after=1}{print > "rds-cert-" n ".pem"}' < $RDS_COMBINED_CA_BUNDLE_FILE; \
  for cert_file in rds-cert-*; do \
    cert_alias=$(openssl x509 -noout -text -in $cert_file | perl -ne 'next unless /Subject:/; s/.*(CN=|CN = )//; print'); \
    echo "Importing $cert_alias"; \
    keytool -importcert -file $cert_file -alias "$cert_alias" -cacerts -storepass changeit -noprompt; \
    rm $cert_file; \
  done; \
  rm $RDS_COMBINED_CA_BUNDLE_FILE;

COPY . .
RUN mvn clean package -DskipTests

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD [ "java", "-jar", "app.jar" ]