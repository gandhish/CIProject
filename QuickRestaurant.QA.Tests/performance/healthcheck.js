import http from "k6/http";
import { Rate } from 'k6/metrics';
import { check } from 'k6';

var successRate = new Rate('SuccessRate');
export default function main() {
    let response;

    response = http.get("http://coreapi:80/HealthCheck");

    check(response, {
        'health check: is status 200': (r) => r.status === 200,
    });

    successRate.add(response.status === 200);

}
