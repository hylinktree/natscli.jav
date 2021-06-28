FROM gdca/basenp
ENV PORT 80
ENV BASEDIR /opt/npmon

WORKDIR ${BASEDIR}
# COPY ./bin/ethr /usr/bin/
# COPY ./bin/iperf3_3.9-1_amd64.deb ./
# COPY ./bin/libiperf0_3.9-1_amd64.deb ./
# RUN apt remove -y iperf3 libiperf0 && apt install -y libsctp1 && dpkg -i libiperf0_3.9-1_amd64.deb iperf3_3.9-1_amd64.deb
# CMD java -Dspring.config.location=./settings/ -Xms64m -Xmx2048m -XX:ThreadStackSize=512 -XX:MaxPermSize=256 -jar "baso.jar"

COPY target/*.jar npmon.jar
CMD java -Xms64m -Xmx2048m -XX:ThreadStackSize=512 -jar "npmon.jar"
