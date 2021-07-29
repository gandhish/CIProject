import http from "k6/http";
import { Rate } from 'k6/metrics';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: "6s", target: 10 },
        { duration: "48s", target: 10 },
        { duration: "6s", target: 0 }
    ],
    summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(95)', 'p(99)'],
    thresholds: { http_req_duration: ["p(99)<5000"],
        SuccessRate:["rate<10"]
    }
}

var printed = false;
var successRate = new Rate('SuccessRate');
export default function main() {
    let response;
    if(!printed){
        console.log("URL: "+ `http://${__ENV.QSR__API_BASEURL}/healthcheck`);
        printed = true;
    } 
    response = http.get(`http://${__ENV.QSR__API_BASEURL}/healthcheck`);

    check(response, {
        'Soak: is status 200': (r) => r.status === 200,
    });

    successRate.add(response.status === 200);

}
