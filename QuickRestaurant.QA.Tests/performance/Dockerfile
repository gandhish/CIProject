FROM loadimpact/k6:latest
COPY . .
ENTRYPOINT []
CMD ["sh", "-c", "printf '\nPerformance Test for http://localhost:5001/HealthCheck';\
                  k6 run healthcheck.js --vus 10  --duration 10s;\
                  printf '\nPerformance Tests for: http://localhost:5002/api/v1/locations';\
                  k6 run -e HOSTNAME=adminui:80/api/v1 GetLocations/LoadTest.js;\
                  k6 run -e HOSTNAME=adminui:80/api/v1 GetLocations/SoakTest.js;\
                  k6 run -e HOSTNAME=adminui:80/api/v1 GetLocations/SpikeTest.js;\
                  k6 run -e HOSTNAME=adminui:80/api/v1 GetLocations/StressTest.js;\
                  printf '\nPerformance Tests for: http://localhost:5001/locations';\
                  k6 run -e HOSTNAME=coreapi:80 GetLocations/LoadTest.js;\
                  k6 run -e HOSTNAME=coreapi:80 GetLocations/SoakTest.js;\
                  k6 run -e HOSTNAME=coreapi:80 GetLocations/SpikeTest.js;\
                  k6 run -e HOSTNAME=coreapi:80 GetLocations/StressTest.js"]