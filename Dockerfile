FROM alpine:3.18

ARG version=21.0.5.11.1

# Please note that the THIRD-PARTY-LICENSE could be out of date if the base image has been updated recently.
# The Corretto team will update this file but you may see a few days' delay.
RUN wget -O /THIRD-PARTY-LICENSES-20200824.tar.gz https://corretto.aws/downloads/resources/licenses/alpine/THIRD-PARTY-LICENSES-20200824.tar.gz && \
    echo "82f3e50e71b2aee21321b2b33de372feed5befad6ef2196ddec92311bc09becb  /THIRD-PARTY-LICENSES-20200824.tar.gz" | sha256sum -c - && \
    tar x -ovzf THIRD-PARTY-LICENSES-20200824.tar.gz && \
    rm -rf THIRD-PARTY-LICENSES-20200824.tar.gz && \
    wget -O /etc/apk/keys/amazoncorretto.rsa.pub https://apk.corretto.aws/amazoncorretto.rsa.pub && \
    SHA_SUM="6cfdf08be09f32ca298e2d5bd4a359ee2b275765c09b56d514624bf831eafb91" && \
    echo "${SHA_SUM}  /etc/apk/keys/amazoncorretto.rsa.pub" | sha256sum -c - && \
    echo "https://apk.corretto.aws" >> /etc/apk/repositories && \
    apk add --no-cache amazon-corretto-21=$version-r0 && \
    rm -rf /usr/lib/jvm/java-21-amazon-corretto/lib/src.zip


ENV LANG=C.UTF-8

ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH=$PATH:/usr/lib/jvm/default-jvm/bin

WORKDIR /app

ARG RDS_COMBINED_CA_BUNDLE_FILE=/tmp/rds-combined-ca-bundle.pem

# install ssl documentdb dependencies
#RUN yum -y install wget
#RUN wget https://s3.amazonaws.com/rds-downloads/rds-combined-ca-bundle.pem
RUN yum -y install openssl perl
RUN yum -y install curl openssl perl; \
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
ADD /app/target/*.jar app.jar

CMD [ "java", "-jar", "app.jar" ]