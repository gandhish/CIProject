import http from "k6/http";
import { Rate } from 'k6/metrics';
import { check } from 'k6';

export const options = {
    stages: [
        { duration: "12s", target: 5 },
        { duration: "12s", target: 5 },
        { duration: "12s", target: 10 },
        { duration: "12s", target: 10 },
        { duration: "12s", target: 0 },
      ],
      thresholds: { http_req_duration: ["avg<10"],
      SuccessRate:["rate<10"],
      http_req_failed: [{ threshold: "rate<10", abortOnFail: true }], 
  },
}

var printed = false;
var successRate = new Rate('SuccessRate');
export default function main() {
    let response;
    if(!printed){
        console.log("URL: "+ `http://${__ENV.HOSTNAME}/locations`);
        printed = true;
    } 
    response = http.get(`http://${__ENV.HOSTNAME}/locations`);

    check(response, {
        'Stress: is status 200': (r) => r.status === 200,
    });

    successRate.add(response.status === 200);

}
