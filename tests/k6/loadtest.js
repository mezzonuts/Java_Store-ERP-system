import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '2m', target: 100 },
    { duration: '5m', target: 1000 },
    { duration: '2m', target: 500 },
    { duration: '1m', target: 0 },
  ],
  thresholds: {
    http_req_duration: ['p(95)<200'],
    http_req_failed: ['<0.1'],
  },
};

export default function () {
  // Test POS checkout endpoint
  const checkoutPayload = JSON.stringify({
    tenantId: 'tenant-1',
    branchId: 'branch-1',
    idempotencyKey: `order-${__VU}-${__ITER}`,
    items: [
      { productId: 'prod-1', qty: 2, unitPrice: 25000 },
      { productId: 'prod-2', qty: 1, unitPrice: 50000 },
    ],
  });

  const checkoutRes = http.post('http://localhost:8080/api/v1/sales/checkout', checkoutPayload, {
    headers: { 'Content-Type': 'application/json' },
  });

  check(checkoutRes, {
    'checkout status 200': (r) => r.status === 200 || r.status === 201,
    'checkout latency <200ms': (r) => r.timings.duration < 200,
  });

  // Test product search
  const searchRes = http.get('http://localhost:8080/api/v1/products?search=kopi&limit=50', {
    headers: { Authorization: 'Bearer mock-jwt' },
  });

  check(searchRes, {
    'search status 200': (r) => r.status === 200,
    'search latency <100ms': (r) => r.timings.duration < 100,
  });

  // Test stock check
  const stockRes = http.get('http://localhost:8080/api/v1/inventory/stock?productId=prod-1&warehouseId=wh-1');

  check(stockRes, {
    'stock status 200': (r) => r.status === 200,
  });

  sleep(1);
}
